package sift;

import blurring.GaussianFilter;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lasse on 04-08-2016.
 */
public class SiftController {

    private BufferedImage image;
    private float sigma = 1.6f;
    private float k = (float) Math.sqrt(2);


    public SiftController(){

    }

    /**
     * Receives an image an calculates the differences of Gaussian function between several of these images.
     * @param image
     * @return
     */
    public List<int[]> findDifferences(BufferedImage image){
        List<int[]> differenciatedImages = new ArrayList<>();
        BufferedImage previousImage = null;
        for(int i = 0; i<5; i++){
            float kValue = (float)Math.pow(k, i);
            GaussianFilter filter = new GaussianFilter(2, kValue, sigma);
            BufferedImage currentImage = filter.filter(image, null);
            if(previousImage != null) {
                int[] differentiatedRGB = differenceOfGaussians(currentImage, previousImage,  kValue);
                differenciatedImages.add(differentiatedRGB);
            }
        }
        return differenciatedImages;
    }

    /**
     * Find the difference between the two Gaussian Filters
     * It is being expected they have the same format, since they belong to the same source.
     * @param blurredImage1
     * @param blurredImage2
     * @param k
     * @return
     */
    private int[] differenceOfGaussians(BufferedImage blurredImage1, BufferedImage blurredImage2, float k){
        if(blurredImage1.getWidth() != blurredImage2.getWidth() && blurredImage1.getHeight() != blurredImage2.getHeight())
            throw new IllegalArgumentException("Both images have to contain the same source");
        int[] image1RGB = new int[blurredImage1.getWidth()*blurredImage1.getHeight()];
        int[] image2RGB = new int[blurredImage2.getWidth()*blurredImage2.getHeight()];
        int[] outPixels = new int[blurredImage2.getWidth()*blurredImage2.getHeight()];
        blurredImage1.getRGB( 0, 0, blurredImage1.getWidth(), blurredImage1.getHeight(), image1RGB, 0, blurredImage1.getWidth());
        blurredImage2.getRGB( 0, 0, blurredImage2.getWidth(), blurredImage2.getHeight(), image2RGB, 0, blurredImage2.getWidth());
        for(int h = 0; h<image1RGB.length; h++){
            outPixels[h] = calculateDifferenceOfRGB(image1RGB[h], image2RGB[h], k * sigma);
        }
        return outPixels;
    }

    private int calculateDifferenceOfRGB(int rgb1, int rgb2, double divide){
        int a =  (int) ((((double) ((rgb1 >> 24) & 0xff - (rgb2 >> 24) & 0xff)) / divide) + 0.5);
        int r =  (int) ((((double) ((rgb1 >> 16) & 0xff - (rgb2 >> 16) & 0xff)) / divide) + 0.5);
        int g =  (int) ((((double) ((rgb1 >> 8) & 0xff - (rgb2 >> 8) & 0xff)) / divide) + 0.5);
        int b =  (int) ((((double) (rgb1 & 0xff - rgb2 & 0xff)) / divide) + 0.5);
        return (a << 24) | (a << 16) | (a << 8) | a;
    }
}
