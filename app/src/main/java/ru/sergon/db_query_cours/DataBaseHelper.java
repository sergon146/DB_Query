package ru.sergon.db_query_cours;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = "/data/data/ru.sergon.db_query_cours/databases/";
    private static String DB_NAME = "warehouse";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        try {
            createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
        }else{
            try {
                this.getWritableDatabase();
            }
            catch (SQLiteException ex){
                this.getReadableDatabase();
            }
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase(){
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }
    private void copyDataBase() throws IOException{

        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}