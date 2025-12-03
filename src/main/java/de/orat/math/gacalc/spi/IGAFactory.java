package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.GAFactory.Callback;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public interface IGAFactory<EXPR extends IMultivectorExpression<EXPR>, VAR extends IMultivectorVariable<EXPR>, VAL extends IMultivectorValue<VAL, EXPR>> {

    default void init(Callback callback) {

    }

    String getAlgebra();

    String getImplementationName();

    default Optional<Path> getAlgebraLibFile() {
        return Optional.empty();
    }

    default Map<String, EXPR> getConstants() {
        return Collections.emptyMap();
    }

    default Map<String, UnaryOperator<EXPR>> getUnaryBuiltins() {
        var builtins = new HashMap<String, UnaryOperator<EXPR>>();
        builtins.put("abs", IMultivectorExpression::scalarAbs);
        builtins.put("acos", IMultivectorExpression::scalarAcos);
        builtins.put("asin", IMultivectorExpression::scalarAsin);
        builtins.put("atan", IMultivectorExpression::scalarAtan);
        builtins.put("cos", IMultivectorExpression::scalarCos);
        builtins.put("down", IMultivectorExpression::down);
        builtins.put("exp", IMultivectorExpression::exp);
        builtins.put("log", IMultivectorExpression::log);
        builtins.put("normalize", IMultivectorExpression::normalizeBySquaredNorm);
        builtins.put("sign", IMultivectorExpression::scalarSign);
        builtins.put("sin", IMultivectorExpression::scalarSin);
        builtins.put("sqrt", IMultivectorExpression::sqrt);
        builtins.put("tan", IMultivectorExpression::scalarTan);
        builtins.put("up", IMultivectorExpression::up);
        return builtins;
    }

    default Map<String, BinaryOperator<EXPR>> getBinaryBuiltins() {
        var builtins = new HashMap<String, BinaryOperator<EXPR>>();
        builtins.put("atan2", IMultivectorExpression::scalarAtan2);
        builtins.put("dot", IMultivectorExpression::dot);
        builtins.put("ip", IMultivectorExpression::ip);
        builtins.put("scp", IMultivectorExpression::scp);
        return builtins;
    }

    int getBasisBladesCount();

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
