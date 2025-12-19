package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorExpression.Callback;

public interface IMultivectorExpression<EXPR extends IMultivectorExpression<EXPR>>
    extends IMultivector<EXPR> {

    default void init(Callback callback) {

    }
}
