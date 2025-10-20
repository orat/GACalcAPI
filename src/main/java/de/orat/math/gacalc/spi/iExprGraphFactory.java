package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.ExprGraphFactory.Callback;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;
import java.util.Random;

/**
 * Constructors.
 */
public interface iExprGraphFactory<IMVSymbolic extends iMultivectorSymbolic<IMVSymbolic>, IMVPurelySymbolic extends iMultivectorPurelySymbolic<IMVSymbolic>, IMVNumeric extends iMultivectorNumeric<IMVNumeric, IMVSymbolic>> {

    default void init(Callback callback) {

    }

    String getAlgebra();

    String getImplementationName();

    public int getBasisBladesCount();

    iLoopService getLoopService();

    iConstantsFactorySymbolic<IMVSymbolic> constantsSymbolic();

    iConstantsFactoryNumeric<IMVNumeric, IMVSymbolic> constantsNumeric();

    IMVPurelySymbolic createMultivectorPurelySymbolicFrom(String name, IMVSymbolic from);

    IMVPurelySymbolic createMultivectorPurelySymbolic(String name, MatrixSparsity sparsity);

    IMVPurelySymbolic createMultivectorPurelySymbolicDense(String name);

    IMVPurelySymbolic createMultivectorPurelySymbolicSparse(String name);

    IMVPurelySymbolic createMultivectorPurelySymbolic(String name, int grade);

    IMVPurelySymbolic createMultivectorPurelySymbolic(String name, int[] grades);

    IMVSymbolic createMultivectorSymbolic(String name, SparseDoubleMatrix sparseVector);

    default IMVSymbolic createMultivectorSymbolic(String name, double scalar) {
        return createMultivectorSymbolic(name, createScalar(scalar));
    }

    /**
     * Create a numeric multivector. Sparsity is created from zero values.
     */
    IMVNumeric createMultivectorNumeric(SparseDoubleMatrix vec);

    IMVNumeric createMultivectorNumeric(double[] values);

    IMVNumeric createMultivectorNumeric(double[] nonzeros, int[] rows);

    default IMVNumeric createMultivectorNumeric(double scalar) {
        return createMultivectorNumeric(createScalar(scalar));
    }

    iFunctionSymbolic<IMVSymbolic, IMVNumeric> createFunctionSymbolic(String name, List<IMVPurelySymbolic> parameters, List<IMVSymbolic> returns);

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

    IMVNumeric createRandomMultivectorNumeric();

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

    SparseDoubleMatrix createInversePseudoscalar();

    SparseDoubleMatrix createBaseVectorInfinityDorst();

    SparseDoubleMatrix createBaseVectorOriginDorst();

    SparseDoubleMatrix createBaseVectorInfinityDoran();

    SparseDoubleMatrix createBaseVectorOriginDoran();
}
