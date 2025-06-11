import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> map = new TreeMap<>();
        String lowercaseLetters = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < lowercaseLetters.length(); i++) {
            map.put(lowercaseLetters.charAt(i), i + 1);
        }
        return map;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> squaresMap = new TreeMap<>();

        for (Integer n: nums) {
            squaresMap.put(n, n * n);
        }
        return squaresMap;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> countWordsMap = new TreeMap<>();

        for (String word: words) {
            if (countWordsMap.containsKey(word)) {
                countWordsMap.put(word, countWordsMap.get(word) + 1);
            } else {
                countWordsMap.put(word, 1);
            }

        }
        return countWordsMap;
    }
}
