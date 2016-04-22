package ru.sergon.db_query_cours;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ShowQueryActivity extends Activity {
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        Intent intent = getIntent();
        int counts = intent.getIntExtra("query", 0);
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();
        String queryText="";
        Cursor cursor1;
        TextView qvr = (TextView) findViewById(R.id.qvr);
        TableLayout queryTable = (TableLayout) findViewById(R.id.showQueryTable);
        TableRow rowName = (TableRow) findViewById(R.id.showNameRow);
        switch (counts){
            case 1:
                int stack = intent.getIntExtra("query1",0);
                qvr.setText("Запрос №1");
                queryText="SELECT creator.name as Название " +
                        "FROM creator " +
                        "WHERE creator._id NOT IN (SELECT id_creator from place where id_stack="+stack+")";
                break;
            case 2:
                String nameGoods = intent.getStringExtra("query2");
                qvr.setText("Запрос №2");
                queryText="select stack.name as Название  from stack " +
                        "where stack._id not in(select stack._id from stack,place,goods "+
                        "where stack._id=place.id_stack and place.id_goods=goods._id and goods.name=\""+nameGoods+"\")";
                break;
            case 3:
                String nameCreator = intent.getStringExtra("query3");
                qvr.setText("Запрос №3");
                queryText="select stack.name as Название from stack " +
                        "where stack._id not in(select stack._id from stack,place,creator "+
                        "where stack._id=place.id_stack and place.id_creator=creator._id and creator.name=\""+nameCreator+"\")";
                break;
            case 4:
                qvr.setText("Запрос №4");
                queryText="select creator.name as Название, sum(place.count) as Количество " +
                        "from creator, place " +
                        "where creator._id=place.id_creator " +
                        "group by name  " +
                        "order by sum(place.count) DESC " +
                        "Limit 1";
                break;
            case 5:
                String nameInGoods = intent.getStringExtra("query5");
                qvr.setText("Запрос №5");
                queryText="select distinct stack.name as Название " +
                        "from stack, place, goods " +
                        "where stack._id=place.id_stack and  " +
                        "goods._id=place.id_goods and goods.name=\""+nameInGoods+"\"";
                break;
            case 6:
                String stackName = intent.getStringExtra("query6");
                qvr.setText("Запрос №6");
                queryText="select max(goods.length) as Длина, max(goods.height) as Высота, max(goods.width) as Ширина"+
                        "from goods,stack,place " +
                        "where stack._id=place.id_stack and goods._id=place.id_goods and  stack.name=\""+stackName+"\"";
                break;
            case 7:
                qvr.setText("Запрос №7");
                queryText="select stack.name as Название from stack, creator, place " +
                        "where stack._id=place.id_stack and creator._id=place.id_creator " +
                        "group by stack.name " +
                        "having count(distinct creator.name)=(select count(_id) from creator)";
                break;
            case 8:
                qvr.setText("Запрос №8");
                queryText="select creator.name as Название from stack, creator, place " +
                        "where stack._id=place.id_stack and creator._id=place.id_creator " +
                        "group by creator.name " +
                        "having count(stack.name)>1";
                break;
            case 9:
                qvr.setText("Запрос №9");
                queryText="select stack.name as Название from stack,place, goods " +
                        "where stack._id=place.id_stack and goods._id=place.id_goods " +
                        "group by stack.name,goods.material,\"2\" " +
                        "having count(goods.material)>1";
                break;
            case 10:
                qvr.setText("Запрос №10");
                queryText="select c.name as Название, c.city as Город \n" +
                        "from place,creator as c \n" +
                        "where (SELECT count(d.city) from creator AS d WHERE c.city = d.city)>1 and c._id=place.id_creator \n" +
                        "group by name";
                break;
        }
        try {
            cursor1 = db.rawQuery(queryText, null);
            for (int i = 0; i < cursor1.getColumnCount(); i++) {
                TextView nameCol = new TextView(this);
                nameCol.setText(" " + cursor1.getColumnName(i) + " ");
                nameCol.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                nameCol.setTextSize(17);
                rowName.addView(nameCol);
            }
            int rowID = 0;
            while (cursor1.moveToNext()) {
                rowID++;
                TableRow row = new TableRow(this);
                queryTable.addView(row, rowID);
                for (int j = 0; j < cursor1.getColumnCount(); j++) {
                    TextView view = new TextView(this);
                    view.setText(cursor1.getString(j));
                    view.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    row.addView(view, j);
                }
            }
            cursor1.close();
            myDbHelper.close();
        } catch (SQLiteException sqle){
            Toast.makeText(getApplicationContext(), "Ошибка!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
