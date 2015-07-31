package Callbacks;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created Allen Space on 7/21/2015.
 */
abstract public class ImageViewRunnable implements Runnable {

    @Override
    public void run() {

    }

    abstract public void runWithImageView(Bitmap bitmap);
}
