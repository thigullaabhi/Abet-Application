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
		String selectedvalue=request.getParameter("map-${SOId.value}_${CLODescription.key");
		
		if(request.getParameterMap().containsKey("id")){
			id=Integer.parseInt(request.getParameter("id"));
		}
		try{
			Context ctx= new InitialContext();
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");
			conn=ds.getConnection();
			  	
				if(id>=0)
				{
					HashMap<String,Integer> SOIdsMap=new HashMap<String,Integer>();
					HashMap<Integer,String> SODescriptionsMap=new HashMap<Integer,String>();
					HashMap<Integer,String> CLODescriptionsMap=new HashMap<Integer,String>();
					HashMap<String,String> CLOtoSOMap=new HashMap<String,String>();
					HashMap<Integer,String> TopicsCovered=new HashMap<Integer,String>();
					HashMap<Integer,String>  optionvalues=new HashMap<Integer,String>();
					
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
                    rs=stat.executeQuery("SELECT CLOID, SOId, link FROM CLOtoSO WHERE CourseId='" + id + "';")	;			
				    while(rs.next()){
				    	CLOtoSOMap.put(rs.getString("CLOID") + "-" + rs.getString("SOID"),rs.getString("link"));
				    }
				    rs=stat.executeQuery("SELECT ID,Description FROM TopicsCovered;");
				    while(rs.next()){
				    	TopicsCovered.put(Integer.parseInt(rs.getString("ID")), rs.getString("Description"));
				    }
				    
				    	    
				    
				    
				    request.setAttribute("CLODescriptionsMap", CLODescriptionsMap);
				    request.setAttribute("SODescriptionsMap", SODescriptionsMap);
				    request.setAttribute("SOIdsMap",SOIdsMap);
				    request.setAttribute("CLOtoSOMap", CLOtoSOMap);
				    request.setAttribute("id",id);
				    request.setAttribute("TopicsCovered", TopicsCovered);
				    
				    optionvalues.put(1, "-");
				    optionvalues.put(2, "I");
				    optionvalues.put(3, "R");
				    optionvalues.put(4, "A");
				    request.setAttribute("optionvalues", optionvalues);
				    request.setAttribute("selectedvalue", selectedvalue);
				    
				    
          // System.out.println(SOIdsMap +" "+ SODescriptionsMap +" "+CLODescriptionsMap );				    
				}else{ 
					Statement stat=conn.createStatement();
					ResultSet rs=stat.executeQuery("select ID,CourseDesignator from Course;");
					
					HashMap<String,Integer> CourseIdMap=new HashMap<String,Integer>(); 
					while(rs.next()){
					     CourseIdMap.put(rs.getString("CourseDesignator"),Integer.parseInt(rs.getString("ID")));	
					     
					 }
					request.setAttribute("CourseIdMap",CourseIdMap);
		   //System.out.println(CourseIdMap);
			       	 }
				
			
		}catch (NamingException ne) {
			System.out.println(ne);
		}catch (SQLException se) {
			System.out.println(se);
		}
		
		getServletContext().getRequestDispatcher("/CLOtoSO.jsp").forward(request, response);
		getServletContext().getRequestDispatcher("/SyllabusGenerator.jsp").forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("rawtypes")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out=response.getWriter();
		
		String id=request.getParameter("id");
		
		Connection conn=null;
		
		try{
			Context ctx=new InitialContext();
			DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");
			conn=ds.getConnection();
			
		Set keys=request.getParameterMap().keySet(); 
		
		Iterator it=keys.iterator();
		
		while(it.hasNext()){
			 String key=((String)it.next());
			 if(key.startsWith("map") && !request.getParameter(key).equals("-")){
			        String[] ids=key.substring(key.indexOf('-') + 1).split("_");
			        System.out.println("ids is"+ids[0]);
			        System.out.println("ids is"+ids[1]);
			        String Identifier=request.getParameter(key);
                    
			        Statement stat=conn.createStatement(); 
			        String query="UPDATE CLOtoSO SET link='" + Identifier + "' where CourseId='" + id + "' AND CLOID='" + ids[1] +"' AND SOID='" + ids[0] + "';";
	                	    		        
			        ResultSet rs = stat.executeQuery(query);
			        //while(rs.next()){ System.out.println(rs);}
					//System.out.println(ids[0] + "," + ids[1]);
					request.setAttribute("Identifier", Identifier);
			    }
		    }
		}catch(NamingException ne){
			System.out.println(ne);
		}catch(SQLException se){
			System.out.println(se);
		}
//System.out.println(request.getParameterMap().keySet() + "");
		
		response.sendRedirect("CLOtoSO?id=" + request.getParameter("id"));
		
		
	}

}
