package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.ExprGraphFactory.Callback;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;
import java.util.Random;

/**
 * Constructors.
 */
public interface iExprGraphFactory<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>, IMultivectorPurelySymbolic extends iMultivectorPurelySymbolic<IMultivectorSymbolic>, IMultivectorNumeric extends iMultivectorNumeric<IMultivectorNumeric, IMultivectorSymbolic>> {

    default void init(Callback callback) {

    }

    String getAlgebra();

    String getName();

    public int getBasisBladesCount();

    iConstantsFactorySymbolic<IMultivectorSymbolic> constantsSymbolic();

    iConstantsFactoryNumeric<IMultivectorNumeric, IMultivectorSymbolic> constantsNumeric();

    IMultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, MatrixSparsity sparsity);

    IMultivectorPurelySymbolic createMultivectorPurelySymbolicDense(String name);

    IMultivectorPurelySymbolic createMultivectorPurelySymbolicSparse(String name);

    IMultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, int grade);

    IMultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, int[] grades);

    IMultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleMatrix sparseVector);

    default IMultivectorSymbolic createMultivectorSymbolic(String name, double scalar) {
        return createMultivectorSymbolic(name, createScalar(scalar));
    }

    /**
     * Create a numeric multivector. Sparsity is created from zero values.
     */
    IMultivectorNumeric createMultivectorNumeric(SparseDoubleMatrix vec);

    IMultivectorNumeric createMultivectorNumeric(double[] values);

    IMultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows);

    default IMultivectorNumeric createMultivectorNumeric(double scalar) {
        return createMultivectorNumeric(createScalar(scalar));
    }

    iFunctionSymbolic<IMultivectorSymbolic, IMultivectorNumeric> createFunctionSymbolic(String name, List<IMultivectorPurelySymbolic> parameters, List<IMultivectorSymbolic> returns);

    // random multivectors
    default double[] createRandomMultivector(/*int basisBladesCount*/) {

        Random random = new Random();
        return random.doubles(-1, 1).
            limit(getBasisBladesCount()).toArray();
    }

    default double[] createRandomMultivector(int[] indizes) {
        int basisBladesCount = getBasisBladesCount();
        double[] temp = createRandomMultivector(/*basisBladesCount*/);
        double[] result = new double[basisBladesCount];
        for (int i = 0; i < indizes.length; i++) {
            result[indizes[i]] = temp[indizes[i]];
        }
        return result;
    }

    //double[] createRandomMultivector();
    double[] createRandomKVector(int grade);

    IMultivectorNumeric createRandomMultivectorNumeric();

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

    SparseDoubleMatrix createMinkovskiBiVector();

    SparseDoubleMatrix createEuclideanPseudoscalar();

    SparseDoubleMatrix createPseudoscalar();

    SparseDoubleMatrix createBaseVectorInfinityDorst();

    SparseDoubleMatrix createBaseVectorOriginDorst();

    SparseDoubleMatrix createBaseVectorInfinityDoran();

    SparseDoubleMatrix createBaseVectorOriginDoran();
}
