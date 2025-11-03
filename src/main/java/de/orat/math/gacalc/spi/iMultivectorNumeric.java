package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorNumeric;
import de.orat.math.gacalc.api.MultivectorNumeric.Callback;
import de.orat.math.gacalc.util.GeometricObject;
import de.orat.math.sparsematrix.SparseDoubleMatrix;

/**
 * Numeric multivector.
 */
public interface iMultivectorNumeric<IMVNumeric extends iMultivectorNumeric<IMVNumeric, IMVSymbolic>, IMVSymbolic extends iMultivectorSymbolic<IMVSymbolic>>
    extends iMultivector<IMVNumeric> {

    void init(Callback callback);

    @Override
    String toString();

    //double[] elements();
    SparseDoubleMatrix elements();

    IMVSymbolic toSymbolic();
    
    GeometricObject decompose(boolean isIPNS);
    
    boolean isNull(double precision);
}
