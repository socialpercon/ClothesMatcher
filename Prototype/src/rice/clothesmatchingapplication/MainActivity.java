package rice.clothesmatchingapplication;

import java.io.File;
import java.util.Date;

import rice.clothesmatchingapplication.MainActivity.FeedReaderContract.FeedEntry;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void moveToUserHome(View view){
		Intent move = new Intent(this, UserHome1.class);
		startActivity(move);
	}
	
	public void moveToExpertHome(View view){
		Intent move = new Intent(this, ExpertHome1.class);
		startActivity(move);
	}
	
	
	public final class FeedReaderContract {
	    // To prevent someone from accidentally instantiating the contract class,
	    // give it an empty constructor.
	public FeedReaderContract() {}

	    /* Inner class that defines the table contents */
	abstract class FeedEntry implements BaseColumns {
	        public static final String TABLE_NAME = "entry";
	        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
	        public static final String COLUMN_NAME_TITLE = "title";
	        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
	    }
	}
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
	    FeedEntry._ID + " INTEGER PRIMARY KEY," +
	    FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
	    FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP+")";

	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
	

