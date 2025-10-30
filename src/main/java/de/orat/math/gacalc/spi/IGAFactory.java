package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.ExprGraphFactory.Callback;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;
import java.util.Random;

public interface IGAFactory<EXPR extends IMultivectorExpression<EXPR>, VAR extends IMultivectorVariable<EXPR>, VAL extends IMultivectorValue<VAL, EXPR>> {

    default void init(Callback callback) {

    }

    String getAlgebra();

    String getImplementationName();

    public int getBasisBladesCount();

    ILoopService getLoopService();

    IConstantsExpr<EXPR> constantsExpr();

    IConstantsValue<VAL, EXPR> constantsValue();

    VAR createVariable(String name, EXPR from);

    VAR createVariable(String name, MatrixSparsity sparsity);

    VAR createVariableDense(String name);

    VAR createVariableSparse(String name);

    VAR createVariable(String name, int grade);

    VAR createVariable(String name, int[] grades);

    default EXPR createExpr(String name, double scalar) {
        return createValue(scalar).toExpr();
    }

    VAL createValue(SparseDoubleMatrix vec);

    /**
     * Create a numeric multivector. Sparsity is created from zero values.
     */
    VAL createValue(double[] values);

    VAL createValue(double[] nonzeros, int[] rows);

    VAL createValue(double scalar);

    IGAFunction<EXPR, VAL> createFunction(String name, List<? extends VAR> parameters, List<? extends EXPR> returns);

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

    double[] createRandomKVector(int grade);

    VAL createRandomValue();

    // Part of public API in GAFactory
    SparseDoubleMatrix createE(double x, double y, double z);

    SparseDoubleMatrix createBaseVectorOrigin(double scalar);

    SparseDoubleMatrix createBaseVectorInfinity(double scalar);

    // Used to build constants in IConstants
    SparseDoubleMatrix createScalar(double scalar);

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
