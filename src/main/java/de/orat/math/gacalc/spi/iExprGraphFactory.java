package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.ExprGraphFactory.Callback;
import de.orat.math.gacalc.api.MultivectorSymbolic;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.SparseDoubleColumnVector;
import java.util.ArrayList;
import java.util.List;
import org.jogamp.vecmath.Tuple3d;
import util.cga.CGAMultivectorSparsity;
import util.cga.SparseCGAColumnVector;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iExprGraphFactory {

    default void init(Callback callback) {

    }

    String getAlgebra();

    String getName();

    iMultivectorSymbolic createMultivectorSymbolic(String name, ColumnVectorSparsity sparsity);

    iMultivectorSymbolic createMultivectorSymbolic(String name);

    iMultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleColumnVector sparseVector);

    iMultivectorSymbolic createMultivectorSymbolic(String name, int grade);

    /**
     * Create a numeric multivector. Sparsity is created from zero values.
     */
    iMultivectorNumeric createMultivectorNumeric(SparseDoubleColumnVector vec);

    iMultivectorNumeric createMultivectorNumeric(double[] values);

    iMultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows);

    iMultivectorNumeric createRandomMultivectorNumeric();

    iFunctionSymbolic createFunctionSymbolic(String name, List<iMultivectorSymbolic> parameters,
            List<iMultivectorSymbolic> returns);

    double[] createRandomCGAMultivector();
    
    
    // create constants
    
    SparseDoubleColumnVector createBaseVectorOrigin(double scalor);

    SparseDoubleColumnVector createBaseVectorInfinity(double scalar);

    SparseDoubleColumnVector createBaseVectorX(double scalar);

    SparseDoubleColumnVector createBaseVectorY(double scalar);

    SparseDoubleColumnVector createBaseVectorZ(double scalar);
    
    SparseDoubleColumnVector createScalar(double scalar);
    
    SparseDoubleColumnVector createE(Tuple3d tuple3d);

    SparseDoubleColumnVector createEpsilonPlus();

    SparseDoubleColumnVector createEpsilonMinus();
    
    SparseDoubleColumnVector createMinkovskyBiVector();

    SparseDoubleColumnVector createEuclideanPseudoscalar();

    SparseDoubleColumnVector createPseudoscalar();
    
    //iEuclideanTypeConverter getEuclideanTypeConverter();
    
}
