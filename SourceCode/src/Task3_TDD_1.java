import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import st.Parser;

import org.junit.Before;

public class Task3_TDD_N {
	private Parser parser;

	@Before
	public void setUp() {
		parser = new Parser();
	}
	
	/**
	 * 3.1
	 */
	@Test
	public void optionOrderTest() {
		parser.add("o", Parser.STRING);
		parser.parse("--o=0 1 2");
		parser.add("oahu", "o", Parser.STRING);
		parser.parse("--oahu=3 4 5");
		List oSecond = parser.getIntegerList("oahu");
		assertTrue(parser.getIntegerList("o") != oSecond);
	}
	
	/**
	 * 3.2
	 */
	@Test
	public void noValueTest() {
		parser.add("o", Parser.STRING);
		List o = parser.getIntegerList("o");
		assertTrue(o.isEmpty());
	}
	
	/**
	 * 3.3
	 */	
	@Test
	public void nonNumberCommaTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1,2,3,4,5");
		List l = parser.getIntegerList("list");	
		System.out.println(testList);
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberSpaceTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1 2 3 4 5");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberPeriodTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1.2.3.4.5");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberColonsTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1:2:3;4;5");
		List l = parser.getIntegerList("list");
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberParenthesesTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list={}1(2)3/4\5[|]");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberSquiggleTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1~2`3~4_5");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberDeclarativeTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1!2?3!4?5");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberHashMoneyPercentAmpersandTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1#2$3%4&5");
		List l = parser.getIntegerList("list");
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberCarrotStarTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1^2*3^4*5");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberApostrophesTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1\"2\"3\'4\'5");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberAddEqualTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1+2=3+4=5");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	@Test
	public void nonNumberSidewaysCarrotsTest() {
		int[] arr = {1,2,3,4,5};
		List<Integer> testList = new ArrayList<Integer>(arr.length);
		for (int i : arr) {
			testList.add(Integer.valueOf(i));
		}
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=1<2>3<4>5");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	/**
	 * 3.4
	 */
	
	@Test
	public void inclusiveRangeTest() {
		parser.add("list1",  "l1", Parser.STRING);
		parser.parse("--list1=1-50");
		List l1 = parser.getIntegerList("list");	
		parser.add("list2",  "l2", Parser.STRING);
		parser.parse("--list2=50-1");
		List l2 = parser.getIntegerList("list");
		assertTrue(l2 == l1);
	}
	
	/**
	 * 3.5
	 */
	@Test
	public void allNegativeValuesDecreasingTest() {
		List<String> testList = Arrays.asList("-5", "-4", "-3", "-2","-1");
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=-1--5");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	@Test
	public void allNegativeValuesIncreasingTest() {
		List<String> testList = Arrays.asList("-2", "-1", "0", "1","2");
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=-2-2");
		List l = parser.getIntegerList("list");
		assertEquals(testList, l);
	}
	
	@Test
	public void positiveAndNegativeValuesTest() {
		List<String> testList = Arrays.asList("-5", "-4", "-3", "-2","-1");
		parser.add("list",  "l", Parser.STRING);
		parser.parse("--list=-5--1");
		List l = parser.getIntegerList("list");	
		assertEquals(testList, l);
	}
	
	/**
	 * 3.6
	 */
	@Test
	public void hyphenLastOneItemTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=3-");
		List l = parser.getIntegerList("list");
		assertTrue(l.isEmpty());
	}
	
	@Test
	public void hyphenLastLastItemTest() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=0-2,3-");
		List l = parser.getIntegerList("list");
		assertTrue(l.isEmpty());
	}
}
