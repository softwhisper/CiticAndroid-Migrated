package es.sw.weather;


import android.content.Context;
import android.os.AsyncTask;

public class SaveQuery<T extends ActiveRecord> extends AsyncTask<Context, Void, Boolean>{
	
    private static final String TAG = "SaveQuery";

    private Class<? extends ActiveRecord> dbClass;
    private T t;

    public SaveQuery(Class<? extends ActiveRecord> dbClass, T t){
        this.dbClass = dbClass;
        this.t = t;
    }

    @Override
    protected Boolean doInBackground(Context... params) {
        return ActiveRecord.save(params[0],dbClass,t);
    }

    @Override
    protected void onPostExecute(Boolean isSaved) {
        onSave(isSaved);
        cleanQuery();
    }

    @Override
    protected void onCancelled() {
        cleanQuery();
    }

    private void cleanQuery(){
        dbClass = null;
        t = null;
    }

    /**
     * Listener that runs in UIThread exec after the query have been completed
     * @param success
     */
    public void onSave(boolean success){}


}
