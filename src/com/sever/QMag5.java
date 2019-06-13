package com.sever;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Mag5
 */
@WebServlet("/QMag5")
public class QMag5 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("���յ�get����");
		String magStr = request.getParameter("Mag");
		double mag = Double.parseDouble(magStr);		//��stringתΪdouble��ֵ
		int count = 0;
		 	Connection conn = null;
		    Statement stmt = null;
		    try {
		      Class.forName("org.sqlite.JDBC");
		      conn = DriverManager.getConnection("jdbc:sqlite::resource:db/cloudDB.db");
		      //conn = DriverManager.getConnection("jdbc:sqlite:"+"resource/db/xxwDB.db");
		      conn.setAutoCommit(false);
		      System.out.println("Opened database successfully");

		      stmt = conn.createStatement();
		      String sql = "SELECT * FROM QUIZE";
		      ResultSet rs = stmt.executeQuery(sql);

		      while ( rs.next() ) {
		         
		    	  double magOut = Double.parseDouble("".equals(rs.getString("MAG"))?"0.0":rs.getString("MAG"));
		    	  if((magOut>=2.0)&& (magOut<2.001)) {
		    		  System.out.println(rs.getString(1)+" "+rs.getString(6)+" --"+rs.getString(8));
		    	  }
		    	  
		      }
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
		
		    
	      // ������Ӧ��������
	      response.setContentType("text/html");

	      // ʵ�ʵ��߼���������
	      PrintWriter out = response.getWriter();
	      out.println("<p>" + count + "</p>");
	}

}
