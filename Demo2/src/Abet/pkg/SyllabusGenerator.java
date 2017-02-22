package Abet.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class SyllabusGenerator
 */
@WebServlet("/SyllabusGenerator")
public class SyllabusGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SyllabusGenerator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=-1;
		Connection conn=null;
		if(request.getParameterMap().containsKey("id")){
			id=Integer.parseInt(request.getParameter("id"));
		}
		try{
			Context ctx= new InitialContext();
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");
			conn=ds.getConnection();
			  	
				if(id>=0)
				{
					
					HashMap<Integer,String> TopicsCovered=new HashMap<Integer,String>();
					HashMap<Integer,String> Instructor=new HashMap<Integer,String>();
					
					Statement stat=conn.createStatement();
					ResultSet rs=stat.executeQuery("SELECT ID,Description FROM TopicsCovered where CourseId='" + id +"';");
				    while(rs.next()){
				    	TopicsCovered.put(Integer.parseInt(rs.getString("ID")), rs.getString("Description"));
				    }
				    rs=stat.executeQuery("SELECT TopicsId,Name from Instructor where CourseIdentifier='" + id +"';");
				    while(rs.next()){
				    	Instructor.put(Integer.parseInt(rs.getString("TopicsId")),rs.getString("Name"));
				    }
				    
				    request.setAttribute("id",id);
				    request.setAttribute("TopicsCovered", TopicsCovered);
				    request.setAttribute("Instructor", Instructor);
				    
           System.out.println(TopicsCovered);				    
				}else{ 
					Statement stat=conn.createStatement();
					ResultSet rs=stat.executeQuery("select ID,CourseDesignator from Course;");
					
					HashMap<String,Integer> CourseIdMap=new HashMap<String,Integer>(); 
					while(rs.next()){
					     CourseIdMap.put(rs.getString("CourseDesignator"),Integer.parseInt(rs.getString("ID")));	
					     
					 }
					request.setAttribute("CourseIdMap",CourseIdMap);
		   System.out.println(CourseIdMap);
			       	 }
			
		}catch (NamingException ne) {
			System.out.println(ne);
		}catch (SQLException se) {
			System.out.println(se);
		}
		
		
		getServletContext().getRequestDispatcher("/SyllabusGenerator.jsp").forward(request, response);
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
