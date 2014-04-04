package example.androidgridlayoutactivity;
import java.io.File;
import com.j256.ormlite.android.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
	
	private Context mContext;
	public Bitmap[] bits;
	
	
	
	public ImageAdapter(Context c){
		File sdCard = Environment.getExternalStorageDirectory();
		File directory = new File(sdCard.getAbsolutePath() + "/ClothesMatchingApplication");
		File[] names = directory.listFiles();
		Log.d("Files", "Size: "+ names.length);
		bits = new Bitmap[names.length];
		
	    mContext = c;
	    for (int i=0; i< names.length; i++){
            Bitmap bitmap = BitmapFactory.decodeFile(names[i].getAbsolutePath());
            bits[i]=bitmap;
        }
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

	    return 0;
	}
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
	    ImageView imageView = new ImageView(mContext);
	    imageView.setImageBitmap(bits[position]);
	    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	    imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
	    return imageView;
	}
	 
}

