package apihelpers.networkhelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by core6_000 on 7/21/2015.
 */
public class LRUBitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    public LRUBitmapCache(int maxSize)
    {
        super(maxSize);

    }

    public LRUBitmapCache(Context pContext)
    {
        this(getCacheSize(pContext));
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return super.sizeOf(key, value);
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
<<<<<<< HEAD
            put(url,bitmap);
=======
        put(url,bitmap);
>>>>>>> bf7380c2da6bf7d8460391847994beba71ccf6cd
    }

    public static int getCacheSize(Context pContex)
    {
        final DisplayMetrics displayMetrics = pContex.getResources().getDisplayMetrics();

        final int screenWidth =  displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;

        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }

}
