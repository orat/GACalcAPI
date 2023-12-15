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
    
    public iMultivectorSymbolic gp(iMultivectorSymbolic rhs);
    
    public iMultivectorSymbolic reverse();


    /**
     * Dual.
     *
     * Poincare duality operator.
     *
     * @param a
     * @return !a
     */
    public iMultivectorSymbolic dual();


    /**
     * Conjugate.
     *
     * Clifford Conjugation
     *
     * @param a
     * @return a.Conjugate()
     */
    public iMultivectorSymbolic conjugate();


    /**
     * Involute.
     *
     * Main involution
     *
     * @param a
     * @return a.Involute()
     */
    public iMultivectorSymbolic involute();
    
    public iMultivectorSymbolic op(iMultivectorSymbolic b);

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
     * Dot.
     *
     * The inner product.
     *
     * @param a
     * @param b
     * @return a | b
     */
    public iMultivectorSymbolic dot (iMultivectorSymbolic b);
    
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
