package model;

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
	
	public double process(String data) throws UnsupportException {
		throw new UnsupportException();
	}

}
