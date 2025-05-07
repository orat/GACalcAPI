package de.orat.math.gacalc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Generate Cayley-tables.
 *
 * Compare with https://github.com/wandelbotsgmbh/geometricalgebra/blob/main/geometricalgebra/cayley.py
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CayleyTableGenerator {

    private record Signature(int p, int q, int r) {

    }

    ;
    
    /**
     * Return the Kendall tau rank distance between two sequences.
     * 
     * The Kendall tau rank distance is a metric (distance function) that counts 
     * the number of pairwise disagreements between two ranking lists. The larger 
     * the distance, the more dissimilar the two lists are. Kendall tau distance 
     * is also called bubble-sort distance since it is equivalent to the number 
     * of swaps that the bubble sort algorithm would take to place one list in 
     * the same order as the other list.<p>
     *
     * Code following:<p>
     * https://en.wikipedia.org/wiki/Kendall_tau_distance<p>
     *
     * @param s first sequence
     * @param t second sequence with the same length as s
     * @return the (unnormalized) Kendall tau distance
     * @throws IllegalArgumentException if both sequences do not have same length
     */
    public static int kendallTauRankDistance(int[] s, int[] t) {
        int len = s.length;
        if (s.length != t.length) {
            throw new IllegalArgumentException("Both sequences must have same length");
        }

        int i, j, v = 0;
        boolean a, b;

        for (i = 0; i < len; i++) {
            for (j = i + 1; j < len; j++) {
                a = s[i] < s[j] && t[i] > t[j];
                b = s[i] > s[j] && t[i] < t[j];
                if (a || b) {
                    v++;
                }
            }
        }
        return Math.abs(v);
    }

    /**
     * Return the basis of the exterior algebra.
     *
     * https://codereview.stackexchange.com/questions/125850/all-combinations-of-all-sizes-less-than-n
     *
     * determineBasisBlades(3) =
     * <p>
     * [], [1], [2], [3], [1, 2], [1, 3], [2, 3], [1, 2, 3]
     * <p>
     *
     * @param N the number of basis vectors
     * @return All basis vectors (blades) for the multivector space
     */
    private List<int[]> determineBasisBlades(int N) {
        List<int[]> result = new ArrayList<>();
        result.add(new int[0]);
        for (int n = 1; n <= N; ++n) {
            int[] combination = IntStream.range(0, n).toArray();
            do {
                result.add(combination);
            } while ((combination
                = generateNextCombination(combination, N)) != null);
        }
        return result;
    }

    private static int[] generateNextCombination(int[] combination, int n) {
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

    /**
     * Multiplication of two int[] into an int[][].
     *
     * @param s1
     * @param s2
     * @param signature
     * @param result the result tuple of multiplied input arguments s1 and s2
     * @return sign
     */
    private int multiply(int[] s1, int[] s2, int signature, int[][] result) {//-> Tuple[Tuple[int, ...], int]:

        //merged = s1 + s2;
        /*int[] merged =  IntStream.concat(Arrays.stream(s1), Arrays.stream(s2)).toArray();
        int sign = normalize(merged);
        
        //result = []
        for key, group in groupby(merged):
            a, b = divmod(len(list(group)), 2)
            if b:
                result.append(key)
            sign = sign * signature[key - 1] ** a
        return tuple(result), sign*/
        throw new RuntimeException("not yet implemented!");
    }

    /**
     * Sort the given tuple and return +1 (-1) if the number of swaps of adjacent elements was even (odd),
     * respectively.
     *
     * Examples:
     * <p>
     * normalize((1,2,3)) = ((1, 2, 3), 1) normalize((1,3,2)) = ((1, 2, 3), -1) normalize((3,2,1)) = ((1, 2,
     * 3), -1)
     *
     * @return sign
     */
    private int normalize(int[] s) {
        // https://www.google.com/search?channel=fs&client=ubuntu-sn&q=pytho+tuple%28sorted%28s%29%29
        // tuple = immutable sequence type (immutable list)
        // The sorted() method sorts the elements of the given iterable in ascending order and returns it.
        int[] t = Arrays.copyOf(s, s.length);
        Arrays.sort(s);
        //int[] index = tuple(sorted(tuple));
        return (int) Math.pow(-1, kendallTauRankDistance(s, t));
    }

    /**
     * Create Caylay table from signature.
     *
     * Implementation following:
     * <p>
     *
     * https://rjw57.github.io/phd-thesis/rjw-thesis.pdf<p>
     *
     * 1. Sort first component numerically by exchanging neighbours and alternate sign once for each swap. 2.
     * Reverse sort second component numerically by exchanging neighbours and alternate sign once for each
     * swap. 3. If the last digit of the first component and first digit of the second component match, change
     * sign as appropriate to the square of the compo- nents and remove. 4. If there are more pairs to match,
     * goto step 3. 5. Concatenate components and output.
     *
     * @param p number of basis vectors squared to 1
     * @param q number of basis vectors squared to -1
     * @param r number of basis vectors squared to 0
     * @return the Cayley table
     */
    public int[][] CreateCayleyTable(int p, int q, int r) {
        int[] signature = determineSignature(p, q, r);
        List<int[]> basisIndices = determineBasisBlades(p + q + r);
        int n_dim = basisIndices.size();//len(basisIndices)
        //c = np.zeros([n_dim, n_dim, n_dim], dtype=np.int8)
        int[][][] c = new int[n_dim][n_dim][n_dim];
        /*for i in range(n_dim):
            for j in range(n_dim):
                a = basisIndices[i]
                b = basisIndices[j]
                index, sign = multiply(a, b, signature);
                c[i, basisIndices.index(index), j] = sign;
        return c*/
        throw new RuntimeException("not yet implemented!");
    }

    /**
     * Determine the default signature.
     *
     * @param p number of positive dimensions.
     * @param q number of negative dimensions.
     * @param r number of zero dimensions.
     * @return signature
     */
    public int[] determineSignature(int p, int q, int r) {
        int[] result = new int[p + q + r];
        for (int i = 0; i < p; i++) {
            result[i] = 1;
        }
        //TODO
        for (int i = 0; i < q; i++) {
            result[p + i] = 0;
        }
        for (int i = 0; i < r; i++) {
            result[p + q + i] = -1;
        }
        return result;
    }
}
