package edu.kh.jdbc.view;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.dto.Employee;
import edu.kh.jdbc.model.service.EmployeeService;

/**
 * @author user1
 * 입 출력 담당 클래스
 * 사용자 담당 인터페이스 요소로 사용자의 요청과 응담을 보여주는 화면
 */
public class EmployeeView {
	
	private Scanner sc = new Scanner(System.in);
	private EmployeeService service = new EmployeeService();
	
	/**
	 * 메인 메뉴
	 */
	public void displayMenu() {
		int menuNum = -1;
		
		do {
			try {
				System.out.println();
				System.out.println("================================================");
				System.out.println("[사원 관리 프로그램]");
				System.out.println("1. 전체 사원 정보 조회");
				System.out.println("2. 사번으로 사원 정보 조회");
				System.out.println("3. 입력 받은 급여 이상으로 받는 모든 직원 조회");
				System.out.println("4. 새로운 사원 정보 추가");
				System.out.println("5.");
				System.out.println("6.");
				System.out.println("0. 프로그램 종료");
				System.out.println("================================================\n");
				
				System.out.print("메뉴 선택 >>");
				menuNum = sc.nextInt();
				System.out.println();
				
				switch (menuNum) {
				case 1: selectAll(); break;
				case 2: selectOne(); break;
				case 3: selectSalary(); break;
				case 4: insertEmployee(); break;
				
				
				case 0: System.out.println("프로그램을 종료합니다.");break;
				default:System.out.println("잘못 입력하셨습니다."); break;
				}
				
			} catch (InputMismatchException e) {
				System.out.println("입력 형식이 잘못되었습니다. 다시 입력해주세요.");
			}			
			
			
		} while(menuNum != 0);
		
		
	}



	



	/**
	 * 전체 사원 정보 조회 view
	 */
	private void selectAll() {
		
		ArrayList<Employee> empList = service.selectAll();
		print(empList);
	}
	
	/**
	 * 사원 한명 정보 조회 view
	 */
	private void selectOne() {
		try {
			System.out.println("뒤로가기 : 0");
			System.out.print("검색할 사번 입력 >>");
			int empId = sc.nextInt();
			if (empId == 0) {
				System.out.println("메인메뉴로 돌아갑니다.");
			} else {
				print(service.selectOne(empId));
			}
		} catch (InputMismatchException e) {
			sc.nextLine();
			System.out.println("숫자만 입력해 주세요.");
			selectOne();
		}
	}
	
	
	/**입력받은 급여 이상의 모든 직원 view
	 * 
	 */
	private void selectSalary() {
		System.out.println("[입력 받은 급여 이상으로 받는 모든 지원 조회]");
		try {
			System.out.println("뒤로가기 : 0");
			System.out.print("검색할 급여 입력 >>");
			int salary = sc.nextInt();
			if (salary == 0) {
				System.out.println("메인메뉴로 돌아갑니다.");
			} else {
				List<Employee> empList = service.selectSalary(salary);
				print(empList);
				if (empList.size() != 0) System.out.printf("총 %d명 입니다.",empList.size());
			}
		} catch (InputMismatchException e) {
			sc.nextLine();
			System.out.println("숫자만 입력해 주세요.");
			selectSalary();
		}
	}

	
	/**새로운 사원 정보를 추가
	 * 
	 */
	private void insertEmployee() {
		
	      int result = service.insertEmployee();
	      
	      if (result != 0) {
	    	  System.out.println("사원정보 추가 성공");
	      } else {
	    	  System.out.println("정보 추가 실패");
	      }
	}
	
	
	
	
	
	
	
	
	

	/**Employee list 출력용 view
	 * @param list
	 */
	private void print(List<Employee> list) {
		if (list == null || list.isEmpty()){
			System.out.println("조회 결과가 없습니다.");
		} else {
			for(Employee emp : list) {
				System.out.println(emp);
			}
		}
	}
	
	/**Employee 출력용 view
	 * @param emp
	 */
	private void print(Employee emp) {
		if (emp == null){
			System.out.println("조회 결과가 없습니다.");
		} else {
			System.out.println(emp);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
