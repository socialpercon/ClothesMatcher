package rice.clothesmatchingapplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class OldEntryHome extends Activity {

public static OldEntryHome instance;
	
	public OldEntryHome() {
		instance = this;
	}
	
	public static Context getContext(){
		return instance;
	}
	
	private final String LOG_TAG = getClass().getSimpleName();
	private DatabaseHelper databaseHelper = null;
	public final String TAG = "OldEntryHome";
	public ImageAdapter imageAdapter; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_old_entry_home);
		
		Intent intent = getIntent();
		Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
		
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(bitmap);
		
		imageAdapter = new ImageAdapter(instance);
		
	}
	
	public void moveToMatchingHome(View view){
		Intent move = new Intent(this, MatchingHome.class);
		startActivity(move);
	}
	
	public void moveToExpertHome1(View view){
		Intent move = new Intent(this, ExpertHome1.class);
		startActivity(move);
		
	}
}


