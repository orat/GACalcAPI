package util.cga;

import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 * @Deprecated und wenn ich das doch brauchen sollte, dann solle das von
 * SparseCGAColumnVector erben
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SparseCGAKVector extends SparseDoubleMatrix {

    public SparseCGAKVector(CGAKVectorSparsity sparsity, double[][] values) {
        super(sparsity, values);
    }

}
