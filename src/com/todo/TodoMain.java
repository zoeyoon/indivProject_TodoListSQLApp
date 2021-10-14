package com.todo;

import java.util.Scanner;

//import org.sqlite.core.DB;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.DbConnect;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todoList.txt");
		boolean isList = false;
		boolean quit = false;
	
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
			
			case "mult_del":
				TodoUtil.mult_del(l);
				break;
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name":
				System.out.println("\n========= 정렬-이름(오름차순) ==========");
				TodoUtil.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("\n========= 정렬-이름(내림차순) ==========");
				TodoUtil.listAll(l, "title", 0);
				break;
				
			case "ls_date":
				System.out.println("\n========= 정렬-날짜 순 ==========");
				TodoUtil.listAll(l, "due_date", 1);
				break;
				
			case "ls_date_desc":
				System.out.println("\n========= 정렬-날짜 순 ==========");
				TodoUtil.listAll(l, "due_date", 0);
				break;
			
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "find":
				String keyword = sc.nextLine().trim();
				TodoUtil.findList(l, keyword);
				break;
				
			case "find_cate":
				String cate = sc.nextLine().trim();
				TodoUtil.findCateList(l, cate);
				break;
				
			case "comp":
				int num1 = sc.nextInt();
				TodoUtil.completeItem(l,num1);
				break;
				
			case "comp_cancel":
				int num2 = sc.nextInt();
				TodoUtil.cancelComplete(l,num2);
				break;
				
			case "mult_comp":
				TodoUtil.multComp(l);
				break;
				
			case "ls_comp":
				System.out.println("\n========= 완료된 항목 ==========");
				TodoUtil.listComp(l);
				break;
			
			case "edit_importance":
				int num3 = sc.nextInt();
				TodoUtil.editImportance(l, num3);
				break;
				
			case "find_importance":
				int num4 = sc.nextInt();
				TodoUtil.findImportance(l, num4);
				break;
				
			case "ls_importance":
				System.out.println("\n========= 정렬-중요도 (오름차순) ==========");
				TodoUtil.listAll(l, "importance", 1);
				break;
				
			case "ls_importance_desc":
				System.out.println("\n========= 정렬-중요도 (내림차순) ==========");
				TodoUtil.listAll(l, "importance", 0);
				break;
				
			case "help":
				Menu.displaymenu();
				System.out.println();
				break;
				
			case "exit":
				quit = true;
				break;
				
			default:
				System.out.println("\n제공하지 않는 기능입니다. 다시 입력해주세요. - 도움말은 \"help\"를 입력");
				break;
			}
			
			if(isList) l.getList();
		} while (!quit);
		System.out.println("\nHave a Wonderful Day!");
		DbConnect.closeConnection();
	}
}
