package ru.sergon.db_query_cours;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.InputMismatchException;

public class ChooseQueryActivity extends AppCompatActivity {
    EditText stack,nameGoods,nameCreator,nameInGoods,stackName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_query);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Запросы");
    }

    public void onClick(View view) {
        boolean start=false;
        Intent intent = new Intent(this, ShowQueryActivity.class);
        switch (view.getId()){
            case R.id.showQueryAtt:
                intent = new Intent(this, ShowQueryAttActivity.class);
                start=true;
                break;
            case R.id.query1:
                try {
                    intent.putExtra("query", 1);
                    stack = (EditText)findViewById(R.id.query1text);
                    intent.putExtra("query1", Integer.parseInt(stack.getText().toString()));
                    start=true;
                } catch (Throwable e){
                    Toast.makeText(getApplicationContext(),"Некорректные данные!",Toast.LENGTH_SHORT);
                }
                break;
            case R.id.query2:
                nameGoods = (EditText) findViewById(R.id.nameGoods);
                if (nameGoods.getText().toString().equals("")) start=false;
                else {
                    intent.putExtra("query", 2);
                    intent.putExtra("query2", nameGoods.getText().toString());
                    start = true;
                }
                break;
            case R.id.query3:
                nameCreator = (EditText) findViewById(R.id.nameCreator);
                if (nameCreator.getText().toString().equals("")) start=false;
                else {
                    intent.putExtra("query3", nameCreator.getText().toString());
                    intent.putExtra("query", 3);
                    start = true;
                }
                break;
            case R.id.query4:
                intent.putExtra("query", 4);
                start=true;
                break;
            case R.id.query5:
                nameInGoods = (EditText) findViewById(R.id.nameInGoods);
                if (nameInGoods.getText().toString().equals("")) start=false;
                else {
                    intent.putExtra("query5", nameInGoods.getText().toString());
                    intent.putExtra("query", 5);
                    start = true;
                    break;
                }
            case R.id.query6:
                stackName = (EditText) findViewById(R.id.stackName);
                if (stackName.getText().toString().equals("")) start=false;
                else {
                    intent.putExtra("query6", stackName.getText().toString());
                    intent.putExtra("query", 6);
                    start = true;
                }
                break;
            case R.id.query7:
                    intent.putExtra("query", 7);
                    start = true;
                break;
            case R.id.query8:
                intent.putExtra("query", 8);
                start = true;
                break;
            case R.id.query9:
                intent.putExtra("query", 9);
                start = true;
                break;
            case R.id.query10:
                intent.putExtra("query", 10);
                start = true;
                break;
        }
        if (start)
        startActivity(intent);
    }
}
