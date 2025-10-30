package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IMultivectorVariable;

public class MultivectorVariable extends MultivectorExpression {

    protected static MultivectorVariable get(IMultivectorVariable impl) {
        return new MultivectorVariable(impl);
    }

    protected MultivectorVariable(IMultivectorVariable impl) {
        super(impl);
    }

    @Override
    protected IMultivectorVariable getImpl() {
        return (IMultivectorVariable) super.getImpl();
    }
}
