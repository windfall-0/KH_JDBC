package edu.kh.jdbc.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.kh.jdbc.model.dao.EmployeeDAO;
import edu.kh.jdbc.model.dto.Employee;

/**
 * @author user1
 * Service : 요청에 맞는 기능을 수행하여 결과를 제공
 * 전달 받은 데이터 또는 DAO 수행 결과 데이터를 필요한 형태로 가공처리
 */
public class EmployeeService {
	
	private EmployeeDAO dao = new EmployeeDAO();
	
	
	
	
	
	/**
	 * 전체 사원 정보 조회 서비스
	 * @return ArrayList<Employee>
	 */
	public ArrayList<Employee> selectAll() {
		//별도로 가공할 내용이 없으면 DAO 호출
		
		return dao.selectAll();
	}





	public Employee selectOne(int empId) {
		return dao.selectOne(empId);
	}





	public List<Employee> selectSalary(int salary) {
		return dao.selectSalary(salary);
	}





	public int insertEmployee() {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("사번 : ");
		int empId = sc.nextInt();

		System.out.print("이름 : ");
		String empName = sc.next();

		System.out.print("주민등록번호 : ");
		String empNo = sc.next();

		System.out.print("이메일 : ");
		String email = sc.next();

		System.out.print("전화번호 : ");
		String phone = sc.next();

		System.out.print("부서 코드(D1 ~ D9) : ");
		String deptCode = sc.next();

		System.out.print("직급 코드(J1 ~ J7) : ");
		String jobCode = sc.next();

		System.out.print("급여 : ");
		int salary = sc.nextInt();

		System.out.print("보너스율 : ");
		double bonus = sc.nextDouble();
	      
	    Employee emp = new Employee(empId, empName, empNo, email, phone, deptCode, jobCode, salary, bonus);
		return dao.insertEmployee(emp);
		
	}





	public int fireEmployee(int empId) {
		return dao.fireEmployee(empId);
	}





	public int updateEmployee(String[] updateStrArr, int[] updateIntArr) {
		return dao.updateEmployee(updateStrArr, updateIntArr);
	}





	public int updateDeptBonus(String deptCode, String bonusRate) {
		return dao.updateDeptBonus(deptCode, bonusRate);
	}
	
	
	
	
	
	
}
