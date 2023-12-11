package util;

import de.orat.math.ga.matrix.utils.CayleyTable;
import de.orat.math.sparsematrix.DenseDoubleMatrix;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import static java.lang.Math.pow;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class OperatorMatrixUtils {
    
    public static SparseDoubleMatrix createReversionOperatorMatrix(CayleyTable cayleyTable){
        int size = cayleyTable.getBladesCount();
        MatrixSparsity sparsity = MatrixSparsity.diagonal(size);
        double[] nonzeros = new double[size];
        for (int i=0;i<size;i++){
            int gradei = cayleyTable.getGrade(i);
            double exp = gradei*(gradei-1)*0.5;
            nonzeros[i] = pow(-1d, exp);
        }
        return new SparseDoubleMatrix(sparsity, nonzeros);
    }
}
