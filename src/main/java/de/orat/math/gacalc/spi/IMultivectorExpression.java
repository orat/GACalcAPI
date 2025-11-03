package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorExpression;

public interface IMultivectorExpression<EXPR extends IMultivectorExpression<EXPR>>
    extends IMultivector<EXPR> {

    void init(MultivectorExpression.Callback callback);
}
