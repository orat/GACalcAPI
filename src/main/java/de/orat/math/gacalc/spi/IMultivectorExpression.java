package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorExpression.Callback;
import java.util.List;

public interface IMultivectorExpression<EXPR extends IMultivectorExpression<EXPR, VAR, VAL>, VAR extends IMultivectorVariable<EXPR, VAR, VAL>, VAL extends IMultivectorValue<EXPR, VAR, VAL>>
    extends IMultivector<EXPR> {

    default void init(Callback callback) {

    }

    /**
     * Downcast. Expected to work always. Only the current subtype should be assigned to the generic
     * parameter.
     */
    EXPR asEXPR();

    EXPR simplify(List<? extends VAR> variables);

    String LaTeXify();
}
