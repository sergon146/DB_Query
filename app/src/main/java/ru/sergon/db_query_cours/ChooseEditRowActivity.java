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

public class ChooseEditRowActivity extends AppCompatActivity implements View.OnClickListener {
    TableLayout table;
    Intent intent;
    int editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_edit_row);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Редактирование");
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        intent = getIntent();
        editing = intent.getIntExtra("edit",0);
        TextView name = (TextView) findViewById(R.id.addTableName);
        table = (TableLayout) findViewById(R.id.addTable);
        table.removeAllViews();
        TableRow rowName = new TableRow(this);
        table.addView(rowName, 0);
        String queryText="";
        switch (editing){
            case 1:
                name.setText("Выберете товар для редактирования");
                queryText="select _id, name as Название, material as Материал, " +
                        "length as Длина,height as Высота, width as Ширина from goods";
                break;
            case 2:
                name.setText("Выберете изготовителя для редактирования");
                queryText="select _id, name as Название, city as Город from creator";
                break;
            case 3:
                name.setText("Выберете стеллаж для редактирования");
                queryText="select _id, name as Название, map as Расположение from stack";
                break;
            case 4:
                name.setText("Выберете расположение для редоактирования");
                queryText="select place._id, goods.name as 'Товар', creator.name as 'Изготовитель', " +
                        "stack.name as 'Стеллаж', count as Количество from place, goods, stack, creator " +
                        "where place.id_goods=goods._id and place.id_creator = creator._id and place.id_stack=stack._id";
                break;
        }
        Cursor cursor1 = db.rawQuery(queryText, null);

        for (int i = 0; i < cursor1.getColumnCount(); i++) {
            if (i!=0) {
                TextView nameCol = new TextView(this);
                nameCol.setText(" " + cursor1.getColumnName(i) + " ");
                nameCol.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                nameCol.setTextSize(23);
                rowName.addView(nameCol);
            }  else {
                TextView nameCol = new TextView(this);
                nameCol.setText(" " + cursor1.getColumnName(i) + " ");
                nameCol.setVisibility(View.GONE);
                rowName.addView(nameCol);
            }
        }
        int rowID = 0;
        while (cursor1.moveToNext()) {
            rowID++;
            TableRow row = new TableRow(this);
            row.setId(rowID);
            row.setOnClickListener(this);
            table.addView(row);
            for (int j = 0; j < cursor1.getColumnCount(); j++) {
                if (j!=0) {
                    TextView item = new TextView(this);
                    item.setText(" " + cursor1.getString(j) + " ");
                    item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    item.setTextSize(23);
                    item.setId(j);
                    row.addView(item);
                }  else{
                    TextView item = new TextView(this);
                    item.setText(" " + cursor1.getString(j) + " ");
                    item.setVisibility(View.GONE);
                    item.setId(j);
                    row.addView(item);
                }
            }
        }
        cursor1.close();
        Context context = getApplicationContext();
        CharSequence text = "Все данные выведены!";
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }


    public  void onClick(View view){
        TableRow row = (TableRow) view ;
        TextView inner = (TextView) row.getChildAt(0);
        String value =inner.getText().toString().substring(1,inner.getText().toString().length()-1);
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, value, Toast.LENGTH_SHORT);
        toast.show();

        intent = new Intent(this, EditActivity.class);
        intent.putExtra("editrow", value);
        intent.putExtra("edit", editing);
        startActivity(intent);
    }
}
