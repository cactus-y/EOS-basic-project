package com.example.eos_basic_todo.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Todo")
public class TodoItem {
    public TodoItem(String title, String start_date, String due_date, String memo) {
        this.title = title;
        this.start_date = start_date;
        this.due_date = due_date;
        this.memo = memo;
        this.checked = false;
    }

    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "start")
    private String start_date;

    @ColumnInfo(name = "due")
    private String due_date;

    @ColumnInfo(name = "memo")
    private String memo;

    @Ignore
    public TodoItem(String title){
        this.title = title;
        due_date = null;
        checked = false;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @ColumnInfo(name = "checked")
    private Boolean checked;
}
