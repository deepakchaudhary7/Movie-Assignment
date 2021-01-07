package fun.crejo;

import java.util.Map;

public class User {
	String name;
	int reviewCount;
	Map<String, Integer> movies;	// movies that the user reviewed
	Map<String, Integer> movieLevel;
	
	int capability; // normal viewer - 0, critic - 1, and so on.....
	int noOfRatingsGiven;
	
	public User(String name, int reviewCount, Map<String, Integer> movies, Map<String, Integer> movieLevel,
			int capability, int noOfRatingsGiven) {
		this.name = name;
		this.reviewCount = reviewCount;
		this.movies = movies;
		this.movieLevel = movieLevel;
		this.capability = capability;
		this.noOfRatingsGiven = noOfRatingsGiven;
	}
	
	
	
	
	
	
	
}
