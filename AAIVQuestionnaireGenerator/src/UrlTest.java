import static org.junit.Assert.*;

import org.junit.Test;


public class UrlTest {

	private String regex = "(^.*jpg$)|(^.*png$)|(^.*jpeg$)|(^.*gif$)|(^.*bmp$)|(^.*tif$)";
	
	@Test
	public void testValidUrl() {
						
		assertTrue("https://fbcdn-sphotos-a-a.akamaihd.net/hphotos-ak-xpa1/t1.0-9/10458488_10201140399591210_5515194899679479377_n.jpg".matches(regex));
	}
	
	@Test
	public void testInvalidFacebook() {
						
		assertFalse("https://www.facebook.com/photo.php?fbid=682443308457668&set=a.396686107033391.76747.100000760162968&type=1&theater".matches(regex));
	}
	
	@Test
	public void testValidPng() {				
		assertTrue("www.google.com/a.png".matches(regex));
	}
	
	@Test
	public void testValidJpg() {				
		assertTrue("www.google.com/a.jpg".matches(regex));
	}
	
	@Test
	public void testValidJpeg() {				
		assertTrue("www.google.com/a.jpeg".matches(regex));
	}
	
	@Test
	public void testValidBmp() {				
		assertTrue("www.google.com/a.bmp".matches(regex));
	}
	
	@Test
	public void testValidTif() {				
		assertTrue("www.google.com/a.tif".matches(regex));
	}
	
	@Test
	public void testInvalidAlmostUrl() {				
		assertFalse("httsdfsdfs.com".matches(regex));
	}
	

}
