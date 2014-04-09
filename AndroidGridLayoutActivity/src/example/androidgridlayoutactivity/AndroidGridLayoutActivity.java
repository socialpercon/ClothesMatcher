package example.androidgridlayoutactivity;

import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;
 

public class AndroidGridLayoutActivity extends OrmLiteBaseActivity<DatabaseHelper> {
	public final String TAG = "AndroidGridLayoutActivity";
//	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_grid_layout);
		Log.d(TAG, "create grid view");
		
		GridView gridView = (GridView) findViewById(R.id.gridView1);
	        // Instance of ImageAdapter Class
	    gridView.setAdapter(new ImageAdapter(this));
	    
	    TextView tv = new TextView(this);
		doSampleDatabaseStuff("onCreate", tv);
		setContentView(tv);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.android_grid_layout, menu);
		return true;
	}
	
	private void doSampleDatabaseStuff(String action, TextView tv){
		RuntimeExceptionDao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
		// query for all of the data objects in the database
		List<SimpleData> list = simpleDao.queryForAll();
		// our string builder for building the content-view
		StringBuilder sb = new StringBuilder();
		sb.append("got ").append(list.size()).append(" entries in ").append(action).append("\n");

		// if we already have items in the database
		int simpleC = 0;
		for (SimpleData simple : list) {
			sb.append("------------------------------------------\n");
			sb.append("[").append(simpleC).append("] = ").append(simple).append("\n");
			simpleC++;
		}
		sb.append("------------------------------------------\n");
		for (SimpleData simple : list) {
			simpleDao.delete(simple);
			sb.append("deleted id ").append(simple.id).append("\n");
			Log.d(TAG, "deleting simple(" + simple.id + ")");
			simpleC++;
		}

		int createNum;
		do {
			createNum = new Random().nextInt(3) + 1;
		} while (createNum == list.size());
		for (int i = 0; i < createNum; i++) {
			// create a new simple object
			long millis = System.currentTimeMillis();
			SimpleData simple = new SimpleData(millis);
			// store it in the database
			simpleDao.create(simple);
			Log.d(TAG, "created simple(" + millis + ")");
			// output it
			sb.append("------------------------------------------\n");
			sb.append("created new entry #").append(i + 1).append(":\n");
			sb.append(simple).append("\n");
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				// ignore
			}
		}

		tv.setText(sb.toString());
		Log.d(TAG, "Done with page at " + System.currentTimeMillis());
	}
	
	

}
