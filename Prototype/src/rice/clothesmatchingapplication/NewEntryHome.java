package rice.clothesmatchingapplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.widget.ImageView;

public class NewEntryHome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_entry_home);
		Intent intent = getIntent();
		String path = intent.getStringExtra(ExpertCategories.EXTRA_MESSAGE);
		Log.d("File", "Path: " + path);
		loadIntoImageview(path);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_entry_home, menu);
		return true;
	}
	
	public void loadIntoImageview(String path){
		Log.d("Path", "Path: " + path);
		Bitmap bitmap = decodeBitmap(path,250,250);
		ImageView imageView = (ImageView) findViewById(R.id.imageViewMain);
		imageView.setImageBitmap(bitmap);
	}
	
	public static Bitmap decodeBitmap(String names, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(names,options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(names, options);
	}
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth){

		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;
		
		if (height>reqHeight || width > reqWidth){
			final int halfHeight = height/2;
			final int halfWidth = width/2;
			
			while ((halfHeight/inSampleSize)>reqHeight && (halfWidth/inSampleSize)>reqWidth){
				inSampleSize*=2;
			}
		}
		
		return inSampleSize;
	}

}
