package statistics;

import blurring.GaussianFilter;
import logic.Type;

import java.awt.image.BufferedImage;

/**
 * Created by Lasse on 02/08/2016.
 */
public class StatisticsHolder {

    private Type type;
    private GaussianFilter filter;


    public StatisticsHolder(Type type, GaussianFilter filter){
        this.type = type;
        this.filter = filter;
    }

    public Type getType(){
        return this.type;
    }

    public void createStatistics(){
        switch(type){
            case GAUSSIAN_FILTER:
                float[] matrix = {
                        0.111f, 0.111f, 0.111f,
                        0.111f, 0.111f, 0.111f,
                        0.111f, 0.111f, 0.111f,
                };

                for (BufferedImage image: Statistics.retrieveAllImages()){
                    filter.filter(image, null);
                }
                break;
        }
    }

}
