package Calculator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;;

public class Calculatorsub {

	public static Double rechne(double operand1, double operand2, char operation)
			throws MathematischerFehlerException, EingabefehlerException {
		switch (operation) {
		case '+':
			return addiere(operand1, operand2);
		case '-':
			return subtrahiere(operand1, operand2);
		case '/':
			return dividiere(operand1, operand2);
		case '*':
			return multipliziere(operand1, operand2);
		case '%':
			return bildeRest(operand1, operand2);
		case '^':
			return hochZahl(operand1, operand2);
		}
		throw new EingabefehlerException();
	}

	public static double addiere(double summand1, double summand2) {
		double ergebnis = summand1 + summand2;
		return ergebnis;
	}

	public static double subtrahiere(double minuent1, double minuent2) {
		double ergebnis = minuent1 - minuent2;
		return ergebnis;
	}

	public static double dividiere(double divident, double devisor) throws MathematischerFehlerException {
		double ergebnis = divident / devisor;
		if (ergebnis == Double.POSITIVE_INFINITY || Double.isNaN(ergebnis)) {
			throw new MathematischerFehlerException();
		}
		return ergebnis;

	}

	public static double multipliziere(double faktor1, double faktor2) {
		double ergebnis = faktor1 * faktor2;
		return ergebnis;
	}

	public static double bildeRest(double ausgangszahl, double devisor) {
		double ergebnis = ausgangszahl % devisor;
		return ergebnis;
	}

	public static double hochZahl(double basis, double exponent) {
		double ergebnis = Math.pow(basis, exponent);
		return ergebnis;
	}

	public Double readString(final String string)
			throws MathematischerFehlerException, EingabefehlerException, NotImplementedException {
		Stack<Double> zahlen = new Stack<>();
		Stack<Character> operationen = new Stack<>();
		List<Integer> nachKomma = new ArrayList<>();
		boolean klammer = false;
		boolean letztesZeichenZahl = false;
		boolean komma = false;
//		boolean letztesZeichenOperation = false;
		for (int j = 0; j < string.length(); j++) {
			char c = string.toCharArray()[j];
			try {
				double number = Integer.parseInt(c + "");
				if (!operationen.isEmpty() && operationen.peek() != '(' && !klammer && j + 1 < string.length()
						&& !Character.isDigit(string.charAt(j + 1)) && string.charAt(j + 1) != '.' && !komma) {
					zahlen.push(rechne(zahlen.pop(), number, operationen.pop()));
				} else {
					if (letztesZeichenZahl && !komma) {
						zahlen.push(zahlen.pop() * 10 + number);
					}

					else if (komma) {
						nachKomma.add((int) number);
						if (!(j + 1 < string.length()) || !Character.isDigit(string.charAt(j + 1))) {
							double stelle = 0;
							for (int l = 0; l < nachKomma.size(); l++) {
								stelle += nachKomma.get(l) / Math.pow(10, l + 1);
								new BigDecimal(stelle);
								BigDecimal.valueOf(stelle);
							}
							nachKomma.clear();
							zahlen.push(zahlen.pop() + stelle);
						}
					} else {
						zahlen.push((double) number);
					}
					letztesZeichenZahl = true;

				}
			} catch (NumberFormatException e) {
				letztesZeichenZahl = false;

				if (c == '+' || c == '-' || c == '/' || c == '*' || c == '%' || c == '^')
						 {
					operationen.push(c);
					klammer = false;
					komma = false;
//					letztesZeichenOperation = true;
//
//					} else if (c == '-') {
//					klammer = false;
//					komma = false;
//					if (letztesZeichenOperation) {
//						zahlen.push(string.charAt(j + 1) * (-1));
//					} else {
//						operationen.push(c);
//						letztesZeichenOperation = true;
//					}
				} else if (c == '.') {
					komma = true;
//					letztesZeichenOperation = false;
				} else if (c == '(') {
					operationen.push(c);
					klammer = true;
					komma = false;
//					letztesZeichenOperation = true;

				} else if (c == ')') {
					boolean klammerEntfernt = false;
					if (!operationen.isEmpty()) {
						if (operationen.peek() == '(') {
							operationen.pop();
							klammerEntfernt = true;
						}
						komma = false;
					} else {
						throw new EingabefehlerException();
					}

					char i;

					try {
						i = operationen.peek();

					} catch (EmptyStackException b) {
						continue;
					}

					if (i == '+' || i == '-' || i == '/' || i == '*' || i == '%') {
						do {
							double operand2 = zahlen.pop();
							double operand1 = zahlen.pop();
							zahlen.push(rechne(operand1, operand2, operationen.pop()));
						} while (zahlen.size() > 1 && !operationen.isEmpty() && operationen.peek() != '(');
						if (!operationen.isEmpty() && operationen.peek() == '(' && !klammerEntfernt) {
							operationen.pop();
						}
					}
				} else {
					throw new NotImplementedException();
				}

			}
		}
		if (operationen.isEmpty() && zahlen.size() == 1)

		{
			return zahlen.pop();
		} else if (zahlen.size() == 2 && !operationen.isEmpty()) {
			double operand2 = zahlen.pop();
			return rechne(zahlen.pop(), operand2, operationen.pop());
		} else {
			throw new EingabefehlerException();
		}
	}
}
