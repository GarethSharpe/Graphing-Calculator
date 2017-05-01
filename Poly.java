package shar1370_a05;

import java.util.StringTokenizer;

/**
 * Defines the <code>Poly</code> class for polynomials.
 * 
 * @author Dr. Eugene Zima
 * @author David Brown
 * @version 2017-02-15
 */
public class Poly {
	// The degree of the polynomial.
	private int deg;
	// An array of coefficients.
	private final int[] coef;

	/**
	 * Creates a <code>Poly</code> based upon a given degree.
	 *
	 * @param n
	 *            The degree. Array of integer coefficients is initialized to
	 *            0s. See
	 *            <a href="http://bohr.wlu.ca/cp213/asgns/A2.pdf">Assignment
	 *            2</a>
	 */
	public Poly(final int n) {
		this.coef = new int[n + 1];
	}

	/**
	 * Creates a <code>Poly</code> based upon a given array of coefficients.
	 *
	 * @param x
	 *            The array of integer coefficients. See
	 *            <a href="http://bohr.wlu.ca/cp213/asgns/A2.pdf">Assignment
	 *            2</a>
	 */

	public Poly(final int[] x) {
		int i;
		this.coef = new int[x.length];
		for (i = 0; i < x.length; i++) {
			this.coef[i] = x[i];
		}
		this.deg = x.length - 1;
	}

	/**
	 * Creates a <code>Poly</code> based upon a string representation of a
	 * polynomial.
	 *
	 * @param line
	 *            The string representation of a polynomial. See
	 *            <a href="http://bohr.wlu.ca/cp213/asgns/A2.pdf">Assignment
	 *            2</a>
	 */
	public Poly(final String line) {
		final StringTokenizer ST = new StringTokenizer(line, " ");
		int i, j;

		String s;
		s = ST.nextToken();
		this.deg = Integer.parseInt(s);
		this.coef = new int[this.deg + 1];
		for (i = this.deg; i >= 0; i--) {
			s = ST.nextToken();
			j = Integer.parseInt(s);
			this.coef[i] = j;
		}
		while (this.deg > 0 && this.coef[this.deg] == 0) {
			this.deg--;
		}
	}

	/**
	 * Adds two polynomials and returns a new <code>Poly</code> representing the
	 * result of this addition.
	 *
	 * @param x
	 *            The <code>Poly</code> to add to this <code>Poly</code>.
	 * @return The new <code>Poly</code> that results from adding
	 *         <code>that</code> <code>Poly</code> to this <code>Poly</code>.
	 */
	public Poly add(final Poly x) {
		int i;
		final int d = Math.max(this.deg, x.deg);
		final int md = Math.min(this.deg, x.deg);
		final Poly res = new Poly(d);
		for (i = 0; i <= md; i++) {
			res.coef[i] = this.coef[i] + x.coef[i];
		}
		if (this.deg > x.deg) {
			for (i = md + 1; i <= d; i++) {
				res.coef[i] = this.coef[i];
			}
		} else {
			for (i = md + 1; i <= d; i++) {
				res.coef[i] = x.coef[i];
			}
		}
		i = d;
		while (i > 0 && res.coef[i] == 0) {
			i--;
		}
		res.deg = i;

		return res;
	}

	/**
	 * Returns the degree of the polynomial.
	 *
	 * @return the degree of the polynomial.
	 */
	public int degree() {
		return this.deg;
	}

	public Poly diff() {
		Poly res = null;

		if (this.deg == 0) {
			res = new Poly("0 0");
		} else {
			final int d = this.deg - 1;
			res = new Poly(d);

			for (int i = 0; i <= d; i++) {
				res.coef[i] = this.coef[i + 1] * (i + 1);
			}
			res.deg = d;
		}
		return res;
	}

