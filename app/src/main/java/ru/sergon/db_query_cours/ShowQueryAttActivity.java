package ru.sergon.db_query_cours;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowQueryAttActivity extends AppCompatActivity {
    DataBaseHelper myDbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String queryText;
    String[] table = {"<таблица>","Товар", "Изготовитель", "Стеллаж", "Расположение"};

    String[] creator = {"Название", "Город"};
    String[] good = {"Название", "Материал", "Длина", "Высота", "Ширина"};
    String[] stack = {"Название", "Расположение"};
    String[] place = {"Товар", "Изготовитель", "Стеллаж", "Расположение", "Количество"};
    Activity main = this;
    Spinner spinner;
    int columnI;
    int tableI;

    TextView rowT;
    EditText colT;
    Spinner rowSpin;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_query_att);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Вывод по аттрибутам");
        myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();

        rowT = (TextView) findViewById(R.id.rowSpinText);
        rowSpin = (Spinner) findViewById(R.id.rowSpin);
        colT = (EditText) findViewById(R.id.valueText) ;
        show = (Button) findViewById(R.id.showAttButton) ;
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, table);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.tableSpin);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Таблица");


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (position==0) {
                    rowT.setVisibility(View.INVISIBLE);
                    rowSpin.setVisibility(Spinner.INVISIBLE);
                    colT.setVisibility(Spinner.INVISIBLE);
                    show.setVisibility(Spinner.INVISIBLE);
                } else {
                    rowT.setVisibility(View.VISIBLE);
                    rowSpin.setVisibility(Spinner.VISIBLE);
                    colT.setVisibility(Spinner.VISIBLE);
                    show.setVisibility(Spinner.VISIBLE);
                    ArrayAdapter<String> rowAdapter = null;
                    switch (position) {
                        case 1:
                            rowAdapter = new ArrayAdapter<>(main, android.R.layout.simple_spinner_item, good);
                            rowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case 2:
                            rowAdapter = new ArrayAdapter<>(main, android.R.layout.simple_spinner_item, creator);
                            rowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case 3:
                            rowAdapter = new ArrayAdapter<>(main, android.R.layout.simple_spinner_item, stack);
                            rowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            break;
                        case 4:
                            rowAdapter = new ArrayAdapter<>(main, android.R.layout.simple_spinner_item, place);
                            rowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            break;
                    }
                    rowSpin.setAdapter(rowAdapter);
                    rowSpin.setPrompt("Столбец");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


    }
    public void onClick(View view) {
        try {
            TableLayout table = (TableLayout) findViewById(R.id.showAttLayout);
            if (table!=null)
                table.removeAllViews();
            TableRow rowName = new TableRow(this);
            table.addView(rowName, 0);
            queryText = "SELECT * ";
            columnI = rowSpin.getSelectedItemPosition();
            tableI = spinner.getSelectedItemPosition();

            switch (tableI){
                case 1:   //Товар
                    queryText+="from goods where ";
                    switch (columnI){
                        case 0:
                            queryText+="name ";
                            break;
                        case 1:
                            queryText+="material ";
                            break;
                        case 2:
                            queryText+="length ";
                            break;
                        case 3:
                            queryText+="hight ";
                            break;
                        case 4:
                            queryText+="width ";
                            break;
                    }
                    break;
                case 2:   //Изготовитель
                    queryText+="from creator where ";
                    switch (columnI){
                        case 0:
                            queryText+="name ";
                            break;
                        case 1:
                            queryText+="city ";
                            break;
                    }
                    break;
                case 3:   //Стеллаж
                    queryText+="from stack where ";
                    switch (columnI){
                        case 0:
                            queryText+="name ";
                            break;
                        case 1:
                            queryText+="map ";
                            break;
                    }
                    break;
                case 4:   //Расположение
                    queryText+="from place where ";
                    switch (columnI){
                        case 0:
                            queryText+="id_goods ";
                            break;
                        case 1:
                            queryText+="id_creator ";
                            break;
                        case 2:
                            queryText+="id_stack ";
                            break;
                        case 3:
                            queryText+="count ";
                            break;
                    }
                    break;
            }
            queryText+="= '"+colT.getText().toString()+"'";
            cursor = db.rawQuery(queryText, null);
            while (cursor.moveToNext()) {
                TableRow row = new TableRow(this);
                for (int j = 0; j < cursor.getColumnCount(); j++) {
                    if (j!=0) {
                        TextView item = new TextView(this);
                        item.setText(" " + cursor.getString(j) + " ");
                        item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        item.setTextSize(23);
                        row.addView(item);
                    }
                }
                table.addView(row);
            }
            cursor.close();
            Context context = getApplicationContext();
            CharSequence text = "Все данные выведены!";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        } catch (SQLiteException sql) {
            Context context = getApplicationContext();
            CharSequence text = "Ошибка запроса, возможно вы ввели некорректные данные!";
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
