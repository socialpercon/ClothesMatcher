package rice.clothesmatchingapplication;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class NewEntryHome extends Activity {

	private final String LOG_TAG = getClass().getSimpleName();
	private DatabaseHelper databaseHelper = null;
	public static final String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	public String filePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_entry_home);
		String[] projection = new String[]{
			    MediaStore.Images.ImageColumns._ID,
			    MediaStore.Images.ImageColumns.DATA,
			    MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
			    MediaStore.Images.ImageColumns.DATE_TAKEN,
			    MediaStore.Images.ImageColumns.MIME_TYPE
			    };
			final Cursor cursor = getContentResolver()
			        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, 
			               null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

			// Put it in the image view
			if (cursor.moveToFirst()) {
			    final ImageView imageView = (ImageView) findViewById(R.id.imageViewMain);
			    filePath = cursor.getString(1);
			    File imageFile = new File(filePath);
			    if (imageFile.exists()) {   // TODO: is there a better way to do this?
			        Bitmap bm = decodeBitmap(filePath, 250, 250);
			        imageView.setImageBitmap(bm);         
			    }
			    
			    
			} 
		
	
			Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
			String[] items = new String[]{"Long Sleeve Shirts", "Short Sleeve Shirts", "Pants", "Skirts", "Shoes"};
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
			dropdown.setAdapter(adapter);
			
/**			Intent moveFrom =  getIntent();
			Bundle bundle  = moveFrom.getExtras();
			filePath = bundle.getString("EXTRA_MESSAGE");
			
		    File imageFile = new File(filePath);
		    if (imageFile.exists()) {
		        loadIntoImageview(filePath);
		}
			
**/		
			
			
	}
	
	public void moveToMatchingHome(View view){
		Intent move = new Intent(this, MatchingHome.class);
		move.putExtra(EXTRA_MESSAGE, filePath);
		startActivity(move);
	}
	
	public void loadItemIntoDatabase(String ClothesName, String ClothesType){
		try {
			Dao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
			
			//inserting random matches for now
			//String[] matches = {"Blue Jeans", "Black Sweater", "Orange Tutu"};
			SimpleData simple = new SimpleData(ClothesName, ClothesType);//, matches);
			simpleDao.create(simple);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void onDestroy(){
		super.onDestroy();
		if(databaseHelper!=null){
			databaseHelper.close();
			databaseHelper = null;
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
	
	public void loadIntoImageview(String path){
		Log.d("Path", "Path: " + path);
		Bitmap bitmap = decodeBitmap(path,250,250);
		ImageView imageView = (ImageView) findViewById(R.id.imageViewMain);
		imageView.setImageBitmap(bitmap);
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
