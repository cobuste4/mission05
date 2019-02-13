package edu.isu.cs.cs3308.algorithms.impl;

import edu.isu.cs.cs3308.algorithms.ArraySearch;

/**
 * Performs an iterative binary search
 *
 * @author Steve Coburn
 */
public class BinarySearch implements ArraySearch {
    @Override
    public <E extends Comparable> int search(E[] array, E item) {
        if (array != null && array.length > 0 && item != null) {
            int beginning = 0;
            int ending = array.length - 1;

            while (beginning <= ending) {
                int middle = (beginning + ending) / 2;

                if (array[middle] == item) return middle;

                else if ((Integer)item < (Integer)array[middle]) ending = middle - 1;

                else beginning = middle + 1;
            }
        }
        return -1;
    }
}
