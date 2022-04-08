package main.java.utilities;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Generate lists for different situations.
 */
public class ListGenerator {

    /**
     * This function will generate a list of integer.
     *
     * @see IntStream StreamApi
     * @param min the lower bound (inclusive)
     * @param max the upper bound (inclusive)
     * @return a list of integer [min, max]
     */
    public List<Integer> createListFromRange(Integer min, Integer max) {
        return IntStream
                .range(min, max+1)
                .boxed()
                .collect(Collectors.toList());
    }
}
