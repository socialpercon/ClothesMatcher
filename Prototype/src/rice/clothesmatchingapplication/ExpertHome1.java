package rice.clothesmatchingapplication;

import java.io.File;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class ExpertHome1 extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expert_home1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expert_home1, menu);
		return true;
	}
	
	public void moveToExpertCategories(View view){
		Intent move = new Intent(this, ExpertCategories.class);
		startActivity(move);
	}
	
	public void moveToEditHome(View view){
		Intent move = new Intent(this, EditHome1.class);
		startActivity(move);
	}
	
}
