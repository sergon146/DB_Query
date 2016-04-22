package ru.sergon.db_query_cours;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;
import java.sql.SQLException;

public class MainActivity  extends Activity {
    Intent intent;
    String triggerText;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = myDbHelper.getWritableDatabase();

        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            myDbHelper.openDataBase();
        }catch(SQLException sqle){
            try {
                throw sqle;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mainShowQuery:
                intent = new Intent(this, ChooseQueryActivity.class);
                break;
            case R.id.mainAdd:
                intent = new Intent(this, ChooseTableActivity.class);
                intent.putExtra("main", 1);
                break;
            case R.id.mainEdit:
                intent = new Intent(this, ChooseTableActivity.class);
                intent.putExtra("main", 2);
                break;
            case R.id.mainDelete:
                intent = new Intent(this, ChooseTableActivity.class);
                intent.putExtra("main", 3);
                break;
            case R.id.mainShow:
                intent = new Intent(this, ChooseTableActivity.class);
                intent.putExtra("main", 4);
                break;
        }
        startActivity(intent);
    }
}