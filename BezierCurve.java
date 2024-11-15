import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BezierCurve extends JPanel {
    private int[] xPoints = new int[4];
    private int[] yPoints = new int[4];
    private boolean pointsSet = false;

    public BezierCurve() {
        JFrame frame = new JFrame("Bezier Curve with 4 Control Points");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        JTextField[] xFields = new JTextField[4];
        JTextField[] yFields = new JTextField[4];
        JButton drawButton = new JButton("Draw Curve");

        for (int i = 0; i < 4; i++) {
            xFields[i] = new JTextField();
            yFields[i] = new JTextField();
            inputPanel.add(new JLabel("Control Point " + (i + 1) + " (X, Y):"));
            inputPanel.add(xFields[i]);
            inputPanel.add(yFields[i]);
        }

        drawButton.addActionListener((ActionEvent e) -> {
            try {
                for (int i = 0; i < 4; i++) {
                    xPoints[i] = Integer.parseInt(xFields[i].getText());
                    yPoints[i] = Integer.parseInt(yFields[i].getText());
                }
                pointsSet = true;
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid integers for all points.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);
        frame.add(drawButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pointsSet) {
            g.setColor(Color.RED);
            drawBezierCurve(g, xPoints, yPoints);
        }
    }

    private void drawBezierCurve(Graphics g, int[] x, int[] y) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        // Draw control polygon
        g.setColor(Color.GRAY);
        for (int i = 0; i < 3; i++) {
            g.drawLine(x[i], y[i], x[i + 1], y[i + 1]);
        }

        // Draw the Bezier curve
        g.setColor(Color.BLUE);
        for (double t = 0; t <= 1; t += 0.001) {
            int px = (int) (Math.pow(1 - t, 3) * x[0]
                    + 3 * Math.pow(1 - t, 2) * t * x[1]
                    + 3 * (1 - t) * t * t * x[2]
                    + Math.pow(t, 3) * x[3]);
            int py = (int) (Math.pow(1 - t, 3) * y[0]
                    + 3 * Math.pow(1 - t, 2) * t * y[1]
                    + 3 * (1 - t) * t * t * y[2]
                    + Math.pow(t, 3) * y[3]);

            g.fillRect(px, py, 1, 1);
        }
    }

    public static void main(String[] args) {
        new BezierCurve();
    }
}
