package sac4java.mydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by it000483 on 8/9/17.
 */

public class DBManager {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    DBManager(Context context){

        this.context=context;
    }

    public DBManager open(){
        dbHelper=new DBHelper(context);
        db=dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insert(String name, String description, String state ){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.COL_NAME,name);
        contentValues.put(DBHelper.COL_DESCRIPTION,description);
        contentValues.put(DBHelper.COL_STATE,state);
        long result = db.insert(DBHelper.TABLE_NAME,null,contentValues);
        if(result== -1){
            return false;
        }

        return true;

    }

    public Cursor fetch() {
        String[] columns = new String[] {DBHelper.COL_NAME, DBHelper.COL_DESCRIPTION, DBHelper.COL_STATE };
        Cursor cursor = db.query(DBHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
