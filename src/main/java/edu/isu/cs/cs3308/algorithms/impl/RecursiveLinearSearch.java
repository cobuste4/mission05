package edu.isu.cs.cs3308.algorithms.impl;

import edu.isu.cs.cs3308.algorithms.ArraySearch;

/**
 * Performs a recursive linear search
 *
 * @author Steve Coburn
 */
public class RecursiveLinearSearch implements ArraySearch {
    @Override
    public <E extends Comparable> int search(E[] array, E item) {
        return recLinearSearch(array, (Integer) item, 0);
    }


    /**
     * Separate method to be able to call recursively
     *
     * @param array Array to be search for the provided item
     * @param item  Item to be found
     * @param index The current index of the array, starting at zero
     * @param <E>   Type of data searched must implement Comparable interface.
     * @return The index of the provided item in the array.
     */
    private <E extends Comparable> int recLinearSearch(E[] array, Integer item, Integer index) {
        if (array != null && index < array.length && array.length > 0 && item != null) {
            if (array[index] == item) return index;
            return recLinearSearch(array, item, index + 1);
        }
        return -1;
    }
}
