package ru.sergon.db_query_cours;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@Deprecated
public class CreateQueryActivity extends AppCompatActivity {
    private EditText createQuery;
    private SQLiteDatabase db;
    private Button enter;
    TableLayout table;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_query);
        createQuery = (EditText) findViewById(R.id.createQuery);
        createQuery.setText("select * from goods");
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();
        enter = (Button) findViewById(R.id.createQueryExe);

    }
    public void onClick(View view) {
        Cursor cursor1;

        table = (TableLayout) findViewById(R.id.createQueryTable);
        table.removeAllViews();
        TableRow rowName = new TableRow(this);
        table.addView(rowName, 0);

        switch (view.getId()) {
            case R.id.createQueryExe:
                if (!(createQuery.getText().equals(""))) {
                    try {
                        cursor1 = db.rawQuery(createQuery.getText().toString(), null);

                        for (int i = 0; i < cursor1.getColumnCount(); i++) {
                            TextView nameCol = new TextView(this);
                            nameCol.setText(" " + cursor1.getColumnName(i) + " ");
                            nameCol.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                            rowName.addView(nameCol);
                        }
                        int rowID = 0;
                        while (cursor1.moveToNext()) {
                            rowID++;
                            TableRow row = new TableRow(this);
                            table.addView(row, rowID);
                            for (int j = 0; j < cursor1.getColumnCount(); j++) {
                                TextView item = new TextView(this);
                                item.setText(" " + cursor1.getString(j) + " ");
                                item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                row.addView(item, j);

                            }
                        }


                        cursor1.close();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(enter.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                        Context context = getApplicationContext();
                        CharSequence text = "Запрос выполнен успешно!";
                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                        toast.show();
                    } catch (SQLiteException sqlte) {
                        Context context = getApplicationContext();
                        CharSequence text = "Ошибка запроса!";
                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
                break;
            case R.id.createQueryClear:
                createQuery.setText("");
                table.removeAllViews();
                Context context = getApplicationContext();
                CharSequence text = "Очищено!";
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
                toast.show();


                break;
        }

    }


}
