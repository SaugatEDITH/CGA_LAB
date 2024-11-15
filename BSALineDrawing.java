import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BSALineDrawing extends JPanel {
    private int x1, y1, x2, y2;

    public BSALineDrawing() {
        JFrame frame = new JFrame("Bresenham's Line Drawing Algorithm");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout());
        JTextField txtX1 = new JTextField(), txtY1 = new JTextField();
        JTextField txtX2 = new JTextField(), txtY2 = new JTextField();
        JButton drawButton = new JButton("Draw Line");

        inputPanel.add(new JLabel("X1:")); inputPanel.add(txtX1);
        inputPanel.add(new JLabel("Y1:")); inputPanel.add(txtY1);
        inputPanel.add(new JLabel("X2:")); inputPanel.add(txtX2);
        inputPanel.add(new JLabel("Y2:")); inputPanel.add(txtY2);

        drawButton.addActionListener((ActionEvent e) -> {
            try {
                x1 = Integer.parseInt(txtX1.getText());
                y1 = Integer.parseInt(txtY1.getText());
                x2 = Integer.parseInt(txtX2.getText());
                y2 = Integer.parseInt(txtY2.getText());
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
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
        drawLineBSA(g, x1, y1, x2, y2);
    }

    private void drawLineBSA(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1, sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            g.fillRect(x1, y1, 1, 1); // Draw pixel
            if (x1 == x2 && y1 == y2) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }

    public static void main(String[] args) {
        new BSALineDrawing();
    }
}
