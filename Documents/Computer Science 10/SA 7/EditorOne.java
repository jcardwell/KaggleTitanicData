/**
 * @author Jack Cardwell added to this class on May 17, 2015
 */
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Basic shape drawing GUI
 * Dartmouth CS 10
 * 
 * @author YOUR NAME HERE
 */

public class EditorOne extends JFrame {	
	private static final int width = 800, height = 800;

	// GUI components
	private JComponent canvas, gui;
	JDialog colorDialog;
	JColorChooser colorChooser;
	JLabel colorL;

	// Current settings on GUI
	private boolean drawing = true;			// adding objects vs. moving/deleting/recoloring them
	private String shape = "ellipse"; 		// type of object to add
	private Color color = Color.black;		// current drawing color

	// Drawing state
	private Point point = null;				// initial mouse press for drawing; current position for moving
	private Shape current = null;			// the one and only object on our canvas
	private boolean selected = false;		// has the object been selecting (for deleting/recoloring)?

	public EditorOne() {
		super("Graphical Editor");

		// Helpers to create the canvas and GUI (buttons, etc.)
		setupCanvas();
		setupGUI();

		// Put the buttons and canvas together into the window
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(gui, BorderLayout.NORTH);

		// Usual initialization
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * Creates a panel with all the buttons, etc.
	 */
	private void setupGUI() {
		// Toggle whether drawing or editing
		JToggleButton drawingB = new JToggleButton("drawing", drawing);
		drawingB.addActionListener(new AbstractAction("drawing") {
			public void actionPerformed(ActionEvent e) {
				drawing = !drawing;
				selected = false;
			}
		});

		// Select type of shape
		String[] shapes = {"ellipse"};
		JComboBox shapeB = new JComboBox(shapes);
		shapeB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				shape = (String)((JComboBox)e.getSource()).getSelectedItem();
			}
		});

		// Select drawing/recoloring color
		// Following Oracle example
		JButton chooseColorB = new JButton("choose color");
		colorChooser = new JColorChooser();
		colorDialog = JColorChooser.createDialog(chooseColorB,
				"Pick a Color",
				true,  //modal
				colorChooser,
				new AbstractAction() { 
			public void actionPerformed(ActionEvent e) {
				color = colorChooser.getColor();
				colorL.setBackground(color); 
			} 
		}, //OK button
		null); //no CANCEL button handler
		chooseColorB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				colorDialog.setVisible(true);
			}
		});
		colorL = new JLabel();
		colorL.setBackground(Color.black);
		colorL.setOpaque(true);
		colorL.setBorder(BorderFactory.createLineBorder(Color.black));
		colorL.setPreferredSize(new Dimension(25, 25));

		// Delete object if it is selected
		JButton deleteB = new JButton("delete");
		deleteB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				// YOUR CODE HERE
				if (selected){
					//delete the object by setting it to null, which gets rid of all of its information
					current=null;
					repaint();
				}
			}
		});

		// Recolor object if it is selected
		JButton recolorB = new JButton("recolor");
		recolorB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				// YOUR CODE HERE
				if (selected){	
					//reset the color attribute of the current object
					current.setColor(color);
					repaint();
				}
			}
		});

		// Put all the stuff into a panel
		gui = new JPanel();
		gui.setLayout(new FlowLayout());
		gui.add(shapeB);
		gui.add(chooseColorB);
		gui.add(colorL);
		gui.add(new JSeparator(SwingConstants.VERTICAL));
		gui.add(drawingB);
		gui.add(deleteB);
		gui.add(recolorB);
	}

	private void setupCanvas() {
		canvas = new JComponent() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				// If there is an object, draw it; if it is selected, also put a border on it
				// YOUR CODE HERE
				if (current!=null){
					//if there is an object, draw it
					current.draw(g);;	
					
					if(selected){
						//if it is selected, also put a border on it
						current.border(g);
					}
				}
				//repaint after running through the conditionals
				repaint();
			}
		};

		canvas.setPreferredSize(new Dimension(width, height));

		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				point = event.getPoint();
				// In drawing mode, start a new object;
				// in editing mode, set selected according to whether the current object (if it exists) contains the point
				// YOUR CODE HERE
				if (drawing && current==null){
					//create a new ellipse and repaint
					current= new Ellipse(point.x,point.y,event.getX(),event.getY(),color);
					repaint();
				}
				else if (current!=null && !drawing){
					if (current.contains(point.x, point.y)){
						//if the point falls within a valid ellipse, switch selected to true
						selected=!selected;
					}
					else if (current.contains(point.x,point.y)==false){
						//if the user clicks on a point that isn't encapsulated by the ellipse, switch to false
						selected=false;
					}
					repaint();
				}
			}
		});		

		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent event) {
				// In drawing mode, update the other corner of the object;
				// in editing mode, move the object by the difference between the current point and the previous one
				// YOUR CODE HERE
				if (drawing){
					//recreate the ellipse and repaint
					current.setCorners(point.x,point.y,event.getPoint().x,event.getPoint().y);
					repaint();
				}
				else if (!drawing && current !=null){
					//move the object and repaint
					current.moveBy(event.getX()-current.x1, event.getY()-current.y1);
					repaint();
				}
			}				
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new EditorOne();
			}
		});	
	}
}
