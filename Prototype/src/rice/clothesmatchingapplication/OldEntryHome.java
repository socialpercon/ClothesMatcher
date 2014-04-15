package rice.clothesmatchingapplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ImageView;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_old_entry_home);
		
		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(bitmap);
		
		try {
			imageAdapter = new ImageAdapter(instance);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		doSampleDatabaseStuff();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.old_entry_home, menu);
		return true;
	}

	@Override
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

	private void doSampleDatabaseStuff() {
		try {
			// our string builder for building the content-view
			StringBuilder sb = new StringBuilder();
			doSimpleDatabaseStuff(sb);
			
			Log.i(LOG_TAG, "Done with page at " + System.currentTimeMillis());
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Database exception", e);
			return;
		}
	}
	
	private StringBuilder doSimpleDatabaseStuff(StringBuilder sb) throws SQLException, java.sql.SQLException {
		// get our dao
		Dao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
		// query for all of the data objects in the database
		List<SimpleData> list = simpleDao.queryForAll();
		
		String[] fileNames = imageAdapter.getFilePaths();
		String name = "";
		Boolean nameStatus = false;
		for (String s: fileNames)
		{
			name = s;
			
			for (SimpleData data: list){
				SimpleData Entry = data;
				
				if(Entry.fileName.equals(name)){
					nameStatus = true;
				}
			
			}
			//maybe the createifnotexist function would have been easier but oh well
		if (nameStatus==false)
		{
		SimpleData simple = new SimpleData("Pants" ,"Blue Jeans");
		simpleDao.create(simple);
		}
		
		nameStatus =false;
		}
//		for deleting database items, use deleteById!		
//		for (SimpleData data: list){
//			simpleDao.deleteById(data.id);
//		}
		
		try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// ignore
		}
		return sb;
	}
	
}


