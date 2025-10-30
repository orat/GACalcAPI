package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IMultivectorExpression;
import de.orat.math.gacalc.spi.IConstantsExpression;

public class ConstantsExpression extends AbstractConstants<ConstantsExpression, IConstantsExpression, MultivectorExpression, IMultivectorExpression> {

    protected ConstantsExpression(IConstantsExpression impl) {
        super(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected static ConstantsExpression get(IConstantsExpression impl) {
        return new ConstantsExpression(impl);
    }

    @Override
    protected MultivectorExpression get_(IMultivectorExpression impl) {
        return MultivectorExpression.get(impl);
    }
}
