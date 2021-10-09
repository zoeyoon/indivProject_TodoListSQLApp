package com.todo.dao;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private int id;
    private int is_completed;

    public TodoItem(String title, String desc, String category, String due_date){
        this.title=title;
        this.desc=desc;
        this.category = category;
        this.due_date = due_date;
        SimpleDateFormat date = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        this.current_date = date.format(new Date());
        
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
    
    public String getCategory() {
    	return category;
    }
     
    public void setCategory(String category) {
    	this.category = category;
    }
     
    public String getDue_date() {
    	return due_date;
    }
    
    public void setDue_date(String due_date) {
    	this.due_date = due_date;
    }
    
    public int getId(){
    	return id;
    }
    
    public void setId(int index) {
    	this.id = index;
    }
    
    public int getIs_Completed() {
    	return is_completed;
    }
    
    public void setIs_Completed(int num) {
    	this.is_completed = num;
    }
     
    public String toString() {
    	if (is_completed == 0)
    		return id + " [" + category + "] " + title + " - " + desc + " - " + due_date + " - " + current_date;
    	return id + " [" + category + "] " + title + " [V] - " + desc + " - " + due_date + " - " + current_date;
    }
}
