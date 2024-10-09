package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorNumeric.Callback;
import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorNumeric<IMultivectorNumeric extends iMultivectorNumeric<IMultivectorNumeric, IMultivectorSymbolic>, IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>>
    extends iMultivectorSymbolic<IMultivectorNumeric> {

    void init(Callback callback);

    @Override
    String toString();

    //double[] elements();
    SparseDoubleMatrix elements();

    IMultivectorSymbolic toSymbolic();

    // All operators are from iMultivectorSymbolic.
}
