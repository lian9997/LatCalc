package model;

import java.util.Arrays;

public class Core {
	
	public double basicOperation(double a, double b, char operator) throws UnsupportException {
		switch (operator) {
			case '+':
				return a + b;
			case '-':
				return a - b;
			case '*':
				return a * b;
			case '/':
				return a / b;
			default:
				throw new UnsupportException();
		}
	}
	
	public double simpleBlock(String data) throws UnsupportException {
		String arguments[] = new String[3];
		data = data.trim();
		int charFound = 0;
		charFound = data.indexOf('+');
		if(charFound == -1) {
			charFound = data.indexOf('-');
			if(charFound == -1) {
				charFound = data.indexOf('*');
				if(charFound == -1) {
					data.indexOf('/');
				}
			}
		}
		if(charFound == -1) {
			throw new UnsupportException();
		}else {
			arguments[0] = data.substring(0, charFound).trim();
			char operator = data.charAt(charFound);
			arguments[1] = Character.toString(operator).trim();
			arguments[2] = data.substring(charFound + 1, data.length()).trim();
			Double a = Double.parseDouble(arguments[0]);
			Double b = Double.parseDouble(arguments[2]);
			return this.basicOperation(a, b, operator);
		}
	}
	

	public double multiSimpleBlock(String data) throws UnsupportException {
		String tmp;
		data = data.trim();
		double result;
		int begin = data.indexOf('(');
		int end = -1;
		if(begin != -1) {
			end = data.indexOf(')');
		}
		if(data.matches("([0-9]+[.]?[0-9]*)([ ]*)(.{1})([ ]*)([0-9]+[.]?[0-9]*)")) {
			return this.simpleBlock(data);
		}else if(begin != -1) {
			tmp = data.substring(begin + 1, end);
			result = this.multiSimpleBlock(tmp);
			data = data.replace("(" + tmp + ")", Double.toString(result));
			return this.multiSimpleBlock(data);
		}else {
			int multiplication = data.indexOf('*');
			int division = data.indexOf('/');
			int addition = data.indexOf('+');
			int subtraction = data.indexOf('-');
			int firstOperator = -1;
			int before = -1, after = -1;

			if(multiplication != -1) {
				if(division != -1) {
					firstOperator = Math.min(multiplication, division);
				}else {
					firstOperator = multiplication;
				}
			}else if(division != -1) {
				firstOperator = division;
			}else {
				firstOperator = Math.min(addition, subtraction);
			}

			int multiplicationBefore = data.lastIndexOf('*', firstOperator - 1);
			int divisionBefore = data.lastIndexOf('/', firstOperator - 1);
			int additionBefore = data.lastIndexOf('+', firstOperator - 1);
			int subtractionBefore = data.lastIndexOf('-', firstOperator - 1);
			int operatorsBefore[] = {multiplicationBefore, divisionBefore, additionBefore, subtractionBefore};
			Arrays.sort(operatorsBefore);

			for(int i = 0; i < 4; i ++) {
				if(operatorsBefore[i] == -1) {
					continue;
				}else {
					before = operatorsBefore[i];
					break;
				}
			}


			int multiplicationAfter = data.indexOf('*', firstOperator + 1);
			int divisionAfter = data.indexOf('/', firstOperator + 1);
			int additionAfter = data.indexOf('+', firstOperator + 1);
			int subtractionAfter = data.indexOf('-', firstOperator + 1);
			int operatorsAfter[] = {multiplicationAfter, divisionAfter, additionAfter, subtractionAfter};
			Arrays.sort(operatorsAfter);

			for(int i = 0; i < 4; i ++) {
				if(operatorsAfter[i] == -1) {
					continue;
				}else {
					after = operatorsAfter[i];
					break;
				}
			}
			if(before == -1) {
				tmp = data.substring(0, after);
			}else if(after == -1) {
				tmp = data.substring(before + 1, data.length());
			}else {
				tmp = data.substring(before + 1, after);
			}
			result = this.multiSimpleBlock(tmp);
			data = data.replace(tmp, Double.toString(result));
			return this.multiSimpleBlock(data);
			/*if(multiplication != -1) {
				if(division == -1 || multiplication < division) {
					if(addition == -1 || multiplication < addition) {
						if(subtraction == -1 || multiplication < subtraction) {
							int nextOp
						}
					}
				}
			}*/
		}
	}
	public double process(String data) throws UnsupportException {
		throw new UnsupportException();
	}

}
