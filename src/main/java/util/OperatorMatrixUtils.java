package util;

import de.orat.math.ga.matrix.utils.CayleyTable;
import de.orat.math.sparsematrix.DenseDoubleMatrix;
import static java.lang.Math.pow;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class OperatorMatrixUtils {
    
    public static DenseDoubleMatrix createReversionOperatorMatrix(CayleyTable cayleyTable){
        int size = cayleyTable.getBladesCount();
        DenseDoubleMatrix result = new DenseDoubleMatrix(size, size);
        for (int i=0;i<size;i++){
            int gradei = cayleyTable.getGrade(i);
            double exp = gradei*(gradei-1)*0.5;
            result.setValue(i,i, pow(-1d, exp));
        }
        return result;
    }
}
