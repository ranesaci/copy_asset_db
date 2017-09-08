package sac4java.mydb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by it000483 on 8/9/17.
 */

public class DBHelper extends SQLiteOpenHelper {


    public static String DATABASE_NAME = "myforts.db";
    public static String TABLE_NAME = "forts";
    public static String COL_ID = "ID";
    public static String COL_NAME = "NAME";
    public static String COL_DESCRIPTION = "DESCRIPTION";
    public static String COL_STATE = "STATE";
    public static int VESRION = 1;
    public static Context context;
    public static SQLiteDatabase db;

    public DBHelper(Context c) {
        super(c, DATABASE_NAME, null, VESRION);
        Log.i("DBHelper COndtructor","**************************");
        db=this.getWritableDatabase();

        try {
            createCopyOfDatabaseIfNeeded(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        context=c;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create table query
       /* String CREATE_TABLE = "create table " + TABLE_NAME + "(" + COL_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT NOT NULL, " + COL_DESCRIPTION + " TEXT," +
                COL_STATE + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_TABLE);*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //onCreate(sqLiteDatabase);

    }

    public static String getDBPath(Context c)
    {
        String file = Environment.getDataDirectory() +
                File.separator + "data" +
                File.separator + c.getPackageName() +
                File.separator + "databases";
        Log.i("getDBPath*******",file);
        return file;
    }


    public static String getDBFileName(Context c)
    {
        String file = Environment.getDataDirectory() +
                File.separator + "data" +
                File.separator + c.getPackageName() +
                File.separator + "databases" +
                File.separator + "myforts.db";
        Log.i("getDBFileName********",file);
        return file;
    }

    public static void createCopyOfDatabaseIfNeeded(Context c) throws FileNotFoundException, IOException
    {
        InputStream myInput = c.getAssets().open("pre_myforts.db");
        boolean check = checkIfTableExists();
        File dbdir = new File(getDBPath(c));
        if (!dbdir.exists())
            dbdir.mkdirs();
        File db = new File(getDBFileName(c));
        if(check){
            //means db exists, do nothing
        }else {
            //copy database
            OutputStream myOutput = new FileOutputStream(db);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myInput.close();
            myOutput.flush();
            myOutput.close();

        }
    }


    public static boolean checkDataBase(String pathToSaveDB){

        SQLiteDatabase checkDB = null;

        try{
            //String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(pathToSaveDB, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    public static boolean checkIfTableExists(){
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TABLE_NAME+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return  false;
    }

}
