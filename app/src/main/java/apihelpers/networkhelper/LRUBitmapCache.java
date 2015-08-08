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

    /**
     * @author Allen Space
     * @param maxSize Int data value.
     * Description: Constructor for hard coding int value.
     * */
    public LRUBitmapCache(int maxSize)
    {
        super(maxSize);

    }

    /**
     * @author Allen Space
     * @param pContext Context data field.
     * Description: Recommended consrtuctor to use.
     * */
    public LRUBitmapCache(Context pContext)
    {
        this(getCacheSize(pContext));
    }

    /**Parent method*/
    @Override
    protected int sizeOf(String key, Bitmap value) {
        return super.sizeOf(key, value);
    }

    /**Parent Method*/
    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
            put(url,bitmap);

    }

    /**
     * @author Allen Space
     * Descriptio: Generate supportive LRU cache.
     * */
    public static int getCacheSize(Context pContex)
    {
        final DisplayMetrics displayMetrics = pContex.getResources().getDisplayMetrics();

        final int screenWidth =  displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;

        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }

}
