package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorNumeric.Callback;
import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 * Numeric multivector.
 */
public interface iMultivectorNumeric<IMultivectorNumeric extends iMultivectorNumeric<IMultivectorNumeric, IMultivectorSymbolic>, IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>>
    extends iMultivector<IMultivectorNumeric> {

    void init(Callback callback);

    @Override
    String toString();

    //double[] elements();
    SparseDoubleMatrix elements();

    IMultivectorSymbolic toSymbolic();
}
