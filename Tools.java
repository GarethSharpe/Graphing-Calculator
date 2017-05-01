package shar1370_a05;

public class Tools {

	public static int P(String operator) {
		switch (operator) {
		case "(":
			return 0;
		case "+":
		case "-":
			return 1;
		case "*":
			return 2;
		case "^":
			return 3;
		default:
			return -1;
		}
	}

	public static Poly convert(String str) throws Exception {
		Poly newPoly;
		Tokenizer strTok = new Tokenizer(str);
		final PStack operand = new PStack();
		Stack operator = new Stack();
		Token token;
		String ts;
		while (strTok.hasNext()) {
			token = strTok.next();
			ts = token.toString();
			if (token.isOperand()) {
				Poly p = new Poly("0 " + ts);
				operand.push(p);
			} else if (token.isVariable()) {
				if (ts.equals("x")) {
					Poly p = new Poly("1 1 0");
					operand.push(p);
				} else
					throw new Exception("Wrong variable definition.");
			}
			// Handles all ( and pushes it onto stack
			else if (ts.equals("(")) {
				operator.push(ts);
			} else if (ts.equals(")")) {
				while (!operator.isEmpty() && !operator.peek().equals("(")) {
					onestep(operator, operand);
				}
				if (!operator.isEmpty())
					operator.pop();
				else
					throw new Exception("Missing ( ");
			} else if (ts.equals("-") || ts.equals("+") || ts.equals("*") || ts.equals("^")) {
				while (!operator.isEmpty() && P(ts) <= P(operator.peek())) {
					onestep(operator, operand);
				}
				operator.push(ts);
			}
		}

		while (!operator.isEmpty())
			onestep(operator, operand);

		newPoly = operand.peek();
		operand.pop();
		if (!operand.isEmpty())
			throw new Exception("Missing operator.");
		return newPoly;
	}

	/**
	 * @param operator
	 * @param operand
	 */
	public static void onestep(Stack operator, PStack operand) throws Exception {

		Poly p, q, result = null;
		String opr;
		if (operand.isEmpty())
			throw new Exception("Missing operand.");
		p = operand.pop();
		if (operand.isEmpty())
			throw new Exception("Missing operand.");
		q = operand.pop();
		if (operator.isEmpty())
			throw new Exception("Missing operator.");
		opr = operator.pop();

		if (opr.equals("+"))
			result = p.add(q);
		if (opr.equals("*"))
			result = p.mult(q);
		if (opr.equals("-"))
			result = q.sub(p);
		if (opr.equals("^")) {
			String temp = (p.toString());
			try {
				int i = Integer.parseInt(temp);
				result = q.pow(i);
			} catch (Exception e) {
				throw new Exception("Non-integer after ^");
			}
		}

		operand.push(result);

	}

}