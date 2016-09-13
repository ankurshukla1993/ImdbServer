package dataCollection;


public class MovieObject {
	
	private String img_src ;
	private String title ;
	private String description ;
	private String rating ;
	private String duration ;
	private String genre ;
	private String director ;
	
	
	public MovieObject(String img_src, String title, String description,
			String rating, String duration, String genre, String director) {
		super();
		this.img_src = img_src;
		this.title = title;
		this.description = description;
		this.rating = rating;
		this.duration = duration;
		this.genre = genre;
		this.director = director ;
	}
	

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getImg_src() {
		return img_src;
	}
	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}

}
