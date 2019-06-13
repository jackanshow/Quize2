package com.sever;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QtwoLocat
 */
@WebServlet("/QtwoLocat")
public class QtwoLocat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String la1= request.getParameter("lat1");
		String lo1= request.getParameter("long1");
		double laNum1=Double.parseDouble(la1);
		double loNum1=Double.parseDouble(lo1);
		String la2= request.getParameter("lat2");
		String lo2= request.getParameter("long2");
		double laNum2=Double.parseDouble(la2);
		double loNum2=Double.parseDouble(lo2);
		
		
		Connection conn = null;
		Statement stmt=null;
		try {
		      Class.forName("org.sqlite.JDBC");
		      conn = DriverManager.getConnection("jdbc:sqlite::resource:db/cloudDB.db");
		      //conn = DriverManager.getConnection("jdbc:sqlite:"+"resource/db/xxwDB.db");
		      conn.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = conn.createStatement();
		      String sql = "SELECT * FROM QUIZE";
		      ResultSet rs = stmt.executeQuery(sql);
		   // ������Ӧ��������
	   	      response.setContentType("text/html");
	   		// ʵ�ʵ��߼���������
	   	      PrintWriter out = response.getWriter();
		      while ( rs.next() ) {
		         Double laSql = Double.parseDouble("".equals(rs.getString("LATITUDE"))?"0.0":rs.getString("LATITUDE"));
		         Double loSql = Double.parseDouble("".equals(rs.getString("LONGITUDE"))?"0.0":rs.getString("LONGITUDE"));
		         if(((laSql>=laNum1&&laSql<=laNum2)||(laSql<=laNum1&&laSql>=laNum2))
		        		 &&((loSql>=loNum1&&loSql<=loNum2)||(loSql<=loNum1&&loSql>=loNum2)) ) {
		

		        	 out.println("<p>"+rs.getString("LATITUDE")+"---"+rs.getString("LONGITUDE")+"---"
		        	 +rs.getString("PLACE")+"</P>");
		        	 
		        	 
		         }
		     
		      }
		      out.flush();
		      out.close();
		      
		      rs.close();
		      stmt.close();
		      conn.close();
		    } 
		    catch(SQLException se) {
	            // ���� JDBC ����
	            se.printStackTrace();
	        }
		    catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    finally{
	            // ��������ڹر���Դ�Ŀ�
	            try{
	                if(stmt!=null)
	                stmt.close();
	            }catch(SQLException se2){
	            }
	            try{
	                if(conn!=null)
	                conn.close();
	            }catch(SQLException se){
	                se.printStackTrace();
	            }
	        }
		    System.out.println("Operation done successfully");
		
		
		
	}

}
