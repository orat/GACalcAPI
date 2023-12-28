package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic.Callback;
import de.orat.math.sparsematrix.ColumnVectorSparsity;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorSymbolic {
    
    public void init(Callback callback);
    public String toString();
     
    public String getName();
    
    public ColumnVectorSparsity getSparsity();
    
    
    // operators
    
    public int grade();
    public iMultivectorSymbolic gradeSelection(int grade);
    
    public iMultivectorSymbolic gp(iMultivectorSymbolic rhs);
    
    public iMultivectorSymbolic reverse();
    
    public iMultivectorSymbolic gradeInversion();

    public iMultivectorSymbolic pseudoscalar();
    public iMultivectorSymbolic inversePseudoscalar();

    /**
     * Dual.
     *
     * Poincare duality operator.
     *
     * @param a
     * @return !a
     */
    default iMultivectorSymbolic dual(){
        return lc(inversePseudoscalar());
    }


    /**
     * Conjugate.
     *
     * Clifford Conjugation
     *
     * @param a
     * @return a.Conjugate()
     */
    public iMultivectorSymbolic conjugate();

    // implementation works only for k-vectors
    // was tun, wenn die grade-summe größer als max grade/pseuscalar-grade?
    // max grade so oft abziehen bis < maxgrade erreicht wird, add(grade a, grade b)
    // in Cayleytable hinzufügen?
    default iMultivectorSymbolic op(iMultivectorSymbolic b){
        return gp(b).gradeSelection(grade()+b.grade());
    }
    
    /**
     * left contraction.
     *
     * @param a
     * @param b
     * @return a | b
     */
    default iMultivectorSymbolic lc (iMultivectorSymbolic b){
        return gp(b).gradeSelection(b.grade()-grade());
    }
    default iMultivectorSymbolic rc (iMultivectorSymbolic b){
        return gp(b).gradeSelection(grade()-b.grade());
    }

    /**
     * Vee.
     *
     * The regressive product. (JOIN)
     *
     * @param a
     * @param b
     * @return a & b
     */
    public iMultivectorSymbolic vee (iMultivectorSymbolic b);


    /**
     * Add.
     *
     * Multivector addition
     *
     * @param a
     * @param b
     * @return a + b
     */
    public iMultivectorSymbolic add (iMultivectorSymbolic b);

    /**
     * Sub.
     *
     * Multivector subtraction
     *
     * @param a
     * @param b
     * @return a - b
     */
    public iMultivectorSymbolic sub (iMultivectorSymbolic b);

    // macht vermutlich nur Sinn für scalars
    public iMultivectorSymbolic mul(iMultivectorSymbolic b);
    
    /**
     * norm.
     *
     * Calculate the Euclidean norm. (strict positive).
     */
    public iMultivectorSymbolic norm();

    /**
     * inorm.
     *
     * Calculate the Ideal norm. (signed)
     */
    public iMultivectorSymbolic inorm();

    /**
     * normalized.
     *
     * Returns a normalized (Euclidean) element.
     */
    public iMultivectorSymbolic normalized();
}
