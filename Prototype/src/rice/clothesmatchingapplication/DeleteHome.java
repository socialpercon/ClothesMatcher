package rice.clothesmatchingapplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
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

public class DeleteHome extends Activity {

	public String filePathOriginal;
	public static final String EXTRA_MESSAGE = "rice.clothesmatchingapplication.MESSAGE";
	public static final String EXTRA_MESSAGE3 = "rice.clothesmatchingapplication.MESSAGE3";
	public DatabaseHelperM databaseHelperM = null;
	List<MatchesData> dataList;
	ArrayList<String> filePathList;
	public Bitmap bitmap;
	private Context mContext;
	public Bitmap[] bits;
	public String oldOrNew;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delete_home);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		filePathOriginal = bundle.getString(EXTRA_MESSAGE);
		oldOrNew = bundle.getString(EXTRA_MESSAGE3);
	
		dataList = checkDatabase();
		filePathList = new ArrayList<String>(dataList.size());
		
		for (MatchesData data: dataList){
			String filePath = data.type2;
			Log.d("filePath", filePath);
			filePathList.add(filePath);
		}
		
		GridView gridView = (GridView)findViewById(R.id.gridView2);
		

		gridView.setAdapter(new ImageAdapterPartial(this));
		
		gridView.setOnItemClickListener (new OnItemClickListener(){
	    	 public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	    String filePath = filePathList.get(position);
	    moveToNewEntryHome(v,filePath);
	    
	    }
	    }
	    		 );
	}

		
	public void moveToNewEntryHome(View view, String filePath){
		try {
		Intent move = new Intent();
		if (oldOrNew.equals("new")==true){
		move = new Intent(this, NewEntryHome.class);
		}
		if (oldOrNew.equals("old")==true){
		move = new Intent(this, OldEntryHome.class);
		}
		move.putExtra(EXTRA_MESSAGE, filePathOriginal);
		Dao<MatchesData, Integer> matchDao = getHelperM().getMatchesDataDao();
		
		DeleteBuilder<MatchesData, Integer> deleteBuilder = matchDao.deleteBuilder();
		deleteBuilder.where().eq("type2", filePath);
		deleteBuilder.delete();
		deleteBuilder.where().eq("type2", filePathOriginal).and().eq("type1", filePath);
		deleteBuilder.delete();
		
		startActivity(move);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.delete_home, menu);
		return true;
	}

	public List<MatchesData> checkDatabase(){
		try {
			Dao<MatchesData, Integer> matchesDao = getHelperM().getMatchesDataDao();
			QueryBuilder<MatchesData,Integer> queryBuilder = matchesDao.queryBuilder();
			queryBuilder.where().eq("type1", filePathOriginal);
			PreparedQuery<MatchesData> preparedQuery = queryBuilder.prepare();
			List<MatchesData> dataList = matchesDao.query(preparedQuery);
			return dataList;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
		
	}
	

	protected void onDestroy(){
		super.onDestroy();
		if(databaseHelperM!=null){
			databaseHelperM.close();
			databaseHelperM = null;
		}
	}
	
	private DatabaseHelperM getHelperM(){
		if(databaseHelperM == null){
			databaseHelperM = DatabaseHelperM.getHelper(this);
			
		}
		return databaseHelperM;
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

