package rice.clothesmatchingapplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MatchingHome extends Activity {

	public final static String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	public String filePath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_matching_home);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		filePath = bundle.getString(EXTRA_MESSAGE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.matching_home, menu);
		return true;
		
	}
	
	
	
}
