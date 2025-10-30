package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IMultivectorVariable;

public class MultivectorPurelySymbolic extends MultivectorSymbolic {

    protected static MultivectorPurelySymbolic get(IMultivectorVariable impl) {
        return new MultivectorPurelySymbolic(impl);
    }

    protected MultivectorPurelySymbolic(IMultivectorVariable impl) {
        super(impl);
    }

    @Override
    protected IMultivectorVariable getImpl() {
        return (IMultivectorVariable) super.getImpl();
    }
}
