package main.java.utilities;

import java.util.List;
import java.util.Random;

/**
 * Randomly pick something. (Pseudo random)
 */
public class RandomPicker {

    /**
     * This function will randomly pick a string from the givenList and return it.
     *
     * @see RandomPicker#getRandomIndexFromList(List)
     * @see RandomPicker#getItemFromIntList(List)
     * @param givenList a list of string.
     * @return a single string from the given list.
     */
    public String getItemFromStrList(List<String> givenList) {
        return givenList.get(getRandomIndexFromList(givenList));
    }

    /**
     * This function will randomly pick an integer from the givenList and return it.
     *
     * @see RandomPicker#getRandomIndexFromList(List)
     * @see RandomPicker#getItemFromStrList(List)
     * @param givenList a list of integer.
     * @return an integer from the given list.
     */
    public int getItemFromIntList(List<Integer> givenList) {
        return givenList.get(getRandomIndexFromList(givenList));
    }

    /**
     * This function will randomly pick an index from the given list.
     *
     * @see RandomPicker#getRandomIndexFromList(List)
     * @see RandomPicker#getItemFromStrList(List)
     * @param givenList a list of any types.
     * @return an index
     */
    public int getRandomIndexFromList(List<?> givenList) {
        Random rand = new Random();
        return rand.nextInt(givenList.size());
    }
}
