package es.sw.weather;

/**
 * Created by albertopenasamor on 11/11/13.
 */

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


public abstract class ActiveRecord extends Object{
	
	public static final int EQ = 0;
	public static final int N_EQ = EQ + 1;
	public static final int AND = 0;
	public static final int OR = AND +1;

	//TODO: flag para evitar release en casos de muchas querys consecutivas
	//static boolean isManualHelper = false;
	//static DatabaseHelper db;
	
    private static final String TAG = "ActiveRecord";

    /**
     * Helper for get DAO
     * @param context
     * @param c
     * @return
     */
    private static Dao getDao(Context context, Class c){
    	/*
    	if (!isManualHelper){
    		db = OpenHelperManager.getHelper(context, DatabaseHelper.class);	
    	}else{
    		if (db == null){
    			db = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    		}
    	}
    	*/
        
    	DatabaseHelper db = OpenHelperManager.getHelper(context, DatabaseHelper.class);
        try {
            Dao<Class, Integer> dao = db.getDao(c);
            return dao;
        } catch (SQLException e) {
            throw new IllegalArgumentException("cannot get Dao for class: " + c.getSimpleName());
        }
    }

    /**
     * Helper for release DDBB
     */
    private static void releaseDao(){
    
    	OpenHelperManager.releaseHelper();
    	
    	/*
    	if (!isManualHelper){
            OpenHelperManager.releaseHelper();
            db = null;
    	}
    	*/
    }
    
    /**
     * Helper for query if a Dao.CreateOrUpdateStatus was created or updated
     * @param status
     * @return
     */
    private static boolean isCreatedOrUpdated(Dao.CreateOrUpdateStatus status){
        if (status == null)
            return false;
        /*
        if (BuildConfig.DEBUG){
        	Log.e(TAG, "isCreated:  "+ status.isCreated());
        }
        if (BuildConfig.DEBUG){
        	Log.e(TAG, "isUpdated:  "+ status.isUpdated());
        }
        */
        if (status.isCreated() || status.isUpdated())
            return true;

        return false;
    }
    
    //TODO: problema cuando se ejecutan en paralelo varias querys. cuando 
    /*
    public static void setManualManageHelper(boolean enable){
    	isManualHelper = enable;
    	
    	if (!isManualHelper){
    		OpenHelperManager.releaseHelper();
    		db = null;
    	}
    }*/

    public static<T extends ActiveRecord> List<T>findAll(Context ctx, Class table){
        Dao<T,Integer> dao = ActiveRecord.getDao(ctx, table);
        List<T> list = null;
        if (dao != null){
            try {
                list = dao.queryForAll();
            } catch (SQLException e) {
                Log.e(TAG, "sql", e);
            } catch (Exception e){
                Log.e(TAG, "exception",e);
            }finally {
                ActiveRecord.releaseDao();
            }
        }
        return list;
    }

    public static<T extends ActiveRecord> T findById(Context ctx, Class table, Integer id){
        Dao<T,Integer> dao = ActiveRecord.getDao(ctx, table);
        T t = null;
        if (dao != null){
            try {
                t = dao.queryForId(id);
            } catch (SQLException e) {
                Log.e(TAG, "sql", e);
            } catch (Exception e){
                Log.e(TAG, "exception",e);
            }finally {
                ActiveRecord.releaseDao();
            }
        }
        return t;
    }

    public static<T extends ActiveRecord> boolean save(Context ctx, Class table, T t){
        Dao<T,Integer> dao = ActiveRecord.getDao(ctx, table);
        boolean success = false;
        if (dao != null){
            try {
                success = isCreatedOrUpdated(dao.createOrUpdate(t));
            } catch (SQLException e) {
                Log.e(TAG, "sql", e);
            } catch (Exception e){
                Log.e(TAG, "exception",e);
            }finally {
                ActiveRecord.releaseDao();
            }
        }
        return success;
    }

