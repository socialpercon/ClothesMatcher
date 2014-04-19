package rice.clothesmatchingapplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.media.ExifInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

public class ShoesHome extends Activity {

	private DatabaseHelper databaseHelper = null;
	List<SimpleData> dataList;
	ArrayList<String> filePathList;
	public Bitmap bitmap;
	private Context mContext;
	public Bitmap[] bits;
	public String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoes_home);
		
		dataList = checkDatabaseType();
		filePathList = new ArrayList<String>(dataList.size());
		
		
		
		for (SimpleData data: dataList){
			String filePath = data.fileName;
			Log.d("filePath", filePath);
			filePathList.add(filePath);
		}
		
		
		
		GridView gridView = (GridView)findViewById(R.id.gridView1);
		
		gridView.setOnItemClickListener (new OnItemClickListener(){
	    	 public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    v.buildDrawingCache();
	    bitmap = v.getDrawingCache();
	    moveToLongSleeveHome(v);
	    
	    }
	    }
	    		 );
	}
	
	public void moveToLongSleeveHome(View view) {
		Intent move = new Intent(this, ImagesHome.class);
		move.putExtra("BitmapImage", bitmap);
		move.putExtra(EXTRA_MESSAGE, "Shoes");
		startActivity(move);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shoes_home, menu);
		return true;
	}


protected void onDestroy(){
	super.onDestroy();
	if(databaseHelper!=null){
		databaseHelper.close();
		databaseHelper = null;
	}
}

private DatabaseHelper getHelper(){
	if (databaseHelper == null){
		databaseHelper = DatabaseHelper.getHelper(this);
	}
	return databaseHelper;
}

public List<SimpleData> checkDatabaseType(){
	try {
		Dao<SimpleData, Integer> simpleDao = getHelper().getSimpleDataDao();
		QueryBuilder<SimpleData,Integer> queryBuilder = simpleDao.queryBuilder();
		queryBuilder.where().eq("type", "Shoes");
		PreparedQuery<SimpleData> preparedQuery = queryBuilder.prepare();
		List<SimpleData> dataList = simpleDao.query(preparedQuery);
		return dataList;
	} catch (SQLException e) {
		
		e.printStackTrace();
	}
	return null;
	
}

private class ImageAdapterPartial extends BaseAdapter{	
	
	public int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth){

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
	
public ImageAdapterPartial(Context c){
		
		mContext = c;
		bits = new Bitmap[filePathList.size()];
		
	    for (int i=0; i< filePathList.size(); i++){  
	    	try {
	    	Log.d("FilePath", filePathList.get(i));
	    	Bitmap bitmap = decodeBitmap(filePathList.get(i),250,250);
	    	if (bitmap == null){
	    		Log.d("ERROR", "BITMAP IS NULL");
	    	}
	    	int pictureRotation;
			pictureRotation = getPictureRotation(filePathList.get(i));
	    	Matrix matrix = new Matrix();
	    	matrix.postRotate(pictureRotation);
	    	bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	    	bits[i]=bitmap;
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
	
	private int exifToDegrees(int exifOrientation) {        
	    if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; } 
	    else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; } 
	    else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }            
	    return 0;    
	 }
	
	public int getPictureRotation(String path) throws IOException{
		ExifInterface exif = new ExifInterface(path);
    	int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
    	int rotationInDegrees = exifToDegrees(rotation);
    	return rotationInDegrees;
	}
	
	public Bitmap decodeBitmap(String names, int reqWidth, int reqHeight) {
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(names,options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(names, options);
	}
	
	public int getCount() {
	    return bits.length;
	}
	@Override

	public Object getItem(int position){
	    return bits[position];
	}

	@Override
	public long getItemId(int position){

	    return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){

	    ImageView imageView = new ImageView(mContext);
	    imageView.setImageBitmap(bits[position]);
	    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	    imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
	    return imageView;
	}

}

}
