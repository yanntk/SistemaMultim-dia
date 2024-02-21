import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageViewer {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        frame.add(panel);

        JButton openButton = new JButton("Open Image");
        openButton.addActionListener(e -> openImage(panel));
        panel.add(openButton);

        JColorChooser colorChooser = new JColorChooser();
        panel.add(colorChooser);

        frame.setVisible(true);
    }

    private static void openImage(JPanel panel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open Image");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg"));

        int result = fileChooser.showOpenDialog(panel);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage bufferedImage = ImageIO.read(selectedFile);
                ImageIcon imageIcon = new ImageIcon(bufferedImage);
                JLabel imageLabel = new JLabel(imageIcon);
                panel.removeAll();
                panel.add(imageLabel);
                panel.add(new JButton("Open Image"));
                panel.add(new JColorChooser());
                panel.revalidate();
                panel.repaint();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}