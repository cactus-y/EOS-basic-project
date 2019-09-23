package com.example.eos_basic_todo.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.eos_basic_todo.R;
import com.example.eos_basic_todo.data.database.MyDatabase;
import com.example.eos_basic_todo.data.entity.TodoItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
        private RecyclerView main_rcv;
        private FloatingActionButton main_fab;
        private MainTodoAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

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
                    Long time = System.currentTimeMillis();
                    TodoItem temp = new TodoItem(time.toString());
                    myDatabase.todoDao().insertTodo(temp);
                    adapter.addItem(temp);
                }
            });
        }
}
