package fun.crejo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class serviceMethodsImpl implements ServiceMethods {

	HashMap<String, Movies> movieMapping = ServiceDatabase.movieMapping;
	HashMap<String, User> userMapping = ServiceDatabase.userMapping;
	
	@Override
	public String addMovie(String movieName, int yearOfRelease, String ...genresForThisMovie) {
		HashSet<String> genres = new HashSet<String>();
		for(String s: genresForThisMovie) {
			genres.add(s.toLowerCase());
		}
		String movieLowerCase = movieName.toLowerCase();
		if(movieMapping.containsKey(movieLowerCase)==true) {
			throw new CustomExecption("Execption: Movie" + movieName +" is already there");
		}
		else {
			Movies val = new Movies(movieLowerCase, yearOfRelease, genres,0,0);
			movieMapping.put(movieLowerCase, val);
		}
		return "1";
	}

	@Override
	public String addUser(String userName) {
		String userLowerCase = userName.toLowerCase();
		
		if(userMapping.containsKey(userLowerCase)==true) {
			throw new CustomExecption("Execption: User" + userName +" is already there");
		}
		
		else {
			User theUser = new User(userLowerCase, 0,new HashMap<String, Integer>(),new HashMap<String, Integer>(),0,0);
			userMapping.put(userLowerCase, theUser);
		}
		return "1";
	}

	@Override
	public String addReviews(String userName, String movieName, int rating) {
		
		String userLowerCase = userName.toLowerCase();
		String movieLowerCase = movieName.toLowerCase();
		
		User userInfo = userMapping.get(userLowerCase);
		Movies movieInfo = movieMapping.get(movieLowerCase);
		LocalDate currentdate = LocalDate.now();
		
		if(userMapping.containsKey(userLowerCase)==false) {
			throw new CustomExecption("Execption: User" + userLowerCase +" is Not there");
		}
		if(movieMapping.containsKey(movieLowerCase)==false) {
			throw new CustomExecption("Execption: Movie" + movieLowerCase +" is Not there");
		}
		if(rating>10 || rating <0) {
			throw new CustomExecption("Please enter rating between 1 to 10");
		}
		if(currentdate.getYear()<movieInfo.releaseYear) {
			throw new CustomExecption("You Cannot rate a movie which will release in future");
		}
		if(userInfo.movies!=null && userInfo.movies.containsKey(movieLowerCase)==true) {
			throw new CustomExecption("The movie "+movieLowerCase+" is alreay rated by "+userLowerCase);
		}
		
		if(userInfo.capability<ServiceDatabase.noOfLevels-1 && userInfo.noOfRatingsGiven==ServiceDatabase.noOfReviewsForCapability[userInfo.capability+1])
			userInfo.capability++;
		
		int multiplicationFactorOnReviews = ServiceDatabase.capabilityFactor[userInfo.capability];
		userInfo.movies.put(movieLowerCase, rating*multiplicationFactorOnReviews);
		
		userInfo.movieLevel.put(movieLowerCase, userInfo.capability);
		
		userMapping.put(userLowerCase, userInfo);
		
		movieInfo.totalRating+=rating*multiplicationFactorOnReviews;
		movieInfo.noOfReviews++;
		movieMapping.put(movieLowerCase, movieInfo);
		
		userInfo.noOfRatingsGiven++;
		return "1";
		
	}

	@Override
	public float averageReviewScoreYear(int year) {
		float response = 0;
		float totRating = 0;
		float totReview = 0;
		
		for(Map.Entry<String, Movies> me: movieMapping.entrySet()) {
			if(me.getValue().releaseYear==year) {
				totReview+=me.getValue().noOfReviews;
				totRating+=me.getValue().totalRating;
			}	
		}
		
		response = totRating/totReview;
		return response;
	}

	@Override
	public float averageReviewScoreGenre(String genre) {
		
		genre = genre.toLowerCase();
		float response = 0;
		float totRating = 0;
		float totReview = 0;
		
		for(Map.Entry<String, Movies> me: movieMapping.entrySet()) {
			if(me.getValue().genres.contains(genre)==true) {
				totReview+=me.getValue().noOfReviews;
				totRating+=me.getValue().totalRating;
			}	
		}
		
		response = totRating/totReview;
		return response;
	}

	@Override
	public float averageReviewScoreMovie(String movie) {
		
		movie = movie.toLowerCase();
		float response = 0;
		float totRating = 0;
		float totReview = 0;
		
		Movies mv = movieMapping.get(movie);
		totRating = mv.totalRating;
		totReview = mv.noOfReviews;
		
		response = totRating/totReview;
		return response;
	}

	@Override
	public List<String> topNMoviesCapabilityWise(int n, int isCritic, int year) {
		
		List<String> reponse = new ArrayList<String>();
		
		Map<String, Integer> mp =  new HashMap<String, Integer>();
		
		for(Map.Entry<String, User> me: userMapping.entrySet()) {
			
			Map<String, Integer> ratedMovies = me.getValue().movies;
			
			for(Map.Entry<String, Integer> theRatedMovies: ratedMovies.entrySet()) {
				if(movieMapping.get(theRatedMovies.getKey()).releaseYear==year) {
					
					if(me.getValue().movieLevel.get(theRatedMovies.getKey())==isCritic) {
						if(mp.containsKey(theRatedMovies.getKey())) {
							int prevValue = mp.get(theRatedMovies.getKey());
							mp.put(theRatedMovies.getKey(), prevValue+theRatedMovies.getValue());
						}
						else {
							mp.put(theRatedMovies.getKey(), theRatedMovies.getValue());
						}
					}
					
				}
			}
		}
		
		List<Map.Entry<String, Integer>> lst = new LinkedList<Map.Entry<String, Integer>>(mp.entrySet());
		
		Collections.sort(lst, new Comparator<Map.Entry<String, Integer>>(){

			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
			
		});
		
		for(Map.Entry<String, Integer> me: lst) {
			if(n==0)
				break;
			reponse.add(me.getKey());
			n--;
		}
		
		
		return reponse;
	}

	@Override
	public List<String> topNMoviesGenre(int n, int isCritic, String genre) {
		
		genre = genre.toLowerCase();
		List<String> reponse = new ArrayList<String>();
		
		Map<String, Integer> mp =  new HashMap<String, Integer>();
		
		for(Map.Entry<String, User> me: userMapping.entrySet()) {
			
			Map<String, Integer> ratedMovies = me.getValue().movies;
			
			for(Map.Entry<String, Integer> theRatedMovies: ratedMovies.entrySet()) {
				if(movieMapping.get(theRatedMovies.getKey()).genres.contains(genre)) {
					
					if(me.getValue().movieLevel.get(theRatedMovies.getKey())==isCritic) {
						if(mp.containsKey(theRatedMovies.getKey())) {
							int prevValue = mp.get(theRatedMovies.getKey());
							mp.put(theRatedMovies.getKey(), prevValue+theRatedMovies.getValue());
						}
						else {
							mp.put(theRatedMovies.getKey(), theRatedMovies.getValue());
						}
					}
				}
			}
		}
		
		List<Map.Entry<String, Integer>> lst = new LinkedList<Map.Entry<String, Integer>>(mp.entrySet());
		
		Collections.sort(lst, new Comparator<Map.Entry<String, Integer>>(){

			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
			
		});
		
		for(Map.Entry<String, Integer> me: lst) {
			if(n==0)
				break;
			reponse.add(me.getKey());
			n--;
		}
		
		
		return reponse;
	}
	
}
