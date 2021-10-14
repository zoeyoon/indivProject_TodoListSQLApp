package com.todo.service;

import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		
		String title, desc, category, due_date, location;
		int importance;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n"
				+ "========== 아이템 추가 ==========\n"
				+ "제목을 입력하세요 > ");
		
		title = sc.nextLine();
		
		if (l.isDuplicate(title)) {
			System.out.println("======== 중복된 제목입니다 ========");
			return;
		}
		
		System.out.print("카테고리를 입력하세요 > ");
		category = sc.nextLine().trim();
		
		System.out.print("설명 내용을 입력하세요 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감 일자를 입력하세요 (년도/월/일) > ");
		due_date = sc.nextLine().trim();
		
		System.out.print("위치를 입력하세요 > ");
		location = sc.nextLine().trim();
		
		System.out.print("중요도를 입력하세요 (1 to 3) > ");
		importance = sc.nextInt();
		
		TodoItem t = new TodoItem(title, desc, category, due_date, location, importance);
		if(l.addItem(t) > 0)
			System.out.println("========== 추가 성공! ==========");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "========== 아이템 삭제 ==========\n"
				+ "삭제할 아이템의 번호를 입력하세요 > ");
		int num = sc.nextInt();
		if (l.deleteItem(num) > 0)
			System.out.println("========== 삭제 완료! ==========");
	}

	public static void mult_del(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("삭제할 아이템의 번호 3개를 입력하세요 > ");
		String input = sc.nextLine();
		StringTokenizer st = new StringTokenizer(input, " ");
		if(l.mult_del(st.nextToken(), st.nextToken(), st.nextToken())> 0)
				System.out.println("======== 삭제 처리 성공! ========");
		else System.out.println("======== 삭제 처리 실패! ========");
	}

	public static void updateItem(TodoList l) {
		String new_title, new_description, new_category, new_due_date, new_location;
		int new_importance;
		Scanner sc = new Scanner(System.in);
		System.out.print("\n"
				+ "========== 아이템 수정 ==========\n"
				+ "수정할 아이템의 번호를 입력하세요 > ");
		int num = sc.nextInt();
		sc.nextLine();
			
		System.out.print("새로운 제목을 입력하세요 > ");
		new_title = sc.nextLine().trim();
			
		if (l.isDuplicate(new_title)) {
			System.out.println("======== 중복된 제목입니다 ========");
			return;
		}
			
		System.out.print("새로운 카테고리를 입력하세요 > ");
		new_category = sc.nextLine().trim();
			
		System.out.print("새로운 설명 내용을 입력하세요 > ");
		new_description = sc.nextLine().trim();
			
		System.out.print("새로운 마감일자를 입력하세요 (년도/월/일) > ");
		new_due_date = sc.nextLine().trim();
		
		System.out.print("새로운 위치를 입력하세요 > ");
		new_location = sc.nextLine().trim();
		
		System.out.print("새로운 중요도를 입력하세요 (1 to 3) > ");
		new_importance = sc.nextInt();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date, new_location, new_importance);
		t.setId(num);
		if(l.updateItem(t) > 0)
				System.out.println("========== 수정 완료! ==========");

	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("\n"+"[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void listAll(TodoList l) {
		System.out.println("\n"+"[아이템 목록 : " + l.getCount() + "개]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void completeItem(TodoList l, int num) {
		if(l.completeItem(num) > 0)
			System.out.println("======== 완료 처리 성공! ========");
		else System.out.println("======== 완료 처리 실패! ========");
		
	}
	
	public static void cancelComplete(TodoList l, int num) {
		if(l.cancelItemComp(num) > 0)
			System.out.println("======== 취소 처리 성공! ========");
		else System.out.println("======== 취소 처리 실패! ========");
	}
	
	public static void multComp(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.println("완료 처리 할 아이템의 번호 3개를 입력하세요 > ");
		String input = sc.nextLine();
		StringTokenizer st = new StringTokenizer(input, " ");
		if (l.multComp(st.nextToken(), st.nextToken(), st.nextToken()) > 0)
			System.out.println("======== 완료 처리 성공! ========");
		else System.out.println("======== 완료 처리 실패! ========");
	}
	
	public static void listComp(TodoList l) {
		int count = 0;
		for (TodoItem item : l.getCompList()) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void editImportance(TodoList l, int num) {
		Scanner sc = new Scanner(System.in);
		System.out.print("새로운 중요도를 입력하세요 > ");
		int input = sc.nextInt();
		if(l.editImportance(num, input) > 0)
			System.out.println("========== 수정 완료! ==========");
		else System.out.println("========== 수정 실패! ==========");
		
	}
	
	public static void findImportance(TodoList l, int num) {
		int count = 0;
		for (TodoItem item : l.findImportance(num)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}
}
