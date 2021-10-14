package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.todo.service.DbConnect;

public class TodoList {
	Connection conn;
	public TodoList() {
		this.conn = DbConnect.getConnection();
	}
	
	public void importData(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (title, memo, category, current_date, due_date)"
					+ " values (?, ?, ?, ?, ?);";
			
			int records = 0;
			while((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				
				int count = pstmt.executeUpdate();
				if (count > 0) records++;
				pstmt.close();
			}
			System.out.println(records + " records read!!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (title, memo, category, current_date, due_date, location, importance)"
				+ " values (?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getLocation());
			pstmt.setInt(7, t.getImportance());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int mult_del(String n1, String n2, String n3) {
		String sql = "delete from list where id in (" + n1 + ", " + n2 + ", " + n3 + ");";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set title=?, memo=?, category=?, current_date=?, due_date=?, location=?, importance=?"
				+ " where id = ?;";
		PreparedStatement pstmt;
		int count=0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTitle());
			pstmt.setString(2, t.getDesc());
			pstmt.setString(3, t.getCategory());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDue_date());
			pstmt.setString(6, t.getLocation());
			pstmt.setInt(7, t.getImportance());
			pstmt.setInt(8, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count; 
	}

	public ArrayList<TodoItem> getList() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			rsToItem(list, rs);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int getCount() {
		Statement stmt;
		int count=0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%"+keyword+"%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			rsToItem(list, rs);
			pstmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return list;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				String category = rs.getString("category");
				list.add(category);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			rsToItem(list, rs);
			pstmt.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getOrderedList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY "+ orderby;
			if (ordering==0) 
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			rsToItem(list, rs);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Boolean isDuplicate(String title) {
		ArrayList<TodoItem> list = getList();
		for (TodoItem item : list) {
			if (title.equals(item.getTitle())) return true;
		}
		return false;
	}
	
	public int completeItem(int num) {
		String sql = "update list set is_completed = 1 where id = " + num +";";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;	
	}
	
	public int cancelItemComp(int num) {
		String sql = "update list set is_completed = 0 where id = " + num +";";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;	
	}
	
	public int multComp(String n1, String n2, String n3) {
		String sql = "update list set is_completed = 1 where id in (" + n1 + ", " + n2 + ", " + n3 + ");";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<TodoItem> getCompList(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list WHERE is_completed = 1";
			ResultSet rs = stmt.executeQuery(sql);
			rsToItem(list, rs);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int editImportance(int num, int importance) {
		String sql = "update list set importance = " + importance +  " where id = " + num +";";
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			count = stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;	
	}
	
	public ArrayList<TodoItem> findImportance(int importance){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE importance = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, importance);
			ResultSet rs = pstmt.executeQuery();
			rsToItem(list, rs);
			pstmt.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public static void rsToItem(ArrayList<TodoItem> list, ResultSet rs) throws SQLException {
		while(rs.next()) {
			
			TodoItem t = new TodoItem(
					rs.getString("title"),
					rs.getString("memo"),
					rs.getString("category"),
					rs.getString("due_date"),
					rs.getString("location"),
					rs.getInt("importance")
			);
					
			int id = rs.getInt("id");
			t.setId(id);
			
			String current_date = rs.getString("current_date");
			t.setCurrent_date(current_date);
			
			if (rs.getInt("is_completed") == 1) t.setIs_Completed(1);
			else t.setIs_Completed(0);
			
			list.add(t);
		}
	}
}
