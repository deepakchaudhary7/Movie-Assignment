package fun.crejo;

import java.util.HashSet;

public class Movies {
	String movieName;
	int releaseYear;
	HashSet<String> genres;	
	int totalRating;
	int noOfReviews;
	
	
	public Movies(String movieName, int releaseYear, HashSet<String> genres, int totalRating, int noOfReviews) {
		this.movieName = movieName;
		this.releaseYear = releaseYear;
		this.genres = genres;
		this.totalRating = totalRating;
		this.noOfReviews = noOfReviews;
	}
	
	// can add more details in future
	
	
}
