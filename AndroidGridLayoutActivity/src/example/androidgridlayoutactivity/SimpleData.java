package example.androidgridlayoutactivity;

import android.content.Context;

import com.orm.SugarRecord;

//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.j256.ormlite.field.DatabaseField;

/**
 * A simple demonstration object we are creating and persisting to the database.
 */
public class SimpleData extends SugarRecord<SimpleData>{
	String fileName;
	public SimpleData(Context ctx) {
		super(ctx);
		
	}
	
	public SimpleData(Context ctx, String fileName){
		super(ctx);
		this.fileName = fileName;
		
	}
}
