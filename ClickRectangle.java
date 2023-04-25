import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class holds the game start up logic.
 */
public class ClickRectangle {

    public static void main(String args[]) {
        GameFrame frame = new GameFrame();
        frame.pack();
        frame.setVisible(true);
    }
}

/**
 * This class is a JFrame, which is how Java Swing represents a window
 * on the
 * screen.  It holds the GamePanel, which is where the rectangles are,
 * and a
 * button to start the game.
 */
class GameFrame extends JFrame {

    private GamePanel gameArea;
    private JButton startButton;

    /**
     * The constructor sets up the components in the frame and attaches the
     * listeners to the objects that generate events.
     */
    public GameFrame() {
        setTitle("Click the Rectangles!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gameArea = new GamePanel();
        startButton = new JButton("Start");

        add(startButton, BorderLayout.NORTH);
        add(gameArea, BorderLayout.CENTER);

        //
        StartButtonActionListener buttonListener = new StartButtonActionListener();
        startButton.addActionListener(buttonListener);


        //
        GameMouseClickListener clickListener = new GameMouseClickListener();
        gameArea.addMouseListener(clickListener);
    }

    /**
     * This class stores the event handler for what should happen when
     * the start button is clicked.
     */
    class StartButtonActionListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            //
            // Create a new MoveShapesActionListener
            // Create a new timer that fires its event every 10 ms; the event
            // should be an instance of your MoveShapesActionListener.
            // Start the timer.

            ActionListener listener = new MoveShapesActionListener();
            Timer timer = new Timer(10, listener);
            timer.start();


        }
    }

    /**
     * This class stores the event handler for what should happen when the
     * mouse is clicked in the main panel area (when the player tries
     * to click
     * a rectangle).
     */
    class GameMouseClickListener extends MouseAdapter {

        public void mouseReleased(MouseEvent event) {
            //
            //
            // The MouseEvent event object will give you the location where
            // the mouse was clicked: event.getX() and event.getY().
            //
            // Get the mouse coordinates from the event, and call
            // handleMouseClick
            // in the GamePanel object (gameArea).
            // Call repaint() at the end!

            gameArea.handleMouseClick(event.getX(), event.getY());
            repaint();
        }
    }

    /**
     * This class will hold an event handler that will be triggered by
     * a timer for moving the rectangles to the left automatically.
     */
    class MoveShapesActionListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {


            // Call moveShapesToLeft on gameArea
            // Call repaint().
            gameArea.moveShapesToLeft();
            repaint();
        }
    }
}

/**
 * GamePanel represents the main playing area.  It stores a list of
 * rectangles
 * that can be clicked for points.
 */
class GamePanel extends JPanel {

    public int AREA_WIDTH = 500;
    public int AREA_HEIGHT = 500;
    private int score;
    private List<Rectangle> shapes = new ArrayList<Rectangle>();

    /**
     * The constructor makes the panel the default dimensions and adds some
     * randomly-placed and randomly-sized rectangles.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
        // add some random rectangles
        for (int c = 0; c < 10; c++) {
            shapes.add(makeRandomRectangle());
        }

    }

    /**
     * handleMouseClick accepts the location of a mouse click checks each
     * rectangle on the panel to see if the click is inside.  If so, it
     * updates
     * your score appropriately.
     */
    public void handleMouseClick(int x, int y) {
        for (Rectangle r : shapes) {
            if (r.contains(x, y)) {
                score += 100000 / (r.width * r.width + r.height *
                        r.height);
                System.out.println(100000 / (r.width * r.width +
                        r.height * r.height));
                Rectangle r2 = makeRandomRectangle();
                r.setRect(AREA_WIDTH, r2.y, r2.width, r2.height);
            }
        }
    }

    /**
     * moveShapesToLeft iterates through all the rectangles on the
     * panel and
     * moves them one pixel to the left.  If any shape goes off the
     * left edge,
     * a new rectangle is created in a random location to take its
     * spot, so we
     * always have a fixed number of rectangles on the board.
     */
    public void moveShapesToLeft() {
        for (Rectangle r : shapes) {
            r.translate(-1, 0);  // shift one pixel left
            if (r.x <= 0) {
                Rectangle r2 = makeRandomRectangle();
                r.setRect(AREA_WIDTH, r2.y, r2.width, r2.height);
            }
        }
    }

    /**
     * Creates a randomly sized and randomly placed rectangle.
     */
    private Rectangle makeRandomRectangle() {
        int MAX_HEIGHT = 100;
        int MAX_WIDTH = 100;

        int x = (int) (Math.random() * AREA_WIDTH);
        int y = (int) (Math.random() * AREA_HEIGHT);

        int w = (int) (Math.random() * (MAX_WIDTH - 10) + 10);
        int h = (int) (Math.random() * (MAX_HEIGHT - 10) + 10);

        return new Rectangle(x, y, w, h);
    }

    /**
     * paintComponent is a method that all Java Swing GUI components have.
     * It is called automatically whenever Java needs to draw (or
     * "paint") the
     * component on the screen.  So this code just draws all the rectangles
     * and our current score.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        g.drawString("Score: " + score, 20, 20);
        for (Rectangle r : shapes) {
            g.drawRect(r.x, r.y, r.width, r.height);
        }
    }
}
