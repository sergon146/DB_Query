package ru.sergon.db_query_cours;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


public class DeleteActivity extends AppCompatActivity implements View.OnClickListener {
    DataBaseHelper myDbHelper;
    SQLiteDatabase db;
    AlertDialog alert;
    int idd;
    String tb;
    Context context;
    TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Удаление");
        myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();
        Intent intent = getIntent();
        int deleting = intent.getIntExtra("delete",0);
        TextView name = (TextView)findViewById(R.id.name);
        table = (TableLayout) findViewById(R.id.deleteLayout);
        TableRow rowName = new TableRow(this);
        table.addView(rowName, 0);
        String queryText="SELECT * FROM ";
        tb="";
        switch (deleting){
            case 1:
                name.setText("Удаление товара");
                queryText="select _id, name as Название, material as Материал, " +
                        "length as Длина,height as Высота, width as Ширина from goods";
                tb="goods";
                break;
            case 2:
                name.setText("Удаление изготовителя");
                queryText="select _id, name as Название, city as Город from creator";
                tb="creator";
                break;
            case 3:
                name.setText("Удаление стеллажа");
                queryText="select _id, name as Название, map as Расположение from stack";
                tb="stack";
                break;
            case 4:
                name.setText("Удаление расположения");
                queryText="select place._id, goods.name as 'Товар', creator.name as 'Изготовитель', " +
                        "stack.name as 'Стеллаж', count as Количество from place, goods, stack, creator " +
                        "where place.id_goods=goods._id and place.id_creator = creator._id and place.id_stack=stack._id";
                tb="place";
                break;
        }
        Cursor cursor1 = db.rawQuery(queryText, null);
        while (cursor1.moveToNext()) {
            TableRow row = new TableRow(this);
            row.setId(Integer.parseInt(cursor1.getString(0)));
            row.setOnClickListener(this);
            table.addView(row);
            for (int j = 1; j < cursor1.getColumnCount(); j++) {
                TextView item = new TextView(this);
                item.setText(" " + cursor1.getString(j) + " ");
                item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                item.setTextSize(23);
                row.addView(item);

            }
        }
        cursor1.close();
        context = getApplicationContext();
        CharSequence text = "Все данные выведены!";
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onClick(View view) {
        idd=view.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
        builder.setTitle("Удаление данных")
                .setMessage("Вы действительно ходите удалить выбранные данные?\n\n(Восстановление будет невозможно)")
                .setCancelable(true)
                .setPositiveButton("Удалить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.execSQL("DELETE FROM "+tb+" WHERE _id="+idd);
                                Toast.makeText(context, "Успешно удалено",
                                        Toast.LENGTH_LONG).show();
                                table.removeViewAt(idd);
                            }
                        })
                .setNegativeButton("Отмена",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        alert = builder.create();
        alert.show();
    }
}
