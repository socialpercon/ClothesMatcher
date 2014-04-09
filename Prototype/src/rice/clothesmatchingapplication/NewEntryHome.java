package rice.clothesmatchingapplication;

import java.io.File;

import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
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
		String[] projection = new String[]{
			    MediaStore.Images.ImageColumns._ID,
			    MediaStore.Images.ImageColumns.DATA,
			    MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
			    MediaStore.Images.ImageColumns.DATE_TAKEN,
			    MediaStore.Images.ImageColumns.MIME_TYPE
			    };
			final Cursor cursor = getContentResolver()
			        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, 
			               null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
			if (cursor.moveToFirst()) {
			    String imageLocation = cursor.getString(1);
			    File imageFile = new File(imageLocation);
			    if (imageFile.exists()) {
			        loadIntoImageview(imageLocation);
			    }
			} 
	
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
