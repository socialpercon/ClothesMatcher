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
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ExpertHome1 extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public final static String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";	
	public final static String FILE_PATH = "FilePath.MESSAGE";
	public static final int REQUEST_FILE_PATH = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expert_home1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.expert_home1, menu);
		return true;
	}
	
	public void moveToEditHome(View view){
		Intent move = new Intent(this, EditHome1.class);
		startActivity(move);
	}

	public void moveToCamera(View view) {
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		File imagesFolder = new File(Environment.getExternalStorageDirectory(), "ClothesMatchingApplication");
		imagesFolder.mkdirs();
	    File image = new File(imagesFolder, "IMG_" + getDate() + ".jpg");
	    Uri uriSavedImage = Uri.fromFile(image);
	    String filePath = image.getAbsolutePath();
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
