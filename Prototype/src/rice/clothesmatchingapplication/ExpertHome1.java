package rice.clothesmatchingapplication;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class ExpertHome1 extends Activity {

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public final static String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";	
	public final static String FILE_PATH = "FilePath.MESSAGE";
	public static final int REQUEST_FILE_PATH = 100;

	public SharedPreferences filepath; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_expert_home1);
		filepath = getSharedPreferences("filepath", MODE_PRIVATE);
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
	    String filePath = image.getAbsolutePath();


	    Editor edit = filepath.edit();
	    edit.clear();
	    edit.putString("file", filePath);
	    edit.commit();


	    Uri uriSavedImage = Uri.fromFile(image);
	    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

	    startActivityForResult(cameraIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

	}

	@SuppressLint("SimpleDateFormat")
	public String getDate(){
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		return timeStamp;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		if (requestCode==CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
			if(resultCode==RESULT_OK){
				Intent move = new Intent(this, NewEntryHome.class);
				move.putExtra(EXTRA_MESSAGE, "picture");
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