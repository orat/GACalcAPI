package util.cga;

//import util.CayleyTable;
import de.orat.math.gacalc.util.CayleyTable;
import de.orat.math.gacalc.util.LinearOperators;
import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOperatorMatrixUtils extends LinearOperators {

    private final CayleyTable cayleyTable;
    private static SparseDoubleMatrix reversionOperatorMatrix;
    private static SparseDoubleMatrix involutionOperatorMatrix;
    private static SparseDoubleMatrix conjugationOperatorMatrix;

    public CGAOperatorMatrixUtils(CayleyTable cayleyTable) {
        this.cayleyTable = cayleyTable;
    }

    public SparseDoubleMatrix getScalarMultiplicationOperatorMatrix(double s) {
        // Caching is wrong here!
        // The value "s" is written directly into the matrix by the create method.
        // And thus the returned matrix depends on the actual value of "s".
        return createScalarMultiplicationMatrix(cayleyTable, s);
    }

    public SparseDoubleMatrix getReversionOperatorMatrix() {
        if (reversionOperatorMatrix == null) {
            reversionOperatorMatrix = createReversionOperatorMatrix(cayleyTable);
        }
        return reversionOperatorMatrix;
    }

    public SparseDoubleMatrix getInvoluteOperatorMatrix() {
        if (involutionOperatorMatrix == null) {
            involutionOperatorMatrix = createInvolutionOperatorMatrix(cayleyTable);
        }
        return involutionOperatorMatrix;
    }

    public SparseDoubleMatrix getConjugationOperatorMatrix() {
        if (conjugationOperatorMatrix == null) {
            conjugationOperatorMatrix = createConjugationOperatorMatrix(cayleyTable);
        }
        return conjugationOperatorMatrix;
    }
}
