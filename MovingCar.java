import javax.swing.*;
import java.awt.*;

public class MovingCar extends JPanel {
    private int carX = 0; // Initial x-coordinate of the car
    private final int carY = 300; // y-coordinate of the car

    public MovingCar() {
        JFrame frame = new JFrame("Moving Car Animation");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);

        Timer timer = new Timer(50, e -> { // Adjust delay for smoother or faster movement
            carX += 5; // Move the car by 5 pixels each time
            if (carX > frame.getWidth()) {
                carX = -200; // Reset position when the car moves out of the screen
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCar(g, carX, carY);
    }

    private void drawCar(Graphics g, int x, int y) {
        // Draw the body of the car
        g.setColor(Color.RED);
        g.fillRect(x, y, 100, 40); // Main body
        g.fillRect(x + 20, y - 20, 60, 20); // Roof

        // Draw the wheels
        g.setColor(Color.BLACK);
        g.fillOval(x + 10, y + 35, 20, 20); // Front wheel
        g.fillOval(x + 70, y + 35, 20, 20); // Rear wheel

        // Draw windows
        g.setColor(Color.CYAN);
        g.fillRect(x + 25, y - 15, 20, 15); // Front window
        g.fillRect(x + 55, y - 15, 20, 15); // Rear window
    }

    public static void main(String[] args) {
        new MovingCar();
    }
}
