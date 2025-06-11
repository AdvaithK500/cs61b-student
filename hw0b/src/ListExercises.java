import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        int sum = 0;
        for (int num: L) {
            sum += num;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> evensList = new ArrayList<>();

        for (int num : L) {
            if (num % 2 == 0) {
                evensList.add(num);
            }
        }

        return evensList;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> overlap = new ArrayList<>();

        for (int num1 : L1) {
            for (int num2 : L2) {
                if (num1 == num2) {
                    overlap.add(num1);
                }
            }
        }
        return overlap;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int totalC = 0;
        for (String word: words) {
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == c) {
                    totalC += 1;
                }
            }
        }
        return totalC;
    }
}
