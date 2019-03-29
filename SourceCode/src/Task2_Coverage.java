import static org.junit.Assert.*;
import org.junit.Test;
import st.Parser;
import st.OptionMap;
import org.junit.Before;

public class Task2_Coverage {
	private Parser parser;	
	private OptionMap optionMap;
	
	@Before
	public void setUp() {
		parser = new Parser();
		optionMap = new OptionMap();
	}
	
	/*
	 * Task 1 Specifications 3
	 * Add option with shortcut
	 */
	
	/*
	 * Task 1 Specification 3.1
	 * Add an option with the same name override option defined previously
	 */
	
	@Test
	public void testOverrideExistingOptionWithShortcut() {
		parser.add("val", "v", Parser.STRING);
		parser.add("val", "v", Parser.BOOLEAN);
		parser.parse("--val 1");	
		// Type STRING should be changed to type BOOLEAN
		assertEquals(true, parser.getBoolean("val"));
	}
	
	/*
	 * Task 1 Specification 3.2
	 * Name and shortcut conventions
	 */
	
	// A valid name should pass
	@Test 
	public void testValidNameWithShortcut() {
		parser.add("valid_name_123", "vn", Parser.STRING);
	}
	
	// Should only contain numbers, alphabets, and underscores for name
	@Test (expected = RuntimeException.class)
	public void testOnlyAlphanumericAndUnderscoreForNameWithShortcut() {
		parser.add("abc_123!", "a", Parser.STRING);	
	}
	
	// Should only contain numbers, alphabets, and underscores for shortcut
	@Test (expected = RuntimeException.class)
	public void testOnlyAlphanumericAndUnderscoreForShortcutWithShortcut() {
		parser.add("abc_123", "@a", Parser.STRING);	
	}
	
	// Number can't be first character for name
	@Test (expected = RuntimeException.class)
	public void testNumberNotFirstCharForNameWithShortcut() {
		parser.add("1a", "a", Parser.STRING);
	}
	
	// Number can't be first character for shortcut
	@Test (expected = RuntimeException.class)
	public void testNumberNotFirstCharForShortcutWithShortcut() {
		parser.add("a1", "1", Parser.STRING);
	}
	
	/*
	 * Task 1 Specification 3.3
	 * Option names and shortcuts are case-sensitive
	 */
	@Test
	public void testCaseSensitiveWithShortcut() {
		parser.add("output", "o", Parser.STRING);
		parser.add("Output", "O", Parser.STRING);
		parser.parse("--output abc");
		parser.parse("-O xyz");
		assertTrue(parser.getString("o") != parser.getString("Output"));
	}
	
