package ru.sergon.db_query_cours;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class ShowQueryAttActivity extends AppCompatActivity {
    DataBaseHelper myDbHelper;
    SQLiteDatabase db;
    String[] data = {"Table", "goods", "creator" ,"stack" , "place"};
    String[] rowdata = {};
    Cursor cursor;
    String queryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_query_att);
        myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();
    }
    public void onClick(View view){
        EditText tablen = (EditText) findViewById(R.id.showAttTable);
        EditText column = (EditText) findViewById(R.id.showAttColumn);
        TableLayout table = (TableLayout) findViewById(R.id.showAttLayout);
        if (table!=null)
            table.removeAllViews();
        TableRow rowName = new TableRow(this);
        table.addView(rowName, 0);
        queryText="SELECT "+column.getText().toString()+" FROM "+tablen.getText().toString();
        try {
            cursor = db.rawQuery(queryText, null);
            int rowID = 1;
            while (cursor.moveToNext()) {
                rowID++;
                TableRow row = new TableRow(this);
                table.addView(row, rowID);
                for (int j = 0; j < cursor.getColumnCount(); j++) {
                    if (j!=0) {
                        TextView item = new TextView(this);
                        item.setText(" " + cursor.getString(j) + " ");
                        item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        item.setTextSize(23);
                        row.addView(item, j);
                    }
                }
            }
            cursor.close();
            Context context = getApplicationContext();
            CharSequence text = "Все данные выведены!";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLiteException sql){
            Context context = getApplicationContext();
            CharSequence text = "Ошибка запроса, возможно вы ввели некорректные данные!";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
