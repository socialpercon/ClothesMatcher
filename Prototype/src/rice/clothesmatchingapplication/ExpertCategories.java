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
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File imagesFolder = new File(Environment.getExternalStorageDirectory(), "ClothesMatchingApplication");
		imagesFolder.mkdirs();
	    File image = new File(imagesFolder, "IMG_" + getDate() + ".jpg");
	    Uri uriSavedImage = Uri.fromFile(image);
	    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
	    startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		   
	}
	
	public String getDate(){
		int second = Calendar.SECOND; 
		int minute = Calendar.MINUTE;
		int hour = Calendar.HOUR_OF_DAY; 
		int month = Calendar.MONTH; 
		int day = Calendar.DAY_OF_MONTH;
		int year = Calendar.YEAR;
		String dateString = ""+year+month+day+hour+minute+second;
		return dateString;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			if(resultCode==RESULT_OK){
				Intent move = new Intent(this, NewEntryHome.class);
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
