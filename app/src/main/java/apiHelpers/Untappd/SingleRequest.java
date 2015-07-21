package apihelpers.Untappd;


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

    /**
     * @author Allen Space
     * @param pContext Context object context.
     * Description: private constructor.
     * */
    private SingleRequest(Context pContext)
    {
        this.mContext = pContext;
        this.mRequestQueue = getRequestQueue();
    }

    public static synchronized SingleRequest getInstance(Context pContext)
    {
        if(mInstance == null)
        {
            mInstance = new SingleRequest(pContext.getApplicationContext());
        }

        return mInstance;
    }
    /**
     * @author Allen Space
     * @return RequestQueue for singleton design of volley request queue instantiate.
     * */
    public RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
        {
            //Application context is important
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        }

        return mRequestQueue;
    }

    /**
     * @author Allen Space
     * @param req Generic network request.
     * Description: Adds request to queue for network requesting.
     * */
    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }

}
