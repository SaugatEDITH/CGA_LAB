import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DDALineDrawing extends JPanel {
    private int x1, y1, x2, y2;

    public DDALineDrawing() {
        JFrame frame = new JFrame("DDA Line Drawing Algorithm");
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
        drawLineDDA(g, x1, y1, x2, y2);
    }

    private void drawLineDDA(Graphics g, int x1, int y1, int x2, int y2) {
        int dx = x2 - x1, dy = y2 - y1, steps = Math.max(Math.abs(dx), Math.abs(dy));
        float x = x1, y = y1, xInc = dx / (float) steps, yInc = dy / (float) steps;

        for (int i = 0; i <= steps; i++) {
            g.fillRect(Math.round(x), Math.round(y), 1, 1);
            x += xInc; y += yInc;
        }
    }

    public static void main(String[] args) {
        new DDALineDrawing();
    }
}
