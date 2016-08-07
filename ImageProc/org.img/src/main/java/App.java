import logic.ImageMath;
import logic.ImagePoint;
import statistics.Statistics;

import java.util.*;

import static logic.ImageMath.findMaximaAndMinima;

/**
 * Created by Lasse on 10/07/2016.
 */
public class App {

    public static void main(String[] args) {
            /*Statistics handler = new Statistics();
            handler.createStatistics();*/
        for(int a = 0; a<5; a++){
            List<int[]> testValues = new ArrayList<>();
            testValues.add(createStatistics());
            testValues.add(createStatistics());
            testValues.add(createStatistics());
            long start = System.currentTimeMillis();
            System.out.println("started!");
            Collection<Integer> returnValues = ImageMath.findMaximaAndMinima(testValues, 500, 500);
            long end = System.currentTimeMillis();
            System.out.println("end: " + (end-start));
        }
    }

    private static int[] createStatistics(){
        int[] statistics = new int[250000];
        Random random = new Random();
        for(int a = 0; a<250000; a++){
            statistics[a] = random.nextInt(255);
        }
        return statistics;
    }

}
