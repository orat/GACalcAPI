package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IMultivectorExpression;

public class MultivectorSymbolic extends AbstractMultivector<MultivectorSymbolic, IMultivectorExpression> {

    /**
     * To be used by other classes in the package.
     */
    protected static MultivectorSymbolic get(IMultivectorExpression impl) {
        MultivectorSymbolic result = new MultivectorSymbolic(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    @Override
    protected MultivectorSymbolic get_(IMultivectorExpression impl) {
        return get(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected IMultivectorExpression getImpl() {
        return super.impl;
    }

    protected MultivectorSymbolic(IMultivectorExpression impl) {
        super(impl);
    }

    public static final class Callback {

        private final MultivectorSymbolic api;

        private Callback(MultivectorSymbolic api) {
            this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation
    }
}
