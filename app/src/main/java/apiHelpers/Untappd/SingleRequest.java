package apiHelpers.Untappd;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Allen Space on 7/11/2015.
 */
public class SingleRequest {
    private static SingleRequest mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private SingleRequest(Context pContext)
    {
        this.mContext = pContext;
        this.mRequestQueue = getRequestQueue();
    }

    public static synchronized SingleRequest getInstance(Context pContext)
    {
        if(mInstance == null)
        {
            mInstance = new SingleRequest(pContext);
        }

        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            //Application context is important
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }

}
