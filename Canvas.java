package shar1370_a05;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Canvas extends JComponent {

	// Initialize min,max values
	private double yMin = PlotModel.getYMin();
	private double yMax = PlotModel.getYMax();
	private double xMin = Double.parseDouble(PlotModel.getXMin());
	private double xMax = Double.parseDouble(PlotModel.getXMax());
	
	// Initialize scaling factors
	private double SCx = PlotModel.getSCx();
	private double SCy = PlotModel.getSCy();

	// Initialize polynomial coordinates lists
	private double[][] polynomialCoordinates = PlotModel.getKeyPolynomialCoordinates();
	private double[][] firstDerivativeCoordinates = PlotModel.getFirstDerivativeCoordinates();
	private double[][] secondDerivativeCoordinates = PlotModel.getSecondDerivativeCoordinates();
	
	// Initialize x,y axis locations
	private int xLocation;
	private int yLocation;
	
	// Initialize axis offsets
	final int XAXIS_OFFSET = 30;
	final int YAXIS_OFFSET = 30;
	
	// Initialize the text offsets
	int textOffset;
	int textWidth;
	int hatchStart;

	// Initialize hatch mark attributes
	private final int HATCH_WIDTH = 5;
	private final int HATCH_DISTANCE = 50;

    /*
     * (non-Javadoc)
     *
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
	public void paintComponent(final Graphics g) {
    	
		super.paintComponents(g);

		final Graphics2D g2d = (Graphics2D) g;
		// Set stroke width
		g2d.setStroke(new BasicStroke(2));

		// Create Cartesian plane on canvas and display polynomials
		clearCanvas(g2d);
		enableRenderingHints(g2d);
		drawAxis(g2d);
		drawHashMarks(g2d);
		drawPolynomials(g2d);
	}

	// -------------------------------------------------------------------------------
	/**
	 * Clears the canvas background.
	 */
	public void clearCanvas(Graphics g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, InputView.WIDTH, InputView.HEIGHT);
	}
	
	public void enableRenderingHints(Graphics2D g2d) {
		g2d.setRenderingHint(
			    RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
	}

	/**
	 * Draws each <code>Poly</code> to the canvas based on coordinates.
	 */
	public void drawPolynomials(Graphics g2d) {
		int x1, x2, y1, y2;
		int length = polynomialCoordinates[0].length;
		for (int i = 0; i < length - 1; i++) {

			// Draw the original polynomial
			x1 = (int) polynomialCoordinates[0][i];
			x2 = (int) polynomialCoordinates[0][i + 1];
			y1 = (int) polynomialCoordinates[1][i];
			y2 = (int) polynomialCoordinates[1][i + 1];
			g2d.setColor(Color.RED);
			g2d.drawLine(x1, y1, x2, y2);

			// Draw the first derivative
			x1 = (int) firstDerivativeCoordinates[0][i];
			x2 = (int) firstDerivativeCoordinates[0][i + 1];
			y1 = (int) firstDerivativeCoordinates[1][i];
			y2 = (int) firstDerivativeCoordinates[1][i + 1];
			g2d.setColor(Color.BLUE);
			g2d.drawLine(x1, y1, x2, y2);

			// draw the second derivative
			x1 = (int) secondDerivativeCoordinates[0][i];
			x2 = (int) secondDerivativeCoordinates[0][i + 1];
			y1 = (int) secondDerivativeCoordinates[1][i];
			y2 = (int) secondDerivativeCoordinates[1][i + 1];
			g2d.setColor(Color.GREEN);
			g2d.drawLine(x1, y1, x2, y2);
		}
	}
	
	/**
	 * Draws the x,y axis on the canvas.
	 */
	public void drawAxis(Graphics g2d) {
		int height = InputView.HEIGHT;
		int width = InputView.WIDTH;
		
		// Find location of y axis
		if (xMin <= 0 && xMax >= 0) {
			yLocation = (int) (-xMin * SCx) + YAXIS_OFFSET;
		} else if (xMax < 0) {
			yLocation = width + XAXIS_OFFSET + (XAXIS_OFFSET / 2);
		} else if (xMin > 0) {
			yLocation = (XAXIS_OFFSET / 2);
		}
		
		// Find location of x axis
		if (yMin <= 0 && yMax >= 0) {
			xLocation = width - (int) (-yMin * SCy) + XAXIS_OFFSET;
		} else if (yMax < 0) {
			xLocation = (YAXIS_OFFSET / 2);
		} else if (yMin > 0) {
			xLocation = height + YAXIS_OFFSET + (YAXIS_OFFSET / 2);
		}

		// Set axis colours to white
		g2d.setColor(Color.WHITE);
		// Draw x axis with offset
		g2d.drawLine(0, xLocation - XAXIS_OFFSET, width, xLocation - XAXIS_OFFSET);
		// Draw y axis with offsest
		g2d.drawLine(yLocation - YAXIS_OFFSET, 0, yLocation - YAXIS_OFFSET, height);
	}

	/**
	 * Draws the hash marks (ticks) on the x,y axis on the canvas.
	 */
	public void drawHashMarks(Graphics g2d) {
		// Get width, height of frame
		int height = InputView.HEIGHT;
		int width = InputView.WIDTH;
		// Find the necessary number of hash marks required
		double n = width / HATCH_DISTANCE;
		// Get the end and start of hash marks
		double end = xMin;
		double start = xMax;
		double h = start - end / n;

		// Initialize x,y values of hatch ticks
		double x = 0;
		double y = 0;

		// Format for outputting string
		NumberFormat df = DecimalFormat.getInstance();
		// Set to rounding mode
		df.getRoundingMode();
		
		// Draw the positive x axis hatch marks as well as the respective, scaled values
		x = (xMin <= 0 && xMax >= 0 ? 0 : start);
		hatchStart = (yLocation < XAXIS_OFFSET ? XAXIS_OFFSET : yLocation);
		for (int i = hatchStart + HATCH_DISTANCE; i < width + XAXIS_OFFSET; i += HATCH_DISTANCE) {
			x = x + h;
			// Round value
			String value = df.format(x);
			textOffset = (int) (g2d.getFontMetrics().getStringBounds(value, g2d).getWidth() / 2);
			// Draw hatch mark
			g2d.drawLine(i - XAXIS_OFFSET, xLocation - XAXIS_OFFSET - HATCH_WIDTH / 2, i - XAXIS_OFFSET,
					xLocation - XAXIS_OFFSET + HATCH_WIDTH / 2);
			// Display value
			if (yMin > 0)
				g2d.drawString(value, i - XAXIS_OFFSET - textOffset, xLocation - XAXIS_OFFSET - 16);
			else
				g2d.drawString(value, i - XAXIS_OFFSET - textOffset, xLocation - XAXIS_OFFSET + 16);
		}
		
		// Draw the negative x axis hatch marks as well as the respective, scaled values
		x = (xMin <= 0 && xMax >= 0 ? 0 : end);
		hatchStart = (yLocation > XAXIS_OFFSET + width ? XAXIS_OFFSET + width : yLocation);
		for (int i = hatchStart - HATCH_DISTANCE; i > XAXIS_OFFSET; i -= HATCH_DISTANCE) {
			x = x - h;
			// Round value
			String value = df.format(x);
			textOffset = (int) (g2d.getFontMetrics().getStringBounds(value, g2d).getWidth() / 2);
			// Draw hatch mark
			g2d.drawLine(i - XAXIS_OFFSET, xLocation - XAXIS_OFFSET - HATCH_WIDTH / 2, i - XAXIS_OFFSET,
					xLocation - XAXIS_OFFSET + HATCH_WIDTH / 2);
			// Display value
			if (yMin > 0)
				g2d.drawString(value, i - XAXIS_OFFSET - textOffset, xLocation - XAXIS_OFFSET - 16);
			else
				g2d.drawString(value, i - XAXIS_OFFSET - textOffset, xLocation - XAXIS_OFFSET + 16);
		}

		// Draw the positive y axis hatch marks as well as the respective, scaled values
		y = (yMin <= 0 && yMax >= 0 ? 0 : yMin);
		hatchStart = (xLocation < YAXIS_OFFSET ? YAXIS_OFFSET : xLocation);
		for (int i = hatchStart - HATCH_DISTANCE; i > YAXIS_OFFSET; i -= HATCH_DISTANCE) {
			y += HATCH_DISTANCE * (yMax - yMin) / (double) (height);
			// Round value
			String value = df.format(y);
			textOffset = (int) (g2d.getFontMetrics().getStringBounds(value, g2d).getHeight() / 2);
			textWidth = (int) (g2d.getFontMetrics().getStringBounds(value, g2d).getWidth());
			// Draw hatch mark
			g2d.drawLine(yLocation - XAXIS_OFFSET - HATCH_WIDTH / 2, i - XAXIS_OFFSET,
					yLocation - XAXIS_OFFSET + HATCH_WIDTH / 2, i - XAXIS_OFFSET);
			// Display value
			if (yLocation + textOffset > width)
				g2d.drawString(value, yLocation - XAXIS_OFFSET - textWidth - 8, i + textOffset - XAXIS_OFFSET);
			else
				g2d.drawString(value, yLocation - XAXIS_OFFSET + 8, i + textOffset - XAXIS_OFFSET);
		}

		// Draw the negative y axis hatch marks as well as the respective, scaled values
		y = (yMin <= 0 && yMax >= 0 ? 0 : yMax);
		hatchStart = (xLocation > YAXIS_OFFSET + height ? height + YAXIS_OFFSET : xLocation);
		for (int i = hatchStart + HATCH_DISTANCE; i < height + YAXIS_OFFSET; i += HATCH_DISTANCE) {
			y -= HATCH_DISTANCE * (yMax - yMin) / (double) height;
			// Round value
			String value = df.format(y);
			textOffset = (int) (g2d.getFontMetrics().getStringBounds(value, g2d).getHeight() / 2);
			textWidth = (int) (g2d.getFontMetrics().getStringBounds(value, g2d).getWidth());
			// Draw hatch mark
			g2d.drawLine(yLocation - XAXIS_OFFSET - HATCH_WIDTH / 2, i - XAXIS_OFFSET,
					yLocation - XAXIS_OFFSET + HATCH_WIDTH / 2, i - XAXIS_OFFSET);
			// Display value
			if (yLocation + textOffset > width)
				g2d.drawString(value, yLocation - XAXIS_OFFSET - textWidth - 8, i + textOffset - XAXIS_OFFSET);
			else
				g2d.drawString(value, yLocation - XAXIS_OFFSET + 8, i + textOffset - XAXIS_OFFSET);
		}
	}

	/**
	 * Initialize canvas getters and setters. Only setters are used.
	 */
	public void setPolynomialCoordinates(double[][] coordinates) {
		this.polynomialCoordinates = coordinates;
	}

	public void setFirstDerivitiveCoordinates(double[][] coordinates) {
		this.firstDerivativeCoordinates = coordinates;
	}

	public void setSecondDerivitiveCoordinates(double[][] coordinates) {
		this.secondDerivativeCoordinates = coordinates;
	}

	public double[][] getPolynomialCoordinates() {
		return this.polynomialCoordinates;
	}

	public double[][] getFirstDerivitiveCoordinates() {
		return this.firstDerivativeCoordinates;
	}

	public double[][] getSecondDerivitiveCoordinates() {
		return this.secondDerivativeCoordinates;
	}

	public void setYMin(double yMin) {
		this.yMin = yMin;
	}

	public void setYMax(double yMax) {
		this.yMax = yMax;
	}

	public void setXMin(double xMin) {
		this.xMin = xMin;
	}

	public void setXMax(double xMax) {
		this.xMax = xMax;
	}

	public void setSCx(double SCx) {
		this.SCx = SCx;
	}

	public void setSCy(double SCy) {
		this.SCy = SCy;
	}
}