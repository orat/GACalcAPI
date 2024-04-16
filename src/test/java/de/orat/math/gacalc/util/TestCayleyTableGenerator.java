package de.orat.math.gacalc.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import util.CayleyTableGenerator;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class TestCayleyTableGenerator {

    private boolean equals(int a, int b) {
        return a == b;
    }

    /**
     * kendallTauRankDistance([1, 2, 3], [1, 3, 2]) = 1
     * <p>
     * kendallTauRankDistance([1, 2, 3], [1, 2, 3]) = 0
     * <p>
     * kendallTauRankDistance([1, 2, 3], [3, 2, 1]) = 3
     * <p>
     */
    @Test
    public void testKendallTauRankDistance() {
        int result = CayleyTableGenerator.kendallTauRankDistance(new int[]{1, 2, 3}, new int[]{1, 3, 2});
        assertTrue(equals(result, 1));
        result = CayleyTableGenerator.kendallTauRankDistance(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        assertTrue(equals(result, 0));
        result = CayleyTableGenerator.kendallTauRankDistance(new int[]{1, 2, 3}, new int[]{3, 2, 1});
        assertTrue(equals(result, 3));
    }

    @Test
    public void testCreateBase() {

        final int N = 3;

        int globalLineNumber = 1;

        for (int n = 1; n <= N; ++n) {
            int[] combination = IntStream.range(0, n).toArray();
            System.out.println("Combination size: " + n);
            int lineNumber = 1;

            do {
                System.out.printf("[%3d] %3d: %s\n",
                    globalLineNumber++,
                    lineNumber++,
                    Arrays.toString(combination));
            } while ((combination
                = generateNextCombination(combination, N)) != null);
        }
    }

    public static int[] generateNextCombination(int[] combination, int n) {
        if (combination[combination.length - 1] < n - 1) {
            combination[combination.length - 1]++;
            return combination;
        }

        for (int i = combination.length - 2; i >= 0; --i) {
            if (combination[i] < combination[i + 1] - 1) {
                combination[i]++;

                for (int j = i + 1; j < combination.length; ++j) {
                    combination[j] = combination[j - 1] + 1;
                }

                return combination;
            }
        }

        return null;
    }
}
