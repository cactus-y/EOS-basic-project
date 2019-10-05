package com.example.eos_basic_todo.main;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.eos_basic_todo.R;
import com.example.eos_basic_todo.data.database.MyDatabase;
import com.example.eos_basic_todo.data.entity.TodoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.chrono.MinguoChronology;
import java.util.List;

public class MainActivity extends AppCompatActivity {
        private RecyclerView main_rcv;
        private FloatingActionButton main_fab;
        private MainTodoAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_all_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            ActionBar actionBar = getSupportActionBar();
            if(actionBar != null){
                actionBar.setTitle("TODO APP");
            }

            main_rcv = findViewById(R.id.main_rcv);
            main_fab = findViewById(R.id.main_fab);
            adapter = new MainTodoAdapter();

            main_rcv.setAdapter(adapter);
            main_rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

            final MyDatabase myDatabase = MyDatabase.getInstance(this);
            List<TodoItem> list_item = myDatabase.todoDao().getAllTodo();
            adapter.submitAll(list_item);

            main_fab.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final EditText input = new EditText(MainActivity.this);
                    builder.setTitle("Add Item");
                    builder.setView(input);
                    builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TodoItem t = new TodoItem(input.getText().toString());
                            myDatabase.todoDao().insertTodo(t);
                            adapter.addItem(t);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                    /*Long time = System.currentTimeMillis();
                    TodoItem temp = new TodoItem(time.toString());
                    myDatabase.todoDao().insertTodo(temp);
                    adapter.addItem(temp);
                    */
                }
            });
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.delete_all_item:
                adapter.removeAllItem();
                MyDatabase myDatabase = MyDatabase.getInstance(this);
                myDatabase.todoDao().deleteAllTodo();
        }
        return super.onOptionsItemSelected(item);
    }
}
