package edu.kh.jdbc.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.kh.jdbc.model.dto.Employee;

/**
 * @author user1
 * DAO(Data Access Object) : 데이터 접근 객체 
 * DB와 연결되어 SQL을 수행하고 결과를 반환 받는 역할
 */
public class EmployeeDAO {

	//JDBC

	private Connection conn;	// DB연결 정보를 담은 객체 (Java - DB 사이의 통로 역할)
	private Statement stat;		// SQL을 실행하고 결과를 return하는 객체
	private ResultSet rets;		// stat에서 반환한 결과를 참조하는 객체

	private PreparedStatement pstmt;
	// Statement의 자식으로 좀더 양산된 기능을 제공
	//  - ?(위치홀더)를 이용하여 SQL에 작성되어지는 리터럴을 동적으로 제어함
	// -> 오타 위험 감소, 가독성 상승
	
	/**
	 * @return ArrayList<Employee>
	 */
	public ArrayList<Employee> selectAll() {

		ArrayList<Employee> empList =  new ArrayList<>();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:XE", "KH_PCS", "KH1234");

			stat = conn.createStatement();

			String sql = "SELECT * FROM EMPLOYEE2 ORDER BY EMP_ID";

			rets = stat.executeQuery(sql);

			while (rets.next()) {
				empList.add(createEmployee(rets));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rets != null) rets.close();
				if (stat != null) stat.close();
				if (conn != null) conn.close();			

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return empList;
	}

	public Employee selectOne(int empId) {
		Employee emp = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:XE", "KH_PCS", "KH1234");

			stat = conn.createStatement();

			String sql = "SELECT * FROM EMPLOYEE2 WHERE EMP_ID = " + empId;

			rets = stat.executeQuery(sql);

			while (rets.next()) {
				emp = createEmployee(rets);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rets != null) rets.close();
				if (stat != null) stat.close();
				if (conn != null) conn.close();			

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return emp;
	}

	public List<Employee> selectSalary(int salary) {
		ArrayList<Employee> empList =  new ArrayList<>();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:XE", "KH_PCS", "KH1234");

			stat = conn.createStatement();

			String sql = "SELECT * FROM EMPLOYEE2 WHERE SALARY >= " + salary;

			rets = stat.executeQuery(sql);

			while (rets.next()) {
				empList.add(createEmployee(rets));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rets != null) rets.close();
				if (stat != null) stat.close();
				if (conn != null) conn.close();			

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return empList;
		
	}
	
	private Employee createEmployee(ResultSet rets) {
		Employee emp = null;
		try {
			emp = new Employee( 
					rets.getInt("EMP_ID"), 
					rets.getString("EMP_NAME"), 
					rets.getString("EMP_NO"), 
					rets.getString("EMAIL"), 
					rets.getString("PHONE"), 
					rets.getString("DEPT_CODE"), 
					rets.getString("JOB_CODE"), 
					rets.getString("SAL_LEVEL"), 
					rets.getInt("SALARY"), 
					rets.getDouble("BONUS"), 
					rets.getInt("MANAGER_ID"), 
					rets.getDate("HIRE_DATE"), 
					rets.getDate("ENT_DATE"), 
					rets.getString("ENT_YN").charAt(0)
			);
		} catch (SQLException e) {
			return emp;
		}
		return emp;
	}

	/**새로운 사원 정보 추가 DAO
	 * @param emp
	 * @return
	 */
	public int insertEmployee(Employee emp) {
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:XE", "KH_PCS", "KH1234");

			String sql = "INSERT INTO EMPLOYEE2 VALUES(?,?,?,?,?,?,?,'S1',?,?,200,SYSDATE, NULL, 'N')";
			// ? 위치 홀더
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, emp.getEmpId()); // 입력 받은 사번을 1번 ?(위치홀더)에 세팅
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getEmpNo());
			pstmt.setString(4, emp.getEmail());
			pstmt.setString(5, emp.getPhone());
			pstmt.setString(6, emp.getDeptCode());
			pstmt.setString(7, emp.getJobCode());
			pstmt.setInt(8, emp.getSalary());
			pstmt.setDouble(9, emp.getBonus());
			//  PREPAREDSTATMENT ? 사용 이후 PREPAREDSTATMENT객체 생성(SQL)적재 - 위치홀더에 알맞은 값 대입


			result = pstmt.executeUpdate();
			
			/*
			 * DML 사용 시 executeUpdate사용
			 * DML 수행 후 성공한 행의 개수를 반환
			 */
			
		} catch (Exception e) {
			
		} finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn !=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public int fireEmployee(int empId) {
		int result = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:XE", "KH_PCS", "KH1234");
			
			String sql = "DELETE FROM EMPLOYEE2 WHERE EMP_ID = ?";
			pstmt = conn.prepareStatement(sql);
			
		
			pstmt.setInt(1, empId);
			return pstmt.executeUpdate();
			
		} catch (Exception e) {
			
		} finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn !=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public int updateEmployee(String[] updateStrArr, int[] updateIntArr) {
		int result = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:XE", "KH_PCS", "KH1234");
			conn.setAutoCommit(false);
			
			String sql = "UPDATE EMPLOYEE2 SET EMAIL = ?, PHONE = ?, SALARY = ? WHERE EMP_ID = ?";
			// ? 위치 홀더
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, updateStrArr[0]);
			pstmt.setString(2, updateStrArr[1]);
			pstmt.setInt(3, updateIntArr[0]);
			pstmt.setInt(4, updateIntArr[1]); 

			

			result = pstmt.executeUpdate();
			
			if (result==0) { 
				conn.rollback();
			} else {
				conn.commit();
			}
			
			/*
			 * DML 사용 시 executeUpdate사용
			 * DML 수행 후 성공한 행의 개수를 반환
			 */
			
		} catch (Exception e) {
			
		} finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(conn !=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		

	}

	public int updateDeptBonus(String deptCode, String bonusRate) {
		int result = 0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@localHost:1521:XE", "KH_PCS", "KH1234");
			conn.setAutoCommit(false);
			
			String sql = String.format("UPDATE EMPLOYEE2 SET BONUS = %s WHERE DEPT_CODE = '%s'",bonusRate,deptCode);
			System.out.println(sql);
			
			stat = conn.createStatement();
			
			
			result = stat.executeUpdate(sql);
			
			if (result==0) { 
				conn.rollback();
			} else {
				conn.commit();
			}

			
		} catch (Exception e) {
			
		} finally {
			try {
				if(stat!=null)stat.close();
				if(conn !=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	
	

}
