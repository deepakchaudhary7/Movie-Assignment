package fun.crejo;

import java.util.List;

public class ApplicationDriver {

	public static void main(String[] args) {
		ServiceMethods serviceMethod = new serviceMethodsImpl();
		
		serviceMethod.addMovie("Don", 2006, "Action", "Comedy");
		serviceMethod.addMovie("Tiger", 2008, "Drama");
		serviceMethod.addMovie("Padmaavat", 2006, "Comedy");
		serviceMethod.addMovie("Lunchbox", 2021, "Drama");
		serviceMethod.addMovie("Guru", 2006, "Drama");
		serviceMethod.addMovie("Metro", 2006, "Romance");
		
		serviceMethod.addUser("SRK");
		serviceMethod.addUser("Salman");
		serviceMethod.addUser("Deepika");
		
		serviceMethod.addReviews("SRK", "Don", 2);
		serviceMethod.addReviews("SRK", "Padmaavat", 8);
		serviceMethod.addReviews("Salman", "Don", 5);
		serviceMethod.addReviews("Deepika", "Don", 9);
		serviceMethod.addReviews("Deepika", "Guru", 6);
		serviceMethod.addReviews("SRK", "Tiger", 5);
		serviceMethod.addReviews("SRK", "Metro", 7);
		
		List<String> ret = serviceMethod.topNMoviesCapabilityWise(1, 0, 2006);
		
		System.out.println(ret.get(0));
		
	}

}
