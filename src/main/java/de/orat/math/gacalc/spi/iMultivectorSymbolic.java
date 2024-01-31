package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic;
import de.orat.math.gacalc.api.MultivectorSymbolic.Callback;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import util.CayleyTable;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorSymbolic {

    void init(Callback callback);

    @Override
    String toString();

    String getName();

    ColumnVectorSparsity getSparsity();

    // operators
    int grade();
    int[] grades();

    iMultivectorSymbolic gradeSelection(int grade);

    iMultivectorSymbolic gp(iMultivectorSymbolic rhs);
    public iMultivectorSymbolic gp(double s);
  
    iMultivectorSymbolic reverse();

    iMultivectorSymbolic gradeInversion();

    iMultivectorSymbolic pseudoscalar();
    iMultivectorSymbolic inversePseudoscalar();

    /**
     * Dual.
     *
     * Poincare duality operator.
     *
     * @param a
     * @return !a
     */
    default iMultivectorSymbolic dual() {
        return lc(inversePseudoscalar());
    }
    
    // da könnte ich eine default impl zur Verfügung stellen die abhängig von der
    // basis/metric das richtigen Vorzeichen liefert
    iMultivectorSymbolic undual();

    iMultivectorSymbolic generalInverse();
    iMultivectorSymbolic scalorInverse();
    
    /**
     * Conjugate.
     *
     * Clifford Conjugation
     *
     * @param a
     * @return a.Conjugate()
     */
    iMultivectorSymbolic conjugate();

    default iMultivectorSymbolic op(iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = zeroInstance(); // scheint dense zu sein
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                System.out.println("op:grade(a)="+String.valueOf(grades_a[i])+" op:grade(b)="+String.valueOf(grades_b[j]));
                int grade = getCayleyTable().addGrades(grades_a[i], grades_b[j]);
                System.out.println("op:grade(result)="+String.valueOf(grade));
                if (grade >=0){
                    //FIXME beim Ausführen des Tests lande ich hier gar nicht, es werden
                    // also keinerlei Komponenten hinzugefügt
                    System.out.println("op:add(grade == "+String.valueOf(grade)+")");
                    iMultivectorSymbolic res = gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade);
                    // hier sehe ich dass alle result componenten 00 sind
                    //FIXME
                    // mir scheint gradeSelection() liefert keine oder falsche Komponenten?
                    System.out.println("res="+res.toString());
                    result.add(res);
                }
            }
        }
        return result;
    }

    /**
     * Left contraction.
     *
     * @param a
     * @param b
     * @return a | b
     */
    default iMultivectorSymbolic lc (iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = zeroInstance();
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = getCayleyTable().addGrades(grades_a[i], -grades_b[j]);
                if (grade >=0){
                    result.add(gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade));
                }
            }
        }
        return result;
    }
    default iMultivectorSymbolic rc (iMultivectorSymbolic b){
        int[] grades_a = grades();
        int[] grades_b = b.grades();
        iMultivectorSymbolic result = zeroInstance(); // dense
        for (int i=0;i<grades_a.length;i++){
            for (int j=0;j<grades_b.length;j++){
                int grade = getCayleyTable().addGrades(-grades_a[i], grades_b[j]);
                if (grade >=0){
                    result.add(gradeSelection(grades_a[i]).gp(b.gradeSelection(grades_b[j])).gradeSelection(grade));
                }
            }
        }
        return result;
    }


    /**
     * The regressive product. (JOIN)
     *
     * @param a
     * @param b
     * @return a & b
     */
    default iMultivectorSymbolic vee (iMultivectorSymbolic b){
         return dual().op(b.dual()).dual().gp(-1);
    }

    /**
     * Add.
     *
     * Multivector addition
     *
     * @param a
     * @param b
     * @return a + b
     */
    iMultivectorSymbolic add(iMultivectorSymbolic b);

    /**
     * Multivector subtraction.
     *
     * @param a
     * @param b
     * @return a - b
     */
    default iMultivectorSymbolic sub (iMultivectorSymbolic b){
        return add(b.gp(-1d));
    }

    // macht vermutlich nur Sinn für scalars
    //iMultivectorSymbolic mul(iMultivectorSymbolic b);

    /**
     * norm.
     *
     * Calculate the Euclidean norm. (strict positive).
     */
    iMultivectorSymbolic norm();

    /**
     * inorm.
     *
     * Calculate the Ideal norm. (signed)
     */
    iMultivectorSymbolic inorm();

    /**
     * normalized.
     *
     * Returns a normalized (Euclidean) element.
     */
    iMultivectorSymbolic normalized();
  
    public iMultivectorSymbolic zeroInstance();
    
    public iMultivectorSymbolic negate14();
    
    /**
     * Get the Cayley-Table for the geometric product.
     * 
     * @return 
     */
    public CayleyTable getCayleyTable();
}
