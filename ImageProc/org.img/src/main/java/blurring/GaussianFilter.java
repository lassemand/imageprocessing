package blurring;

/**
 * Created by Lasse on 28/07/2016.
 */
/*
** Copyright 2005 Huxtable.com. All rights reserved.
*/

import logic.ImageMath;
import logic.PixelUtils;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;

public class GaussianFilter  {

    static final long serialVersionUID = 5377089073023183684L;

    public boolean alpha = true;
    protected float radius;
    protected Kernel kernel;
    public static int ZERO_EDGES = 0;
    public static int CLAMP_EDGES = 1;
    public static int WRAP_EDGES = 2;

    /**
     * Construct a Gaussian filter
     */
    public GaussianFilter() {
        this(2);
    }

    /**
     * Construct a Gaussian filter
     * @param radius blur radius in pixels
     */
    public GaussianFilter(float radius) {
        setRadius(radius, (float) Math.sqrt(2), radius/3);
    }

    public GaussianFilter(float radius, float k, float sigma){
        setRadius(radius, k, sigma);

    }

    /**
     * Set the radius of the kernel, and hence the amount of blur. The bigger the radius, the longer this filter will take.
     * @param radius the radius of the blur in pixels.
     */
    public void setRadius(float radius, float k, float sigma) {
        this.radius = radius;
        kernel = makeKernel(radius, k, sigma);
    }

    /**
     * Get the radius of the kernel.
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
        if ( dstCM == null )
            dstCM = src.getColorModel();
        return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), dstCM.isAlphaPremultiplied(), null);
    }

    /**
     * Blurs the image. dst is the same as the returned element
     * @param src
     * @param dst
     * @return
     */
    public BufferedImage filter( BufferedImage src, BufferedImage dst ) {
        int width = src.getWidth();
        int height = src.getHeight();

        if ( dst == null )
            dst = createCompatibleDestImage( src, null );

        int[] inPixels = new int[width*height];
        int[] outPixels = new int[width*height];
        src.getRGB( 0, 0, width, height, inPixels, 0, width );

        convolveAndTranspose(kernel, inPixels, outPixels, width, height, alpha, CLAMP_EDGES);
        convolveAndTranspose(kernel, outPixels, inPixels, height, width, alpha, CLAMP_EDGES);

        dst.setRGB( 0, 0, width, height, inPixels, 0, width );
        return dst;
    }

    /**
     * Transposes the image, and uses the given radius.
     * @param kernel
     * @param inPixels
     * @param outPixels
     * @param width
     * @param height
     * @param alpha
     * @param edgeAction
     */
    public static void convolveAndTranspose(Kernel kernel, int[] inPixels, int[] outPixels, int width, int height, boolean alpha, int edgeAction) {
        float[] matrix = kernel.getKernelData( null );
        int cols2 = kernel.getWidth()/2;

        for (int y = 0; y < height; y++) {
            int index = y;
            int ioffset = y*width;
            for (int x = 0; x < width; x++) {
                float r = 0, g = 0, b = 0, a = 0;
                int moffset = cols2;
                for (int col = -cols2; col <= cols2; col++) {
                    float f = matrix[moffset+col];

                    if (f != 0) {
                        int ix = x+col;
                        if ( ix < 0 ) {
                            if ( edgeAction == CLAMP_EDGES )
                                ix = 0;
                            else if ( edgeAction == WRAP_EDGES )
                                ix = (x+width) % width;
                        } else if ( ix >= width) {
                            if ( edgeAction == CLAMP_EDGES )
                                ix = width-1;
                            else if ( edgeAction == WRAP_EDGES )
                                ix = (x+width) % width;
                        }
                        int rgb = inPixels[ioffset+ix];
                        a += f * ((rgb >> 24) & 0xff);
                        r += f * ((rgb >> 16) & 0xff);
                        g += f * ((rgb >> 8) & 0xff);
                        b += f * (rgb & 0xff);
                    }
                }
                int ia = alpha ? PixelUtils.clamp((int)(a+0.5)) : 0xff;
                int ir = PixelUtils.clamp((int)(r+0.5));
                int ig = PixelUtils.clamp((int)(g+0.5));
                int ib = PixelUtils.clamp((int)(b+0.5));
                outPixels[index] = (ia << 24) | (ir << 16) | (ig << 8) | ib;
                index += height;
            }
        }
    }

    /**
     * Make a kernel with a radious and k eksponentiel value
     * @param radius
     * @param k
     * @param sigma
     * @return
     */
    public static Kernel makeKernel(float radius, float k, float sigma) {
        int r = (int)Math.ceil(radius);
        float[] matrix = new float[r*2+1];

        float sqrtSigmaPi2 = (float)(k*Math.sqrt(ImageMath.PI*sigma));
        float total = (float)1 / sqrtSigmaPi2;
        int index = 0;
        matrix[(matrix.length - 1) / 2] = (float)1 / sqrtSigmaPi2;
        for (int row = r; row > 0; row--) {
            float distance = row*row;
            if (distance > radius*radius){
                matrix[index] = 0;
                matrix[matrix.length - 1 - index] = 0;
            }
            else{
                float g = (float)Math.exp(-(distance)/(2*sigma*sigma)) / sqrtSigmaPi2;
                matrix[index] = g;
                matrix[matrix.length - 1 - index] = g;
            }

            total += matrix[index] * 2;
            index++;
        }
        for (int i = 0; i < matrix.length; i++)
            matrix[i] /= total;

        return new Kernel(matrix.length, 1, matrix);
    }

    public String toString() {
        return "Blur/Gaussian Blur...";
    }


}