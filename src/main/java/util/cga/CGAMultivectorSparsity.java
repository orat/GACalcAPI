package util.cga;

import de.orat.math.sparsematrix.ColumnVectorSparsity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMultivectorSparsity extends ColumnVectorSparsity {

    private static CGACayleyTable cgaCayleyTable = CGACayleyTableGeometricProduct.instance();

    public static CGAMultivectorSparsity dense() {
        return new CGAMultivectorSparsity(createDenseRows(cgaCayleyTable.getBladesCount()));
    }

    public static CGAMultivectorSparsity even() {
        return new CGAMultivectorSparsity(CGACayleyTableGeometricProduct.getEvenIndizes());
    }

    public static CGAMultivectorSparsity scalar() {
        return new CGAMultivectorSparsity(new int[]{0});
    }

    public boolean isEven() {
        int[] evenIndizes = CGACayleyTableGeometricProduct.getEvenIndizes();
        int[] row = getrow();
        int rows = row.length;
        if (rows != evenIndizes.length) {
            return false;
        }
        for (int i = 0; i < rows; i++) {
            if (row[i] != evenIndizes[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean isScalar() {
        int[] row = getrow();
        if (row.length != 1) {
            return false;
        }
        if (row[0] != 0) {
            return false;
        }
        return true;
    }

    /**
     * It is allowed to have nonzeros indizes corresponding to different grades.
     *
     * @param nonzeros nonzeros indizes of the nonzeros
     */
    public CGAMultivectorSparsity(int[] nonzeros) {
        super(cgaCayleyTable.getBladesCount(), nonzeros);
    }

    public CGAMultivectorSparsity(double[] values) {
        super(values);
    }

    public CGAMultivectorSparsity(ColumnVectorSparsity sparsity) {
        super(cgaCayleyTable.getBladesCount(), sparsity.getrow());
        if (sparsity.getn_row() != cgaCayleyTable.getBladesCount()) {
            throw new IllegalArgumentException("The column vector sparsity argument has not the needed length to reprsent a CGA multivector!");
        }
    }

    public CGAMultivectorSparsity intersect(CGAMultivectorSparsity sparsity) {
        return new CGAMultivectorSparsity(super.meet(sparsity));
    }

    public int[] getGrades() {
        List<Integer> grades = new ArrayList<>();
        int[] rows = getrow();
        for (int i = 0; i < rows.length; i++) {
            int grade = CGACayleyTable.getCGAGrade(rows[i]);
            if (!grades.contains(grade)) {
                grades.add(grade);
            }
        }
        return grades.stream().mapToInt(d -> d).toArray();
    }

    public int getGrade() {
        int[] grades = getGrades();
        if (grades.length != 1) {
            return -1;
        }
        return grades[0];
    }

    public int[] getIndizes(int grade) {
        List<Integer> result = new ArrayList<>();
        int[] rows = getrow();
        for (int i = 0; i < rows.length; i++) {
            if (CGACayleyTable.getCGAGrade(i) == grade) {
                result.add(i);
            }
        }
        return result.stream().mapToInt(d -> d).toArray();
    }

    public static CGAKVectorSparsity createSparsity(int[] nonzeros) {
        return new CGAKVectorSparsity(nonzeros);
    }
}
