package es.sw.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.Map;


public class VolleySingleton {

    private static VolleySingleton mInstance = null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private BitmapLruCache mCache = new BitmapLruCache();
 
    private VolleySingleton(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
        mCache = new BitmapLruCache();
        mImageLoader = new ImageLoader(mRequestQueue, mCache);
        /* old
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(NUM_IMAGES);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });*/
    }

    /**
     * First time init(Should be called in Application class before use it)
     * @param context
     * @return
     */
    public static VolleySingleton getInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleySingleton(context);
        }
        return mInstance;
    }

    /**
     * Get volley singleton
     * @return
     */
    public static VolleySingleton getInstance(){
        return mInstance;
    }
 
    public RequestQueue getRequestQueue(){
        return this.mRequestQueue;
    }
 
    public ImageLoader getImageLoader(){
        return this.mImageLoader;
    }

    public BitmapLruCache getBitmapCache() {
        return mCache;
    }

    /**
     * Custom Bitmap Cache
     */
    public static class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

        public static int getDefaultLruCacheSize() {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
            final int cacheSize = maxMemory / 8;

            return cacheSize;
        }

        public BitmapLruCache() {
            this(getDefaultLruCacheSize());
        }

        public BitmapLruCache(int sizeInKiloBytes) {
            super(sizeInKiloBytes);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value.getRowBytes() * value.getHeight() / 1024;
        }

        @Override
        public Bitmap getBitmap(String url) {
            return get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            put(url, bitmap);
        }

        /**
         * Returns the real key that stores the cache. Cache saves a "#Wxxx#HxxxKEY"
         * @param key
         * @return
         */
        public String getCacheKey(String key){
            Map<String, Bitmap> map = snapshot();
            //Log.e(TAG, "URL to find: " + url);
            //Log.e(TAG, "CACHED KEYS:");
            for(Map.Entry<String,Bitmap>entry:map.entrySet()){
                //Log.e(TAG, "in cache: " +entry.getKey());
                String cacheKey = entry.getKey().replaceFirst("#W\\d*#H\\d*","");
                //Log.e(TAG, "in cache: " + realEntry);
                if (cacheKey.equals(key)){
                    return entry.getKey();
                }
            }
            return null;
        }
    }
}