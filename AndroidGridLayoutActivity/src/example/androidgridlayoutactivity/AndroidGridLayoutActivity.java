package example.androidgridlayoutactivity;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.android.apptools.OrmLiteBaseListActivity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;



@SuppressWarnings("rawtypes")
public class AndroidGridLayoutActivity extends Activity {
	public static AndroidGridLayoutActivity instance;
	
	public AndroidGridLayoutActivity() {
		instance = this;
	}
	
	public static Context getContext(){
		return instance;
	}
	
	private final String LOG_TAG = getClass().getSimpleName();
	private DatabaseHelper databaseHelper = null;
	public final String TAG = "AndroidGridLayoutActivity";
	public ImageAdapter imageAdapter; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		try {
			imageAdapter = new ImageAdapter(instance);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setContentView(R.layout.activity_android_grid_layout);
		GridView gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(imageAdapter);
    

		doSampleDatabaseStuff("onCreate",tv);	
		setContentView(tv);
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
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.android_grid_layout, menu);
		return true;
	}

	private void doSampleDatabaseStuff(String action, TextView tv) {
		try {
			// our string builder for building the content-view
			StringBuilder sb = new StringBuilder();
			doSimpleDatabaseStuff(action, sb);
			sb.append("------------------------------------------\n");
			tv.setText(sb.toString());
				
			Log.i(LOG_TAG, "Done with page at " + System.currentTimeMillis());
		} catch (SQLException e) {
			Log.e(LOG_TAG, "Database exception", e);
			tv.setText("Database exeption: " + e);
			return;
		}
	}
	
	private StringBuilder doSimpleDatabaseStuff(String action, StringBuilder sb) throws SQLException, java.sql.SQLException {
		// get our dao
		Dao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
		// query for all of the data objects in the database
		List<SimpleData> list = simpleDao.queryForAll();
		sb.append("got ").append(list.size()).append(" SimpleData entries in ").append(action).append("\n");
		sb.append("------------------------------------------\n");

		String[] fileNames = imageAdapter.getFilePaths();
		String name = "";
		Boolean nameStatus = false;
		int i=1;
		for (String s: fileNames)
		{
			name = s;
			
			for (SimpleData data: list){
				SimpleData Entry = data;
				
				if(Entry.fileName.equals(name)){
					nameStatus = true;
				}
			
			}
			Log.d("nameStatus", "" + nameStatus);
			//maybe the createifnotexist function would have been easier but oh well
		if (nameStatus==false)
		{
		SimpleData simple = new SimpleData(name,"Pants" ,"Blue");
		simpleDao.create(simple);
		sb.append("------------------------------------------\n");
		sb.append("created SimpleData entry #").append(i).append(":\n");
		sb.append(simple).append("\n");
		i++;
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
