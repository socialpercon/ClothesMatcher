package rice.clothesmatchingapplication;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
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
import android.widget.TextView;

public class OldEntryHome extends Activity {

public static OldEntryHome instance;
	
	public OldEntryHome() {
		instance = this;
	}
	
	public static Context getContext(){
		return instance;
	}
	
	private final String LOG_TAG = getClass().getSimpleName();
	private DatabaseHelper databaseHelper = null;
	public final String TAG = "OldEntryHome";
	public ImageAdapter imageAdapter; 
	public String filePath;
	public static final String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	public static final String EXTRA_MESSAGE3 = "rice.clothesmatchingapplication.MESSAGE3";
	
	private DatabaseHelperM databaseHelperM = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_old_entry_home);
		
		LinearLayout myGallery;
		List<MatchesData> dataList;
		ArrayList<String> filePathList;
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		filePath = bundle.getString(EXTRA_MESSAGE);
		loadIntoImageView(filePath);
		
	    dataList = checkDatabaseType();
		filePathList = new ArrayList<String>(dataList.size());
		
		for (MatchesData data: dataList){
			String filePath = data.type2;
			Log.d("filePath", filePath);
			filePathList.add(filePath);
		}
		

		myGallery = (LinearLayout) findViewById(R.id.mygallery2);
		
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
	    
		ImageView imageView2 = new ImageView(getApplicationContext());
		imageView2.setImageBitmap(bm);
		imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView2.setLayoutParams(new GridView.LayoutParams(250, 250));
		
		layout.addView(imageView2);
		return layout;
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<MatchesData> checkDatabaseType(){
		try {
			Dao<MatchesData, Integer> matchesDao = getHelperM().getMatchesDataDao();
			QueryBuilder<MatchesData,Integer> queryBuilder = matchesDao.queryBuilder();
			queryBuilder.where().eq("type1", filePath);
			PreparedQuery<MatchesData> preparedQuery = queryBuilder.prepare();
			List<MatchesData> dataList = matchesDao.query(preparedQuery);
			return dataList;
		} catch (SQLException e) {
			
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
	

	public void deleteEntry(View v){
		try{
		Intent move = new Intent(this, EditHome1.class);
		Dao<MatchesData, Integer> matchesDao = getHelperM().getMatchesDataDao();
		DeleteBuilder<MatchesData,Integer> deleteBuilder = matchesDao.deleteBuilder();
		deleteBuilder.where().eq("type1", filePath).or().eq("type2", filePath);
		deleteBuilder.delete();
		Dao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
		DeleteBuilder<SimpleData,Integer> deleteBuilderType = simpleDao.deleteBuilder();
		deleteBuilderType.where().eq("name", filePath);
		deleteBuilderType.delete();
		
		File file = new File(filePath);
		file.delete();
		startActivity(move);
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private DatabaseHelperM getHelperM(){
		if(databaseHelperM == null){
			databaseHelperM = DatabaseHelperM.getHelper(this);
			
		}
		return databaseHelperM;
	}
	
	private DatabaseHelper getHelper(){
		if(databaseHelper == null){
			databaseHelper = DatabaseHelper.getHelper(this);
		}
		return databaseHelper;
	}
	
	protected void onDestroy(){
		super.onDestroy();
		if(databaseHelperM==null){
			databaseHelperM.close();
			databaseHelperM=null;
		}
		
		if(databaseHelper==null){
			databaseHelper.close();
			databaseHelper=null;
		}
	}

	
	public void moveToMatchingHome(View view){
		Intent move = new Intent(this, MatchingHome.class);
		move.putExtra(EXTRA_MESSAGE, filePath);
		move.putExtra(EXTRA_MESSAGE3, "old");
		startActivity(move);
	}
	
	public void moveToExpertHome1(View view){
		Intent move = new Intent(this, ExpertHome1.class);
		startActivity(move);
		
	}
	

	public void moveToDeleteHome(View view){
		Intent move = new Intent(this, DeleteHome.class);
		move.putExtra(EXTRA_MESSAGE, filePath);
		move.putExtra(EXTRA_MESSAGE3, "old");
		startActivity(move);
	}

	private static int exifToDegrees(int exifOrientation) {        
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
	
	
	public static Bitmap decodeBitmap(String names, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(names,options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(names, options);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth){

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

}



