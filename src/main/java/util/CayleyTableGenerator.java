package util;

/**
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
     * Examples: >>> get_basis(3) ((), (1,), (2,), (3,), (1, 2), (1, 3), (2, 3), (1, 2, 3))
     *
     * @param size the number of basis vectors
     * @return All basis vectors for the multivector space
     */
    private int[][] getBasisBlades(int size) { //-> Tuple[Tuple[int, ...], ...]:
        //return tuple(indices for grade in range(size + 1) for indices in list(combinations(range(1, size + 1), grade)))
        throw new RuntimeException("not yet implemented!");
    }

    private int[][] multiply(int s1, int s2, int signature) { //-> Tuple[Tuple[int, ...], int]:

        /* merged = s1 + s2
        normed, sign = _normalize(merged)
        result = []
        for key, group in groupby(normed):
            a, b = divmod(len(list(group)), 2)
            if b:
                result.append(key)
            sign = sign * signature[key - 1] ** a
        return tuple(result), sign*/
        throw new RuntimeException("not yet implemented!");
    }

    /**
     * Return a sorted sequence and +1 (-1) if the number of swaps of adjacent elements was even (odd),
     * respectively
     *
     * Example: >>> _normalize((1,2,3)) ((1, 2, 3), 1) >>> _normalize((1,3,2)) ((1, 2, 3), -1) >>>
     * _normalize((3,2,1)) ((1, 2, 3), -1) """
     */
    private int[][] normalize(/*s: Tuple[int, ...]*/) { //-> Tuple[Tuple[int, ...], int]:

        /* index = tuple(sorted(s))
        sign = (-1) ** _kendall_tau_distance(s, index)
        return index, sign*/
        throw new RuntimeException("not yet implemented!");
    }

    /**
     * Create Caylay table from signature.
     *
     * Implementation following<p>
     * https://rjw57.github.io/phd-thesis/rjw-thesis.pdf<p>
     *
     * @param p number of basis vectors squared to 1
     * @param q number of basis vectors squared to -1
     * @param r number of basis vectors squared to 0
     * @return the Cayley table
     *
     * 1. Sort first component numerically by exchanging neighbours and alter- nate sign once for each swap.
     * 2. Reverse sort second component numerically by exchanging neighbours and alternate sign once for each
     * swap. 3. If the last digit of the first component and first digit of the second com- ponent match,
     * change sign as appropriate to the square of the compo- nents and remove. 4. If there are more pairs to
     * match, goto step 3. 5. Concatenate components and output.
     */
    public int[][] CreateCayleyTable(int p, int q, int r/*signature: Tuple[int, ...]*/) {

        /*basis_indices = get_basis(len(signature))
        n_dim = len(basis_indices)
        c = np.zeros([n_dim, n_dim, n_dim], dtype=np.int8)
        for i in range(n_dim):
            for j in range(n_dim):
                a = basis_indices[i]
                b = basis_indices[j]
                index, sign = _multiply(a, b, signature)
                c[i, basis_indices.index(index), j] = sign
        return c*/
        throw new RuntimeException("not yet implemented!");
    }
}
