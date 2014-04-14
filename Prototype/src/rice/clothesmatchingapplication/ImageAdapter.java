package rice.clothesmatchingapplication;
import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.media.ExifInterface;

public class ImageAdapter extends BaseAdapter{

	private Context mContext;
	public Bitmap[] bits;

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
	
	
	public ImageAdapter(Context c) throws IOException{
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File(sdCard.getAbsolutePath() + "/ClothesMatchingApplication");
		File[] names = directory.listFiles();
		Log.d("Files", "Size: "+ names.length);
		bits = new Bitmap[names.length];

	    mContext = c;
	    
	    for (int i=0; i< names.length; i++){
	    	Bitmap bitmap = decodeBitmap(names[i].getAbsolutePath(),250,250);
	    	int pictureRotation = getPictureRotation(names[i].getAbsolutePath());
	    	Matrix matrix = new Matrix();
	    	matrix.postRotate(pictureRotation);
	    	bitmap = Bitmap.createBitmap(bitmap, 0, 0, 250, 250, matrix, true);
	    	bits[i]=bitmap;
        }
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