	/*
	 * Task 1 Specification 3.4
	 * Shortcut of one option can be the same as name of another option
	 */
	@Test
	public void testSameNameShortcutAndOptionWithoutShortcut() {
		parser.add("longname", "shortl", Parser.STRING);
		parser.add("shortl", "l", Parser.BOOLEAN);
		parser.parse("--shortl 1");
		assertEquals(true, parser.getBoolean("l"));
	}
	/*
	 * Task 1 Specification 3.5
	 * Check values given to boolean options
	 */	
	// Assigned to true by not giving a value
	@Test
	public void testBooleanAssignedTrueWithoutGivingAValueWithShortcut() {
		parser.add("val1", "v1", Parser.BOOLEAN);
		parser.parse("--val1");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to true by a value different from 0 and false
	public void testBooleanAssignedTrueGivenNonZeroValueWithShortcut() {
		parser.add("val1", "v1", Parser.BOOLEAN);
		parser.parse("--val1 100");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to true by giving 1
	public void testBooleanAssignedTrueGivenOneWithShortcut() {
		parser.add("val1", "v1", Parser.BOOLEAN);
		parser.parse("--val1 1");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to true by giving lowercase true
	public void testBooleanAssignedLowerCaseTrueGivenTrueWithShortcut() {
		parser.add("val1", "v1", Parser.BOOLEAN);
		parser.parse("--val1 true");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to true by giving uppercase true
	public void testBooleanAssignedUpperCasesTrueGivenTrueWithShortcut() {
		parser.add("val1", "v1", Parser.BOOLEAN);
		parser.parse("--val1 True");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to false by not parsing anything (not using this option in entire string)
	@Test
	public void testBooleanAssignedFalseWithShortcut() {
		parser.add("val2", "v2", Parser.BOOLEAN);
		assertEquals(false, parser.getBoolean("val2"));
	}
	
	// Assigned to false by giving 0
	@Test
	public void testBooleanAssignedGivenZeroWithShortcut() {
		parser.add("val2", "v2", Parser.BOOLEAN);
		parser.parse("--val2 0");
		assertEquals(false, parser.getBoolean("val2"));
	}
	
	// Assigned to false by giving lowercase false
	@Test
	public void testBooleanAssignedGivenLowerCaseFalseWithShortcut() {
		parser.add("val2", "v2", Parser.BOOLEAN);
		parser.parse("--val2 false");
		assertEquals(false, parser.getBoolean("val2"));
	}

	// Assigned to false by giving uppercase false
	@Test
	public void testBooleanAssignedGivenUpperCaseFalseWithShortcut() {
		parser.add("val2", "v2", Parser.BOOLEAN);
		parser.parse("--val2 False");
		assertEquals(false, parser.getBoolean("val2"));
	}
	
	/*
	 * Task 1 Specifications 4
	 * Add option without shortcut
	 */

	/*
	 * Task 1 Specification 4.1
	 * Add an option with the same name override option defined previously
	 */
	
	@Test
	public void testOverrideExistingOptionWithoutShortcut() {
		parser.add("val", Parser.STRING);
		parser.add("val", Parser.BOOLEAN);
		parser.parse("--val 1");	
		assertEquals(true, parser.getBoolean("val"));
	}
	
	/*
	 * Task 1 Specification 4.2
	 * Name and shortcut conventions
	 */
	
	// A valid name should pass
	@Test 
	public void testValidNameWithoutShortcut() {
		parser.add("valid_name", Parser.STRING);
	}
	
	// Should only contain numbers, alphabets, and underscores for name
	@Test (expected = RuntimeException.class)
	public void testOnlyAlphanumericAndUnderscoreForNameWithoutShortcut() {
		parser.add("abc_123!", Parser.STRING);	
	}
	
	// Number can't be first character for name
	@Test (expected = RuntimeException.class)
	public void testNumberNotFirstCharForNameWithoutShortcut() {
		parser.add("1a", Parser.STRING);
	}
	
	/*
	 * Task 1 Specification 4.3
	 * Option names and shortcuts are case-sensitive
	 */
	@Test
	public void testCaseSensitiveWithoutShortcut() {
		parser.add("output", Parser.STRING);
		parser.add("Output", Parser.STRING);
		parser.parse("--output abc");
		parser.parse("--Output xyz");
		assertTrue(parser.getString("output") != parser.getString("Output"));
	}
	
	/*
	 * Task 1 Specification 4.5
	 * Check values given to boolean options
	 */	
	// Assigned to true by not giving a value
	@Test
	public void testBooleanAssignedTrueWithoutGivingAValueWithoutShortcut() {
		parser.add("val1", Parser.BOOLEAN);
		parser.parse("--val1");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to true by a value different from 0 and false
	public void testBooleanAssignedTrueGivenNonZeroValueWithoutShortcut() {
		parser.add("val1", Parser.BOOLEAN);
		parser.parse("--val1 100");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to true by giving 1
	public void testBooleanAssignedTrueGivenOneWithoutShortcut() {
		parser.add("val1", Parser.BOOLEAN);
		parser.parse("--val1 1");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to true by giving lowercase true
	public void testBooleanAssignedTrueGivenLowercaseTrueWithoutShortcut() {
		parser.add("val1", Parser.BOOLEAN);
		parser.parse("--val1 true");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to true by giving uppercase true
	public void testBooleanAssignedTrueGivenUppercaseTrueWithoutShortcut() {
		parser.add("val1", Parser.BOOLEAN);
		parser.parse("--val1 True");
		assertEquals(true, parser.getBoolean("val1"));
	}
	
	// Assigned to false by not parsing anything
	@Test
	public void testBooleanAssignedFalseWithoutShortcut() {
		parser.add("val2", Parser.BOOLEAN);
		assertEquals(false, parser.getBoolean("val2"));
	}
	
	// Assigned to false by giving 0
	@Test
	public void testBooleanAssignedGivenZeroWithoutShortcut() {
		parser.add("val2", Parser.BOOLEAN);
		parser.parse("--val2 0");
		assertEquals(false, parser.getBoolean("val2"));
	}

	// Assigned to false by giving lowercase false
	@Test
	public void testBooleanAssignedGivenLowerCaseFalseWithoutShortcut() {
		parser.add("val2", Parser.BOOLEAN);
		parser.parse("--val2 false");
		assertEquals(false, parser.getBoolean("val2"));
	}
	
	// Assigned to false by giving uppercase false
	@Test
	public void testBooleanAssignedGivenUpperCaseFalseWithoutShortcut() {
		parser.add("val2", Parser.BOOLEAN);
		parser.parse("--val2 False");
		assertEquals(false, parser.getBoolean("val2"));
	}	
	
	/*
	 * Task 1 Specifications 5
	 * Parse command line options
	 */
	
	/*
	 * Task 1 Specification 5.1
	 * Prefix -- is used for full names
	 */
	@Test
	public void testPrefixTwoDashes() {
		parser.add("input", Parser.STRING);
		parser.parse("--input abc");
		assertEquals("abc", parser.getString("input"));
	}
	
	/*
	 * Task 1 Specification 5.2
	 * Prefix - is used for shortcut
	 */
	@Test
	public void testPrefixOneDash() {
		parser.add("input", "i", Parser.STRING);
		parser.parse("-i xyz");
		assertEquals("xyz", parser.getString("i"));
	}
	
	/*
	 * Task 1 Specification 5.3
	 * Assign value to option with "="
	 * (Assigning value to option with " " has already been used so far without problem
	 */
	@Test
	public void testAssignValueWithEqualSign() {
		parser.add("val", Parser.BOOLEAN);
		parser.parse("--val=1");
		assertEquals(true, parser.getBoolean("val"));
	}
	
	/*
	 * Task 1 Specification 5.4
	 * Use quotation marks (double or single) around value
	 * (Assigning value to option without quotation marks have been giving no problem)
	 */
	@Test
	public void testQuotationMarks() {
		parser.add("val", Parser.BOOLEAN);
		parser.parse("--val \"1\"");
		boolean prevBool = parser.getBoolean("val");
		parser.parse("--val '1'");
		assertEquals(prevBool, parser.getBoolean("val"));
	}
	
	/*
	 * Task 1 Specification 5.5
	 * Quotation marks can be part of the value of the option
	 * if they are surrounded by another pair of quotation marks
	 */
	
	// Double quotation marks
	@Test
	public void testDoubleQuotationMarksAsPartOfString() {
		parser.add("option", Parser.STRING);
		parser.parse("--option 'value=\"abc\"'");
		assertEquals("value=\"abc\"", parser.getString("option"));
	}
	
	// Single quotation marks
	@Test
	public void testSingleQuotationMarksAsPartOfString() {
		parser.add("option", Parser.STRING);
		parser.parse("--option \"value=\'abc\'\"");
		assertEquals("value=\'abc\'", parser.getString("option"));
	}
	
	/*
	 * Task 1 Specification 5.6
	 * If assign multiple times, value of option is from last assignment
	 */
	
	// For char
	@Test
	public void testMultipleAssignmentsInteger() {
		parser.add("val", Parser.INTEGER);
		parser.parse("--val 1");
		parser.parse("--val 2");
		parser.parse("--val 3");
		assertEquals(3, parser.getInteger("val"));
	}
	
	// For boolean
	@Test
	public void testMultipleAssignmentsBoolean() {
		parser.add("val", Parser.BOOLEAN);
		parser.parse("--val 1");
		parser.parse("--val 0");
		parser.parse("--val 1");
		assertEquals(true, parser.getBoolean("val"));
	}
	
	// For string
	@Test
	public void testMultipleAssignmentsString() {
		parser.add("val", Parser.STRING);
		parser.parse("--val abc");
		parser.parse("--val xyz");
		parser.parse("--val mno");
		assertEquals("mno", parser.getString("val"));
	}
	
	// For char
	@Test
	public void testMultipleAssignmentsChar() {
		parser.add("val", Parser.CHAR);
		parser.parse("--val a");
		parser.parse("--val b");
		parser.parse("--val c");
		assertEquals('c', parser.getChar("val"));
	}
	
	/*
	 * Task Specification 5.7
	 * Do not need to provide values for every option
	 * Without values given, options get default values
	 * 0 for INTEGER, false for BOOLEAN, "" for STRING, and '\0' for CHAR
	 */
	
	// Integer not given
	@Test 
	public void testValueNotGivenInteger() {
		parser.add("int", Parser.INTEGER);
		assertEquals(0, parser.getInteger("int"));
	}
	
	// Boolean not given
	@Test 
	public void testValueNotGivenBool() {
		parser.add("bool", Parser.BOOLEAN);
		assertEquals(false, parser.getBoolean("bool"));
	}
	
	// String not given
	@Test 
	public void testValueNotGivenString() {
		parser.add("string", Parser.STRING);
		assertEquals("", parser.getString("string"));
	}
	
	// Character not given
	@Test 
	public void testValueNotGivenChar() {
		parser.add("char", Parser.CHAR);
		assertEquals('\0', parser.getChar("char"));
	}
	
	/*
	 * Task 1 Specification 5.8
	 * parse() called multiple times
	 */
	@Test
	public void testMultipleCalls() {
		parser.add("input", Parser.INTEGER);
		parser.add("output", Parser.INTEGER);
		parser.add("Output", "O", Parser.INTEGER);
		parser.parse("--input 1 --output 2");
		parser.parse("-O");
		int[] expected = {1, 2, 0};
		int[] actual = {parser.getInteger("input"), parser.getInteger("output"), parser.getInteger("Output")};
		assertArrayEquals(expected, actual);
	}
	
	/*
	 * Task 1 Specifications 6
	 * Retrieve information
	 */
	
	/*
	 * Task 1 Specification 6.1
	 * Search order, name first, shortcut later
	 */
	@Test
	public void testSearchOrder() {
		parser.add("o", Parser.STRING);
		parser.add("output", "o", Parser.STRING);
		parser.parse("--o abc");
		parser.parse("-o xyz");
		assertEquals("abc", parser.getString("o"));
	}
	
	/*
	 * Task 1 Specification 6.2
	 * Option is not defined or not provided a value, use default values
	 * (Case that value is not provided has already been tested in spec 5.7)
	 */
	
	// For integer
	@Test
	public void testNotDefinedInteger() {
		assertEquals(0, parser.getInteger("neverdefined"));
	}
	
	// For boolean
	@Test
	public void testNotDefinedBoolean() {
		assertEquals(false, parser.getBoolean("neverdefined"));
	}
	
	// For string
	@Test
	public void testNotDefinedString() {
		assertEquals("", parser.getString("neverdefined"));
	}
	
	// For char
	@Test
	public void testNotDefinedChar() {
		assertEquals('\0', parser.getChar("neverdefined"));
	}
	
	/*
	 * Additional tests to improve coverage
	 * for OptionMap
	 */
	
	/*
	 * Name can't be null
	 */
	@Test (expected = RuntimeException.class)
	public void testNameNotNull() {
		parser.add(null, Parser.INTEGER);
	}

	/*
	 * Name can't be empty
	 */
	@Test (expected = RuntimeException.class) 
	public void testNameNotEmpty() {
		parser.add("", Parser.INTEGER);
	}
	
	/*
	 * Shortcut can't be null
	 */
	@Test (expected = RuntimeException.class)
	public void testShortcutNotNull() {
		parser.add("option", null, Parser.INTEGER);
	}
	
	/*
	 * Type can't be smaller than 1 or bigger than 4
	 */
	@Test (expected = RuntimeException.class)
	public void testTypeLowerBound() {
		parser.add("option", 0);
	}
	
	@Test (expected = RuntimeException.class) 
	public void testTypeUpperBound() {
		parser.add("option", 5);
	}
	
	/*
	 * Test toString() method for OptionMap
	 */
	@Test 
	public void testOptionMapToString() {
		optionMap.store("option1", "o1", OptionMap.INTEGER);
		optionMap.setValueWithOptioShortcut("o1", "100");
		String result = "OptionMap [options=\n";
		result = result + "\t{name=option1" + ", shortcut=o1" + ", type=1" + ", value=100" + "}\n";
		result = result + "]";
		assertEquals(result, optionMap.toString());
	}
	
	/*
	 * Additional tests to improve coverage
	 * for Parser
	 */
	
	/*
	 * Test toString() method for Parser
	 */
	@Test
	public void testParserToString() {
		parser.add("option1", "o1", Parser.INTEGER);
		parser.parse("--option1 100");
		String result = "OptionMap [options=\n";
		result = result + "\t{name=option1" + ", shortcut=o1" + ", type=1" + ", value=100" + "}\n";
		result = result + "]";
		assertEquals(result, parser.toString());
	}
	
	/*
	 * Test getInteger() with option that has non-integer value type
	 */
	
	// Boolean type equal true
	@Test
	public void testGetIntegerWithBooleanOptionEqualTrue() {
		parser.add("option", Parser.BOOLEAN);
		parser.parse("--option true");
		assertEquals(1, parser.getInteger("option"));
	}
	
	// Boolean true equal false
	@Test
	public void testGetIntegerWithBooleanOptionEqualFalse() {
		parser.add("option", Parser.BOOLEAN);
		parser.parse("--option false");
		assertEquals(0, parser.getInteger("option"));
	}
	
	// String type with non-digit characters
	@Test
	public void testGetIntegerWithStringTypeWithNonDigit() {
		parser.add("option", Parser.STRING);
		parser.parse("--option 123a123");
		assertEquals(0, parser.getInteger("option"));
	}
	
	// String type with all digits
	@Test
	public void testGetIntegerWithStringTypeWithAllDigits() {
		parser.add("option", Parser.STRING);
		parser.parse("--option 123");
		assertEquals(123, parser.getInteger("option"));
	}
	
	// Char type
	@Test
	public void testGetIntegerWithCharType() {
		parser.add("option", Parser.CHAR);
		parser.parse("--option 0");
		assertEquals(48, parser.getInteger("option"));
	}
	
	/*
	 * Test parse() return values
	 */
	
	// Return value -1
	@Test
	public void testParseReturnMinusOne() {
		parser.add("option", Parser.INTEGER);
		int returnResult = parser.parse(null);
		assertEquals(-1, returnResult);
	}
	
	// Return value -2
	@Test
	public void testParseReturnMinusSecond() {
		parser.add("option", Parser.INTEGER);
		int returnResult = parser.parse("");
		assertEquals(-2, returnResult);
	}
	
	// Return value -3
	@Test
	public void testParseReturnMinusThree() {
		parser.add("option", Parser.INTEGER);
		// All spaces
		int returnResult = parser.parse("   ");
		assertEquals(-3, returnResult);
	}
	
	/*
	 * Trivial test cases to cover all the branches left in parse() in Parser
	 */
	@Test
	public void testParse1() {
		parser.add("val", "v", Parser.BOOLEAN);
		parser.parse("-v");
		assertEquals(true, parser.getBoolean("v"));
	}
	
	@Test
	public void testParse2() {
		parser.add("val", Parser.INTEGER);
		int returnResult = parser.parse("--val -");
		assertEquals(-3, returnResult);
	}
	
	@Test
	public void testParse3() {
		parser.add("val", Parser.BOOLEAN);
		parser.parse("--val ");
		assertEquals(true, parser.getBoolean("val"));
	}	

	@Test
	public void testParse4() {
		parser.add("val", Parser.BOOLEAN);
		parser.parse("--val - ");
		assertEquals(true, parser.getBoolean("val"));
	}
		
	@Test 
	public void testParse5() {
		parser.add("val1", "v1", Parser.STRING);
		parser.add("val2", "v2", Parser.STRING);
		parser.parse("-v1=-v2");	
		String[] expected = {"-v2", ""};
		String[] actual = {parser.getString("v1"), parser.getString("v2")};
		assertArrayEquals(expected, actual);
	}	
	
	@Test
	public void testParse6() {
		parser.add("_abc", Parser.STRING);
		parser.parse("--_abc");
		assertEquals("", parser.getString("_abc"));
	}
	
	/* 
	 * Trivial test cases to cover all the branches left in OptionMap
	 */
} 
