package rice.clothesmatchingapplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class UserHome1 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_home1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_home1, menu);
		return true;
	}

	public void moveToSSShirtsHome(View view){
		Intent move = new Intent(this, ShortSleeveShirtsHome.class);
		startActivity(move);
	}
	
	public void moveToLSShirtsHome(View view){
		Intent move = new Intent(this, LongSleeveShirtsHome.class);
		startActivity(move);
	}
	
	public void moveToSkirtsHome(View view){
		Intent move = new Intent(this, SkirtsHome.class);
		startActivity(move);
	}
	
	public void moveToPantsHome(View view){
		Intent move = new Intent(this, PantsHome.class);
		startActivity(move);
	}
	
	public void moveToShoesHome(View view){
		Intent move = new Intent(this, ShoesHome.class);
		startActivity(move);
	}
}
