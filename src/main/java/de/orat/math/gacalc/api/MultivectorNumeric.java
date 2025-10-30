package de.orat.math.gacalc.api;

import de.orat.math.sparsematrix.SparseDoubleMatrix;
import de.orat.math.gacalc.spi.IMultivectorValue;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MultivectorNumeric extends AbstractMultivector<MultivectorNumeric, IMultivectorValue> {

    /**
     * To be used by other classes in the package.
     */
    protected static MultivectorNumeric get(IMultivectorValue impl) {
        MultivectorNumeric result = new MultivectorNumeric(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    @Override
    protected MultivectorNumeric get_(IMultivectorValue impl) {
        return get(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected IMultivectorValue getImpl() {
        return super.impl;
    }

    protected MultivectorNumeric(IMultivectorValue impl) {
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
        return MultivectorSymbolic.get(impl.toExpr());
    }

}
