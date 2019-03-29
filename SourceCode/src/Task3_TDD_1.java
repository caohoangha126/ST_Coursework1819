import static org.junit.Assert.*;
import org.junit.Test;
import st.Parser;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task3_TDD_1 {
	private Parser parser;

	@Before
	public void setUp() {
		parser = new Parser();
	}

	/*
	 * Specification 1
	 * Search order: full name -> shortcut
	 */
	@Test
	public void testSearchOrder() {
		parser.add("o", Parser.STRING);
		parser.parse("--o=10,11,12");
		parser.add("long_o", "o", Parser.STRING);
		parser.parse("-o=300,400,500");
		List<Integer> expected = Arrays.asList(10, 11, 12);
		assertTrue(expected.equals(parser.getIntegerList("o")));
	}

	/*
	 * Specification 2
	 * If option is not provided a value, empty list is returned
	 */
	@Test
	public void testNoValueProvided() {
		parser.add("o", Parser.STRING);
		assertTrue(parser.getIntegerList("o").isEmpty());
	}

	/*
	 * Specification 3
	 * Non-number separators between numbers except hyphen (-)
	 */
	@Test
	public void testNonNumberSeparator1() {
		parser.add("list1", Parser.STRING);
		parser.parse("--list=\"1,2 4\"");
		parser.add("list2", Parser.STRING);
		parser.parse("--list=1,2.4");
		assertTrue(parser.getIntegerList("list1").equals(parser.getIntegerList("list2")));
	}

	@Test
	public void testNonNumberSeparator2() {
		parser.add("list1", Parser.STRING);
		parser.parse("--list=\"1,2 4\"");
		parser.add("list2", Parser.STRING);
		parser.parse("--list={}1<>2!!4({)");
		assertTrue(parser.getIntegerList("list1").equals(parser.getIntegerList("list2")));
	}

	/*
	 * Specification 4
	 * Hyphen indicates an inclusive range of numbers
	 */
	@Test
	public void testHyphenInclusiveRange() {
		parser.add("list1", Parser.STRING);
		parser.parse("--list1=1-3");
		parser.add("list2", Parser.STRING);
		parser.parse("--list2=3-1");
		assertTrue(parser.getIntegerList("list1").equals(parser.getIntegerList("list2")));
	}

	/*
	 * Specification 5
	 * Unary prefix hyphen indicates negative value
	 */

	// All negative numbers, descending order
	@Test
	public void testAllNegativesDescending() {
		List<Integer> expected = Arrays.asList(-5, -4, -3, -2, -1);
		parser.add("list", Parser.STRING);
		parser.parse("--list=-1--5");
		assertTrue(expected.equals(parser.getIntegerList("list")));
	}

	// All negative numbers, ascending order
	@Test
	public void testAllNegativesAscending() {
		List<Integer> expected = Arrays.asList(-5, -4, -3, -2, -1);
		parser.add("list", Parser.STRING);
		parser.parse("--list=-5--1");
		assertTrue(expected.equals(parser.getIntegerList("list")));
	}

	// Negative to positive numbers, descending order
	@Test
	public void testNegativesAndPositivesDescending() {
		List<Integer> expected = Arrays.asList(-2, -1, 0, 1, 2);
		parser.add("list", Parser.STRING);
		parser.parse("--list=2--2");
		assertTrue(expected.equals(parser.getIntegerList("list")));
	}

	// Negative to positive numbers, ascending order
	@Test
	public void testNegativesAndPositivesAscending() {
		List<Integer> expected = Arrays.asList(-2, -1, 0, 1, 2);
		parser.add("list", Parser.STRING);
		parser.parse("--list=-2-2");
		assertTrue(expected.equals(parser.getIntegerList("list")));
	}

	// All positive numbers, descending order
	@Test
	public void testAllPositivesDescending() {
		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
		parser.add("list", Parser.STRING);
		parser.parse("--list=5-1");
		assertTrue(expected.equals(parser.getIntegerList("list")));
	}

	// All positive numbers, ascending order
	@Test
	public void testAllPositivesAscending() {
		List<Integer> expected = Arrays.asList(1, 2, 3, 4, 5);
		parser.add("list", Parser.STRING);
		parser.parse("--list=1-5");
		assertTrue(expected.equals(parser.getIntegerList("list")));
	}

	// Range and individual numbers together
	@Test
	public void testRangeAndIndividualNumbers() {
		List<Integer> expected = Arrays.asList(-2, -1, 0, 1, 2, 3);
		parser.add("list", Parser.STRING);
		parser.parse("--list=${0--2&&1#3-2"); // With different separators
		assertTrue(expected.equals(parser.getIntegerList("list")));
	}

	/*
	 * Specification 7
	 * Hyphen can't be used as suffix
	 */
	@Test
	public void testHyphenNotSuffixOneItem() {
		parser.add("list", Parser.STRING);
		parser.parse("--list=3-");
		assertTrue(parser.getIntegerList("list").isEmpty());
	}

	@Test
	public void testHyphenNotSuffixLastItem() {
		parser.add("list", Parser.STRING);
		parser.parse("--list=0-2,3-");
		assertTrue(parser.getIntegerList("list").isEmpty());
	}

	/*
	 * getIntegerList() returns empty list for options of type Integer, Boolean, or Char
	 */
	@Test
	public void testGetIntegerListForInteger() {
		parser.add("int", Parser.INTEGER);
		assertTrue(parser.getIntegerList("int").isEmpty());
	}

	@Test
	public void testGetIntegerListForBoolean() {
		parser.add("bool", Parser.BOOLEAN);
		assertTrue(parser.getIntegerList("bool").isEmpty());
	}

	@Test
	public void testGetIntegerListForChar() {
		parser.add("char", Parser.CHAR);
		assertTrue(parser.getIntegerList("char").isEmpty());
	}
	
	/*
	 * Additional testing
	 */
	
	@Test
	public void testInvalidInput1() {
		parser.add("list", Parser.STRING);
		parser.parse("--list=-2--2");
		assertTrue(parser.getIntegerList("list").isEmpty());
	}
	
	@Test
	public void testInvalidInput2() {
		parser.add("list", Parser.STRING);
		parser.parse("--list --123");
		assertTrue(parser.getIntegerList("list").isEmpty());
	}
	
	@Test
	public void testInvalidInput3() {
		parser.add("list", Parser.STRING);
		parser.parse("--list 5---2");
		assertTrue(parser.getIntegerList("list").isEmpty());
	}
	
	@Test
	public void testInvalidInput4() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("-l=\'--2\'");
		assertTrue(parser.getIntegerList("l").isEmpty());
	}
	
	@Test
	public void testInvalidInput5() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("-l=\'4-7-9-12-15  \'");
		assertTrue(parser.getIntegerList("l").isEmpty());
	}
	
	@Test
	public void testInvalidInput6() {
		parser.add("list", Parser.STRING);
		parser.parse("-l=4----9");
		assertTrue(parser.getIntegerList("list").isEmpty());
	}
	
	@Test
	public void testInvalidInput7() {
		parser.add("o", Parser.STRING);
		parser.parse("--list=-7{--5");
		assertTrue(parser.getIntegerList("list").isEmpty());
	}
	
	@Test
	public void testValidInput1() {
		List<Integer> expected = Arrays.asList(-2, -2);
		parser.add("list", Parser.STRING);
		parser.parse("--list=-2@@-2"); // Duplicate numbers
		assertEquals(expected, parser.getIntegerList("list"));
	}
	
	@Test
	public void testValidInput2() {
		ArrayList<Integer> expected = new ArrayList<Integer>();
		for (int i = -100; i <= 200; i++) {
			expected.add(i);
		}
		expected.add(-300);
		Collections.sort(expected);
		parser.add("list", Parser.STRING);
		parser.parse("--list=\'@#$200--100!!-300\'");
		assertEquals(expected, parser.getIntegerList("list"));
	}
	
	@Test
	public void testValidInput3() {
		List<Integer> expected = Arrays.asList(-10, -3, -2, -2, -1, 1, 5);
		parser.add("list", Parser.STRING);
		parser.parse("--list=\'@~~-1--3,-2, ,-10, 5, 1\'");
		assertEquals(expected, parser.getIntegerList("list"));
	}
	
	@Test
	public void testValidInput4() {
		List<Integer> expected = Arrays.asList(2, 3, 4, 5);
		parser.add("list", Parser.STRING);
		parser.parse("--list=05-02");
		assertEquals(expected, parser.getIntegerList("list"));
	}
	
	@Test
	public void testValidInput5() {
		List<Integer> expected = Arrays.asList(1, 2);
		parser.add("o", Parser.STRING);
		parser.parse("--o=1,2 4");
		assertEquals(expected, parser.getIntegerList("o"));
	}
	
	@Test
	public void testValidInput6() {
		List<Integer> expected = Arrays.asList(1, 2, 2, 3);
		parser.add("list", Parser.STRING);
		parser.parse("--list=\'1-2 , 2-3\'");
		assertEquals(expected, parser.getIntegerList("list"));
	}
	
	@Test
	public void testValidInput7() {
		List<Integer> expected = Arrays.asList(-2, -1, 0, 1, 2, 3, 4);
		parser.add("list", Parser.STRING);
		parser.parse("--list 5,8");
		parser.parse("--list \'-2-4\'");
		assertEquals(expected, parser.getIntegerList("list"));
	}
}
