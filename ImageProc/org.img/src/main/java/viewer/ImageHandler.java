package viewer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Lasse on 20/07/2016.
 */
public class ImageHandler {

    private static final String IMG_PATH = "C:\\Users\\Lasse\\Downloads\\test.png";

    public static void showImage(){
        try {
            System.out.println(IMG_PATH);
            BufferedImage img = ImageIO.read(new File(IMG_PATH));
            ImageIcon icon = new ImageIcon(img);
            JLabel label = new JLabel(icon);
            JOptionPane.showMessageDialog(null, label);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showImage(Image image){

    }

}
