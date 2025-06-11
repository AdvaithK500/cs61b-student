/**
 * Exercise sourced from Practice-It by the University of Washington.
 * Original problems available at: https://practiceit.cs.washington.edu/
 *
 * @author Erik Kizior
 */

// TODO: What is the output of the following program?
public class NumberTotal {
    public static void main(String[] args) {
        int total = 25;
        for (int number = 1; number <= (total / 2); number++) {
            total = total - number;
            System.out.println(total + " " + number);
        }
    }
}

/* Before running the code, type your answer below.

TODO: Write output here
12 times we do total -= number where number = 1,2,...12
each time we print total number
so the output is:
24 1 ( number <= 12 )
22 2 (number <= 11 )
19 3 ( number <= 8 )
and so on..
15 4 (number <= 7 )
10 5 ( number <= 5)
after this the for condition becomes false so we stop here

Then, click the green play button to check your work. */