import static org.junit.Assert.*;

import org.junit.Test;


public class NewLineRemoverTest {

	@Test
	public void test() {
		assertFalse(NewLineRemover.hasLineBreaks("dasfkadsfa"));
		assertFalse(NewLineRemover.hasLineBreaks("\"dasfkadsfa\""));
		assertFalse(NewLineRemover.hasLineBreaks("\"dasf\"kad\"sfa\""));
		assertTrue(NewLineRemover.hasLineBreaks("\"dasfkadsfa"));
		assertTrue(NewLineRemover.hasLineBreaks("dasfkadsfa\""));
		assertTrue(NewLineRemover.hasLineBreaks("\"\"dasfkadsfa\""));
	}

}
