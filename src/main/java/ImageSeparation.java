import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageSeparation {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Separation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel panel = new JPanel();
            frame.add(panel);

            JButton openButton = new JButton("Abrir");
            openButton.addActionListener(new OpenButtonListener());
            panel.add(openButton);

            frame.setVisible(true);
        });
    }

    private static class OpenButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedImage image = ImageIO.read(selectedFile);
                    displayImage(image);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao abrir a imagem.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private void displayImage(BufferedImage image) {
            JFrame frame = new JFrame("Imagem");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel panel = new JPanel();
            frame.add(panel);

            BufferedImage redImage = getChannelImage(image, 0);
            BufferedImage greenImage = getChannelImage(image, 1);
            BufferedImage blueImage = getChannelImage(image, 2);

            JLabel redLabel = new JLabel(new ImageIcon(redImage));
            JLabel greenLabel = new JLabel(new ImageIcon(greenImage));
            JLabel blueLabel = new JLabel(new ImageIcon(blueImage));

            panel.add(redLabel);
            panel.add(greenLabel);
            panel.add(blueLabel);

            frame.setVisible(true);
        }

        private BufferedImage getChannelImage(BufferedImage image, int channel) {
            int width = image.getWidth();
            int height = image.getHeight();
            BufferedImage channelImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = image.getRGB(x, y);
                    int[] channels = new int[]{
                            (rgb >> 16) & 0xFF,
                            (rgb >> 8) & 0xFF,
                            rgb & 0xFF
                    };
                    int newPixel = channels[channel];
                    newPixel = (newPixel << 16) | (newPixel << 8) | newPixel;
                    channelImage.setRGB(x, y, newPixel);
                }
            }
            return channelImage;
        }
    }
}