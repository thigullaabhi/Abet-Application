//Project Name :Abet Application
//Module Name  :Syllabus Generator 
//Description  :This servlet is back end code for Syllabus Generator,this code connects the application to data base. it 
//				pulls the values such as Topics covered, Instructor,textbook name from the database with respect to the
//              course user has selected. it populates the combo box in the jsp page with course identifiers from the database 
//				if it is empty. 
//Exceptions Caught:Naming Exception and SQL Exception.
//Libraries Used: IO, util, SQl, apache.commons, javax.servelt, javax.naming,javax.sql.
//
package Abet.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;


import com.sun.org.apache.bcel.internal.generic.ConversionInstruction;

/**
 * Servlet implementation class SyllabusGenerators
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
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=-1;
		Connection conn=null;
		
		//checking if the id contains key, if it exists it is type casted and taken into INT type variable id
		if(request.getParameterMap().containsKey("id")){             
			id=Integer.parseInt(request.getParameter("id"));               
		}
		try{
			
			//establishing connection to the database
			Context ctx= new InitialContext();
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");  
			conn=ds.getConnection();
			  	
			//if id is set the data for the particular course is pulled from the database
				if(id>=0)                                                                      
				{
					
					//Hash map TopicsCovered to store id and description as key value pair
					HashMap<Integer,String> TopicsCovered=new HashMap<Integer,String>();  
					
					//Hash map Instructor to store Topicsid and Instructor name as key value pair
					HashMap<Integer,String> Instructor=new HashMap<Integer,String>();
					
					//Hash map Instructor to store CourseId and TextBook name as key value pair
					HashMap<Integer,String> TextBook=new HashMap<Integer,String>();
					
					Statement stat=conn.createStatement();
					ResultSet rs=stat.executeQuery("SELECT ID,Description FROM TopicsCovered where CourseId='" + id +"';");
				    while(rs.next()){
				    	TopicsCovered.put(Integer.parseInt(rs.getString("ID")), rs.getString("Description"));
				    }
				    rs=stat.executeQuery("SELECT TopicsId,Name from Instructor where CourseIdentifier='" + id +"';");
				    while(rs.next()){
				    	Instructor.put(Integer.parseInt(rs.getString("TopicsId")),rs.getString("Name"));
				    }
				    rs=stat.executeQuery("SELECT ID,TextBook from Course where ID='" + id +"';");
				    while(rs.next()){
				    	TextBook.put(Integer.parseInt(rs.getString("ID")),rs.getString("TextBook"));
				    }
				    
				    //Attempt to push the id
				    request.setAttribute("id",id);
				    
				  //Attempt to push the Topics Covered ,TextBook and Instructor hash maps
				    request.setAttribute("TopicsCovered", TopicsCovered); 
				    request.setAttribute("Instructor", Instructor);
				    request.setAttribute("TextBook", TextBook);
				    
           System.out.println(TopicsCovered);				    
				}else{ 					
					
					// If id is not set pull the data from data base to populate the data base
					Statement stat=conn.createStatement();
					ResultSet rs=stat.executeQuery("select ID,CourseDesignator from Course;");
					
					//CourseIdmap hash map to store Course Designator and ID as key key value pair
					HashMap<String,Integer> CourseIdMap=new HashMap<String,Integer>(); 
					while(rs.next()){
					     CourseIdMap.put(rs.getString("CourseDesignator"),Integer.parseInt(rs.getString("ID")));	
					     
					 }
					//Attempt to push the CourseIdMap hash map 
					request.setAttribute("CourseIdMap",CourseIdMap);
		   System.out.println(CourseIdMap);
			       	 }
			
		}catch (NamingException ne) {
			System.out.println(ne);
		}catch (SQLException se) {
			System.out.println(se);
		}
		//creating a session and store the value id in it
		HttpSession session=request.getSession();
		session.setAttribute("iden", id);
		
		//Dispatching the attributes set to the SyllabusGenerator.jsp page
		getServletContext().getRequestDispatcher("/SyllabusGenerator.jsp").forward(request, response);
		
		
		
		
		
		
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public final String location="/home/thigu1a/thigu1a";
	
	@SuppressWarnings({ "deprecation", "resource" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String tempname=null;
		String reg="---";
		Connection conn=null;
		HttpSession session=request.getSession();
		int id=(int) session.getAttribute("iden");
		
		
		
		if(ServletFileUpload.isMultipartContent(request))
		{
			try
			{
				Context ctx= new InitialContext();
				DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");
				conn=ds.getConnection();
				Statement stat=conn.createStatement();
				ResultSet rs=stat.executeQuery("SELECT CourseDesignator FROM Course where ID='" + id +"';");
				while(rs.next()){tempname=rs.getString("CourseDesignator");}
				
				List<FileItem> multiparts= new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for(FileItem item:multiparts)
				{
					if(!item.isFormField())
					{
						String name=new File(item.getName()).getName();
						
						String finalname=name+reg+tempname;
						item.write(new File(location + File.separator + finalname));
						File f=new File(location + File.separator + finalname);
						System.out.println(f.getAbsolutePath());
						
						}
				}
			conn.close();
			}catch(Exception e)
			{
			System.out.println("the upload exception:"+e);
			}
			}
		}
	}


