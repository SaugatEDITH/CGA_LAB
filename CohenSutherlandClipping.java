import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CohenSutherlandClipping extends JPanel {
    private int xmin, ymin, xmax, ymax;
    private int x1, y1, x2, y2;

    // Region codes
    private static final int INSIDE = 0; // 0000
    private static final int LEFT = 1;   // 0001
    private static final int RIGHT = 2;  // 0010
    private static final int BOTTOM = 4; // 0100
    private static final int TOP = 8;    // 1000

    public CohenSutherlandClipping() {
        JFrame frame = new JFrame("Cohen-Sutherland Line Clipping Algorithm");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        JTextField txtXMin = new JTextField(), txtYMin = new JTextField();
        JTextField txtXMax = new JTextField(), txtYMax = new JTextField();
        JTextField txtX1 = new JTextField(), txtY1 = new JTextField();
        JTextField txtX2 = new JTextField(), txtY2 = new JTextField();
        JButton drawButton = new JButton("Draw and Clip");

        inputPanel.add(new JLabel("Window XMin:")); inputPanel.add(txtXMin);
        inputPanel.add(new JLabel("Window YMin:")); inputPanel.add(txtYMin);
        inputPanel.add(new JLabel("Window XMax:")); inputPanel.add(txtXMax);
        inputPanel.add(new JLabel("Window YMax:")); inputPanel.add(txtYMax);
        inputPanel.add(new JLabel("Line X1:")); inputPanel.add(txtX1);
        inputPanel.add(new JLabel("Line Y1:")); inputPanel.add(txtY1);
        inputPanel.add(new JLabel("Line X2:")); inputPanel.add(txtX2);
        inputPanel.add(new JLabel("Line Y2:")); inputPanel.add(txtY2);

        drawButton.addActionListener((ActionEvent e) -> {
            try {
                xmin = Integer.parseInt(txtXMin.getText());
                ymin = Integer.parseInt(txtYMin.getText());
                xmax = Integer.parseInt(txtXMax.getText());
                ymax = Integer.parseInt(txtYMax.getText());
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

        // Draw the clipping window
        g.drawRect(xmin, ymin, xmax - xmin, ymax - ymin);

        // Draw the original line
        g.setColor(Color.RED);
        g.drawLine(x1, y1, x2, y2);

        // Perform clipping and draw the clipped line
        g.setColor(Color.BLUE);
        clipAndDrawLine(g, x1, y1, x2, y2);
    }

    private int computeCode(int x, int y) {
        int code = INSIDE;
        if (x < xmin) code |= LEFT;
        if (x > xmax) code |= RIGHT;
        if (y < ymin) code |= BOTTOM;
        if (y > ymax) code |= TOP;
        return code;
    }

    private void clipAndDrawLine(Graphics g, int x1, int y1, int x2, int y2) {
        int code1 = computeCode(x1, y1);
        int code2 = computeCode(x2, y2);
        boolean accept = false;

        while (true) {
            if ((code1 | code2) == 0) { // Both endpoints are inside
                accept = true;
                break;
            } else if ((code1 & code2) != 0) { // Both endpoints share an outside region
                break;
            } else {
                int codeOut;
                int x = 0, y = 0;

                // At least one endpoint is outside
                codeOut = (code1 != 0) ? code1 : code2;

                // Find intersection point
                if ((codeOut & TOP) != 0) {
                    x = x1 + (x2 - x1) * (ymax - y1) / (y2 - y1);
                    y = ymax;
                } else if ((codeOut & BOTTOM) != 0) {
                    x = x1 + (x2 - x1) * (ymin - y1) / (y2 - y1);
                    y = ymin;
                } else if ((codeOut & RIGHT) != 0) {
                    y = y1 + (y2 - y1) * (xmax - x1) / (x2 - x1);
                    x = xmax;
                } else if ((codeOut & LEFT) != 0) {
                    y = y1 + (y2 - y1) * (xmin - x1) / (x2 - x1);
                    x = xmin;
                }

                if (codeOut == code1) {
                    x1 = x;
                    y1 = y;
                    code1 = computeCode(x1, y1);
                } else {
                    x2 = x;
                    y2 = y;
                    code2 = computeCode(x2, y2);
                }
            }
        }

        if (accept) {
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {
        new CohenSutherlandClipping();
    }
}
