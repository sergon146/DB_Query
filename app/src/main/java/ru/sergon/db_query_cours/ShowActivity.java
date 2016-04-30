package ru.sergon.db_query_cours;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class ShowActivity extends AppCompatActivity {
    TableLayout table;
    String queryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Вывод");
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        Intent intent = getIntent();
        int showing = intent.getIntExtra("show",0);
        TextView name = (TextView) findViewById(R.id.addTableName);
        table = (TableLayout) findViewById(R.id.addTable);
        table.removeAllViews();
        TableRow rowName = new TableRow(this);
        table.addView(rowName, 0);
        queryText="";
        switch (showing){
            case 1:
                name.setText("Все товары");
                queryText="select name as Название, material as Материал, " +
                        "length as Длина,height as Высота, width as Ширина from goods";
                break;
            case 2:
                name.setText("Все изготовители");
                queryText="select name as Название, city as Город from creator";
                break;
            case 3:
                name.setText("Все стеллажи");
                queryText="select name as Название, map as Расположение from stack";
                break;
            case 4:
                name.setText("Все расположения");
                queryText="select goods.name as 'Товар', creator.name as 'Изготовитель', " +
                        "stack.name as 'Стеллаж', count as Количество from place, goods, stack, creator " +
                        "where place.id_goods=goods._id and place.id_creator = creator._id and place.id_stack=stack._id";
                break;

        }
        Cursor cursor1 = db.rawQuery(queryText, null);
        int rowID = 0;
        rowName = new TableRow(this);
        table.addView(rowName, rowID);
        for (int j = 0; j < cursor1.getColumnCount(); j++) {
            TextView item = new TextView(this);
            item.setText(" " + cursor1.getColumnName(j) + " ");
            item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            item.setTextSize(23);
            rowName.addView(item, j);
        }
        while (cursor1.moveToNext()) {
            rowID++;
            TableRow row = new TableRow(this);
            table.addView(row, rowID);

            for (int j = 0; j < cursor1.getColumnCount(); j++) {
                    TextView item = new TextView(this);
                    item.setText(" " + cursor1.getString(j) + " ");
                    item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    item.setTextSize(23);
                    row.addView(item, j);
            }
        }
        cursor1.close();
        Context context = getApplicationContext();
        CharSequence text = "Все данные выведены!";
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }
}