	/**
	 * Returns whether this <code>Poly</code> object and another
	 * <code>Poly</code> object are equal.
	 *
	 * @param p
	 *            The <code>Poly</code> to compare this <code>Poly</code> to.
	 * @return <code>true</code> if this <code>Poly</code> and <code>p</code>
	 *         <code>Poly</code> are equal, <code>false</code> otherwise.
	 */
	public boolean equals(final Poly p) {
		if (this.deg != p.deg) {
			return false;
		}
		for (int i = this.deg; i >= 0; i--) {
			if (this.coef[i] != p.coef[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Evaluates a polynomial given an <i>x</i> value and returns the result of
	 * that evaluation.
	 *
	 * @param x
	 *            The value to substitute for <i>x</i> in the polynomial.
	 * @return The result of substituting the value <code>x</code> for <i>x</i>
	 *         in the polynomial.
	 */
	public int evalAt(final int x) {
		int v = 0;
		int i;

		for (i = this.deg; i >= 0; i--) {
			v = v * x + this.coef[i];
		}

		return v;
	}

	public double evalAt(final double x) {
		double v = 0;
		int i;

		for (i = this.deg; i >= 0; i--) {
			v = v * x + this.coef[i];
		}
		return v;
	}

	public Poly pow(int n) {
		Poly tmp = this;

		for (int i = 1; i < n; i++)
			tmp = tmp.mult(this);

		return tmp;

	}

	/**
	 * Prints all of the integer roots of the polynomial.
	 */
	public void iRoots() {
		int i;
		boolean flag = true;
		if (this.deg == 0) {
			if (this.coef[0] == 0) {
				System.out.println("Infinitely many roots...");
			} else {
				System.out.println("No integer roots!");
			}
			return;
		}
		i = 0;
		while (this.coef[i] == 0) {
			i++;
		}
		if (i != 0) {
			System.out.println(0);
		}
		for (int j = 1; j <= Math.abs(this.coef[i]); j++) {
			if (this.coef[i] % j == 0 && this.evalAt(-j) == 0) {
				flag = false;
				System.out.println(-j);
			}
			if (this.coef[i] % j == 0 && this.evalAt(j) == 0) {
				flag = false;
				System.out.println(j);
			}
		}
		if (flag) {
			System.out.println("No integer roots!");
		}
	}

	/**
	 * Multiplies two polynomials and returns a new <code>Poly</code>
	 * representing the result of this addition.
	 *
	 * @param x
	 *            The <code>Poly</code> to multiply by this <code>Poly</code>.
	 * @return The new <code>Poly</code> that results from adding
	 *         <code>that</code> <code>Poly</code> to this <code>Poly</code>.
	 */
	public Poly mult(final Poly x) {
		int i, j;
		final int d = this.deg + x.deg;
		final Poly res = new Poly(d);
		for (i = 0; i <= this.deg; i++) {
			for (j = 0; j <= x.deg; j++) {
				res.coef[i + j] = res.coef[i + j] + this.coef[i] * x.coef[j];
			}
		}
		i = d;
		while (i > 0 && res.coef[i] == 0) {
			i--;
		}
		res.deg = i;
		return res;
	}

	/**
	 * Subtracts two polynomials and returns a new <code>Poly</code>
	 * representing the result of this addition.
	 *
	 * @param x
	 *            The <code>Poly</code> to subtract from this <code>Poly</code>.
	 * @return The new <code>Poly</code> that results from adding
	 *         <code>that</code> <code>Poly</code> to this <code>Poly</code>.
	 */
	public Poly sub(final Poly x) {
		int i;
		final int d = Math.max(this.deg, x.deg);
		final int md = Math.min(this.deg, x.deg);
		final Poly res = new Poly(d);
		for (i = 0; i <= md; i++) {
			res.coef[i] = this.coef[i] - x.coef[i];
		}
		if (this.deg > x.deg) {
			for (i = md + 1; i <= d; i++) {
				res.coef[i] = this.coef[i];
			}
		} else {
			for (i = md + 1; i <= d; i++) {
				res.coef[i] = -x.coef[i];
			}
		}
		i = d;
		while (i > 0 && res.coef[i] == 0) {
			i--;
		}
		res.deg = i;

		return res;
	}

	/**
	 * Returns a string representation of this <code>Poly</code>.
	 *
	 * @return The string version of this <code>Poly</code>.
	 */
	@Override
	public String toString() {
		int i;
		String s = "";
		String xbase = "x^";
		String xdeg;
		if (this.deg == 0 && this.coef[0] == 0) {
			return "0";
		}
		for (i = this.deg; i > 0; i--) {
			xdeg = "" + i;
			if (i == 1) {
				xbase = "x";
				xdeg = "";
			}
			if (this.coef[i] == 1) {
				s = s + "+" + xbase + xdeg;
			} else if (this.coef[i] == -1) {
				s = s + "-" + xbase + xdeg;
			} else if (this.coef[i] > 0) {
				s = s + "+" + this.coef[i] + xbase + xdeg;
			} else if (this.coef[i] < 0) {
				s = s + this.coef[i] + xbase + xdeg;
			}
		}
		if (this.coef[0] > 0) {
			s = s + "+" + this.coef[0];
		} else if (this.coef[0] < 0) {
			s = s + this.coef[0];
		}
		if (s.startsWith("+")) {
			s = s.substring(1);
		}
		return s;
	}

}
