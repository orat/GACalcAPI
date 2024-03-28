package util.cga;

import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleColumnVector;
//import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SparseCGAColumnVector extends SparseDoubleColumnVector {

    public SparseCGAColumnVector(CGAMultivectorSparsity sparsity, double[] data) {
        //TODO
        // data.length muss kleiner 32 sein
        // sparsity.col==1, sparsity.rows==32, und die Zahl der nonzeros mut der length von data entsprechen
        //if (data.length != 32) throw new IllegalArgumentException("CGA has 32 elements but the tiven array is of length \""+String.valueOf(data.length)+"!");
        super(sparsity, data);
    }

}
