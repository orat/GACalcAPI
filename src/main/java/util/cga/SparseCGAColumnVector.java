package util.cga;

import de.orat.math.sparsematrix.SparseDoubleColumnVector;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SparseCGAColumnVector extends SparseDoubleColumnVector {
    
    public SparseCGAColumnVector(CGAMultivectorSparsity sparsity, double[] data) {
        //if (data.length != 32) throw new IllegalArgumentException("CGA has 32 elements but the tiven array is of length \""+String.valueOf(data.length)+"!");
        super(sparsity, data);
    }
    public SparseCGAColumnVector(SparseDoubleColumnVector mv){
        super(mv.getSparsity(), mv.nonzeros());
    }
}
