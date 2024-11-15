import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MidPointEllipseDrawing extends JPanel {
    private int centerX, centerY, radiusX, radiusY;

    public MidPointEllipseDrawing() {
        JFrame frame = new JFrame("Mid-Point Ellipse Drawing Algorithm");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout());
        JTextField txtCenterX = new JTextField(), txtCenterY = new JTextField();
        JTextField txtRadiusX = new JTextField(), txtRadiusY = new JTextField();
        JButton drawButton = new JButton("Draw Ellipse");

        inputPanel.add(new JLabel("Center X:")); inputPanel.add(txtCenterX);
        inputPanel.add(new JLabel("Center Y:")); inputPanel.add(txtCenterY);
        inputPanel.add(new JLabel("Radius X:")); inputPanel.add(txtRadiusX);
        inputPanel.add(new JLabel("Radius Y:")); inputPanel.add(txtRadiusY);

        drawButton.addActionListener((ActionEvent e) -> {
            try {
                centerX = Integer.parseInt(txtCenterX.getText());
                centerY = Integer.parseInt(txtCenterY.getText());
                radiusX = Integer.parseInt(txtRadiusX.getText());
                radiusY = Integer.parseInt(txtRadiusY.getText());
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
        drawEllipseMidPoint(g, centerX, centerY, radiusX, radiusY);
    }

    private void drawEllipseMidPoint(Graphics g, int xc, int yc, int rx, int ry) {
        int x = 0, y = ry;
        int rx2 = rx * rx, ry2 = ry * ry;
        int tworx2 = 2 * rx2, twory2 = 2 * ry2;
        int p = (int) (ry2 - (rx2 * ry) + (0.25 * rx2));

        // Region 1
        int px = 0, py = tworx2 * y;
        while (px < py) {
            drawEllipsePoints(g, xc, yc, x, y);
            x++;
            px += twory2;
            if (p < 0) {
                p += ry2 + px;
            } else {
                y--;
                py -= tworx2;
                p += ry2 + px - py;
            }
        }

        // Region 2
        p = (int) (ry2 * (x + 0.5) * (x + 0.5) + rx2 * (y - 1) * (y - 1) - rx2 * ry2);
        while (y >= 0) {
            drawEllipsePoints(g, xc, yc, x, y);
            y--;
            py -= tworx2;
            if (p > 0) {
                p += rx2 - py;
            } else {
                x++;
                px += twory2;
                p += rx2 - py + px;
            }
        }
    }

    private void drawEllipsePoints(Graphics g, int xc, int yc, int x, int y) {
        g.fillRect(xc + x, yc + y, 1, 1);
        g.fillRect(xc - x, yc + y, 1, 1);
        g.fillRect(xc + x, yc - y, 1, 1);
        g.fillRect(xc - x, yc - y, 1, 1);
    }

    public static void main(String[] args) {
        new MidPointEllipseDrawing();
    }
}
