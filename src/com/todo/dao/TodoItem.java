package com.todo.dao;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TodoItem {
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private String location;
    private int id;
    private int is_completed;
    private int importance;
    
    public TodoItem(String title, String desc, String category, String due_date, String location, int importance){
        this.title=title;
        this.desc=desc;
        this.category = category;
        this.due_date = due_date;
        this.location = location;
        SimpleDateFormat date = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss");
        this.current_date = date.format(new Date());
        this.importance = importance;
        
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
    
    public String getLocation() {
    	return location;
    }
    
    public void setLocation(String location) {
    	this.location = location;
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
    
    public int getImportance() {
    	return importance;
    }
    
    public void setImportance(int importance) {
    	this.importance = importance;
    }
     
    public String toString() {
    	String mark;
    	if (importance == 1) mark = "!";
    	else if (importance == 2) mark = "!!";
    	else mark = "!!!";
    	
    	if (is_completed == 0)
    		return id + " [" + category + "] " + title + " - 설명: " + desc + " - 위치: " + location + " - 마감: " + due_date + " - " + current_date + " (" + mark + ")";
    	return id + " [" + category + "] " + title + " [V] - 설명: " + desc + " - 위치: " + location + " - 마감: " + due_date + " - " + current_date + " (" + mark + ")";
    }
}
