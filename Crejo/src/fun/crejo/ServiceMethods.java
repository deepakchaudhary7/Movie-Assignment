package fun.crejo;

import java.util.List;

public interface ServiceMethods {
	
	String addMovie(String movieName, int yearOfRelease, String ...genresForThisMovie);
	
	String addUser(String userName);
	
	String addReviews(String userName, String movieName, int rating);
	
	float averageReviewScoreYear(int year);
	
	float averageReviewScoreGenre(String genre);
	
	float averageReviewScoreMovie(String movie);
	
	List<String> topNMoviesCapabilityWise(int n, int isCritic, int year);
	
	List<String> topNMoviesGenre(int n, int isCritic, String genre);
	
}
