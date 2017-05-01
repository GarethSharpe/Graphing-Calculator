package shar1370_a05;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PlotView extends JPanel implements Runnable {

	// Initialize plot model
	PlotModel model;
	
	// Set canvas attributes
	final double WIDTH = InputView.WIDTH;
	final double HEIGHT = InputView.HEIGHT;
	final double xMax = PlotModel.getMax();
	final double xMin = PlotModel.getMin();
	final int n = PlotModel.DATA_POINTS;

	// Get the polynomials representing the original Poly as well as
	// the first and second derivatives.
	Poly[] polynomial = PlotModel.getPolynomials();

	// Get the coordinates of each Poly
	double[][] polynomialCoordinates = PlotModel.getCoords(polynomial[0]);
	double[][] firstDerivitiveCoordinates = PlotModel.getCoords(polynomial[1]);
	double[][] secondDerivitiveCoordinates = PlotModel.getCoords(polynomial[2]);

	// Initialize yMin,yMax
	double yMin = polynomialCoordinates[1][0];
	double yMax = polynomialCoordinates[1][0];

	public PlotView(PlotModel model) {
		this.model = model;
		this.layoutView();
	}

	// -------------------------------------------------------------------------------
	/**
	 * Lays out the panel components.
	 */
	private void layoutView() {

		// Create new canvas to plot on
		final Canvas canvas = new Canvas();
		// Add canvas to plot model
		this.add(canvas);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		// Find the minimum in each coordinate list and compare to next list
		for (double val : polynomialCoordinates[1]) {
			yMin = Math.min(yMin, val);
			yMax = Math.max(yMax, val);
		}

		for (double val : firstDerivitiveCoordinates[1]) {
			yMin = Math.min(yMin, val);
			yMax = Math.max(yMax, val);
		}

		for (double val : secondDerivitiveCoordinates[1]) {
			yMin = Math.min(yMin, val);
			yMax = Math.max(yMax, val);
		}

		// Calculate the scaling factor for x,y coordinates
		double SCx = (WIDTH) / (xMax - xMin);
		double SCy = (HEIGHT) / (yMax - yMin);

		// Apply scale factor to each coordinate for each polynomial
		for (int i = 0; i <= n; i++) {
			double x = polynomialCoordinates[0][i];
			double y = polynomialCoordinates[1][i];
			x = Math.round((x - xMin) * SCx);
			y = Math.round((yMax - y) * SCy);
			polynomialCoordinates[0][i] = x;
			polynomialCoordinates[1][i] = y;

			x = firstDerivitiveCoordinates[0][i];
			y = firstDerivitiveCoordinates[1][i];
			x = Math.round((x - xMin) * SCx);
			y = Math.round((yMax - y) * SCy);
			firstDerivitiveCoordinates[0][i] = x;
			firstDerivitiveCoordinates[1][i] = y;

			x = secondDerivitiveCoordinates[0][i];
			y = secondDerivitiveCoordinates[1][i];
			x = Math.round((x - xMin) * SCx);
			y = Math.round((yMax - y) * SCy);
			secondDerivitiveCoordinates[0][i] = x;
			secondDerivitiveCoordinates[1][i] = y;
		}
		
		// Initialize canvas min, max, scale
		PlotModel.setYMax(yMax);
		PlotModel.setYMin(yMin);
		PlotModel.setSCx(SCx);
		PlotModel.setSCy(SCy);
		// Set the values for each polynomial for the unique canvas
		PlotModel.setPolynomialCoordinates(polynomialCoordinates);
		PlotModel.setFirstDerivativeCoordinates(firstDerivitiveCoordinates);
		PlotModel.setSecondDerivativeCoordinates(secondDerivitiveCoordinates);
	}

	@Override
	public void run() {
		this.grabFocus();
	}
}
