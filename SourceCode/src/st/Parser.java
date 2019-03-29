package st;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Parser {
	public static final int INTEGER = 1;
	public static final int BOOLEAN = 2;
	public static final int STRING = 3;
	public static final int CHAR = 4;
	
	private OptionMap optionMap;
	
	public Parser() {
		optionMap = new OptionMap();
	}
	
	public void add(String option_name, String shortcut, int value_type) {
		optionMap.store(option_name, shortcut, value_type);
	}
	
	public void add(String option_name, int value_type) {
		optionMap.store(option_name, "", value_type);
	}

	public int getInteger(String option) {
		String value = getString(option);
		int type = getType(option);
		int result;
		switch (type) {
		case INTEGER:
			try {
				result = Integer.parseInt(value);
			} catch (Exception e) {
		        try {
		            new BigInteger(value);
		        } catch (Exception e1) {
		            result = 0;
		        }
		        result = 0;
		    }
			break;
		case BOOLEAN:
			if (getBoolean(option) == false) {
				result = 0;
			} else {
				result = 1;
			}
			break;
		case STRING:
		    int power = 1;
		    result = 0;
		    char c;
		    for (int i = value.length() - 1; i >= 0; --i){
		        c = value.charAt(i);
		        if (!Character.isDigit(c)) return 0;
		        result = result + power * (c - '0');
		        power *= 10;
		    }
		    break;
		case CHAR:
			result = (int)getChar(option);
			break;
		default:
			result = 0;
		}
		return result;
	}
	
	public boolean getBoolean(String option) {
		String value = getString(option);
		boolean result;
		if (value.toLowerCase().equals("false") || value.equals("0") || value.equals("")) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
	
	public String getString(String option) {
		String result = optionMap.getValue(option);
		return result;
	}
	
	public char getChar(String option) {
		String value = getString(option);
		char result;
		if (value.equals("")) {
			result = '\0';
		} else {
			result = value.charAt(0);
		}
		return result;
	}
	
	public int parse(String command_line_options) {
		if (command_line_options == null) {
			return -1;
		}
		int length = command_line_options.length();
		if (length == 0) {
			return -2;
		}
		
		int char_index = 0;
		while (char_index < length) {
			char current_char = command_line_options.charAt(char_index);

			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (current_char != ' ') {
					break;
				}
				char_index++;
			}
			
			boolean isShortcut = true;
			String option_name = "";
			String option_value = "";
			if (current_char == '-') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
				if (current_char == '-') {
					isShortcut = false;
					char_index++;
				}
			} else {
				return -3;
			}
			current_char = command_line_options.charAt(char_index);
			
			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (Character.isLetterOrDigit(current_char) || current_char == '_') {
					option_name += current_char;
					char_index++;
				} else {
					break;
				}
			}
			
			boolean hasSpace = false;
			if (current_char == ' ') {
				hasSpace = true;
				while (char_index < length) {				
					current_char = command_line_options.charAt(char_index);
					if (current_char != ' ') {
						break;
					}
					char_index++;
				}
			}

			if (current_char == '=') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
			}
			if ((current_char == '-'  && hasSpace==true ) || char_index == length) {
				if (getType(option_name) == BOOLEAN) {
					option_value = "true";
					if (isShortcut) {
						optionMap.setValueWithOptioShortcut(option_name, option_value);
					} else {
						optionMap.setValueWithOptionName(option_name, option_value);
					}
				} else {
					return -3;
				}
				continue;
			} else {
				char end_symbol = ' ';
				current_char = command_line_options.charAt(char_index);
				if (current_char == '\'' || current_char == '\"') {
					end_symbol = current_char;
					char_index++;
				}
				while (char_index < length) {
					current_char = command_line_options.charAt(char_index);
					if (current_char != end_symbol) {
						option_value = option_value + current_char;
						char_index++;
					} else {
						break;
					}
				}
			}
			
			if (isShortcut) {
				optionMap.setValueWithOptioShortcut(option_name, option_value);
			} else {
				optionMap.setValueWithOptionName(option_name, option_value);
			}
			char_index++;
		}
		return 0;
	}
	
	private int getType(String option) {
		int type = optionMap.getType(option);
		return type;
	}
	
	@Override
	public String toString() {
		return optionMap.toString();
	}

	/*
	 * Implementation of getIntegerList for Task 3
	 */
	public List<Integer> getIntegerList(String option) {
		// Return empty list if option is of type Integer, Boolean, or Char
		if (getType(option) != STRING) return Arrays.asList();	
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		String value = getString(option);
		int charIndex = 0;
		int length = value.length();
		boolean hasHyphen = false;
		boolean potentialRange = false;
		int oneEnd = 0;
		boolean hasSeparator = false;
		boolean firstNum = false;
		
		while(charIndex < length) {
			// Skip the separator(s)
			while (!Character.isDigit(value.charAt(charIndex)) && value.charAt(charIndex) != '-') {
				hasSeparator = true;
				charIndex++;				
				// If there is a potential range, but there is a bunch of separators in between
				// that means there is no range
				if (potentialRange) potentialRange = false;
				if (charIndex == length) break;
			}
			
			// If already hit the end of the string
			if (charIndex == length) return list;
			
			// If hit a hyphen
			if (value.charAt(charIndex) == '-') {
				hasHyphen = true;
				charIndex++;
				if (charIndex == length) return Arrays.asList();
			} 
			else if (Character.isDigit(value.charAt(charIndex))) { // If hit a number
				String numString = Character.toString(value.charAt(charIndex));
				charIndex++;
				while (charIndex < length) {
					// If the next char is also a number
					// meaning the individual number has more than one digit
					if (Character.isDigit(value.charAt(charIndex))) {
						numString = numString + Character.toString(value.charAt(charIndex));
						charIndex++;						
					} 
					else break;
				}
				if (list.isEmpty()) firstNum = true;
				else firstNum = false;
				// Convert the string to a number and add it to the list 
				list.add(Integer.valueOf(numString));
				// It might be one end of a range
				potentialRange = true;
				oneEnd = Integer.valueOf(numString);	
				// If hit the end of the string
				if (charIndex == length) break;
			} 
			else break;
			
			// If actually hit a hyphen 
			// If the char immediately after the hyphen is a number
			if (hasHyphen && Character.isDigit(value.charAt(charIndex))) {
				hasHyphen = false;
				// If there is a range
				if (potentialRange) {
					String numString = Character.toString(value.charAt(charIndex));
					charIndex++;
					while (charIndex < length) {
						// If the next char is also a number
						// meaning the individual number has more than one digit
						if (Character.isDigit(value.charAt(charIndex))) {
							numString = numString + Character.toString(value.charAt(charIndex));
							charIndex++;
						} 
						else break;
					}
					if (list.isEmpty()) firstNum = true;
					else firstNum = false;
					// Convert the string to a number  
					int otherEnd = Integer.valueOf(numString); 
					// If two ends are equal, invalid
					if (otherEnd == oneEnd) return Arrays.asList();
					int lowerBound;
					int upperBound;
					boolean lowerBoundAlreadyAdded = false; // false means upperbound number has been added
					if (oneEnd < otherEnd) {
						lowerBound = oneEnd;
						lowerBoundAlreadyAdded = true;
						upperBound = otherEnd;
					} 
					else {
						lowerBound = otherEnd;
						upperBound = oneEnd;
					}
					// Add all the numbers in range to list
					// Except for one bound that has already been added
					for (int i = lowerBound; i <= upperBound; i++) {
						if (lowerBoundAlreadyAdded && i == lowerBound) continue;
						if (!lowerBoundAlreadyAdded && i == upperBound) continue;
						else list.add(i);
					}
					potentialRange = false;
				}
				else {
					// If there isn't a range
					// meaning the hyphen signifies negative value
					String numString = Character.toString(value.charAt(charIndex));
					charIndex++;
					while (charIndex < length) {
						// If the next char is also a number
						// meaning the individual number has more than one digit
						if (Character.isDigit(value.charAt(charIndex))) {
							numString = numString + Character.toString(value.charAt(charIndex));
							charIndex++;
						} 
						else break;
					}
					if (list.isEmpty()) firstNum = true;
					else firstNum = false;
					// Convert the string to a number and add it to the list 
					list.add(-Integer.valueOf(numString));
					// It might be one end of a range
					potentialRange = true;
					oneEnd = -Integer.valueOf(numString); 
					if (!hasSeparator && charIndex == length) return Arrays.asList();
					if (!hasSeparator && !firstNum) return Arrays.asList();
					hasSeparator = false;
				}
				// If hit the end of the string
				if (charIndex == length) break;
			}
			// If the char immediately after hyphen is another hyphen
			// meaning the second hyphen has to signify negative value
			else if (hasHyphen && value.charAt(charIndex) == '-') {
				hasHyphen = false;
				charIndex++;
				// If the char immediately after two hyphens isn't a number, invalid
				if (!Character.isDigit(value.charAt(charIndex))) return Arrays.asList();
				else { // If it's a number
					// If there is a range
					if (potentialRange) {
						String numString = Character.toString(value.charAt(charIndex));
						charIndex++;
						while (charIndex < length) {
							// If the next char is also a number
							// meaning the individual number has more than one digit
							if (Character.isDigit(value.charAt(charIndex))) {
								numString = numString + Character.toString(value.charAt(charIndex));
								charIndex++;
							}
							else break;
						}
						if (list.isEmpty()) firstNum = true;
						else firstNum = false;
						// Convert the string to a number  
						int otherEnd = -Integer.valueOf(numString); 
						// If two ends are equal, invalid
						if (otherEnd == oneEnd) return Arrays.asList();
						int lowerBound;
						int upperBound;
						boolean lowerBoundAlreadyAdded = false; // false means upperbound number has been added
						if (oneEnd < otherEnd) {
							lowerBound = oneEnd;
							lowerBoundAlreadyAdded = true;
							upperBound = otherEnd;
						} 
						else {
							lowerBound = otherEnd;
							upperBound = oneEnd;
						}
						
						// Add all the numbers in range to list
						// Except for one bound that has already been added
						for (int i = lowerBound; i <= upperBound; i++) {
							if (lowerBoundAlreadyAdded && i == lowerBound) continue;
							if (!lowerBoundAlreadyAdded && i == upperBound) continue;
							else list.add(i);
						}
						potentialRange = false;
					}
					else {
						// If there isn't a range
						// meaning the hyphen signifies negative value
						String numString = Character.toString(value.charAt(charIndex));
						charIndex++;
						while (charIndex < length) {
							// If the next char is also a number
							// meaning the individual number has more than one digit
							if (Character.isDigit(value.charAt(charIndex))) {
								numString = numString + Character.toString(value.charAt(charIndex));
								charIndex++;
							}
							else break;
						}
						if (charIndex == length) return Arrays.asList();
						else { 
							if (list.isEmpty()) firstNum = true;
							else firstNum = false;
							// Convert the string to a number and add it to the list 
							list.add(-Integer.valueOf(numString));
							// It might be one end of a range
							potentialRange = true;
							oneEnd = -Integer.valueOf(numString); 
						}
					}
					// If hit the end of the string
					if (charIndex == length) break;
				}
			}
			else if (hasHyphen){ // IF after hyphen is anything other than a hyphen or a number, invalid
				return Arrays.asList();				
			}
		}
		Collections.sort(list);
		return list;
 	}
}

