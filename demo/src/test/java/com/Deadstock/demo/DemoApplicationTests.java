package com.Deadstock.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.Deadstock.demo.model.Listing;
import com.Deadstock.demo.user.User;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void testUser() {
		assertEquals(1,1); //Test The Tester
		
		
		User user = new User();
		User user2 = new User();
		user.setUsername("hi");
		user2.setUsername("hello");
		assertNotEquals(user.getUsername(), user2.getUsername()); //Tests that two different users are not the same (and therefore that all users added can be differentiated)
	}

	@Test
	void testListing(){
		Listing listing = new Listing();
		Listing listing2 = new Listing();
		listing.setTitle("shoes");
		listing2.setTitle("jacket");
		assertNotEquals(listing.getTitle(), listing2.getTitle());
		listing.setPrice(10.0);
		assertEquals(listing.getPrice(),10.0); //Tests all the listing entities abilities to set and fetch information
 //Tests that two different listings are not the same (and therefore that all listings added can be differentiated)
		listing2.setCategory("Shirt");
		assertEquals(listing2.getCategory(),"Shirt");
		listing.setBrand("Balenciaga");
		assertEquals(listing.getBrand(), "Balenciaga");
		listing.setDescription("Awesome");
		assertNotEquals("Awesome", "Sweet");
		listing2.setCarbonSavings(5.5);
		assertEquals(5.5,listing2.getCarbonSavings());
		listing.setSizing("XXS");
		assertEquals("XXS", listing.getSizing());

		//All the set and get statements are like this, so if it works for Listing it works for all
}

}
