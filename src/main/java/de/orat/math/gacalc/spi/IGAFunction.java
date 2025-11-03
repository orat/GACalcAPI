package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.GAFunction.Callback;
import java.util.List;

public interface IGAFunction<EXPR extends IMultivectorExpression<EXPR>, VAL extends IMultivectorValue<VAL, EXPR>> {

    void init(Callback callback);

    String getName();

    int getArity();

    int getResultCount();

    List<EXPR> callExpr(List<? extends EXPR> arguments);

    List<VAL> callValue(List<? extends VAL> arguments);
}
