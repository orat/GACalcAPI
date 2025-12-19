package de.orat.math.gacalc.api;

import de.orat.math.sparsematrix.SparseDoubleMatrix;
import de.orat.math.gacalc.spi.IMultivectorValue;
import de.orat.math.gacalc.util.GeometricObject;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MultivectorValue extends AbstractMultivector<MultivectorValue, IMultivectorValue> {

    /**
     * To be used by other classes in the package.
     */
    protected static MultivectorValue get(IMultivectorValue impl) {
        MultivectorValue result = new MultivectorValue(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    @Override
    protected MultivectorValue get_(IMultivectorValue impl) {
        return get(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected IMultivectorValue getImpl() {
        return super.impl;
    }

    protected MultivectorValue(IMultivectorValue impl) {
        super(impl);
    }

    public static final class Callback {

        private final MultivectorValue api;

        private Callback(MultivectorValue api) {
            this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation
    }

    public SparseDoubleMatrix elements() {
        return impl.elements();
    }

    public MultivectorExpression toExpr() {
        return MultivectorExpression.get(impl.toExpr());
    }

    public GeometricObject decompose(boolean isIPNS){
        return impl.decompose(isIPNS);
    }
    public boolean isNull(){
        return impl.getSparsity().isNull();
    }
    public boolean isNull(double precision){
        return impl.isNull(precision);
    }

    @Override
    public String toString() {
        return impl.toString();
    }
}
