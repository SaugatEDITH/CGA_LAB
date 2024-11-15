import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MidPointCircleDrawing extends JPanel {
    private int centerX, centerY, radius;

    public MidPointCircleDrawing() {
        JFrame frame = new JFrame("Mid-Point Circle Drawing Algorithm");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout());
        JTextField txtCenterX = new JTextField(), txtCenterY = new JTextField(), txtRadius = new JTextField();
        JButton drawButton = new JButton("Draw Circle");

        inputPanel.add(new JLabel("Center X:")); inputPanel.add(txtCenterX);
        inputPanel.add(new JLabel("Center Y:")); inputPanel.add(txtCenterY);
        inputPanel.add(new JLabel("Radius:")); inputPanel.add(txtRadius);

        drawButton.addActionListener((ActionEvent e) -> {
            try {
                centerX = Integer.parseInt(txtCenterX.getText());
                centerY = Integer.parseInt(txtCenterY.getText());
                radius = Integer.parseInt(txtRadius.getText());
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
        drawCircleMidPoint(g, centerX, centerY, radius);
    }

    private void drawCircleMidPoint(Graphics g, int xc, int yc, int r) {
        int x = 0, y = r;
        int p = 1 - r;

        drawCirclePoints(g, xc, yc, x, y);

        while (x < y) {
            x++;
            if (p < 0) {
                p += 2 * x + 1;
            } else {
                y--;
                p += 2 * (x - y) + 1;
            }
            drawCirclePoints(g, xc, yc, x, y);
        }
    }

    private void drawCirclePoints(Graphics g, int xc, int yc, int x, int y) {
        g.fillRect(xc + x, yc + y, 1, 1);
        g.fillRect(xc - x, yc + y, 1, 1);
        g.fillRect(xc + x, yc - y, 1, 1);
        g.fillRect(xc - x, yc - y, 1, 1);
        g.fillRect(xc + y, yc + x, 1, 1);
        g.fillRect(xc - y, yc + x, 1, 1);
        g.fillRect(xc + y, yc - x, 1, 1);
        g.fillRect(xc - y, yc - x, 1, 1);
    }

    public static void main(String[] args) {
        new MidPointCircleDrawing();
    }
}
