//Project Name  :Abet Application
//Module Name   :Course Learning Objectives to Student Outcomes Mapper
//Description   :This servlet is back end code for CLOtoSO.jsp, this code connects the CLOtoSo module to the database. depending
//				 on the id pulled from the jsp page, the required data from the CLOtoSO, StudentOutcome, CourseLearningObjective
//               tables in the database is pulled and further pushed onto jsp page. 
//Exceptions Caught:Naming Exception and SQL Exception.
//Libraries Used: IO, util, SQl, javax.servelt, javax.naming,javax.sql.
//
package Abet.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.naming.*;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class CLOtoSO
 */
@WebServlet("/CLOtoSO")
public class CLOtoSO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CLOtoSO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=-1;
		Connection conn=null;
			
		String selectedvalue=request.getParameter("map-${CLODescription.key_${SOId.value}");
		
		//checking if the id contains key, if it exists it is type casted and taken into INT type variable id
		if(request.getParameterMap().containsKey("id")){
			id=Integer.parseInt(request.getParameter("id")); //Attempt to pull the id
		}
		try{
			//establishing connection to the database
			Context ctx= new InitialContext();
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");
			conn=ds.getConnection();
			  	
			//if id is set the data for the particular course is pulled from the database
				if(id>=0)
				{
					HashMap<String,Integer> SOIdsMap=new HashMap<String,Integer>(); //HashMap SOIdsMap to store Id,Identifier from Student Outcome
					HashMap<Integer,String> SODescriptionsMap=new HashMap<Integer,String>(); //HashMap SODescriptionMap to store Id,Description From Student Outcome
					HashMap<Integer,String> CLODescriptionsMap=new HashMap<Integer,String>(); //HashMap CLODescriptionMap to store Id,Description from CourseLearningObjective
					HashMap<String,String> CLOtoSOMap=new HashMap<String,String>();      //HashMap CLOtoSOMap to store CLOID,SOID,link from CLOtoSO
					HashMap<Integer,String> TopicsCovered=new HashMap<Integer,String>(); //Hash map TopicsCovered to store id and description as key value pair
					HashMap<String,String>  optionvalues=new HashMap<String,String>();
					
					Statement stat=conn.createStatement();
					ResultSet rs=stat.executeQuery("SELECT ID,Identifier FROM StudentOutcome;");
					while(rs.next()){
						SOIdsMap.put(rs.getString("Identifier"), Integer.parseInt(rs.getString("ID")));
					}
				    rs=stat.executeQuery("SELECT ID,Description FROM StudentOutcome;");
					while(rs.next()){
				        SODescriptionsMap.put(Integer.parseInt(rs.getString("ID")), rs.getString("Description"));
				    }
					rs=stat.executeQuery("SELECT ID,Description FROM CourseLearningObjective WHERE CourseId='" + id +"';");
	                while(rs.next()){
	                	CLODescriptionsMap.put(Integer.parseInt(rs.getString("ID")), rs.getString("Description"));
	                }			
                    rs=stat.executeQuery("SELECT CLOID, SOID, link FROM CLOtoSO WHERE CourseId='" + id + "';")	;			
				    while(rs.next()){
				    	CLOtoSOMap.put(rs.getString("CLOID") + "-" + rs.getString("SOID"),rs.getString("link"));
				    }
				    rs=stat.executeQuery("SELECT ID,Description FROM TopicsCovered;");
				    while(rs.next()){
				    	TopicsCovered.put(Integer.parseInt(rs.getString("ID")), rs.getString("Description"));
				    }
				    
				    	    
				    
				    //Attempt to push CLODescriptionsMap, SODescriptionsMap,SOIdsMap,CLOtoSOMap,TopicsCovered onto CLOtoSO.jsp page
				    request.setAttribute("CLODescriptionsMap", CLODescriptionsMap);
				    request.setAttribute("SODescriptionsMap", SODescriptionsMap);
				    request.setAttribute("SOIdsMap",SOIdsMap);
				    request.setAttribute("CLOtoSOMap", CLOtoSOMap);
				    request.setAttribute("id",id);
				    request.setAttribute("TopicsCovered", TopicsCovered);
				    
				    
				    request.setAttribute("selectedvalue", selectedvalue);
				    
				    
          // System.out.println(SOIdsMap +" "+ SODescriptionsMap +" "+CLODescriptionsMap );				    
				}else{ 
					// If id is not set pull the data from data base to populate the Combo Box
					Statement stat=conn.createStatement();
					ResultSet rs=stat.executeQuery("select ID,CourseDesignator from Course;");
					
					//CourseIdmap hash map to store Course Designator and ID as key key value pair
					HashMap<String,Integer> CourseIdMap=new HashMap<String,Integer>(); 
					while(rs.next()){
					     CourseIdMap.put(rs.getString("CourseDesignator"),Integer.parseInt(rs.getString("ID")));	
					     
					 }
					//Attempt to push the CourseIdMap hash map 
					request.setAttribute("CourseIdMap",CourseIdMap);
		   //System.out.println(CourseIdMap);
			       	 }
				
			
		}catch (NamingException ne) {
			System.out.println(ne);
		}catch (SQLException se) {
			System.out.println(se);
		}
		
		//Dispatching the attributes set to the CLOtoSO.jsp page
		getServletContext().getRequestDispatcher("/CLOtoSO.jsp").forward(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		
		String id=request.getParameter("id");    //Attempt to pull id 
		
		Connection conn=null;
		
		try{
			
			//establishing connection to the database
			Context ctx=new InitialContext();
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");
			conn=ds.getConnection();
			
		Set keys=request.getParameterMap().keySet();    //Attempt to pull all the keys from KeySet to keys
		/*System.out.println("the keys are"+keys);*/
		Iterator it=keys.iterator();                    //iterating all the keys from the key set
		
		while(it.hasNext()){
			 String key=((String)it.next());
System.out.println(key+"," +request.getParameter(key));
			//checking if the value is equal to the previously selected value by comparing the keys
			 if(key.startsWith("map") && !request.getParameter(key).equals(request.getParameter("prev-"+key.substring(key.indexOf('-') + 1)))){
			        String[] ids=key.substring(key.indexOf('-') + 1).split("_");
			        
			        
			        
			       
			        String Identifier=request.getParameter(key);
                    
			        Statement stat=conn.createStatement(); //query to update the CLOtoSO table in the data base
			        String query="UPDATE CLOtoSO SET link='" + Identifier + "' where CourseId='" + id + "' AND CLOID='" + ids[0] +"' AND SOID='" + ids[1] + "';";
	                	
			        
			        
			         stat.executeUpdate(query);
			        //Attempt to push the Identifier
					request.setAttribute("Identifier", Identifier);
			    }
		    }
		}catch(NamingException ne){
			System.out.println(ne);
		}catch(SQLException se){
			System.out.println(se);
		}

		
		response.sendRedirect("CLOtoSO?id=" + request.getParameter("id"));
		
		
	}

}
