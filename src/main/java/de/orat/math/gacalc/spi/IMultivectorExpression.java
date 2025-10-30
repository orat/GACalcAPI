package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic;

public interface IMultivectorExpression<EXPR extends IMultivectorExpression<EXPR>>
    extends IMultivector<EXPR> {

    void init(MultivectorSymbolic.Callback callback);
}
