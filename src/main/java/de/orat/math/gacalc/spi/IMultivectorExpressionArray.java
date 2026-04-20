package de.orat.math.gacalc.spi;

import java.util.List;

public interface IMultivectorExpressionArray<EXPR extends IMultivectorExpression<EXPR, VAR, VAL>, VAR extends IMultivectorVariable<EXPR, VAR, VAL>, VAL extends IMultivectorValue<EXPR, VAR, VAL>>
    extends List<EXPR> {
}
