package edu.isu.cs.cs3308;

import com.sun.source.tree.ArrayAccessTree;
import edu.isu.cs.cs3308.algorithms.ArraySearch;
import edu.isu.cs.cs3308.algorithms.impl.BinarySearch;
import edu.isu.cs.cs3308.algorithms.impl.LinearSearch;
import edu.isu.cs.cs3308.algorithms.impl.RecursiveBinarySearch;
import edu.isu.cs.cs3308.algorithms.impl.RecursiveLinearSearch;

import javax.sound.midi.SysexMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Random;

/**
 * Driver class for the experimental simulator.
 *
 * @author Isaac Griffith
 */
public class Driver {

    // Source: https://sourcemaking.com/design_patterns/strategy/java/1
    @SuppressWarnings("Duplicates")
    public static void main(String args[]) {
        BigInteger avgIterLinear, avgRecurLinear, avgIterBinary, avgRecurBinary;
        avgIterLinear = avgRecurLinear = avgIterBinary = avgRecurBinary = BigInteger.ZERO;

        long[] iterLinTimes = new long[20];
        long[] recLinTimes = new long[20];
        long[] iterBinTimes = new long[20];
        long[] recBinTimes = new long[20];

        ArraySearch binSearch = new BinarySearch();
        ArraySearch linSearch = new LinearSearch();
        ArraySearch recBinSearch = new RecursiveBinarySearch();
        ArraySearch recLinSearch = new RecursiveLinearSearch();

        Random r = new Random();
        r.nextInt(10000 - 2000);
        int timeIndex = 0;

            for (int index = 2000; index < 4000; index = index + 100){
                Integer[] array = generateRandomArray(index);
                Arrays.sort(array);

                for (int i = 0; i < 2000; i++) {
                    long startTime = System.nanoTime();
                    binSearch.search(array, r.nextInt(array.length));
                    long endTime = System.nanoTime();
                    avgIterBinary = avgIterBinary.add(BigInteger.valueOf(endTime - startTime));

                    startTime = System.nanoTime();
                    recBinSearch.search(array, r.nextInt(array.length));
                    endTime = System.nanoTime();
                    avgRecurBinary = avgRecurBinary.add(BigInteger.valueOf(endTime - startTime));

                    startTime = System.nanoTime();
                    linSearch.search(array, r.nextInt(array.length));
                    endTime = System.nanoTime();
                    avgIterLinear = avgIterLinear.add(BigInteger.valueOf(endTime - startTime));

                    startTime = System.nanoTime();
                    recLinSearch.search(array, r.nextInt(array.length));
                    endTime = System.nanoTime();
                    avgRecurLinear = avgRecurLinear.add(BigInteger.valueOf(endTime - startTime));
                }

                avgIterLinear = avgIterLinear.divide(BigInteger.valueOf(2000));
                avgRecurLinear = avgRecurLinear.divide(BigInteger.valueOf(2000));
                avgIterBinary = avgIterBinary.divide(BigInteger.valueOf(2000));
                avgRecurBinary = avgRecurBinary.divide(BigInteger.valueOf(2000));

                iterLinTimes[timeIndex] = avgIterLinear.longValue();
                recLinTimes[timeIndex] = avgRecurLinear.longValue();
                iterBinTimes[timeIndex] = avgIterBinary.longValue();
                recBinTimes[timeIndex] = avgRecurBinary.longValue();

                timeIndex++;
            }
            report(iterLinTimes, recLinTimes, iterBinTimes, recBinTimes, 2000, 100);
    }

    /**
     * Generates a random ordered array of integers of the provided size
     *
     * @param size Size of the random array
     * @return An array of the provided size of random numbers in ascending
     * order.
     */
    public static Integer[] generateRandomArray(int size) {
        Random rand = new Random();

        Integer[] array = new Integer[size];

        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(2000);
        }

        Arrays.sort(array);
        return array;
    }

    /**
     * Generates the output to both a Comma Separated Values file called
     * "results.csv" and to the screen.
     *
     * @param iterLinTimes   Array of average values for the Iterative Linear
     *                       Search
     * @param recLinTimes    Array of average values for the Recursive Linear
     *                       Search
     * @param iterBinTimes   Array of average values for the Iterative Binary
     *                       Search
     * @param recBinTimes    Array of average values for the Recursive Binary
     *                       Search
     * @param startIncrement Start increment for array sizes
     * @param increment      Increment of array sizes.
     */
    private static void report(long[] iterLinTimes, long[] recLinTimes, long[] iterBinTimes, long[] recBinTimes, int startIncrement, int increment) {
        StringBuilder file = new StringBuilder();
        StringBuilder screen = new StringBuilder();

        screen.append(String.format("N    IterLin\tRecLin\tIterBin\tRecBin%s", System.lineSeparator()));
        file.append(String.format("N,IterLin,RecLin,IterBin,RecBin%s", System.lineSeparator()));

        for (int i = 0; i < iterLinTimes.length; i++) {
            screen.append(String.format("%d %d\t%d\t%d\t%d%s", startIncrement + (i * increment), iterLinTimes[i], recLinTimes[i], iterBinTimes[i], recBinTimes[i], System.lineSeparator()
            ));
            file.append(String.format("%d,%d,%d,%d,%d%s", startIncrement + (i * increment), iterLinTimes[i], recLinTimes[i], iterBinTimes[i], recBinTimes[i], System.lineSeparator()
            ));
        }

        System.out.println(screen.toString());

        Path p = Paths.get("results.csv");
        try {
            Files.deleteIfExists(p);
        } catch (IOException e) {

        }

        try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(p, StandardOpenOption.CREATE, StandardOpenOption.WRITE))) {
            pw.println(file.toString());
        } catch (IOException e) {

        }
    }
}
