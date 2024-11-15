import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TransformationTriangle extends JPanel {
    private int[][] triangle = new int[3][2]; // Stores triangle vertices
    private int tx = 2, ty = 2;              // Translation vector
    private double angle = Math.toRadians(45); // Rotation angle in radians
    private double sx = 2, sy = 2;           // Scaling factors
    private String transformation = "";     // Tracks selected transformation

    public TransformationTriangle() {
        JFrame frame = new JFrame("2D Transformations on a Triangle");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        JTextField txtX1 = new JTextField(), txtY1 = new JTextField();
        JTextField txtX2 = new JTextField(), txtY2 = new JTextField();
        JTextField txtX3 = new JTextField(), txtY3 = new JTextField();
        JButton translateButton = new JButton("Translate");
        JButton scaleButton = new JButton("Scale");
        JButton rotateButton = new JButton("Rotate");

        inputPanel.add(new JLabel("Vertex 1 (X1, Y1):"));
        inputPanel.add(txtX1);
        inputPanel.add(txtY1);
        inputPanel.add(new JLabel("Vertex 2 (X2, Y2):"));
        inputPanel.add(txtX2);
        inputPanel.add(txtY2);
        inputPanel.add(new JLabel("Vertex 3 (X3, Y3):"));
        inputPanel.add(txtX3);
        inputPanel.add(txtY3);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        buttonPanel.add(translateButton);
        buttonPanel.add(scaleButton);
        buttonPanel.add(rotateButton);

        translateButton.addActionListener((ActionEvent e) -> {
            try {
                getInputVertices(txtX1, txtY1, txtX2, txtY2, txtX3, txtY3);
                transformation = "translate";
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        scaleButton.addActionListener((ActionEvent e) -> {
            try {
                getInputVertices(txtX1, txtY1, txtX2, txtY2, txtX3, txtY3);
                transformation = "scale";
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        rotateButton.addActionListener((ActionEvent e) -> {
            try {
                getInputVertices(txtX1, txtY1, txtX2, txtY2, txtX3, txtY3);
                transformation = "rotate";
                repaint();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(this, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void getInputVertices(JTextField txtX1, JTextField txtY1,
                                  JTextField txtX2, JTextField txtY2,
                                  JTextField txtX3, JTextField txtY3) {
        triangle[0][0] = Integer.parseInt(txtX1.getText());
        triangle[0][1] = Integer.parseInt(txtY1.getText());
        triangle[1][0] = Integer.parseInt(txtX2.getText());
        triangle[1][1] = Integer.parseInt(txtY2.getText());
        triangle[2][0] = Integer.parseInt(txtX3.getText());
        triangle[2][1] = Integer.parseInt(txtY3.getText());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the original triangle
        g.setColor(Color.RED);
        drawTriangle(g, triangle);

        // Apply and draw the transformed triangle
        int[][] transformedTriangle = switch (transformation) {
            case "translate" -> translateTriangle(triangle, tx, ty);
            case "scale" -> scaleTriangle(triangle, sx, sy);
            case "rotate" -> rotateTriangle(triangle, angle);
            default -> triangle;
        };

        g.setColor(Color.BLUE);
        drawTriangle(g, transformedTriangle);
    }

    private void drawTriangle(Graphics g, int[][] triangle) {
        g.drawLine(triangle[0][0], triangle[0][1], triangle[1][0], triangle[1][1]);
        g.drawLine(triangle[1][0], triangle[1][1], triangle[2][0], triangle[2][1]);
        g.drawLine(triangle[2][0], triangle[2][1], triangle[0][0], triangle[0][1]);
    }

    private int[][] translateTriangle(int[][] triangle, int tx, int ty) {
        int[][] result = new int[3][2];
        for (int i = 0; i < 3; i++) {
            result[i][0] = triangle[i][0] + tx;
            result[i][1] = triangle[i][1] + ty;
        }
        return result;
    }

    private int[][] scaleTriangle(int[][] triangle, double sx, double sy) {
        int[][] result = new int[3][2];
        for (int i = 0; i < 3; i++) {
            result[i][0] = (int) (triangle[i][0] * sx);
            result[i][1] = (int) (triangle[i][1] * sy);
        }
        return result;
    }

    private int[][] rotateTriangle(int[][] triangle, double angle) {
        int[][] result = new int[3][2];
        for (int i = 0; i < 3; i++) {
            result[i][0] = (int) (triangle[i][0] * Math.cos(angle) - triangle[i][1] * Math.sin(angle));
            result[i][1] = (int) (triangle[i][0] * Math.sin(angle) + triangle[i][1] * Math.cos(angle));
        }
        return result;
    }

    public static void main(String[] args) {
        new TransformationTriangle();
    }
}
