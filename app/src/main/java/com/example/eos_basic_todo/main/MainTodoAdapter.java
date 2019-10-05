package com.example.eos_basic_todo.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eos_basic_todo.R;
import com.example.eos_basic_todo.data.database.MyDatabase;
import com.example.eos_basic_todo.data.entity.TodoItem;

import java.util.ArrayList;
import java.util.List;

public class MainTodoAdapter extends RecyclerView.Adapter<MainTodoViewHolder> {

    private ArrayList<TodoItem> itemList = new ArrayList<>();

    public void submitAll(List<TodoItem> list){
        itemList.clear();
        itemList.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(TodoItem item){
        itemList.add(item);
        notifyDataSetChanged();
    }

    public void removeAllItem(){
        itemList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainTodoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        final MainTodoViewHolder viewHolder = new MainTodoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TodoItem temp = itemList.get(viewHolder.getAdapterPosition());
                temp.setChecked(!temp.getChecked());
                MyDatabase myDatabase = MyDatabase.getInstance(parent.getContext());
                myDatabase.todoDao().updateTodo(temp);
                notifyItemChanged(viewHolder.getAdapterPosition());
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final TodoItem temp = itemList.get(viewHolder.getAdapterPosition());
                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(temp.getTitle());
                final String[] items = {"수정", "삭제", "취소"};
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int position) {
                        switch(items[position]){
                            case "수정":
                                final AlertDialog.Builder edit = new AlertDialog.Builder(builder.getContext());
                                final EditText input = new EditText(builder.getContext());
                                edit.setTitle("Edit Item");
                                edit.setView(input);
                                edit.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        itemList.set(viewHolder.getAdapterPosition(),new TodoItem(input.getText().toString()));
                                        notifyItemChanged(viewHolder.getAdapterPosition());
                                    }
                                });
                                edit.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                edit.show();
                                break;
                            case "삭제":
                                itemList.remove(temp);
                                MyDatabase myDatabase = MyDatabase.getInstance(parent.getContext());
                                myDatabase.todoDao().deleteTodo(temp);
                                notifyItemRemoved(viewHolder.getAdapterPosition());
                                break;
                            case "취소":
                                break;
                        }
                    }
                });
                builder.show();
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainTodoViewHolder holder, int position) {
        holder.onBind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}
