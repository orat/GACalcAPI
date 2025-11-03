package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.GAFactory.Callback;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;

public interface IGAFactory<EXPR extends IMultivectorExpression<EXPR>, VAR extends IMultivectorVariable<EXPR>, VAL extends IMultivectorValue<VAL, EXPR>> {

    default void init(Callback callback) {

    }

    String getAlgebra();

    String getImplementationName();

    public int getBasisBladesCount();

    ILoopService getLoopService();

    IConstantsExpression<EXPR> constantsExpr();

    IConstantsValue<VAL, EXPR> constantsValue();

    VAR createVariable(String name, EXPR from);

    VAR createVariable(String name, MatrixSparsity sparsity);

    VAR createVariableDense(String name);

    VAR createVariableSparse(String name);

    VAR createVariable(String name, int grade);

    VAR createVariable(String name, int[] grades);

    default EXPR createExpr(double scalar) {
        return createValue(scalar).toExpr();
    }

    VAL createValue(SparseDoubleMatrix vec);

    VAL createValue(double scalar);

    IGAFunction<EXPR, VAL> createFunction(String name, List<? extends VAR> parameters, List<? extends EXPR> returns);

    // random multivectors
    VAL createValueRandom();

    VAL createValueRandom(int[] grades);

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
