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
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImagesHome extends Activity {
	
	private DatabaseHelperM databaseHelper = null;
	public static ImagesHome instance;
	public ImageAdapter imageAdapter; 
	public String category;
	public String original_file;
	public String match_file;
	public static final String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	public static final String EXTRA_MESSAGE2 = "rice.clothesmatchingapplication.MESSAGE2";
	
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
//		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
		//ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		//imageView.setImageBitmap(bitmap);
		
		Bundle bundle = intent.getExtras();
		original_file = bundle.getString(EXTRA_MESSAGE);
		match_file = bundle.getString(EXTRA_MESSAGE2);
		
		loadIntoImageView(original_file);
		dataList = checkDatabaseType();
		filePathList = new ArrayList<String>(dataList.size());
				
		for (MatchesData data: dataList){
			String filePath = data.type2;
			Log.d("filePath", filePath);
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
		
	public void loadIntoImageView(String path){
		try {
		Log.d("Path", "Path: " + path);
		Bitmap bitmap = decodeBitmap(path,250,250);
		
		int pictureRotation;
		
			pictureRotation = getPictureRotation(path);
		Matrix matrix = new Matrix();
		matrix.postRotate(pictureRotation);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(bitmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		if(databaseHelper == null){
			databaseHelper = DatabaseHelperM.getHelper(this);
			
		}
		return databaseHelper;
	}
	
//replace with querying for specific matches
	public List<MatchesData> checkDatabaseType(){
		try {
			Dao<MatchesData, Integer> matchesDao = getHelper().getMatchesDataDao();
			QueryBuilder<MatchesData,Integer> queryBuilder = matchesDao.queryBuilder();
			queryBuilder.where().eq("type1", original_file);
			PreparedQuery<MatchesData> preparedQuery = queryBuilder.prepare();
			List<MatchesData> dataList = matchesDao.query(preparedQuery);
			return dataList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;

}
	
}