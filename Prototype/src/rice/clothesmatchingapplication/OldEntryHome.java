package rice.clothesmatchingapplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;

public class OldEntryHome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_old_entry_home);
		
		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(bitmap);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.old_entry_home, menu);
		return true;
	}

}
