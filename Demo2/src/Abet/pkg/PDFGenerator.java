package Abet.pkg;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.swing.text.Document;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;





/**
 * Servlet implementation class PDFGenerator
 */
@WebServlet("/PDFGenerator")
public class PDFGenerator extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PDFGenerator() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn=null;
		String top=null,prof=null,cor=null,desc=null,credits=null,book=null,lab=null,clo="",prereq=null,Map="",SOID="",Link="",Sdesc="";
		HttpSession session=request.getSession();
		int id=(int) session.getAttribute("iden");                    //Attempt to pull the values from the session
		 System.out.println(id);
		 
		try {  
		   //establishing connection to the database
		   Context ctx=new InitialContext();
		   DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/abetdb.sqlite.sqlite");
		   conn=ds.getConnection();
		   Statement stat=conn.createStatement();
		   ResultSet rs1=stat.executeQuery("select CourseDesignator,Name,Credits,Prerequisite,TextBook,LabHours from Course where ID='" + id +"';");
		   while(rs1.next()){cor=rs1.getString("Name");
		                      top=rs1.getString("CourseDesignator");
		                      credits=rs1.getString("Credits");
		                      prereq=rs1.getString("Prerequisite");
		                      book=rs1.getString("TextBook");
		                      lab=rs1.getString("LabHours");
		                     } 
		   ResultSet rs2=stat.executeQuery("select Description from TopicsCovered where CourseId='" + id +"';");
		   while(rs2.next()){desc=rs2.getString("Description");
		                     } 
		   ResultSet rs3=stat.executeQuery("select Name from Instructor where CourseIdentifier='" + id +"';");
		   while(rs3.next()){prof=rs3.getString("Name");
		                     }
		   ResultSet rs4=stat.executeQuery("select Name from Instructor where CourseIdentifier='" + id +"';");
		   while(rs3.next()){prof=rs4.getString("Name");
		                     }
		   ResultSet rs5=stat.executeQuery("Select CLOtoSO.SOID,CLOtoSO.Link,CourseLearningObjective.Description from CLOtoSO JOIN CourseLearningObjective ON CLOtoSO.CLOID=CourseLearningObjective.ID and CLOtoSO.CourseId='" + id +"' and Link='I';");
		   while(rs5.next()){SOID+="-->"+rs5.getString("Description").replaceAll("\\s+", " ")+"Maps"+rs5.getString("SOID").replaceAll("\\s+", " ");
		   					 
		                     }
		   
		   ResultSet rs7=stat.executeQuery("select Description from CourseLearningObjective where CourseId='" + id +"';");
		   while(rs7.next()){clo+=rs7.getString("Description").replaceAll("\\s+", " ");
		                     }
		   System.out.println(SOID);
		   
			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			PDPage page2 = new PDPage();
			document.addPage( page );
          
			
			int i=0,j=50,count=0,x=0,y=50,u=0,v=50, temp,temp1,temp2;
			String Substr=null,Substr1=null,Substr2=null;
			List<String> lines = new ArrayList<String>();
			List<String> clolines = new ArrayList<String>();
			List<String> maplines = new ArrayList<String>();
			System.out.println("length"+desc.length());
			
			
			while(j<=desc.length())
			{
				Substr=desc.substring(i,j);
				
				if((Substr.length())%50==0){
					lines.add(Substr);
					
					
				}
				
				i=j;
				j+=50;
				count+=1;
			}
			Substr=desc.substring(i,desc.length());
			lines.add(Substr);
			System.out.println(lines);
			
			
			
			while(y<=clo.length())
			{
				Substr1=clo.substring(x,y);
				
				if((Substr1.length())%50==0){
					clolines.add(Substr1);
					
					
				}
				
				x=y;
				y+=50;
				count+=1;
			}
			Substr1=clo.substring(x,clo.length());
			clolines.add(Substr1);
			System.out.println(clolines);
				
			temp=510-(15*clolines.size());
			
			while(v<=SOID.length())
			{
				Substr2=SOID.substring(u,v);
				
				if((Substr2.length())%50==0){
					maplines.add(Substr2);
					
					
				}
				
				u=v;
				v+=50;
				count+=1;
			}
			Substr2=SOID.substring(u,SOID.length());
			maplines.add(Substr2);
			System.out.println(maplines);
			
			temp=510-(15*clolines.size());
			temp1=temp-(20*maplines.size());
			temp2=temp1-(15*lines.size());
			System.out.println(temp2);
			
			// Create a new font object by loading a TrueType font into the document
			PDFont font = PDType1Font.COURIER_BOLD;
			//PDFont font = PDTrueTypeFont.loadTTF(document, "Arial.ttf");

			// Start a new content stream which will "hold" the to be created content
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			contentStream.beginText();
			contentStream.setFont( font, 18 );
			contentStream.moveTextPositionByAmount( 180, 700 );
			contentStream.drawString( "Abet Syllabus for Course " );
			contentStream.drawString(top);
			contentStream.endText();
			contentStream.close();
			PDPageContentStream contentStream1 = new PDPageContentStream(document, page,true,true);
			contentStream1.beginText();
			contentStream1.setFont( font, 12 );
			contentStream1.moveTextPositionByAmount( 75, 650 );
			contentStream1.drawString( "Course : " );
			contentStream1.drawString(cor);
			contentStream1.endText();
			contentStream1.close();
			PDPageContentStream contentStream4 = new PDPageContentStream(document, page,true,true);
			contentStream4.beginText();
			contentStream4.setFont( font, 12 );
			contentStream4.moveTextPositionByAmount( 75, 630 );
			contentStream4.drawString( "Number of Credits : " );
			contentStream4.drawString(credits);
			contentStream4.endText();
			contentStream4.close();			
			PDPageContentStream contentStream2 = new PDPageContentStream(document, page,true,true);
			contentStream2.beginText();
			contentStream2.setFont( font, 12 );
			contentStream2.moveTextPositionByAmount( 75, 610 );
			contentStream2.drawString( "Instructor : " );
			contentStream2.drawString(prof);
			contentStream2.endText();
			contentStream2.close();
			PDPageContentStream contentStream7 = new PDPageContentStream(document, page,true,true);
			contentStream7.beginText();
			contentStream7.setFont( font, 12 );
			contentStream7.moveTextPositionByAmount( 75, 590 );
			contentStream7.drawString( "Course Specific Information : " );
			contentStream7.endText();
			contentStream7.close();
			PDPageContentStream contentStream5 = new PDPageContentStream(document, page,true,true);
			contentStream5.beginText();
			contentStream5.setFont( font, 12 );
			contentStream5.moveTextPositionByAmount( 75, 570 );
			contentStream5.drawString( "TextBook : " );
			contentStream5.drawString(book);
			contentStream5.endText();
			contentStream5.close();
			PDPageContentStream contentStream6 = new PDPageContentStream(document, page,true,true);
			contentStream6.beginText();
			contentStream6.setFont( font, 12 );
			contentStream6.moveTextPositionByAmount( 75, 550 );
			contentStream6.drawString( "LabHours : " );
			contentStream6.drawString(lab);
			contentStream6.endText();
			contentStream6.close();
			
			PDPageContentStream contentStream8 = new PDPageContentStream(document, page,true,true);
			contentStream8.beginText();
			contentStream8.setFont( font, 12 );
			contentStream8.moveTextPositionByAmount( 75, 530 );
			contentStream8.drawString( "Course Learning Goals : " );
			
			
			contentStream8.drawString(clolines.get(0));
			contentStream8.newLineAtOffset(75, -1.5f*12);
			for(int n=1;n<clolines.size();n++)
				
			{
				
				contentStream8.drawString(clolines.get(n));
				contentStream8.newLineAtOffset(0, -1.5f*12);
				
				
				
				}
			
			contentStream8.endText();
			contentStream8.close();
			
			
			PDPageContentStream contentStream9 = new PDPageContentStream(document, page,true,true);
			contentStream9.beginText();
			contentStream9.setFont( font, 12 );
			contentStream9.moveTextPositionByAmount( 75, temp );
			contentStream9.drawString( "CLO maps SO : " );
			
			
			contentStream9.drawString(maplines.get(0));
			contentStream9.newLineAtOffset(75, -1.5f*12);
			for(int n=1;n<maplines.size();n++)
				
			{
				
				contentStream9.drawString(maplines.get(n));
				contentStream9.newLineAtOffset(0, -1.5f*12);
											
				}
			
			contentStream9.endText();
			contentStream9.close();
			
			if(temp2>10){
			
			PDPageContentStream contentStream3 = new PDPageContentStream(document, page,true,true);
			contentStream3.beginText();
			contentStream3.setFont( font, 12 );
			contentStream3.moveTextPositionByAmount( 75, temp1 );
			contentStream3.drawString( "Topics Covered : " );
			
			
			contentStream3.drawString(lines.get(0));
			contentStream3.newLineAtOffset(75, -1.5f*12);
			for(int n=1;n<lines.size();n++)
				
			{
				
				contentStream3.drawString(lines.get(n));
				contentStream3.newLineAtOffset(0, -1.5f*12);
				
				
				
				}
			
			contentStream3.endText();
			contentStream3.close();
			}
			else{
				document.addPage(page2);
				PDPageContentStream contentStream3 = new PDPageContentStream(document, page2,true,true);
				contentStream3.beginText();
				contentStream3.setFont( font, 12 );
				contentStream3.moveTextPositionByAmount( 75, 700 );
				contentStream3.drawString( "Topics Covered : " );
				
				
				contentStream3.drawString(lines.get(0));
				contentStream3.newLineAtOffset(75, -1.5f*12);
				for(int n=1;n<lines.size();n++)
					
				{
					
					contentStream3.drawString(lines.get(n));
					contentStream3.newLineAtOffset(0, -1.5f*12);
					
					
					
					}
				
				contentStream3.endText();
				contentStream3.close();
			}
			
			
			/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
			document.save(baos);*/
			document.save("/home/thigu1a/Downloads/abfil.pdf");                  //the pdf file is saved at this location
			
			document.close();
			/*byte[] pdfBytes = baos.toByteArray();
			baos.close();*/
			
			//response.setHeader("Expires", "0");
			//response.setHeader("Cache-Control",
			//    "must-revalidate, post-check=0, pre-check=0");                 //use this code when using on other machines
			//response.setHeader("Pragma", "public");
			// setting the content type
			/*response.setContentType("application/pdf");
			
			response.setContentLength(pdfBytes.length);
			// write ByteArrayOutputStream to the ServletOutputStream
			OutputStream os = response.getOutputStream();
			os.write(pdfBytes);
			os.flush();*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
