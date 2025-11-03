package de.orat.math.gacalc.spi;

import java.util.List;

public interface ILoopService<EXPR extends IMultivectorExpression<EXPR>, VAR extends IMultivectorVariable<EXPR>, EXPR_ARRAY extends IMultivectorExpressionArray<EXPR>> {

    // Only to be used in the gacalc implementation. Not part of the gacalc API.
    // Method only needed by LoopService internally.
    IMultivectorExpressionArray<EXPR> toExprArray(List<EXPR> from);

    List<EXPR_ARRAY> map(
        List<VAR> paramsSimple,
        List<VAR> paramsArray,
        List<EXPR> returnsArray,
        List<EXPR> argsSimple,
        List<EXPR_ARRAY> argsArray,
        int iterations);

    public static record AccumArrayListReturn<K, V>(List<K> returnsAccum, List<V> returnsArray) {

    }

    AccumArrayListReturn<EXPR, EXPR_ARRAY> fold(
        List<VAR> paramsAccum,
        List<VAR> paramsSimple,
        List<VAR> paramsArray,
        List<EXPR> returnsAccum,
        List<EXPR> returnsArray,
        List<EXPR> argsAccumInitial,
        List<EXPR> argsSimple,
        List<EXPR_ARRAY> argsArray,
        int iterations);

    AccumArrayListReturn<EXPR_ARRAY, EXPR_ARRAY> mapaccum(
        List<VAR> paramsAccum,
        List<VAR> paramsSimple,
        List<VAR> paramsArray,
        List<EXPR> returnsAccum,
        List<EXPR> returnsArray,
        List<EXPR> argsAccumInitial,
        List<EXPR> argsSimple,
        List<EXPR_ARRAY> argsArray,
        int iterations);
}
