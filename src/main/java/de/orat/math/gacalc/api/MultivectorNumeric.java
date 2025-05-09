package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iMultivectorNumeric;
import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MultivectorNumeric extends AbstractMultivector<MultivectorNumeric, iMultivectorNumeric> {

    /**
     * To be used by other classes in the package.
     */
    protected static MultivectorNumeric get(iMultivectorNumeric impl) {
        MultivectorNumeric result = new MultivectorNumeric(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    @Override
    protected MultivectorNumeric get_(iMultivectorNumeric impl) {
        return get(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected iMultivectorNumeric getImpl() {
        return super.impl;
    }

    protected MultivectorNumeric(iMultivectorNumeric impl) {
        super(impl);
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
    
    public MultivectorSymbolic toSymbolic() {
        return MultivectorSymbolic.get(impl.toSymbolic());
    }

}
