package example.androidgridlayoutactivity;

import android.os.Bundle;
import android.view.Menu;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
 

public class AndroidGridLayoutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_grid_layout);

		 GridView gridView = (GridView) findViewById(R.id.gridView1);
	        // Instance of ImageAdapter Class
	     gridView.setAdapter(new ImageAdapter(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.android_grid_layout, menu);
		return true;
	}
	
	

}
