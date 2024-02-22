package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.ExprGraphFactory.Callback;
//import de.orat.math.gacalc.api.MultivectorSymbolic;
//import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;
import java.util.Random;
//import util.cga.CGAMultivectorSparsity;
//import util.cga.SparseCGAColumnVector;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iExprGraphFactory {

    default void init(Callback callback) {

    }

    String getAlgebra();

    String getName();

    iMultivectorSymbolic createMultivectorSymbolic(String name, /*ColumnVectorSparsity*/ MatrixSparsity sparsity);

    iMultivectorSymbolic createMultivectorSymbolic(String name);

    iMultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleMatrix sparseVector);

    iMultivectorSymbolic createMultivectorSymbolic(String name, int grade);

    /**
     * Create a numeric multivector. Sparsity is created from zero values.
     */
    iMultivectorNumeric createMultivectorNumeric(SparseDoubleMatrix vec);

    iMultivectorNumeric createMultivectorNumeric(double[] values);

    iMultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows);

    
    
    
    
    iFunctionSymbolic createFunctionSymbolic(String name, List<iMultivectorSymbolic> parameters,
            List<iMultivectorSymbolic> returns);

    
    // random multivectors
    
    
    default double[] createRandomMultivector(int basisBladesCount) {
        Random random = new Random();
        return random.doubles(-1, 1).
                limit(basisBladesCount).toArray();
    }
    
    iMultivectorNumeric createRandomMultivectorNumeric();

    double[] createRandomMultivector();
    
    double[] createRandomKVector(int grade);
    
    
    // create constants
    
    SparseDoubleMatrix createBaseVectorOrigin(double scalor);

    SparseDoubleMatrix createBaseVectorInfinity(double scalar);

    SparseDoubleMatrix createBaseVectorX(double scalar);

    SparseDoubleMatrix createBaseVectorY(double scalar);

    SparseDoubleMatrix createBaseVectorZ(double scalar);
    
    SparseDoubleMatrix createScalar(double scalar);
    
    SparseDoubleMatrix createE(double x, double y, double z);

    SparseDoubleMatrix createEpsilonPlus();

    SparseDoubleMatrix createEpsilonMinus();
    
    SparseDoubleMatrix createMinkovskyBiVector();

    SparseDoubleMatrix createEuclideanPseudoscalar();

    SparseDoubleMatrix createPseudoscalar();
    
    SparseDoubleMatrix createBaseVectorInfinityDorst() ;

    SparseDoubleMatrix createBaseVectorOriginDorst();

    SparseDoubleMatrix createBaseVectorInfinityDoran();

    SparseDoubleMatrix createBaseVectorOriginDoran();
    
}
