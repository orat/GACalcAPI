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
public interface iExprGraphFactory<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>, IMultivectorPurelySymbolic extends iMultivectorPurelySymbolic<IMultivectorSymbolic>> {

    default void init(Callback callback) {

    }

    String getAlgebra();

    String getName();

    iConstantsSymbolic<IMultivectorSymbolic> constants();

    IMultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, MatrixSparsity sparsity);

    IMultivectorPurelySymbolic createMultivectorPurelySymbolic(String name);

    IMultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, int grade);

    IMultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleMatrix sparseVector);

    default IMultivectorSymbolic createMultivectorSymbolic(String name, double scalar) {
        return createMultivectorSymbolic(name, createScalar(scalar));
    }

    IMultivectorSymbolic createDenseEmptyInstance();

    // brauche ich das wirklich?
    //FIXME
    IMultivectorSymbolic createSparseEmptyInstance();

    /**
     * Create a numeric multivector. Sparsity is created from zero values.
     */
    iMultivectorNumeric createMultivectorNumeric(SparseDoubleMatrix vec);

    iMultivectorNumeric createMultivectorNumeric(double[] values);

    iMultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows);

    iFunctionSymbolic<IMultivectorSymbolic> createFunctionSymbolic(String name, List<IMultivectorPurelySymbolic> parameters, List<IMultivectorSymbolic> returns);

    // random multivectors
    default double[] createRandomMultivector(int basisBladesCount) {
        Random random = new Random();
        return random.doubles(-1, 1).
            limit(basisBladesCount).toArray();
    }

    iMultivectorNumeric createRandomMultivectorNumeric();

    double[] createRandomMultivector();

    double[] createRandomKVector(int grade);

    /**
     * SparseDoubleMatrix to build both numeric and symbolic multivectors.
     */
    // Scalar
    SparseDoubleMatrix createScalar(double scalar);

    // Part of public API in ExprGraphFactory
    SparseDoubleMatrix createE(double x, double y, double z);

    SparseDoubleMatrix createBaseVectorOrigin(double scalar);

    SparseDoubleMatrix createBaseVectorInfinity(double scalar);

    // Used to build constants in iConstantsProvider
    SparseDoubleMatrix createBaseVectorX(double scalar);

    SparseDoubleMatrix createBaseVectorY(double scalar);

    SparseDoubleMatrix createBaseVectorZ(double scalar);

    SparseDoubleMatrix createEpsilonPlus();

    SparseDoubleMatrix createEpsilonMinus();

    SparseDoubleMatrix createMinkovskyBiVector();

    SparseDoubleMatrix createEuclideanPseudoscalar();

    SparseDoubleMatrix createPseudoscalar();

    SparseDoubleMatrix createBaseVectorInfinityDorst();

    SparseDoubleMatrix createBaseVectorOriginDorst();

    SparseDoubleMatrix createBaseVectorInfinityDoran();

    SparseDoubleMatrix createBaseVectorOriginDoran();
}
