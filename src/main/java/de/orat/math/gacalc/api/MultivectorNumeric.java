package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iMultivectorNumeric;
import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MultivectorNumeric {

    protected final iMultivectorNumeric impl;

    protected static MultivectorNumeric get(iMultivectorNumeric impl) {
        MultivectorNumeric result = new MultivectorNumeric(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected MultivectorNumeric(iMultivectorNumeric impl) {
        this.impl = impl;
    }

    public static final class Callback {

        private final MultivectorNumeric api;

        private Callback(MultivectorNumeric api) {
            this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation
    }

    /*public double[] elements() {
        return impl.elements();
    }*/
    public SparseDoubleMatrix elements() {
        return impl.elements();
    }

    @Override
    public String toString() {
        return impl.toString();
    }
}
