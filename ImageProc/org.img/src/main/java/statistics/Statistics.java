package statistics;

import blurring.GaussianFilter;
import persistance.StatisticsService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lasse on 29/07/2016.
 */
public class Statistics {

    private StatisticsService service = new StatisticsService();
    //Receives all statistics and maps it into this, in order to know which ones has to be updated.

    public Statistics() {

    }

    /**
     * Version compare of two statistics
     * @param version1 version of statistics
     * @param version2 version of statistics
     * @return Whether version1 is greater than version2
     */
    private boolean compareTwoStatisticsSameType(String version1, String version2){
        String[] type1Array = version1.split("\\.");
        String[] type2Array = version2.split("\\.");
        for(int a = 0; a<Math.min(type1Array.length, type2Array.length); a++){
            if(Integer.parseInt(type1Array[a]) < Integer.parseInt(type2Array[a])) return true;
            else if(Integer.parseInt(type1Array[a]) > Integer.parseInt(type2Array[a])) return false;
        }
        return type2Array.length > type1Array.length;
    }

    public static List<BufferedImage> retrieveAllImages(){
        List<BufferedImage> testImages = new ArrayList<>();
        File file = new File("org.img/src/main/java/images");
        File[] childFiles = file.listFiles();
        for(int a = 0; a<childFiles.length; a++){
            File childFile = childFiles[a];
            try {
                testImages.add(ImageIO.read(childFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return testImages;
    }

    public void createStatistics(){
        List<StatisticsHolder> allStatistics = new ArrayList<StatisticsHolder>();
        allStatistics.add(new StatisticsHolder(Type.GAUSSIAN_FILTER, new GaussianFilter(1)));
        for(StatisticsHolder holder: allStatistics){
                long start = System.currentTimeMillis();
                holder.createStatistics();
                long end = System.currentTimeMillis();
            createStatistics(holder, end-start);
        }
    }

    private void createStatistics(StatisticsHolder holder, long duration){
        String testName = holder.getType().getTestName().getName();
        String version = holder.getType().getVersion();
        service.insertNewStatistiscsTest(version, holder.getType().getName(), duration, testName);
    }



}
