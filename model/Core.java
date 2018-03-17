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
		int charFound = -1;
		charFound = this.operatorAfter(data, 0);
		/*charFound = data.indexOf('+');
		if (charFound == -1) {

			charFound = data.indexOf('-');
			if (charFound == -1) {
				charFound = data.indexOf('*');
				if (charFound == -1) {
					data.indexOf('/');
				}
			}
		}*/
		if (charFound == -1) {
			if(data.matches("([0-9]+)([.]*)([0-9]*)")) {
				return Double.parseDouble(data);
			}else {
				throw new UnsupportException();
			}
		} else {
			arguments[0] = data.substring(0, charFound).trim();
			char operator = data.charAt(charFound);
			arguments[1] = Character.toString(operator).trim();
			arguments[2] = data.substring(charFound + 1, data.length()).trim();
			Double a = Double.parseDouble(arguments[0]);
			Double b = Double.parseDouble(arguments[2]);
			return this.basicOperation(a, b, operator);
		}
	}

	public String power(String data) throws UnsupportException {
		int powerOperator = data.indexOf('^');
		int before = -1, after = -1;
		String base, power;
		if(powerOperator == -1) {
			return data;
		}
		while(data.charAt(powerOperator - 1) == ')') {
			before = data.lastIndexOf('(', powerOperator);
			after = data.indexOf(')', before);
			String tmp = data.substring(before, after + 1);
			String tmpResult = this.process(tmp);
			data = data.replace(tmp, tmpResult);
			powerOperator = data.indexOf('^');
		}
		
		while(data.charAt(powerOperator + 1) == '(') {
			after = data.indexOf(')', powerOperator);
			before = data.lastIndexOf('(', after);
			String tmp = data.substring(before, after + 1);
			String tmpResult = this.process(tmp);
			data = data.replace(tmp, tmpResult);
			powerOperator = data.indexOf('^');
		}
		before = this.operatorBefore(data, powerOperator);
		after = this.operatorAfter(data, powerOperator);
		if(before == -1) {
			base = data.substring(0, powerOperator);
		}else {
			base = data.substring(before + 1, powerOperator);
		}
		if(after == -1) {
			power = data.substring(powerOperator + 1, data.length());
		}else {
			power = data.substring(powerOperator + 1, after);
		}
		String tmpResult = Double.toString(Math.pow(Double.parseDouble(base), Double.parseDouble(power)));
		tmpResult = data.replace(base + "^" + power, tmpResult);
		return tmpResult;
	}
	public double multiSimpleBlock(String data) throws UnsupportException {
		String tmp;
		data = data.trim();
		double result;
		int begin = -1;
		int end = data.indexOf(')');
		if(data.matches("[0-9]+")) {
			return Double.parseDouble(data);
		}
		if (end != -1) {
			begin = data.lastIndexOf('(', end - 1);
		}
		if (data.matches("([0-9]+[.]?[0-9]*)([ ]*)(.{1})([ ]*)([0-9]+[.]?[0-9]*)")) {
			return this.simpleBlock(data);
		} else if (begin != -1) {
			tmp = data.substring(begin + 1, end);
			result = this.multiSimpleBlock(tmp);
			data = data.replace("(" + tmp + ")", Double.toString(result));
			return this.multiSimpleBlock(data);
		} else {
			int multiplication = data.indexOf('*');
			int division = data.indexOf('/');
			int addition = data.indexOf('+');
			int subtraction = data.indexOf('-');
			int firstOperator = -1;
			int before = -1, after = -1;

			if (multiplication != -1) {
				if (division != -1) {
					firstOperator = Math.min(multiplication, division);
				} else {
					firstOperator = multiplication;
				}
			} else if (division != -1) {
				firstOperator = division;
			} else {
				firstOperator = Math.min(addition, subtraction);
			}

			before = this.operatorBefore(data, firstOperator - 1);
			after = this.operatorAfter(data, firstOperator + 1);
			if (before == -1) {
				tmp = data.substring(0, after);
			} else if (after == -1) {
				tmp = data.substring(before + 1, data.length());
			} else {
				tmp = data.substring(before + 1, after);
			}
			result = this.multiSimpleBlock(tmp);
			data = data.replace(tmp, Double.toString(result));
			return this.multiSimpleBlock(data);
			/*
			 * if(multiplication != -1) { if(division == -1 || multiplication < division) {
			 * if(addition == -1 || multiplication < addition) { if(subtraction == -1 ||
			 * multiplication < subtraction) { int nextOp } } } }
			 */
		}
	}

	public String process(String data)throws UnsupportException {
		try {
			if(data.matches("([0-9]+)([.]*)([0-9]*)")) {
				return data;
			}else if(data.indexOf('^') != -1) {
				data = this.power(data);
			}else {
				data = Double.toString(this.multiSimpleBlock(data));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return this.process(data);
	}


	private int operatorAfter(String data, int pos) {
		int multiplicationAfter = data.indexOf('*', pos);
		int divisionAfter = data.indexOf('/', pos);
		int additionAfter = data.indexOf('+', pos);
		int subtractionAfter = data.indexOf('-', pos);
		int operatorsAfter[] = {multiplicationAfter, divisionAfter, additionAfter, subtractionAfter};
		Arrays.sort(operatorsAfter);

		for(int i = 0; i < 4; i ++) {
			if(operatorsAfter[i] == -1) {
				continue;
			}else {
				return operatorsAfter[i];
			}
		}
		return -1;
		}
	private int operatorBefore(String data, int pos) {
			int multiplicationBefore = data.lastIndexOf('*', pos);
			int divisionBefore = data.lastIndexOf('/', pos);
			int additionBefore = data.lastIndexOf('+', pos);
			int subtractionBefore = data.lastIndexOf('-', pos);
			int operatorsBefore[] = { multiplicationBefore, divisionBefore, additionBefore, subtractionBefore };
			Arrays.sort(operatorsBefore);
			return operatorsBefore[3];
	}
}