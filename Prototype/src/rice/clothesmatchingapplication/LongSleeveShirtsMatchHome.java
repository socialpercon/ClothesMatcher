package rice.clothesmatchingapplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class LongSleeveShirtsMatchHome extends Activity {

	private final String LOG_TAG = getClass().getSimpleName();
	private DatabaseHelper databaseHelper = null;
	private List<SimpleData> dataList = checkDatabaseType();
	private ArrayList<String> filePathList = new ArrayList<String>(dataList.size());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_long_sleeve_shirts_match_home);
		
		
		for (SimpleData data: dataList){
			String filePath = data.fileName;
			Log.d("filePath", filePath);
			filePathList.add(filePath);
		}
		
		
		GridView gridView = (GridView)findViewById(R.id.gridView1);
		
		try {
			gridView.setAdapter(new ImageAdapterPartial(this));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.long_sleeve_shirts_match_home, menu);
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
			queryBuilder.where().eq("type", "LongSleeveShirts");
			PreparedQuery<SimpleData> preparedQuery = queryBuilder.prepare();
			List<SimpleData> dataList = simpleDao.query(preparedQuery);
			return dataList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	
	private class ImageAdapterPartial extends BaseAdapter{

			private Context mContext;
			public Bitmap[] bits;
			
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
			
			
			public ImageAdapterPartial(Context c) throws IOException{
				
				mContext = c;
				
			    for (int i=0; i< filePathList.size(); i++){    	
			    	Bitmap bitmap = decodeBitmap(filePathList.get(i),250,250);
			    	int pictureRotation = getPictureRotation(filePathList.get(i));
			    	Matrix matrix = new Matrix();
			    	matrix.postRotate(pictureRotation);
			    	bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
			    	bits[i]=bitmap;
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
