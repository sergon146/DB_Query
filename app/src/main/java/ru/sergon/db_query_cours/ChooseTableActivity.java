package ru.sergon.db_query_cours;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChooseTableActivity extends AppCompatActivity {
    int choose;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_table);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        choose = getIntent().getIntExtra("main",0);
    }

    public void onClick(View view){
        try {
            switch (choose) {
                case 1:
                    intent = new Intent(this, AddActivity.class);
                    switch (view.getId()) {
                        case R.id.chooseGoods:
                            intent.putExtra("addT", 1);
                            break;
                        case R.id.chooseCreator:
                            intent.putExtra("addT", 2);
                            break;
                        case R.id.chooseStack:
                            intent.putExtra("addT", 3);
                            break;
                        case R.id.choosePlace:
                            intent.putExtra("addT", 4);
                            break;
                    }

                    break;
                case 2:
                    intent = new Intent(this, ChooseEditRowActivity.class);
                    switch (view.getId()) {
                        case R.id.chooseGoods:
                            intent.putExtra("edit", 1);
                            break;
                        case R.id.chooseCreator:
                            intent.putExtra("edit", 2);
                            break;
                        case R.id.chooseStack:
                            intent.putExtra("edit", 3);
                            break;
                        case R.id.choosePlace:
                            intent.putExtra("edit", 4);
                            break;
                    }
                    break;
                case 3:
                    intent = new Intent(this, DeleteActivity.class);
                    switch (view.getId()) {
                        case R.id.chooseGoods:
                            intent.putExtra("delete", 1);
                            break;
                        case R.id.chooseCreator:
                            intent.putExtra("delete", 2);
                            break;
                        case R.id.chooseStack:
                            intent.putExtra("delete", 3);
                            break;
                        case R.id.choosePlace:
                            intent.putExtra("delete", 4);
                            break;
                    }
                    break;
                case 4:
                    intent = new Intent(this, ShowActivity.class);
                    switch (view.getId()) {
                        case R.id.chooseGoods:
                            intent.putExtra("show", 1);
                            break;
                        case R.id.chooseCreator:
                            intent.putExtra("show", 2);
                            break;
                        case R.id.chooseStack:
                            intent.putExtra("show", 3);
                            break;
                        case R.id.choosePlace:
                            intent.putExtra("show", 4);
                            break;
                    }
                    break;
            }
            startActivity(intent);
        }catch (Throwable ignored){}
    }
    @Override
    public void onResume() {
        super.onResume();
        setTitle("Выберите таблицу");

    }

}
