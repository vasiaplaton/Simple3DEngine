package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawPanel extends JPanel {
    ImgDrawer imgDrawer;

    public void setImgDrawer(ImgDrawer imgDrawer) {
        this.imgDrawer = imgDrawer;
    }


    public interface ImgDrawer {
        void drawImage(BufferedImage image);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D canvas = (Graphics2D) g;

        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

        clearImage(image);
        imgDrawer.drawImage(image);
        canvas.drawImage(image, 0, 0, null);
        canvas.dispose();
    }

    private void clearImage(BufferedImage image) {
        Graphics2D imageGraphics = image.createGraphics();

        imageGraphics.setColor(Color.WHITE);
        imageGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        imageGraphics.setColor(Color.BLACK);

        imageGraphics.dispose();
    }
}