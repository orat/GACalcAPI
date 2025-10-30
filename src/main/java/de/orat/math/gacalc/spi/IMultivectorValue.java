package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorValue.Callback;
import de.orat.math.sparsematrix.SparseDoubleMatrix;

public interface IMultivectorValue<VAL extends IMultivectorValue<VAL, EXPR>, EXPR extends IMultivectorExpression<EXPR>>
    extends IMultivector<VAL> {

    void init(Callback callback);

    SparseDoubleMatrix elements();

    EXPR toExpr();
}
