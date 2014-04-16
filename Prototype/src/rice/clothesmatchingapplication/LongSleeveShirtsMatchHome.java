package rice.clothesmatchingapplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;

public class LongSleeveShirtsMatchHome extends Activity {

	private final String LOG_TAG = getClass().getSimpleName();
	private DatabaseHelper databaseHelper = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_long_sleeve_shirts_match_home);
		
		List<SimpleData> dataList = checkDatabaseType();
		ArrayList<String> filePathList = new ArrayList<String>(dataList.size());
		for (SimpleData data: dataList){
			String filePath = data.fileName;
			Log.d("filePath", filePath);
			filePathList.add(filePath);
		}
		
		
		GridView gridView = (GridView)findViewById(R.id.gridView1);
		
		try {
			gridView.setAdapter(new ImageAdapter(this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.long_sleeve_shirts_match_home, menu);
		return true;
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

	public List<SimpleData> checkDatabaseType(){
		try {
			Dao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
			QueryBuilder<SimpleData,Integer> queryBuilder = simpleDao.queryBuilder();
			queryBuilder.where().eq(SimpleData.type, "LongSleeveShirts");
			PreparedQuery<SimpleData> preparedQuery = queryBuilder.prepare();
			List<SimpleData> dataList = simpleDao.query(preparedQuery);
			return dataList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
}
