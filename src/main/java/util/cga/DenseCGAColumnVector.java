package util.cga;

import de.orat.math.gacalc.api.MultivectorNumeric.Callback;
import de.orat.math.sparsematrix.DenseDoubleColumnVector;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class DenseCGAColumnVector extends DenseDoubleColumnVector {

    private static CGACayleyTable cgaCayleyTable = CGACayleyTableGeometricProduct.instance();

    private Callback callback;

    public DenseCGAColumnVector(double[] data) {
        super(data);
        if (data.length != cgaCayleyTable.getBladesCount()) {
            throw new IllegalArgumentException("Array length of the given data \"" + String.valueOf(data.length)
                + "\" does not correspond to CGA!");
        }
    }

    public DenseCGAColumnVector(double[] nonzeros, int[] rows) {
        super(cgaCayleyTable.getBladesCount(), nonzeros, rows);
    }
}
