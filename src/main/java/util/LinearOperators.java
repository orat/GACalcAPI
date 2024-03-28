package util;

import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import de.orat.math.sparsematrix.iDoubleMatrix;
import static java.lang.Math.pow;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class LinearOperators {

    // negate14
    public static SparseDoubleMatrix createNegate14MultiplicationMatrix(CayleyTable cayleyTable) {
        int size = cayleyTable.getBladesCount();
        MatrixSparsity sparsity = MatrixSparsity.diagonal(size);
        double[] nonzeros = new double[size];
        for (int i = 0; i < size; i++) {
            nonzeros[i] = 1d;
            int grade = cayleyTable.getGrade(i);
            if (grade == 1 || grade == 4) {
                nonzeros[i] *= -1;
            }
        }
        return new SparseDoubleMatrix(sparsity, nonzeros);
    }

    // scalar multiplication
    public static SparseDoubleMatrix createScalarMultiplicationMatrix(CayleyTable cayleyTable, double s) {
        int size = cayleyTable.getBladesCount();
        MatrixSparsity sparsity = MatrixSparsity.diagonal(size);
        double[] nonzeros = new double[size];
        for (int i = 0; i < size; i++) {
            nonzeros[i] = s;
        }
        return new SparseDoubleMatrix(sparsity, nonzeros);
    }

    // reversion
    public static SparseDoubleMatrix createReversionOperatorMatrix(CayleyTable cayleyTable) {
        int size = cayleyTable.getBladesCount();
        MatrixSparsity sparsity = MatrixSparsity.diagonal(size);
        double[] nonzeros = new double[size];
        for (int i = 0; i < size; i++) {
            int gradei = cayleyTable.getGrade(i);
            double exp = gradei * (gradei - 1) * 0.5;
            nonzeros[i] = pow(-1d, exp);
        }
        return new SparseDoubleMatrix(sparsity, nonzeros);
    }

    // clifford conjugation
    public static SparseDoubleMatrix createConjugationOperatorMatrix(CayleyTable cayleyTable) {
        int size = cayleyTable.getBladesCount();
        MatrixSparsity sparsity = MatrixSparsity.diagonal(size);
        double[] nonzeros = new double[size];
        for (int i = 0; i < size; i++) {
            int gradei = cayleyTable.getGrade(i);
            double exp = gradei * (gradei + 1) * 0.5;
            nonzeros[i] = pow(-1d, exp);
        }
        return new SparseDoubleMatrix(sparsity, nonzeros);
    }

    // grade involution
    public static SparseDoubleMatrix createInvolutionOperatorMatrix(CayleyTable cayleyTable) {
        int size = cayleyTable.getBladesCount();
        MatrixSparsity sparsity = MatrixSparsity.diagonal(size);
        double[] nonzeros = new double[size];
        for (int i = 0; i < size; i++) {
            int gradei = cayleyTable.getGrade(i);
            nonzeros[i] = pow(-1d, gradei);
        }
        return new SparseDoubleMatrix(sparsity, nonzeros);
    }

    // grade selection
    public static SparseDoubleMatrix createGradeSelectionOperatorMatrix(CayleyTable cayleyTable, int grade) {
        int size = cayleyTable.getBladesCount();
        double[] values = new double[size];
        for (int i = 0; i < size; i++) {
            int gradei = cayleyTable.getGrade(i);
            if (gradei == grade) {
                values[i] = 1;
            }
        }
        MatrixSparsity sparsity = MatrixSparsity.diagonal(values);
        return new SparseDoubleMatrix(sparsity, nonzeros(values));
    }

    private static double[] nonzeros(double[] values) {
        List<Double> nonzeros = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            if (values[i] != 0) {
                nonzeros.add(values[i]);
            }
        }
        return nonzeros.stream().mapToDouble(d -> d).toArray();
    }

    /**
     * Versuch einer Portierung aquivalenten ganja.js codes. TODO - eine matrix erzeugen - tests
     *
     * @param values
     * @param cayleyTable
     * @return
     */
    // get Reverse (){ var res = new this.constructor(); for (var i=0; i<this.length; i++) res[i]= this[i]*[1,1,-1,-1][grades[i]%4]; return res; };
    public static double[] reverse(double[] values, CayleyTable cayleyTable) {
        double[] result = new double[values.length];
        double[] pattern = new double[]{1, 1, -1, -1};
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i] * pattern[cayleyTable.getGrade(i) % 4];
        }
        return result;
    }

    // cga dual
    public static SparseDoubleMatrix createDualOperatorMatrix(CayleyTable cayleyTable) {
        int size = cayleyTable.getBladesCount();
        MatrixSparsity sparsity = MatrixSparsity.diagonal(size);
        double[] nonzeros = new double[]{
            -1d, -1d, 1d, -1d, 1d, 1d, 1d, -1d, 1d, 1d, 1d, -1d, -1d, 1d, 1d, -1d, 1d, -1d, -1d, 1d, 1d,
            -1d, -1d, -1d, 1d, -1d, -1d, -1d, 1d, -1d, 1d, 1d};
        iDoubleMatrix result = new SparseDoubleMatrix(sparsity, nonzeros).transpose();
        //TODO in eine sparse matrix verwandeln
        // wie kann ich das unabhängig von cga formulieren für beliebige algebren?
        throw new UnsupportedOperationException("not yet implemented!");
        //TODO
        // Umwandlung in eine Sparsematrix?

        /*res[0]=-values[31];
	res[1]=-values[30];
	res[2]=values[29];
	res[3]=-values[28];
	res[4]=values[27];
	res[5]=values[26];
	res[6]=values[25];
	res[7]=-values[24];
	res[8]=values[23];
	res[9]=values[22];
	res[10]=values[21];
	res[11]=-values[20];
	res[12]=-values[19];
        
	res[13]=values[18];
	res[14]=values[17];
        
	res[15]=-values[16];
        
	res[16]=values[15];
        
	res[17]=-values[14];
	res[18]=-values[13];
        
	res[19]=values[12];
	res[20]=values[11];
        
	res[21]=-values[10];
	res[22]=-values[9];
	res[23]=-values[8];
        
	res[24]=values[7];
        
	res[25]=-values[6];
	res[26]=-values[5];
	res[27]=-values[4];
        
	res[28]=values[3];
        
	res[29]=-values[2];
        
	res[30]=values[1];
	res[31]=values[0];*/
    }

    //  get Dual (){ if (r) return this.map((x,i,a)=>a[drm[i]]*drms[i]); 
    // var res = new this.constructor(); res[res.length-1]=1; return res.Mul(this); };
    /*public static double[] dual(double[] values){
        double[] res = new double[values.length];
        if (r) return this.map((x,i,a)=>a[drm[i]]*drms[i]); 
        var res = new this.constructor(); 
        res[res.length-1]=1; 
        return res.Mul(this); 
    }*/
}