    public static<T extends ActiveRecord> boolean saveAll(Context ctx, Class table, final List<T> list){
        final Dao<T,Integer> dao = ActiveRecord.getDao(ctx, table);
        boolean isSaved = false;
        if (dao != null){
            try {
                //disables auto commit before launch query and reenables after finish
                isSaved = dao.callBatchTasks(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        for (T t : list) {
                            boolean createdOrUpdated = ActiveRecord.isCreatedOrUpdated(dao.createOrUpdate(t));
                            if (!createdOrUpdated) {
                                Log.e(TAG, "cannot save or update list entry");
                                return false;
                            }
                        }
                        return true;
                    }
                });
            } catch (SQLException e) {
                Log.e(TAG, "sql",e);
            } catch (Exception e){
                Log.e(TAG, "exception",e);
            }finally {
                ActiveRecord.releaseDao();
            }
        }
        return isSaved;
    }

    public static<T extends ActiveRecord> int deleteAll(Context ctx, Class table){
        final Dao<T,Integer> dao = ActiveRecord.getDao(ctx, table);
        int delRows = 0;
        if (dao != null){
            try {
               delRows = dao.deleteBuilder().delete();
            } catch (SQLException e) {
                Log.e(TAG, "sql",e);
            } catch (Exception e){
                Log.e(TAG, "exception",e);
            }finally {
                ActiveRecord.releaseDao();
            }
        }
        return delRows;
    }

    public static<T extends ActiveRecord> int deleteByField(Context ctx, Class table, String field, Object value){
        final Dao<T,Integer> dao = ActiveRecord.getDao(ctx, table);
        int delRows = 0;
        if (dao != null){
            try {
                DeleteBuilder<T, Integer> deleteBuilder = dao.deleteBuilder();
                deleteBuilder.where().eq(field, value);
                delRows = deleteBuilder.delete();
            } catch (SQLException e) {
                Log.e(TAG, "sql",e);
            } catch (Exception e){
                Log.e(TAG, "exception",e);
            }finally {
                ActiveRecord.releaseDao();
            }
        }
        return delRows;
    }

    public static<T extends ActiveRecord> List<T> where(Context ctx, Class table, String field, int whereType, Object value){
        final Dao<T,Integer> dao = ActiveRecord.getDao(ctx, table);
        List<T> list = null;
        if (dao != null){
            try {
                // get our query builder from the DAO
                QueryBuilder<T, Integer> queryBuilder =dao.queryBuilder();
                if (whereType == EQ){
                	queryBuilder.where().eq(field, value);
                }else if (whereType == N_EQ){
                	queryBuilder.where().ne(field, value);
                }/*else if (whereType == ){
                	//TODO: here goes most operators, under building
                }*/
                
                // prepare our sql statement
                PreparedQuery<T> preparedQuery = queryBuilder.prepare();
                list = dao.query(preparedQuery);
            } catch (SQLException e) {
                Log.e(TAG, "sql",e);
            } catch (Exception e){
                Log.e(TAG, "exception",e);
            }finally {
                ActiveRecord.releaseDao();
            }
        }
        if (list == null){
            list = new ArrayList<T>();
        }
        return list;
    }
    
    public static<T extends ActiveRecord> List<T> whereMultiple(Context ctx, Class table, ArrayList<String> fieldList, ArrayList<Integer> whereTypeList, ArrayList<Object> valueList,
    				ArrayList<Integer> operatorsList) {
    	if (fieldList == null || whereTypeList == null || valueList == null || operatorsList == null) {
    		Log.e(TAG, "Params are null");
    		return new ArrayList<T>();
    	}
    	if (fieldList.size() != valueList.size() || fieldList.size() != whereTypeList.size() || fieldList.size() != (operatorsList.size() + 1)) {
    		Log.e(TAG, "Params are not equals size or whereTypeList doesnt match params size");
    		return new ArrayList<T>();
    	}
        final Dao<T,Integer> dao = ActiveRecord.getDao(ctx, table);
        List<T> list = null;
        if (dao != null){
            try {
                // get our query builder from the DAO
                QueryBuilder<T, Integer> queryBuilder = dao.queryBuilder();
                Where<T,Integer> where = queryBuilder.where();
                
                for (int i = 0; i < fieldList.size(); i++) {
                	String field = fieldList.get(i);
                	int whereType = whereTypeList.get(i);
                	Object value = valueList.get(i);
                	
              
                	if (whereType == EQ){
                    	where.eq(field, value);
                    }else if (whereType == N_EQ){
                    	where.ne(field, value);
                    }
                	
                	if (i < operatorsList.size()){
                		int operator = operatorsList.get(i);
                		if (operator == AND) {
                			where.and();
                		}else if (operator == OR) {
                			where.or();
                		}
                	}
                }
             
                PreparedQuery<T> preparedQuery = queryBuilder.prepare();
                list = dao.query(preparedQuery);
            } catch (SQLException e) {
                Log.e(TAG, "sql",e);
            } catch (Exception e){
                Log.e(TAG, "exception",e);
            }finally {
                ActiveRecord.releaseDao();
            }
        }
        if (list == null){
            list = new ArrayList<T>();
        }
        return list;
    }

    
    public static<T extends ActiveRecord> List<T> foreignWhere(Context ctx, Class table, Class foreignTable, String foreignField, Object foreignValue){
        final Dao<T,Integer> dao = ActiveRecord.getDao(ctx, table);
        final Dao<T,Integer> foreignDao = ActiveRecord.getDao(ctx, foreignTable);
        
        List<T> list = null;
        if (dao != null && foreignDao != null){
            try {
                QueryBuilder<T, Integer> foreignQb = foreignDao.queryBuilder();
                foreignQb.where().eq(foreignField, foreignValue);
                
                QueryBuilder<T, Integer> qb = dao.queryBuilder();
                
                // join
                list = qb.join(foreignQb).query();
            } catch (SQLException e) {
                Log.e(TAG, "sql",e);
            } catch (Exception e){
                Log.e(TAG, "exception",e);
            }finally {
                ActiveRecord.releaseDao();
            }
        }
        if (list == null){
            list = new ArrayList<T>();
        }
        return list;
    }

}
