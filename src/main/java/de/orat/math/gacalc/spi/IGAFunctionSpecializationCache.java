package de.orat.math.gacalc.spi;

import java.util.List;
import java.util.function.Function;

/**
 * Backend cache for symbolic function specializations.
 */
public interface IGAFunctionSpecializationCache<
    EXPR extends IMultivectorExpression<EXPR, VAR, VAL>,
    VAR extends IMultivectorVariable<EXPR, VAR, VAL>,
    VAL extends IMultivectorValue<EXPR, VAR, VAL>
> {

    List<EXPR> executeCached(
        List<? extends EXPR> arguments,
        String funcName,
        Function<List<VAR>, List<EXPR>> creator
    );

    void clearCache();

    int getCacheSize();
}
