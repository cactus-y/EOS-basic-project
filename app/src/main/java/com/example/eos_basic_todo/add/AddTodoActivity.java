package com.example.eos_basic_todo.add;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.EntityDeletionOrUpdateAdapter;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.eos_basic_todo.R;
import com.example.eos_basic_todo.data.database.MyDatabase;
import com.example.eos_basic_todo.data.entity.TodoItem;
import com.example.eos_basic_todo.main.MainActivity;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddTodoActivity extends AppCompatActivity {

    private TextInputLayout til_title, til_sDate, til_dDate, til_memo;
    private ImageButton ib_sDate, ib_dDate;
    private final int START_DATE = 0;
    private final int DUE_DATE = 1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save_todo, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        til_title = findViewById(R.id.add_til_todo);
        til_sDate = findViewById(R.id.add_til_start_date);
        til_dDate = findViewById(R.id.add_til_due_date);
        til_memo = findViewById(R.id.add_til_memo);

        ib_sDate = findViewById(R.id.add_ibtn_start_date);
        ib_dDate = findViewById(R.id.add_ibtn_due_date);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Add Todo");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ib_sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(START_DATE);
            }
        });
        ib_dDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(DUE_DATE);
            }
        });

        til_sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(START_DATE);
            }
        });
        til_dDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(DUE_DATE);
            }
        });

    }

    private void showCalendar(final int mode) {
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(AddTodoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = "" + year + "/" + (month + 1) + "/" + dayOfMonth;
                if (mode == START_DATE) {
                    til_sDate.getEditText().setText(date);
                } else if (mode == DUE_DATE) {
                    til_dDate.getEditText().setText(date);
                } else {
                    //TODO: ERROR
                }
            }
        }, mYear, mMonth, mDay).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                finish();
                break;
            case R.id.menu_save_todo:
                String title = til_title.getEditText().getText().toString();
                String sDate = til_sDate.getEditText().getText().toString();
                String dDate = til_dDate.getEditText().getText().toString();
                String memo = til_memo.getEditText().getText().toString();

                if(title.equals("")){
                    til_title.setError("Title cannot be empty!");
                } else{
                    til_title.setError(null);
                }
                if(sDate.equals("")){
                    til_sDate.setError("Starting date cannot be empty!");
                } else{
                    til_sDate.setError(null);
                }
                if(dDate.equals("")){
                    til_dDate.setError("Due date cannot be empty!");
                } else{
                    til_dDate.setError(null);
                }
                if(!title.equals("") && !sDate.equals("") && !dDate.equals("")){
                    MyDatabase myDatabase = MyDatabase.getInstance(AddTodoActivity.this);
                    myDatabase.todoDao().insertTodo(new TodoItem(title, sDate, dDate, memo));
                    finish();
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
