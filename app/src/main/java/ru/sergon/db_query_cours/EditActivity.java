package ru.sergon.db_query_cours;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class EditActivity extends Activity {
    Intent intent;
    DataBaseHelper myDbHelper;
    SQLiteDatabase db;
    int table,count;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();
        TextView name = (TextView) findViewById(R.id.editName);
        LinearLayout layout = (LinearLayout) findViewById(R.id.editLayout);
        intent = getIntent();
        table = intent.getIntExtra("edit",0);
        intent = getIntent();
        id = intent.getStringExtra("editrow");

        String queryText= "SELECT * from ";
        switch (table){
            case 1:
                name.setText("Редактирование товаров");
                queryText+="goods";
                break;
            case 2:
                name.setText("Редактирвоание изготовителей");
                queryText+="creator";
                break;
            case 3:
                name.setText("Редактирование стеллажей");
                queryText+="stack";
                break;
            case 4:
                name.setText("Редактирование расположений");
                queryText+="place";
                break;
        }
        queryText+=" WHERE _id= "+id;
        Cursor cursor1 = db.rawQuery(queryText, null);
        count = cursor1.getColumnCount();
        cursor1.moveToFirst();
        for (int i = 0; i < cursor1.getColumnCount(); i++) {
            if (i != 0) {
                TextView nameColl = new TextView(this);
                EditText innerr = new EditText(this);
                nameColl.setText(" " + cursor1.getColumnName(i) + ": ");
                nameColl.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                nameColl.setTextSize(20);
                nameColl.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                innerr.setId(i);
                layout.addView(nameColl);
                innerr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                innerr.setText(cursor1.getString(i));
                layout.addView(innerr);
            }
        }
        cursor1.close();
    }

    public void onClick(View view){
        Button editB = (Button)findViewById(R.id.editButton);
        myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();
        String queryText="DELETE FROM ";
        switch (table){
            case 1:
                queryText+="goods";
                break;
            case 2:
                queryText+="creator";
                break;
            case 3:
                queryText+="stack";
                break;
            case 4:
                queryText+="place";
                break;
        }
        queryText+=" WHERE _id= "+id;
        db.execSQL(queryText);
        queryText="INSERT INTO ";
        String startQuery = "SELECT * FROM ";
        switch (table) {
            case 1:
                queryText += "goods (";
                startQuery += "goods";
                break;
            case 2:
                queryText += "creator (";
                startQuery += "creator";
                break;
            case 3:
                queryText += "stack (";
                startQuery += "stack";
                break;
            case 4:
                queryText += "place (";
                startQuery += "place";
                break;
        }
        String pool;
        Cursor cursor1 = db.rawQuery(startQuery, null);
        EditText edit;
        cursor1.moveToFirst();
        for (int i =0; i<count;i++){
            if (i!=0) {
                pool = cursor1.getColumnName(i);
                if (i == count - 1) queryText += pool + ") VALUES (";
                else queryText += pool + ", ";
            }
        }
        for (int i = 0; i<count;i++){
            if (i!=0) {
                int fld = cursor1.getType(i);
                edit = (EditText) findViewById(i);
                pool = edit.getText().toString();

                if (fld == Cursor.FIELD_TYPE_STRING) {
                    if (i == count - 1) queryText += "\"" + pool + "\"" + ");";
                    else queryText += "\"" + pool + "\"" + ", ";
                } else if (fld != Cursor.FIELD_TYPE_NULL) {
                    if (i == count - 1) queryText += pool + ");";
                    else queryText += pool + ", ";
                }
            }
        }
        try {
            db.execSQL(queryText);
            cursor1.close();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editB.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            Context context = getApplicationContext();
            CharSequence text = "Успешно изменено!";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLiteException sqlte) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, queryText, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
