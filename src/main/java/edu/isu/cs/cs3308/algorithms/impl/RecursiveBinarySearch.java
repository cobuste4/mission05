package edu.isu.cs.cs3308.algorithms.impl;

import edu.isu.cs.cs3308.algorithms.ArraySearch;

/**
 * Performs a recursive binary search
 *
 * @author Steve Coburn
 */
public class RecursiveBinarySearch implements ArraySearch {
    @Override
    public <E extends Comparable> int search(E[] array, E item) {
        if (array != null) return recBinarySearch(array, (Integer) item, 0, array.length - 1);
        return -1;
    }

    /**
     * Separate method to be able to call recursively
     *
     * @param array     Array to be search for the provided item
     * @param beginning Beginning index of the array
     * @param ending    Ending index of the array
     * @param item      Item to be found
     * @param <E>       Type of data searched must implement Comparable interface.
     * @return The index of the provided item in the array.
     */
    private <E extends Comparable> int recBinarySearch(E[] array, Integer item, Integer beginning, Integer ending) {
        if (array != null && ending >= beginning && array.length > 0 && item != null) {
            Integer middle = (beginning + ending) / 2;
            if ((int) array[middle] == item) return middle;

            if ((int) array[middle] > item) return recBinarySearch(array, item, beginning, middle - 1);

            return recBinarySearch(array, item, middle + 1, ending);
        }
        return -1;
    }
}
