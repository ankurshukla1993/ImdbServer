package ServerListing;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataCollection.*;

@WebServlet("/ListServer")
public class ListServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<dataCollection.MovieObject> list = new ArrayList<dataCollection.MovieObject>() ;;
      
    public ListServer() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		NameParser n = new NameParser() ;
		
		String question = request.getParameter("task") ;
		if(question.equals("data")){
			System.out.println("Asked for Data Collection");
			n.getData();
			list = n.getMovielist() ;
			System.out.println("\n\nMovie List from Server: \n" + list.toString());
		}
			
		else{
			System.out.println("Asked for list");
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(list.toString());
			System.out.println("\n\nMovie List from Server: \n" + list.toString());
		}
			
		
	}

}
