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
    String delID;
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
                name.setText("DELETE FROM Goods");
                queryText+="goods";
                tb="goods";
                break;
            case 2:
                name.setText("DELETE FROM Creator");
                queryText+="creator";
                tb="creator";
                break;
            case 3:
                name.setText("DELETE FROM Stack");
                queryText+="stack";
                tb="stack";
                break;
            case 4:
                name.setText("DELETE FROM Place");
                queryText+="place";
                tb="place";
                break;
        }
        Cursor cursor1 = db.rawQuery(queryText, null);
        int rowID = 0;
        while (cursor1.moveToNext()) {
            rowID++;
            TableRow row = new TableRow(this);
            row.setId(rowID);
            row.setOnClickListener(this);
            table.addView(row);
            for (int j = 0; j < cursor1.getColumnCount(); j++) {
                if (j==0) delID=cursor1.getString(j);
                TextView item = new TextView(this);
                item.setText(" " + cursor1.getString(j) + " ");
                item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                item.setTextSize(23);
                item.setId(j);
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
                .setMessage("Вы действительно ходите удалить данную строку (ID="+idd+")?\n\n(Восстановление будет невозможно)")
                .setCancelable(true)
                .setPositiveButton("Удалить",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                db.execSQL("DELETE FROM "+tb+" WHERE _id="+delID);
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
