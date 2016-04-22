package ru.sergon.db_query_cours;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseEditRowActivity extends Activity implements View.OnClickListener {
    TableLayout table;
    Intent intent;
    int editing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_edit_row);
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        SQLiteDatabase db = myDbHelper.getReadableDatabase();
        intent = getIntent();
        editing = intent.getIntExtra("edit",0);
        TextView name = (TextView) findViewById(R.id.addTableName);
        table = (TableLayout) findViewById(R.id.addTable);
        table.removeAllViews();
        TableRow rowName = new TableRow(this);
        table.addView(rowName, 0);
        String queryText="select * from ";
        switch (editing){
            case 1:
                name.setText("Goods");
                queryText+="goods";
                break;
            case 2:
                name.setText("Creator");
                queryText+="creator";
                break;
            case 3:
                name.setText("Stack");
                queryText+="stack";
                break;
            case 4:
                name.setText("Place");
                queryText+="place";
                break;
        }
        Cursor cursor1 = db.rawQuery(queryText, null);

        for (int i = 0; i < cursor1.getColumnCount(); i++) {
            TextView nameCol = new TextView(this);
            nameCol.setText(" " + cursor1.getColumnName(i) + " ");
            nameCol.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            nameCol.setTextSize(23);
            rowName.addView(nameCol);
        }
        int rowID = 0;
        while (cursor1.moveToNext()) {
            rowID++;
            TableRow row = new TableRow(this);
            row.setId(rowID);
            row.setOnClickListener(this);
            table.addView(row);
            for (int j = 0; j < cursor1.getColumnCount(); j++) {
                TextView item = new TextView(this);
                item.setText(" " + cursor1.getString(j) + " ");
                item.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                item.setTextSize(23);
                item.setId(j);
                row.addView(item);
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
