package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IMultivectorExpression;

public class MultivectorExpression extends AbstractMultivector<MultivectorExpression, IMultivectorExpression> {

    /**
     * To be used by other classes in the package.
     */
    protected static MultivectorExpression get(IMultivectorExpression impl) {
        MultivectorExpression result = new MultivectorExpression(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    @Override
    protected MultivectorExpression get_(IMultivectorExpression impl) {
        return get(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected IMultivectorExpression getImpl() {
        return super.impl;
    }

    protected MultivectorExpression(IMultivectorExpression impl) {
        super(impl);
    }

    public static final class Callback {

        private final MultivectorExpression api;

        private Callback(MultivectorExpression api) {
            this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation
    }
}
