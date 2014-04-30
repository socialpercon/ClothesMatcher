package rice.clothesmatchingapplication;
import java.io.File;
import java.io.IOException;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.media.ExifInterface;

public class ImageAdapter extends BaseAdapter{

	private Context mContext;
	public String[] filePaths;
	public static final String TAG = "ImageAdapter";
	
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth){

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
	
	
	public ImageAdapter(Context c){
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File(sdCard.getAbsolutePath() + "/ClothesMatchingApplication");
		File[] names = directory.listFiles();
		//Log.d("Files", "Size: "+ names.length);
		
		if(names.length == 0){
			
		}
		else{
			filePaths = new String[names.length];
			
		    mContext = c;
		    
		    for (int i=0; i< names.length; i++){
		    	filePaths[i] = names[i].getAbsolutePath();
		    	Log.d("FilePath", names[i].getAbsolutePath());
	        }
		}
		
	}
	//need to add method to return position
	public String[] getFilePaths(){
		return filePaths;
	}
	
	private static int exifToDegrees(int exifOrientation) {        
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
	
	public static Bitmap decodeBitmap(String names, int reqWidth, int reqHeight) {
		
		
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(names,options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(names, options);
	}
	
	public int getCount() {
	    return filePaths.length;
	}
	@Override

	public Object getItem(int position){
	    return filePaths[position];
	}

	@Override
	public long getItemId(int position){

	    return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		ImageView imageView = (ImageView) convertView;
		if (imageView == null) {
		  imageView = new ImageView(mContext);
		  Log.d(TAG, "Initializing the image view.");
		}
		String url = filePaths[position];
		Log.d(TAG, url);
		Picasso.with(mContext)
			.load(new File(url))
			.centerCrop()
			.resize(250, 250)
			.into(imageView);

	    return imageView;
	    
	}

}
