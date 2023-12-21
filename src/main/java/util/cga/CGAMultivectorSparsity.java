package util.cga;

import de.orat.math.sparsematrix.ColumnVectorSparsity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class CGAMultivectorSparsity extends ColumnVectorSparsity {
    
    private static CGACayleyTable cgaCayleyTable = CGACayleyTableGeometricProduct.instance();
    
    public static CGAMultivectorSparsity dense(){
        return new CGAMultivectorSparsity(createDenseRows(cgaCayleyTable.getBladesCount()));
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
    
    public CGAMultivectorSparsity(ColumnVectorSparsity sparsity){
        super(cgaCayleyTable.getBladesCount(), sparsity.getrow());
        if (sparsity.getn_row() != cgaCayleyTable.getBladesCount()){
            throw new IllegalArgumentException("The column vector sparsity argument has not the needed length to reprsent a CGA multivector!");
        }
    }
     
    public CGAMultivectorSparsity intersect(CGAMultivectorSparsity sparsity){
        return new CGAMultivectorSparsity(super.meet(sparsity));
    }
    public int[] getGrades(){
        List<Integer> grades = new ArrayList<>();
        int[] rows = getrow();
        for (int i=0;i<rows.length;i++){
            int grade = CGACayleyTable.getCGAGrade(rows[i]);
            if (!grades.contains(grade)) grades.add(grade);
        }
        return grades.stream().mapToInt(d -> d).toArray();
    }
    
    public int[] getIndizes(int grade){
        List<Integer> result = new ArrayList<>();
        int[] rows = getrow();
        for (int i=0;i<rows.length;i++){
            if (CGACayleyTable.getCGAGrade(i) == grade){
                result.add(i);
            }
        }
        return result.stream().mapToInt(d -> d).toArray();
    }
    
    public static CGAKVectorSparsity createSparsity(int[] nonzeros){
        return new CGAKVectorSparsity(nonzeros);
    }
}
