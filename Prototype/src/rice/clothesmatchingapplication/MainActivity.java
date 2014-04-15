package rice.clothesmatchingapplication;

import java.io.File;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void moveToUserHome(View view){
		Intent move = new Intent(this, UserHome1.class);
		startActivity(move);
	}
	
	public void moveToExpertHome(View view){
		Intent move = new Intent(this, ExpertHome1.class);
		startActivity(move);
	}
	
}	

