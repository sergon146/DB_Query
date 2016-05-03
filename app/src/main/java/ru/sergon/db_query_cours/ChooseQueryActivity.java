package ru.sergon.db_query_cours;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class ChooseQueryActivity extends AppCompatActivity {
    EditText nameGoods,nameCreator,nameInGoods,stackName;
    Cursor cursor;
    SQLiteDatabase db;

    ArrayList<String> query1, query2, query3, query4, query5;
    Spinner spinner1, spinner2, spinner3,spinner4, spinner5;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_query);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        query1 = new ArrayList<>();
        query2 = new ArrayList<>();
        query3 = new ArrayList<>();
        query4 = new ArrayList<>();
        query5 = new ArrayList<>();
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();
        setTitle("Запросы");
        cursor = db.rawQuery("select distinct name from stack", null);
        while (cursor.moveToNext()) {
            query1.add(cursor.getString(0));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, query1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1 = (Spinner) findViewById(R.id.stack);
        spinner1.setAdapter(adapter);

        cursor = db.rawQuery("select distinct name from goods", null);
        while (cursor.moveToNext()) {
            query2.add(cursor.getString(0));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, query2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2 = (Spinner) findViewById(R.id.nameGoods);
        spinner2.setAdapter(adapter);

        cursor = db.rawQuery("select distinct name from creator", null);
        while (cursor.moveToNext()) {
            query3.add(cursor.getString(0));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, query3);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3 = (Spinner) findViewById(R.id.nameCreator);
        spinner3.setAdapter(adapter);

        cursor = db.rawQuery("select distinct name from goods", null);
        while (cursor.moveToNext()) {
            query4.add(cursor.getString(0));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, query4);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner4 = (Spinner) findViewById(R.id.nameInGoods);
        spinner4.setAdapter(adapter);

        cursor = db.rawQuery("select distinct name from stack", null);
        while (cursor.moveToNext()) {
            query5.add(cursor.getString(0));
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, query5);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner5 = (Spinner) findViewById(R.id.stackName);
        spinner5.setAdapter(adapter);

    }

    public void onClick(View view) {
        Intent intent = new Intent(this, ShowQueryActivity.class);
        switch (view.getId()){
            case R.id.showQueryAtt:
                intent = new Intent(this, ShowQueryAttActivity.class);
                break;
            case R.id.query1:
                intent.putExtra("query", 1);
                intent.putExtra("query1", spinner1.getSelectedItem().toString());
                break;
            case R.id.query2:
                    intent.putExtra("query", 2);
                    intent.putExtra("query2", spinner2.getSelectedItem().toString());
                break;
            case R.id.query3:
                intent.putExtra("query", 3);
                intent.putExtra("query3", spinner3.getSelectedItem().toString());
                break;
            case R.id.query4:
                intent.putExtra("query", 4);
                break;
            case R.id.query5:
                intent.putExtra("query", 5);
                intent.putExtra("query5", spinner4.getSelectedItem().toString());
                break;
            case R.id.query6:
                intent.putExtra("query", 6);
                intent.putExtra("query6", spinner5.getSelectedItem().toString());
                break;
            case R.id.query7:
                    intent.putExtra("query", 7);
                break;
            case R.id.query8:
                intent.putExtra("query", 8);
                break;
            case R.id.query9:
                intent.putExtra("query", 9);
                break;
            case R.id.query10:
                intent.putExtra("query", 10);
                break;
        }
        startActivity(intent);
    }
}
