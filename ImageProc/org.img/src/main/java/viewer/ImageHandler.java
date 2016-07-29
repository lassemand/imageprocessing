package viewer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Lasse on 20/07/2016.
 */
public class ImageHandler {


    /**
     * Shows the given image
     *
     * @param image The image to be shown
     */
    public static void showImage(Image image, String imageName){
        BufferedImage img = toBufferedImage(image);
        showImageHelper(img, imageName);
    }

    /**
     * Shows the given byte array as image
     *
     * @param bytes The byte array representation of the image to be shown
     */
    public static void showImage(byte[] bytes, String imageName){
        try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
            showImageHelper(img, imageName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the image
     *
     * @param img converted to bufferedImage
     */
    private static void showImageHelper(BufferedImage img, String imageName){
        File f = new File("org.img/src/main/java/blurredimages/" + imageName + ".png");
        try {
            f.createNewFile();
            ImageIO.write(img, "png", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Desktop dt = Desktop.getDesktop();
        try {
            dt.open(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a given Image into a BufferedImage
     *
     * @param img The Image to be converted
     * @return The converted BufferedImage
     */
    private static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }

}
