package shar1370_a05;

import javax.swing.JOptionPane;

public class PlotModel {

	private static String FUNCTION;
	private static String XMIN;
	private static String XMAX;
	private static double YMIN;
	private static double YMAX;
	private static double SCX;
	private static double SCY;
	private static double[][] polynomialCoordinates;
	private static double[][] firstDerivativeCoordinates;
	private static double[][] secondDerivativeCoordinates;
	final static int DATA_POINTS = 500;

	/**
	 * Returns a list of <code>Poly</code> objects representing the original
	 * <code>Poly</code>, the first derivative, and the second derivative.
	 */
	public static Poly[] getPolynomials() {
		final Poly[] polynomial = new Poly[3];
		try {
			// This has already been checked and exception handled
			polynomial[0] = Tools.convert(FUNCTION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		polynomial[1] = polynomial[0].diff();
		polynomial[2] = polynomial[1].diff();

		return polynomial;
	}

	/**
	 * Returns an array of x,y coordinates of each <code>Poly</code> evaluated
	 * at every point between the minimum and maximum values determined by user
	 * input.
	 */
	public static double[][] getCoords(Poly poly) {
		int n = DATA_POINTS;
		double[] yArray = new double[n + 1];
		double[] xArray = new double[n + 1];
		double d = (getMax() - getMin()) / n;
		double x = getMin();
		for (int i = 0; i <= n; i++) {
			xArray[i] = x;
			double y = poly.evalAt(x);
			yArray[i] = y;
			x += d;
		}

		double[][] result = new double[2][1];
		result[0] = xArray;
		result[1] = yArray;
		return result;
	}

	public static double getMin() {
		double min = 0;
		try {
			min = Integer.valueOf(XMIN);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Minimum value must be integer.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return min;
	}

	public static double getMax() {
		double max = 0;
		try {
			max = Integer.valueOf(XMAX);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Maximum value must be integer.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return max;
	}
	
	/**
	 * Implement getters and setters for each model attribute.
	 */
	public static void setFunction(String function) {
		FUNCTION = function;
	}
	
	public static void setXMin(String xMin) {
		XMIN = xMin;
	}
	
	public static void setXMax(String xMax) {
		XMAX = xMax;
	}
	
	public static void setYMin(double yMin2) {
		YMIN = yMin2;
	}
	
	public static void setYMax(double yMax2) {
		YMAX = yMax2;
	}
	
	public static void setSCx(double SCx) {
		SCX = SCx;
	}
	
	public static void setSCy(double SCy) {
		SCY = SCy;
	}
	
	public static void setPolynomialCoordinates(double[][] keyCoordinates) {
		polynomialCoordinates = keyCoordinates;
	}

	public static void setFirstDerivativeCoordinates(double[][] firstCoordinates) {
		firstDerivativeCoordinates = firstCoordinates;
	}

	public static void setSecondDerivativeCoordinates(double[][] secondCoordinates) {
		secondDerivativeCoordinates = secondCoordinates;
	}

	public static String getFunction() {
		return FUNCTION;
	}
	
	public static String getXMin() {
		return XMIN;
	}
	
	public static double getYMin() {
		return YMIN;
	}
	
	public static String getXMax() {
		return XMAX;
	}
	
	public static double getYMax() {
		return YMAX;
	}
	
	public static double getSCx() {
		return SCX;
	}
	
	public static double getSCy() {
		return SCY;
	}

	public static double[][] getKeyPolynomialCoordinates() {
		return polynomialCoordinates;
	}

	public static double[][] getFirstDerivativeCoordinates() {
		return firstDerivativeCoordinates;
	}

	public static double[][] getSecondDerivativeCoordinates() {
		return secondDerivativeCoordinates;
	}

}