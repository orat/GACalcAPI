package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorNumeric.Callback;
import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorNumeric {

    void init(Callback callback);

    @Override
    String toString();

    //double[] elements();
    SparseDoubleMatrix elements();

    // Alle benötigten Operatoren aus iMultivectorSymbolic
    // um den EuclideanTypeConverter imlementieren zu können
    //iMultivectorNumeric op(iMultivectorNumeric mv);
    //iMultivectorNumeric add(iMultivectorNumeric mv);
}
