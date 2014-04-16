package rice.clothesmatchingapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PantsMatchHome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pants_match_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pants_match_home, menu);
		return true;
	}

}
