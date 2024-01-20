package util.cga;

//import util.CayleyTable;
import de.orat.math.sparsematrix.DenseDoubleMatrix;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import util.CayleyTable;
import util.LinearOperators;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAOperatorMatrixUtils extends LinearOperators {
    
    private final CayleyTable cayleyTable;
    private static SparseDoubleMatrix scalarMultiplicationOperatorMatrix;
    private static SparseDoubleMatrix reversionOperatorMatrix;
    private static SparseDoubleMatrix involutionOperatorMatrix;
    private static SparseDoubleMatrix conjugationOperatorMatrix;
    
    public CGAOperatorMatrixUtils(CayleyTable cayleyTable){
        this.cayleyTable = cayleyTable;
    }
    
    public SparseDoubleMatrix getScalarMultiplicationOperatorMatrix(double s){
        if (scalarMultiplicationOperatorMatrix == null){
            scalarMultiplicationOperatorMatrix = createScalarMultiplicationMatrix(cayleyTable, s);
        }
        return scalarMultiplicationOperatorMatrix;
    }
    public SparseDoubleMatrix getReversionOperatorMatrix(){
        if (reversionOperatorMatrix == null){
            reversionOperatorMatrix = createReversionOperatorMatrix(cayleyTable);
        }
        return reversionOperatorMatrix;
    }
    
    public SparseDoubleMatrix getInvoluteOperatorMatrix(){
        if (involutionOperatorMatrix == null){
            involutionOperatorMatrix = createInvolutionOperatorMatrix(cayleyTable);
        }
        return involutionOperatorMatrix;
    }
    
    public SparseDoubleMatrix getConjugationOperatorMatrix(){
        if (conjugationOperatorMatrix == null){
            conjugationOperatorMatrix = createConjugationOperatorMatrix(cayleyTable);
        }
        return conjugationOperatorMatrix;
    }
}
