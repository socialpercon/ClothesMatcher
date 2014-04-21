package rice.clothesmatchingapplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.media.ExifInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImagesHome extends Activity {
	
	private DatabaseHelperM databaseHelper = null;
	public static ImagesHome instance;
//	public ImageAdapter imageAdapter; 
	public String original_file;
	public String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	//need to get original filepath
	public ImagesHome() {
		instance = this;
	}
	
	public static Context getContext(){
		return instance;
	}
	LinearLayout myGallery;
	List<MatchesData> dataList;
	ArrayList<String> filePathList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_long_sleeve_home);
		
		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
		Bundle bundle = intent.getExtras();
		original_file = bundle.getString(EXTRA_MESSAGE);
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(bitmap);
		
//		imageAdapter = new ImageAdapter(instance);	
		
		dataList = checkDatabaseType();
		filePathList = new ArrayList<String>(dataList.size());
		
		
		
		for (MatchesData data: dataList){
			//add second item in table if first item =original filepath
			String filePath = data.type2;
			Log.d("Item2", filePath);
			filePathList.add(filePath);
		}
		

		myGallery = (LinearLayout) findViewById(R.id.myGallery);
		
		for (int i=0; i< filePathList.size(); i++){
		myGallery.addView(insertPhoto(filePathList.get(i))); 
		}
	}
		
	View insertPhoto(String path){
		try {
			
		Bitmap bm = decodeBitmap(path, 220, 220);
	   	int pictureRotation;
		pictureRotation = getPictureRotation(path);
		
		Matrix matrix = new Matrix();
		matrix.postRotate(pictureRotation);
		bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
		 
		
	    LinearLayout layout = new LinearLayout(getApplicationContext());
	    layout.setLayoutParams(new LayoutParams(250, 250));
	    layout.setGravity(Gravity.CENTER);
	    
		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setImageBitmap(bm);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
		
		layout.addView(imageView);
		return layout;
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
		
		
	private int exifToDegrees(int exifOrientation) {        
	    if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; } 
	    else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; } 
	    else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }            
	    return 0;    
	 }
	
	public int getPictureRotation(String path) throws IOException{
		ExifInterface exif = new ExifInterface(path);
    	int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    	int rotationInDegrees = exifToDegrees(rotation);
    	return rotationInDegrees;
	}
	
	public Bitmap decodeBitmap(String names, int reqWidth, int reqHeight) {
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(names,options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(names, options);
	}

	public int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth){

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		
		if (height>reqHeight || width > reqWidth){
			final int halfHeight = height/2;
			final int halfWidth = width/2;
			
			while ((halfHeight/inSampleSize)>reqHeight && (halfWidth/inSampleSize)>reqWidth){
				inSampleSize*=2;
			}
		}
		
		return inSampleSize;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.long_sleeve_home, menu);
		return true;
	}


	protected void onDestroy(){
		super.onDestroy();
		if(databaseHelper!=null){
			databaseHelper.close();
			databaseHelper = null;
		}
	}

	private DatabaseHelperM getHelper(){
		if (databaseHelper == null){
			databaseHelper = DatabaseHelperM.getHelper(this);
		}
		return databaseHelper;
	}
//replace with querying for specific matches
	public List<MatchesData> checkDatabaseType(){
		try {
			Dao<MatchesData, Integer> matchDao = getHelper().getMatchesDataDao();
			QueryBuilder<MatchesData,Integer> queryBuilder = matchDao.queryBuilder();
			
			queryBuilder.where().eq("type", original_file);
//			if (category.equals("Long Sleeve Shirts")){
//			queryBuilder.where().eq("type", "Long Sleeve Shirts");
//			}
//			if (category.equals("Short Sleeve Shirts")){
//				queryBuilder.where().eq("type", "Short Sleeve Shirts");
//			}
//				
//			if (category.equals("Pants")){
//				queryBuilder.where().eq("type", "Pants");
//			}
//				
//			if (category.equals("Shorts")){
//				queryBuilder.where().eq("type", "Shorts");
//			}
			
				
			PreparedQuery<MatchesData> preparedQuery = queryBuilder.prepare();
			List<MatchesData> dataList = matchDao.query(preparedQuery);
			return dataList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;

}
	
}