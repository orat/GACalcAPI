package util.cga;

import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SparseCGAKVector extends SparseDoubleMatrix {
    
    public SparseCGAKVector(CGAKVectorSparsity sparsity, double[][] values){
        super(sparsity, values);
    }
    
}
