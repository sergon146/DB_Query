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



public class AddActivity extends Activity {
    DataBaseHelper myDbHelper;
    SQLiteDatabase db;
    int adding;
    int  count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();
        String queryText="select * from ";
        TextView name = (TextView) findViewById(R.id.addName);
        LinearLayout layout = (LinearLayout) findViewById(R.id.addLayout);
        Intent intent = getIntent();
        adding = intent.getIntExtra("addT",0);
        switch (adding){
            case 1:
                name.setText("Добавление товара");
                queryText+="goods";
                break;
            case 2:
                name.setText("Добавление изготовителя");
                queryText+="creator";
                break;
            case 3:
                name.setText("Добавление стеллажа");
                queryText+="stack";
                break;
            case 4:
                name.setText("Добавления расположения");
                queryText+="place";
                break;
        }
        Cursor cursor1 = db.rawQuery(queryText, null);
        cursor1.moveToFirst();
        count = cursor1.getColumnCount();
        for (int i = 0; i < cursor1.getColumnCount(); i++) {
            TextView nameCol = new TextView(this);
            EditText inner = new EditText(this);
            if (i!=0) {
                nameCol.setText(" " + cursor1.getColumnName(i) + ": ");
                nameCol.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                nameCol.setTextSize(20);
                nameCol.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.addView(nameCol);
                inner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                inner.setId(i);
                layout.addView(inner);
            }else {
                inner.setText("null");
                inner.setVisibility(View.GONE);
                nameCol.setVisibility(View.GONE);
                layout.addView(nameCol);
                inner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                inner.setId(i);
                layout.addView(inner);
            }
        }
        cursor1.close();
    }

    public void onClick(View view){
        Button add = (Button) findViewById(R.id.addButton);
        Intent intent = getIntent();
        adding = intent.getIntExtra("addT",0);
        EditText edit;
        String pool;
        String queryText="INSERT INTO ";
        myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();
        String startQuery = "SELECT * FROM ";
        switch (adding){
            case 1:
                queryText+="goods (";
                startQuery+="goods";
                break;
            case 2:
                queryText+="creator (";
                startQuery+="creator";
                break;
            case 3:
                queryText+="stack (";
                startQuery+="stack";
                break;
            case 4:
                queryText+="place (";
                startQuery+="place";
                break;
        }
        Cursor cursor1 = db.rawQuery(startQuery, null);
        cursor1.moveToFirst();
        for (int i =0; i<count;i++){
            pool = cursor1.getColumnName(i);
            if (i==count-1) queryText+=pool+") VALUES (";
            else queryText+=pool+", ";
        }
        for (int i = 0; i<count;i++){
            int fld = cursor1.getType(i);
            edit= (EditText) findViewById(i);
            pool =  edit.getText().toString();

            if (fld == Cursor.FIELD_TYPE_STRING){
                if (i==count-1) queryText+="\""+pool+"\""+");";
                else  queryText+="\""+pool+"\""+", ";
            } else if (fld != Cursor.FIELD_TYPE_NULL){
                if (i==count-1) queryText+=pool+");";
                else  queryText+=pool+", ";
            }
        }
        try {
            db.execSQL(queryText);
            cursor1.close();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(add.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            Context context = getApplicationContext();
            CharSequence text = "Успешно добавлено!";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLiteException sqlte) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, queryText, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
