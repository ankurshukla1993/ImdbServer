package dataCollection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class NameParser {
	
	private BufferedReader br ;
	private FileInputStream fstream = null;
	private String strLine;
	private String top250 = "http://www.imdb.com/chart/top" ;
    
    
	private int line = 0 ;
	private String parts[] ;
    
	private File top250Html = new File("Top250HTML.txt") ;
	private File movieLinks = new File("MovieLinks.txt") ;
	private File values = new File("Values.txt") ;
	private File movieHtml = new File("1.txt") ;	    
	private PrintWriter p ;
	private String link = "" ;
	public List<MovieObject> movieList ;
	private List<String> movieLinksList ;
	
	public List<MovieObject> getMovielist() {
		return movieList;
	}
	
	public static void main(String[] args) {
		NameParser n = new NameParser() ;
				
		n.getData() ;   // get HTML Page for top 250 and store the links in File 'top250Html'
		
		//System.out.println("\n\nMovie List : \n" + n.movieList.toString());
	    
	}
	
	
	
	// Method used to get the HTML source page for String top250 = "http://www.imdb.com/chart/top"
	// and print the source code in top250Html
	public void getData(){
			
		URL url;
		InputStream is = null;
		BufferedReader br;
		String line;
		PrintWriter writer = null ;
		    
		    
		try {
			writer = new PrintWriter(top250Html, "UTF-8");
			url = new URL(top250);
		    is = url.openStream();  // throws an IOException
		    br = new BufferedReader(new InputStreamReader(is));
	
		    while ((line = br.readLine()) != null) {
		        writer.println(line);
		    }
		    getMainData();// parse the top250Html page to get the links of the pages for 250 movies
		    // the links for all the movies is stored in the file movieLinks using method getMainData
		} catch (MalformedURLException mue) {
		     mue.printStackTrace();
		} catch (IOException ioe) {
		     ioe.printStackTrace();
		} finally {
		    try {
		        if (is != null) is.close();
		    } catch (IOException ioe) {
		    	ioe.printStackTrace();
		    }
		}
	}
	
	
	
	// Method to read the top250Html page and get the links for 250 movies
	// the links are then stored in list movieLinks
	public void getMainData(){
		
		movieLinksList = new ArrayList<String>() ;
		
		try {
			fstream = new FileInputStream(top250Html);
			br = new BufferedReader(new InputStreamReader(fstream));
			p = new PrintWriter(movieLinks) ;
		    while ((strLine = br.readLine()) != null)   {
		      //System.out.println (strLine);
		    	if(strLine.matches("(.*)titleColumn(.*)")){
		    		line++ ;
		    		System.out.println("Line = " + line + ":");
		    		for(int i = 0; i < 2; i++){
		    			strLine = br.readLine() ; 
		    		}
		    		parts = strLine.split("=") ;
		    		for(int i = 0; i < parts.length-1; i++)
		    			link = link + parts[i+1] ;
		    		p.println(link);
		    		movieLinksList.add(link) ;
		    		link = "" ;
		    	}
		    	
		    }
		    br.close();
		    
		    getDataEveryMovie() ;// Method call to read the movieLinks file and get the Html page for 250 movies and parse them
		    
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Method to get the Html pages for all the 250 links. The Html page is stored in movieHtml.
	// MovieHtml page is parsed for every link using th readForMovie() method and final movie object is 
	// written to List = movieList inn the method readForMovie()
	public void getDataEveryMovie(){
		
		URL url;
		String surl ;
	    InputStream is = null;
	    BufferedReader br;
	    String line;
	    PrintWriter writer = null ;
	    movieList = new ArrayList<MovieObject>() ;
	    
	    
	    	for(int i = 0; i < movieLinksList.size(); i++){
	    		System.out.println("Looping for i = " + i);
				surl = "" ;
				surl = "http://www.imdb.com" + movieLinksList.get(i).replaceAll("\"", "") ;
				System.out.println("\n\nMovie Number :: " + i);
				//System.out.println("\nlink = " + surl);
				try {
					writer = new PrintWriter(movieHtml, "UTF-8");
					url = new URL(surl);
				    is = url.openStream();
				    br = new BufferedReader(new InputStreamReader(is));

				    while ((line = br.readLine()) != null) {
				        writer.println(line);
				    }
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			    readForMovie(); // Method call to read the movie's Html page and make the Movie object

				clearTheFile();
	    	}
			
	    
	}
	
	public  void clearTheFile() {
        FileWriter fwOb;
		try {
			fwOb = new FileWriter(movieHtml, false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
	        pwOb.flush();
	        pwOb.close();
	        fwOb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
    }
	
	
	// Method to read the movieHtml file and parse to get the values for the MovieObject
	public void readForMovie(){
		
		BufferedReader br ;
	    FileInputStream fstream = null;
	    String strLine;
	    String parts[] ;
	    MovieObject movie ;
	    String img_src = "";
	    String title = "";
	    String description = "";
	    String rating =  "";
	    String duration =  "";
	    String genre = "";
	    String director = "";
	    PrintWriter k;
		
		
		try {
			fstream = new FileInputStream(movieHtml);
			br = new BufferedReader(new InputStreamReader(fstream));
			int yearc = 0;
			int genc = 0 ;
			genre = "" ;
			k = new PrintWriter(values);
			
		    while ((strLine = br.readLine()) != null)   {
		      
				//System.out.println (strLine);
		    	if(strLine.matches("(.*)image_src(.*)")){
		    		//System.out.println(strLine);
		    		parts = strLine.split("=") ;
		    		parts[2] = parts[2].replaceAll("\"", "") ;
		    		parts[2] = parts[2].replaceAll(">", "") ;
		    		img_src = parts[2] ;
		    		k.println("Image Source = "+parts[2]);
		    		System.out.println("Image Source = " + parts[2]);
		    	}
		    	else if(strLine.matches("(.*)property='og:title' content=(.*)")){
		    		//System.out.println("Line = in values" + line + ":" + strLine);		    		
		    		parts = strLine.split("\"") ;
		    		//String title[] = parts[1].split("\"") ;
		    		title = parts[1] ;
		    		k.println("Title = "+parts[1]);
		    		System.out.println("Title = " + parts[1]);
		    	}
		    	else if(strLine.matches("(.*)<time itemprop=\"duration\" datetime=\"PT142M\">(.*)") && yearc  == 0){
		    		
		    		strLine = br.readLine() ;
		    		strLine = strLine.replaceAll(" ", "") ;
		    		duration = strLine ;
		    		k.println("Duration = " + strLine) ;
		    		System.out.println("Duration = " + strLine);
		    		yearc++ ;
		    	}
		    	else if(strLine.matches("(.*)<meta name=\"description(.*)")){
		    		//System.out.println(strLine);
		    		parts = strLine.split("=") ;// />
		    		parts[2] = parts[2].replaceAll("\"", "") ;
		    		parts[2] = parts[2].replaceAll(" />", "") ;
		    		description = parts[2].replaceAll("\"", "") ;
		    		//description = description.replaceAll("[^\\p{L}\\p{Nd}]+", "");
		    		k.println("Description = " + description);
		    		System.out.println("Description = " + description);
		    	}
		    	else if(strLine.matches("(.*)<span itemprop=\"ratingValue\">(.*)")){
		    		//System.out.println(strLine);
		    		//System.out.println("Index = " + ind);
		    		if(strLine.charAt(83) == '/')
		    			rating = strLine.substring(79, 82) ;
		    		else
		    			rating = strLine.substring(81, 84) ;
		    		k.println("Rating = " + rating);
		    		System.out.println("Rating = " + rating);
		    		/*System.out.println(strLine.charAt(ind+15));
		    		System.out.println(strLine.charAt(ind+16));*/
		    	}
		    	else if(strLine.matches("(.*)itemprop=\"genre\"(.*)") &&(genc == 0)){
		    		genc++ ;
		    		//System.out.println(strLine);
		    		/*int ind = strLine.lastIndexOf("\"genre\">") ;
		    		System.out.println(strLine.substring(41, 84));*/
		    		parts = strLine.split(">") ;
		    		parts[2] = parts[2].replaceAll("</span", "") ;
		    		genre = genre + parts[2] + "," ;
		    		if(genc == 1){
		    			k.println(genre);
		    			System.out.println(genre);
		    		}
		    	}
		    	else if(strLine.matches("(.*)<h4 class=\"inline\">Director:</h4>(.*)")){
		    		
		    		for(int i = 0; i < 3; i++){
		    			strLine = br.readLine() ;
		    		}
		    		//System.out.println(strLine);
		    		parts = strLine.split(">") ;
		    		parts[2] = parts[2].replaceAll("</span", "") ;
		    		k.println(parts[2]);
		    		System.out.println(parts[2]);
		    	}
		    	
		    }
		    
		    movie = new MovieObject(img_src, title, description, rating, duration, genre, director) ;
		    movieList.add(movie) ;
		    k.println(movie);
		    
		    br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}		
}