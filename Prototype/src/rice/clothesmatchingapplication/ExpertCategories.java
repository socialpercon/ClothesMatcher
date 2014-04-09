package rice.clothesmatchingapplication;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ExpertCategories extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public int Category = 0;
	public final String IMAGE_PATH = "IMAGEPATH";
	public final static String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expert_categories);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expert_categories, menu);
		return true;
	}
	
	public void LongSleeveShirts(View view){
		Category = 1;
		moveToCamera(view);
	}
	
	public void ShortSleeveShirts(View view){
		Category = 2;
		moveToCamera(view);
	}
	
	public void Pants(View view){
		Category = 3;
		moveToCamera(view);
	}
	
	public void Skirts(View view){
		Category = 4;
		moveToCamera(view);
	}
	
	public void Shoes(View view){
		Category = 5;
		moveToCamera(view);
	}
	
	public void moveToCamera(View view) {
		Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File imagesFolder = new File(Environment.getExternalStorageDirectory(), "ClothesMatchingApplication");
		imagesFolder.mkdirs();
	    File image = new File(imagesFolder, "IMG_" + getDate() + ".jpg");
	    Uri uriSavedImage = Uri.fromFile(image);
	    CameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
	    CameraIntent.putExtra(IMAGE_PATH, image.getAbsolutePath());
	    startActivityForResult(CameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		   
	}
	
	public String getDate(){
		int Second = Calendar.SECOND; 
		int Minute = Calendar.MINUTE;
		int Hour = Calendar.HOUR_OF_DAY; 
		int Month = Calendar.MONTH; 
		int Day = Calendar.DAY_OF_MONTH;
		int Year = Calendar.YEAR;
		String dateString = ""+Year+Month+Day+Hour+Minute+Second;
		return dateString;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			if(resultCode==RESULT_OK){
				Log.d("File", "Path: " + data.getData());
				//String path = Camera.getStringExtra(IMAGE_PATH);
				Intent move = new Intent(this, NewEntryHome.class);
				//Log.d("File", "Path: " + path);
				//move.putExtra(EXTRA_MESSAGE, path);
				startActivity(move);
				
			}
			else if (resultCode == RESULT_CANCELED){
				
			}
			else{
				Toast.makeText(this, "Camera failed to take a picture, please try again", Toast.LENGTH_LONG).show();
			}
		}
	}
}
