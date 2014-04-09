package rice.clothesmatchingapplication;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LongSleeveShirtsHome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_long_sleeve_shirts_home);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.long_sleeve_shirts_home, menu);
		return true;
	}

}
