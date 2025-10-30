package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IConstantsExpr;
import de.orat.math.gacalc.spi.IMultivectorExpression;

public class ConstantsFactorySymbolic extends AbstractConstantsFactory<ConstantsFactorySymbolic, IConstantsExpr, MultivectorSymbolic, IMultivectorExpression> {

    protected ConstantsFactorySymbolic(IConstantsExpr impl) {
        super(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected static ConstantsFactorySymbolic get(IConstantsExpr impl) {
        return new ConstantsFactorySymbolic(impl);
    }

    @Override
    protected MultivectorSymbolic get_(IMultivectorExpression impl) {
        return MultivectorSymbolic.get(impl);
    }
}
