package rice.clothesmatchingapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ShortSleeveShirtsMatchHome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_short_sleeve_shirts_match_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.short_sleeve_shirts_match_home, menu);
		return true;
	}

}
