package util;

import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class LinearOperators {
    
    public static SparseDoubleMatrix createReversionOperatorMatrix(CayleyTable cayleyTable){
        int size = cayleyTable.getBladesCount();
        MatrixSparsity sparsity = MatrixSparsity.diagonal(size);
        double[] nonzeros = new double[size];
        for (int i=0;i<size;i++){
            int gradei = cayleyTable.getGrade(i);
            double exp = gradei*(gradei-1)*0.5;
            nonzeros[i] = pow(-1d, exp);
        }
        return new SparseDoubleMatrix(sparsity, nonzeros);
    }
    
    public static SparseDoubleMatrix createGradeSelectionOperatorMatrix(CayleyTable cayleyTable, int grade){
        int size = cayleyTable.getBladesCount();
        double[] values = new double[size];
        for (int i=0;i<size;i++){
            int gradei = cayleyTable.getGrade(i);
            if (gradei == grade){
                values[i] = 1;
            }
        }
        MatrixSparsity sparsity = MatrixSparsity.diagonal(values);
        return new SparseDoubleMatrix(sparsity, nonzeros(values));
    }
    private static double[] nonzeros(double[] values){
        List<Double> nonzeros = new ArrayList<>();
        for (int i=0;i<values.length;i++){
            if (values[i] != 0){
                nonzeros.add(values[i]);
            }
        }
        return nonzeros.stream().mapToDouble(d -> d).toArray();
    }
    
    /**
     * Versuch einer Portierung aquivalenten ganja.js codes. 
     * TODO
     * - eine matrix erzeugen
     * - tests
     * 
     * @param values
     * @param cayleyTable
     * @return 
     */
    // get Reverse (){ var res = new this.constructor(); for (var i=0; i<this.length; i++) res[i]= this[i]*[1,1,-1,-1][grades[i]%4]; return res; };
    public static double[] reverse(double[] values, CayleyTable cayleyTable){
        double[] result = new double[values.length];
        double[] pattern = new double[]{1,1,-1,-1};
        for (int i=0; i<values.length; i++){
            result[i]= values[i]*pattern[cayleyTable.getGrade(i)%4]; 
        }
        return result; 
    }
    
    
    //  get Dual (){ if (r) return this.map((x,i,a)=>a[drm[i]]*drms[i]); var res = new this.constructor(); res[res.length-1]=1; return res.Mul(this); };
     
    /*public static double[] dual(double[] values){
        double[] res = new double[values.length];
        if (r) return this.map((x,i,a)=>a[drm[i]]*drms[i]); 
        var res = new this.constructor(); 
        res[res.length-1]=1; 
        return res.Mul(this); 
    }*/
}
