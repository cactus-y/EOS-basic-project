package com.example.eos_basic_todo.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eos_basic_todo.data.entity.TodoItem;

import java.util.List;

@Dao
public interface TodoDao {
    @Insert
    void insertTodo(TodoItem item);

    @Delete
    void deleteTodo(TodoItem item);

    @Update
    void updateTodo(TodoItem item);

    @Query("SELECT * FROM Todo")
    List<TodoItem> getAllTodo();

    @Query("SELECT * FROM Todo WHERE id = :id")
    TodoItem getTodo(int id);
}
