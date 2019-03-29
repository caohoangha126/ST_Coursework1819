import static org.junit.Assert.*;
import org.junit.Test;
import st.Parser;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Debug {
	private Parser parser;

	@Before
	public void setUp() {
		parser = new Parser();
	}

	@Test
	public void testInvalidInput5() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("-l=\'   4-7-9-12-15   \'");
		System.out.println(parser.getIntegerList("l"));
		assertTrue(parser.getIntegerList("l").isEmpty());
	}
}
