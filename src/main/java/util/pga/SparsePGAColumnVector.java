package util.pga;

import util.cga.*;
import de.orat.math.sparsematrix.SparseDoubleColumnVector;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class SparsePGAColumnVector extends SparseDoubleColumnVector {

    public SparsePGAColumnVector(PGAMultivectorSparsity sparsity, double[] nonzeros) {
        //TODO
        // data.length muss kleiner 32 sein
        // sparsity.col==1, sparsity.rows==32, und die Zahl der nonzeros mut der length von data entsprechen
        //if (data.length != 32) throw new IllegalArgumentException("CGA has 32 elements but the tiven array is of length \""+String.valueOf(data.length)+"!");
        super(sparsity, nonzeros);
    }

    public static SparsePGAColumnVector createEuclid(double[] nonzeros) {
        return new SparsePGAColumnVector(PGAMultivectorSparsity.euclid(), nonzeros);
    }

    public static SparsePGAColumnVector createScalar(double value) {
        return new SparsePGAColumnVector(PGAMultivectorSparsity.scalar(), new double[]{value});
    }

    public static SparsePGAColumnVector fromValues(double[] values) {
        List<Integer> nonzeroPositions = new ArrayList<>(values.length);
        List<Double> nonzeroValues = new ArrayList<>(values.length);

        for (int i = 0; i < values.length; i++) {
            double value = values[i];
            if (value == 0d) {
                continue;
            }
            nonzeroPositions.add(i);
            nonzeroValues.add(value);
        }

        int[] nonzerosPositionsArray = nonzeroPositions.stream().mapToInt(Integer::intValue).toArray();
        double[] nonzeroValuesArray = nonzeroValues.stream().mapToDouble(Double::doubleValue).toArray();

        var sparsity = new PGAMultivectorSparsity(nonzerosPositionsArray);
        return new SparsePGAColumnVector(sparsity, nonzeroValuesArray);
    }
}
