package rice.clothesmatchingapplication;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class MatchingHome extends Activity {

	public final static String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	public final static String EXTRA_MESSAGE2 = "rice.clothesmatchingapplication.MESSAGE2";
	public static final String EXTRA_MESSAGE3 = "rice.clothesmatchingapplication.MESSAGE3";
	public String oldOrNew;
	public String filePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matching_home);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		filePath = bundle.getString(EXTRA_MESSAGE);
		oldOrNew = bundle.getString(EXTRA_MESSAGE3);
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matching_home, menu);
		return true;
		
	}
	
	public void moveToLongSleeveShirtsHome(View view){
		Intent move = new Intent(this, LongSleeveShirtsMatchHome.class);
		move.putExtra(EXTRA_MESSAGE2, filePath);
		move.putExtra(EXTRA_MESSAGE3, oldOrNew);
		startActivity(move);
		
	}

	public void moveToShortSleeveShirtsHome(View view){
		Intent move = new Intent(this, ShortSleeveShirtsMatchHome.class);
		move.putExtra(EXTRA_MESSAGE2, filePath);
		move.putExtra(EXTRA_MESSAGE3, oldOrNew);
		startActivity(move);
		
	}
	

	public void moveToPantsHome(View view){
		Intent move = new Intent(this, PantsMatchHome.class);
		move.putExtra(EXTRA_MESSAGE2, filePath);
		move.putExtra(EXTRA_MESSAGE3, oldOrNew);
		startActivity(move);
		
	}
	

	public void moveToSkirtsHome(View view){
		Intent move = new Intent(this, ShortsMatchHome.class);
		move.putExtra(EXTRA_MESSAGE2, filePath);
		move.putExtra(EXTRA_MESSAGE3, oldOrNew);
		startActivity(move);
		
	}
	

	public void moveToShoesHome(View view){
		Intent move = new Intent(this, ShoesMatchHome.class);
		move.putExtra(EXTRA_MESSAGE2, filePath);
		move.putExtra(EXTRA_MESSAGE3, oldOrNew);
		startActivity(move);
		
	}
	
	public void moveToNewEntryHome(View view){
		Intent move = new Intent(this, NewEntryHome.class);
		move.putExtra(EXTRA_MESSAGE, filePath);
		Log.d("FilePath", filePath);
		startActivity(move);
	}
	
}
