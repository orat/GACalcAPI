package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iMultivectorSymbolic;

public class MultivectorSymbolic extends AbstractMultivector<MultivectorSymbolic, iMultivectorSymbolic> {

    /**
     * To be used by other classes in the package.
     */
    protected static MultivectorSymbolic get(iMultivectorSymbolic impl) {
        MultivectorSymbolic result = new MultivectorSymbolic(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    @Override
    protected MultivectorSymbolic get_(iMultivectorSymbolic impl) {
        return get(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected iMultivectorSymbolic getImpl() {
        return super.impl;
    }

    protected MultivectorSymbolic(iMultivectorSymbolic impl) {
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
