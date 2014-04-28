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
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class NewEntryHome extends Activity {

	private final String LOG_TAG = getClass().getSimpleName();
	private DatabaseHelper databaseHelper = null;
	private DatabaseHelperM databaseHelperM = null;
	public static final String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	public static final String EXTRA_MESSAGE2 = "rice.clothesmatchingapplication.MESSAGE2";
	public static final String EXTRA_MESSAGE3 = "rice.clothesmatchingapplication.MESSAGE3";
	public String filePath;
	public SharedPreferences filepath; 
	public String filePathOriginal;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_entry_home);
		filepath = getSharedPreferences("filepath", MODE_PRIVATE);
		LinearLayout myGallery;
		List<MatchesData> dataList;
		ArrayList<String> filePathList;

	
		Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
		String[] items = new String[]{"Long Sleeve Shirts", "Short Sleeve Shirts", "Pants", "Shorts"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
		dropdown.setAdapter(adapter);
			
		Intent moveFrom =  getIntent();
		Bundle bundle  = moveFrom.getExtras();
			
		if (bundle.getString(EXTRA_MESSAGE).equals("picture")==false);{
			filePathOriginal = bundle.getString(EXTRA_MESSAGE);
			Log.d("FilePath", filePathOriginal);
			}
			filePath = filepath.getString("file", "");
			
			File imageFile = new File(filePath);
		if (imageFile.exists()) {
		    loadIntoImageview(filePath);
		    }
		    
		    if (filePathOriginal.equals("picture")==false){
		    dataList = checkDatabaseType();
			filePathList = new ArrayList<String>(dataList.size());
			
			for (MatchesData data: dataList){
				String filePath = data.type2;
				Log.d("filePath", filePath);
				filePathList.add(filePath);
			}
			

			myGallery = (LinearLayout) findViewById(R.id.myGallery1);
			
			for (int i=0; i< filePathList.size(); i++){
			myGallery.addView(insertPhoto(filePathList.get(i))); 
			}
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

		    
		
	
	public void moveToMatchingHome(View view){
		Intent move = new Intent(this, MatchingHome.class);
		move.putExtra(EXTRA_MESSAGE, filePath);
		move.putExtra(EXTRA_MESSAGE3, "new");
		startActivity(move);
	}
	
	public List<MatchesData> checkDatabaseType(){
		try {
			Dao<MatchesData, Integer> matchesDao = getHelperM().getMatchesDataDao();
			QueryBuilder<MatchesData,Integer> queryBuilder = matchesDao.queryBuilder();
			queryBuilder.where().eq("type1", filePathOriginal);
			PreparedQuery<MatchesData> preparedQuery = queryBuilder.prepare();
			List<MatchesData> dataList = matchesDao.query(preparedQuery);
			return dataList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;

}


	
	public void loadItemIntoDatabase(String ClothesName, String ClothesType){
		try {
			Dao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
//			Dao<MatchesData, Integer> matchDao = getHelper().getMatchesDataDao();
			
			SimpleData simple = new SimpleData(ClothesName, ClothesType);
//			MatchesData matches = new MatchesData(ClothesName, null, null);
			simpleDao.create(simple);
//			matchDao.create(matches);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	//Deleting Matches Code
	//DeleteBuilder<SimpleData, Integer> deleteBuilder = simpleDao.deleteBuilder();
	//deleteBuilder.where().eq("type", "Long Sleeve Shirts");
	//deleteBuilder.delete();

	
	private DatabaseHelperM getHelperM(){
		if(databaseHelperM == null){
			databaseHelperM = DatabaseHelperM.getHelper(this);
			
		}
		return databaseHelperM;
	}
	
	
	protected void onDestroy(){
		super.onDestroy();
		if(databaseHelper!=null){
			databaseHelper.close();
			databaseHelper = null;
		}
		
		if(databaseHelperM!=null){
			databaseHelperM.close();
			databaseHelperM=null;
		}
		
		
	}
	
	private DatabaseHelper getHelper(){
		if (databaseHelper == null){
			databaseHelper = DatabaseHelper.getHelper(this);
		}
		return databaseHelper;
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_entry_home, menu);
		return true;
	}
	
	public void moveToExpertHome(View view){
		Intent move = new Intent(this, ExpertHome1.class);
		Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
		String type = dropdown.getSelectedItem().toString();
		loadItemIntoDatabase(filePath, type);
		startActivity(move);
	}
	
	public void moveToDeleteHome(View view){
		Intent move = new Intent(this, DeleteHome.class);
		move.putExtra(EXTRA_MESSAGE, filePath);
		startActivity(move);
	}
	public void loadIntoImageview(String path){
		try {
		Log.d("Path", "Path: " + path);
		Bitmap bitmap = decodeBitmap(path,250,250);
		
		int pictureRotation;
		
			pictureRotation = getPictureRotation(path);
		Matrix matrix = new Matrix();
		matrix.postRotate(pictureRotation);
		bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	
		ImageView imageView = (ImageView) findViewById(R.id.imageViewMain);
		imageView.setImageBitmap(bitmap);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
