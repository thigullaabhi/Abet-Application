//Project Name  :Abet Application
//Module Name   :Program Expected Outcome to Student Outcomes Mapper
//Description   :This servlet is back end code for PEOtoSO.jsp, this code connects the PEOtoSO module to the database. depending
//				 on the id pulled from the jsp page, the required data from the PEOtoSO, StudentOutcome, ProgramExpectedOutcome
//               tables in the database is pulled and further pushed onto jsp page. 
//Exceptions Caught:Naming Exception and SQL Exception.
//Libraries Used: IO, util, SQl, javax.servelt, javax.naming,javax.sql.
//
package Abet.pkg;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
 * Servlet implementation class PEOtoSO
 */
@WebServlet("/PEOtoSO")
public class PEOtoSO extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PEOtoSO() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pid=-1;
		Connection conn=null;
		
		//checking if the id contains key, if it exists it is type casted and taken into INT type variable id
		if(request.getParameterMap().containsKey("pid")){
			pid=Integer.parseInt(request.getParameter("pid"));
		}
		try{
			//establishing connection to the database
			Context ctx= new InitialContext();
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");
			conn=ds.getConnection();
			
			//if id is set the data for the particular course is pulled from the database
			if(pid>=0)
			{
				HashMap<String,Integer> SOIdsMap=new HashMap<String,Integer>();//HashMap SOIdsMap to store Id,Identifier from Student Outcome
				HashMap<Integer,String> SODescriptionsMap=new HashMap<Integer,String>();//HashMap SODescriptionMap to store Id,Description From Student Outcome
				HashMap<Integer, String> PEODescriptionsMap=new HashMap<Integer,String>(); //HashMap PEODescriptionMap to store Id,Description from ProgramExpectedOutcome
				HashMap<String, String> PEOtoSOMap=new HashMap<String,String>(); //HashMap CLODescriptionMap to store Id,Description from CourseLearningObjective
				
				Statement stat=conn.createStatement();
				ResultSet rs=stat.executeQuery("SELECT ID,Identifier FROM StudentOutcome;");
				while(rs.next()){
					SOIdsMap.put(rs.getString("Identifier"), Integer.parseInt(rs.getString("ID")));
				}
			    rs=stat.executeQuery("SELECT ID,Description FROM StudentOutcome;");
				while(rs.next()){
			        SODescriptionsMap.put(Integer.parseInt(rs.getString("ID")), rs.getString("Description"));
			    }
				rs=stat.executeQuery("SELECT ID,Description FROM ProgramExpectedOutcome WHERE Number='" + pid +"';");
				while(rs.next()){
				    PEODescriptionsMap.put(Integer.parseInt(rs.getString("ID")),rs.getString("Description"));
				}
				rs=stat.executeQuery("SELECT PEOID, SOID, link FROM PEOtoSO WHERE Ordinal='" + pid + "';")	;			
			    while(rs.next()){
			    	PEOtoSOMap.put(rs.getString("PEOID") + "-" + rs.getString("SOID"),rs.getString("link"));
			    }
			    //Attempt to push PEODescriptionsMap, SODescriptionsMap,SOIdsMap,PEOtoSOMap onto PEOtoSO.jsp page
			    request.setAttribute("PEODescriptionsMap", PEODescriptionsMap);
			    request.setAttribute("SODescriptionsMap", SODescriptionsMap);
			    request.setAttribute("SOIdsMap",SOIdsMap);
			    request.setAttribute("PEOtoSOMap", PEOtoSOMap);
			    request.setAttribute("pid",pid);
		}else{ 
			// If id is not set pull the data from data base to populate the Combo Box
			Statement stat=conn.createStatement();
			ResultSet rs=stat.executeQuery("select ID,Number from ProgramExpectedOutcome;");
			
			//ProgramIdmap hash map to store Course Designator and ID as key key value pair
			HashMap<Integer,Integer> ProgramIdMap=new HashMap<Integer,Integer>(); 
			while(rs.next()){
			     ProgramIdMap.put(Integer.parseInt(rs.getString("ID")),Integer.parseInt(rs.getString("Number")));	
			     
			 }
			//Attempt to push the ProgramIdMap hash map 
			request.setAttribute("ProgramIdMap",ProgramIdMap);
   //System.out.println(CourseIdMap);
	       	 }
		}catch (NamingException ne) {
			System.out.println(ne);
		}catch (SQLException se) {
			System.out.println(se);
		}
		//Dispatching the attributes set to the PEOtoSO.jsp page
		getServletContext().getRequestDispatcher("/PEOtoSO.jsp").forward(request, response);
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
		String pid=request.getParameter("pid");    //Attempt to pull pid 
		
		Connection conn=null;
		
		//establishing connection to the database
		try{
			Context ctx=new InitialContext();
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");
			conn=ds.getConnection();
			
		Set keys=request.getParameterMap().keySet(); 	//Attempt to pull all the keys from KeySet to keys
		/*System.out.println("the keys are"+keys);*/
		Iterator it=keys.iterator();                    //iterating all the keys from the key set
		
		while(it.hasNext()){
			 String key=((String)it.next());
System.out.println(key+"," +request.getParameter(key));
			 //checking if the value is equal to the previously selected value by comparing the keys
			 if(key.startsWith("maps") && !request.getParameter(key).equals(request.getParameter("prevs-"+key.substring(key.indexOf('-') + 1)))){
			        String[] ids=key.substring(key.indexOf('-') + 1).split("_");
			        
			        String Identifier=request.getParameter(key);
                    Statement stat=conn.createStatement();   //query to update the CLOtoSO table in the data base
			        String query="UPDATE PEOtoSO SET link='" + Identifier + "' where Ordinal='" + pid + "' AND PEOID='" + ids[0] +"' AND SOID='" + ids[1] + "';";
	                stat.executeUpdate(query);
			        
					
			    }
		    }
		}catch(NamingException ne){
			System.out.println(ne);
		}catch(SQLException se){
			System.out.println(se);
		}
//System.out.println(request.getParameterMap().keySet() + "");
		
		response.sendRedirect("PEOtoSO?pid=" + request.getParameter("pid"));
	}

}
