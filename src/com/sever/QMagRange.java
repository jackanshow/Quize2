package com.sever;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MagRange
 */
@WebServlet("/MagRange")
public class QMagRange extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse resp onse)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//如果大于min判断是否小于max，如果是，则把id存到数组，在根据ID统一取出来
		String d1= request.getParameter("depth1");
		String d2= request.getParameter("depth2");
		String st= request.getParameter("step");
		double depth1=Double.parseDouble(d1);
		double depth2=Double.parseDouble(d2);
		double step=Double.parseDouble(st);
		System.out.println("没问题");
		
		
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
		      
		   // 设置响应内容类型
	   	      response.setContentType("text/html");
	   		// 实际的逻辑是在这里
	   	      PrintWriter out = response.getWriter();
	   	      /*out.println("<table border=\"1\">");
	   	      out.println("<tr><th>"+"TIME"+"</th><th>"+"LATITUDE"+
		        			 "<th>"+"LONGITUDE"+"</th><th>"+"DEPTH"+"</th>"+
		        			 "<th>"+"MAG"+"</th><th>"+"RMS"+"</th>"+
		        			 "<th>"+"PLACE"+"</th>"+"</tr>");*/
	   	   while(depth1<depth2) {
	   		ResultSet rs = stmt.executeQuery(sql);
	   		int count = 0;
		      while ( rs.next() ) {
		         Double depthSql = Double.parseDouble("".equals(rs.getString("DEPTH"))?"0.0":rs.getString("DEPTH"));
		         
		         
		        	 if(depthSql>=depth1&&depthSql<=depth1+step) {
		        		 
		        	 /*out.println("<tr><td>"+rs.getString(2)+"</td><td>"+rs.getString(3)+
		        			 "<td>"+rs.getString(4)+"</td><td>"+rs.getString(5)+"</td>"+
		        			 "<td>"+rs.getString(6)+"</td><td>"+rs.getString(7)+"</td>"+
		        			 "<td>"+rs.getString(8)+"</td>"+"</tr>");*/
		        		 
		        	 	count++;	        	 
		        	 }		        	 
		        	 
		         }
		      
		      /*out.println("<tr><td>"+"count"+"</td><td>"+count+
	        			 "<td>"+"=="+"</td><td>"+"=="+"</td>"+
	        			 "<td>"+"=="+"</td><td>"+"=="+"</td>"+
	        			 "<td>"+"=="+"</td><td>"+"=="+"</td>"+
	        			 "<td>"+"=="+"</td><td>"+"=="+"</td>"+"<td>"+"=="+"</td>"
	        			 +"</tr>");*/
		      double dep3=depth1+step;
		      out.println("<p>from depth&ensp;"+depth1+"&ensp;to&ensp;"+dep3+"&ensp;total&ensp;"+count+"</p>");
		      depth1=depth1+step;
		      rs.close();
		      }
		      out.println("</table>"); 
		      out.flush();
		      out.close();
		      
		      
		      stmt.close();
		      conn.close();
		    } 
		    catch(SQLException se) {
	            // 处理 JDBC 错误
	            se.printStackTrace();
	        }
		    catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		    }
		    finally{
	            // 最后是用于关闭资源的块
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
