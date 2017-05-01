package shar1370_a05;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class InputView extends JPanel {

	// -------------------------------------------------------------------------------
	/**
	 * Lays out the input panel components.
	 */
	public static void main(final String[] args) {

		final InputView inputView = new InputView();
		final JFrame f = new JFrame("Gareth Sharpe's Plotter");
		f.setContentPane(inputView);
		f.setSize(300, 200);
		f.getContentPane().setBackground(Color.BLACK);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.setVisible(true);
	}

	private final JLabel functionLabel = new JLabel("Function:");
	private final JLabel variableLabel = new JLabel("Variable:");
	private final JLabel minLabel = new JLabel("From (x min):");
	private final JLabel maxLabel = new JLabel("To (x max):");

	private final static JTextField functionField = new JTextField("x^2");
	private final static JTextField variableField = new JTextField("x");
	private final static JTextField minField = new JTextField("-2");
	private final static JTextField maxField = new JTextField("2");

	private final JButton plotButton = new JButton("Plot");

	/**
	 * Lays out the graphic components.
	 */
	private void layoutView() {

		final JPanel functionPanel = new JPanel();
		functionPanel.setLayout(new GridLayout(1, 2));
		functionPanel.setBackground(Color.BLACK);
		this.functionLabel.setForeground(Color.WHITE);
		functionPanel.add(this.functionLabel);
		functionPanel.add(InputView.functionField);

		final JPanel variablePanel = new JPanel();
		variablePanel.setLayout(new GridLayout(1, 2));
		variablePanel.setBackground(Color.BLACK);
		this.variableLabel.setForeground(Color.WHITE);
		variablePanel.add(this.variableLabel);
		variablePanel.add(InputView.variableField);
		InputView.variableField.setEditable(false);

		final JPanel minPanel = new JPanel();
		minPanel.setLayout(new GridLayout(1, 2));
		minPanel.setBackground(Color.BLACK);
		this.minLabel.setForeground(Color.WHITE);
		minPanel.add(this.minLabel);
		minPanel.add(InputView.minField);

		final JPanel maxPanel = new JPanel();
		maxPanel.setLayout(new GridLayout(1, 2));
		maxPanel.setBackground(Color.BLACK);
		this.maxLabel.setForeground(Color.WHITE);
		maxPanel.add(this.maxLabel);
		maxPanel.add(InputView.maxField);

		final JPanel plotPanel = new JPanel();
		plotPanel.setLayout(new FlowLayout());
		plotPanel.setBackground(Color.BLACK);
		plotPanel.add(plotButton);

		this.setLayout(new GridLayout(5, 1));
		this.add(functionPanel);
		this.add(variablePanel);
		this.add(minPanel);
		this.add(maxPanel);
		this.add(plotPanel);
	}

	/**
	 * Lays out the view and registers the button listeners.
	 *
	 */
	public InputView() {
		this.layoutView();
		this.registerListeners();
	}

	/**
	 * Informs the Plotter what polynomial to generate and plot.
	 */
	private class PlotListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent evt) {

			final String function = InputView.getFunction();
			final String xMax = InputView.getMax();
			final String xMin = InputView.getMin();

			try {
				// Check if input is convertible
				Tools.convert(function);

				// Set plot model attributes
				PlotModel.setFunction(function);
				PlotModel.setXMin(xMin);
				PlotModel.setXMax(xMax);

				// Make a new model based on plot model attributes
				final PlotModel model = new PlotModel();
				// Attach model to plot view
				final PlotView view = new PlotView(model);
				// Create frame for plot view
				final JFrame frame = new JFrame("Plotter: " + function);

				// Set plot frame attributes
				frame.setContentPane(new PlotView(model));
				frame.setSize(SIZE);
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
				// Update the location of the next counter frame.
				InputView.this.nextLocation();
				frame.setLocation(InputView.this.location);
				frame.setResizable(false);
				frame.setVisible(true);
				// Execute the plot model with a thread from the thread pool.
				InputView.this.threadPool.execute(view);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// -------------------------------------------------------------------------------
	// Define the size and location of the plot view frames.
	public static final int WIDTH = 500;
	public static final int HEIGHT = 500;
	public static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);
	private final Point location = new Point(0, 0);
	// Attributes to handle screen placement.
	private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * <code>threadPool</code> provides an unbounded pool of execution threads.
	 */
	private final ExecutorService threadPool = Executors.newCachedThreadPool();

	// -------------------------------------------------------------------------------
	/**
	 * Determines the location of the next Plot frame. Lays out frames from
	 * left-to-right, top-to-bottom.
	 */
	private void nextLocation() {
		int x = this.location.x + WIDTH;
		int y = this.location.y;

		if (x + WIDTH > this.screenSize.width) {
			x = 0;
			y += HEIGHT;
		}
		this.location.setLocation(x, y);
	}

	/**
	 * Registers the button listeners.
	 */
	private void registerListeners() {
		this.plotButton.addActionListener(new PlotListener());
	}

	/**
	 * Implement getters and setters for each input field.
	 */
	public static String getFunction() {
		return InputView.functionField.getText();
	}

	public static String getVariable() {
		return variableField.getText();
	}

	public static String getMin() {
		return minField.getText();
	}

	public static String getMax() {
		return maxField.getText();
	}

}
