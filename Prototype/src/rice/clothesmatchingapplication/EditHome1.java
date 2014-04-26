package rice.clothesmatchingapplication;

import java.io.IOException;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class EditHome1 extends Activity {

	public Bitmap bitmap;
	public String[] filePaths;
	public String filePath;
	public static final String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_home1);
		GridView gridView = (GridView) findViewById(R.id.EditGridView);
	    ImageAdapter imageAdapter = new ImageAdapter(this);
		gridView.setAdapter(imageAdapter);
		filePaths = imageAdapter.getFilePaths();
	     
	    gridView.setOnItemClickListener (new OnItemClickListener(){
	    	 public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    v.buildDrawingCache();
	    bitmap = v.getDrawingCache();
	    filePath = filePaths[position];
	    moveToOldEntryHome(v, filePath);
	    
	    }
	    }
	    		 );
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_home1, menu);
		return true;
	}

	public void moveToOldEntryHome(View view, String filePath) {
		Intent move = new Intent(this, OldEntryHome.class);
		move.putExtra("BitmapImage", bitmap);
		move.putExtra(EXTRA_MESSAGE, filePath);
		startActivity(move);
	}
}
