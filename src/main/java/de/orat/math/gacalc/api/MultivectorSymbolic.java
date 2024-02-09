package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iMultivectorSymbolic;
import de.orat.math.sparsematrix.ColumnVectorSparsity;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MultivectorSymbolic {

    protected final iMultivectorSymbolic impl;

    protected static MultivectorSymbolic get(iMultivectorSymbolic impl) {
        MultivectorSymbolic result = new MultivectorSymbolic(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected MultivectorSymbolic(iMultivectorSymbolic impl) {
        this.impl = impl;
    }

    public static final class Callback {

        private final MultivectorSymbolic api;

        private Callback(MultivectorSymbolic api) {
                this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation
    }

    //======================================================
    // Base 2-ary operators
    //======================================================
    /**
     * geometric product
     *
     * @param rhs
     * @return this rhs
     */
    public MultivectorSymbolic geometricProduct(MultivectorSymbolic rhs) {
        return get(impl.gp(rhs.impl));
    }
    
    /**
     * outer product (join, span for no common subspace)
     *
     * @param rhs
     * @return this ∧ rhs
     */
    public MultivectorSymbolic outerProduct(MultivectorSymbolic rhs) {
        return get(impl.op(rhs.impl));
    }

    /**
     * addition
     *
     * @param rhs
     * @return this + rhs
     */
    public MultivectorSymbolic addition(MultivectorSymbolic rhs)/* throws Exception */ {
        return get(impl.add(rhs.impl));
    }

    /**
     * subtraction
     *
     * @param rhs
     * @return this - rhs
     */
    public MultivectorSymbolic subtraction(MultivectorSymbolic rhs) {
        return get(impl.sub(rhs.impl));
    }

    /**
     * left contraction
     *
     * @param rhs
     * @return this ⌋ rhs
     */
    public MultivectorSymbolic leftContraction(MultivectorSymbolic rhs) {
        return get(impl.lc(rhs.impl));
    }

    /**
     * right contraction
     *
     * @param rhs
     * @return this ⌊ rhs
     */
    public MultivectorSymbolic rightContraction(MultivectorSymbolic rhs) {
        return get(impl.rc(rhs.impl));
    }

    /**
     * regressive product
     *
     * @param rhs
     * @return this ∨ rhs
     */
    public MultivectorSymbolic regressiveProduct(MultivectorSymbolic rhs) {
        return get(impl.vee(rhs.impl));
    }


    //======================================================
    // Additional 2-ary operators
    //======================================================
    
    /**
     * Division (inverse geometric product).
     * 
     * Based on the generalInverse().
     *
     * @param rhs
     * @return this / rhs
     */
    public MultivectorSymbolic division(MultivectorSymbolic rhs) {
        //return geometricProduct(rhs.generalInverse());
        return get(impl.div(rhs.impl));
    }
    
    /**
     * <pre>
     * inner product
     * <strong>Implemented as {@link #leftContraction left contraction}.</strong>
     * </pre>
     *
     * @param rhs
     * @return rhis ⋅ rhs
     */
    public MultivectorSymbolic innerProduct(MultivectorSymbolic rhs) {
        return get(impl.ip(rhs.impl));
    }

    /**
     * TODO
     * eigenes Operator-Symbol einführen?
     * 
     * @param rhs
     * @return 
     */
    public MultivectorSymbolic scalarProduct(MultivectorSymbolic rhs){
        return get(impl.scp(rhs.impl));
    }
    
    // non linear operators
    
    /**
     * meet (intersection) = largest common subspace
     *
     * @param rhs
     * @return this ∩ rhs
     */
    public MultivectorSymbolic meet(MultivectorSymbolic rhs) {
        throw new UnsupportedOperationException();
    }

    /**
     * join (union) of two subspaces is there smallest superspace = smallest space containing them both
     *
     * @param rhs
     * @return this ∪ rhs
     */
    public MultivectorSymbolic join(MultivectorSymbolic rhs) {
        throw new UnsupportedOperationException();
    }

    /**
     * Exponential to implement rotors.
     *
     * @return exp(this)
     */
    public MultivectorSymbolic exp() {
        return get(impl.exp());
    }
    
    /**
     * square root
     *
     * @return sqrt(this)
     */
    public MultivectorSymbolic sqrt() {
        throw new UnsupportedOperationException();
    }

    /**
     * Arcus tansgens 2 (Converts the coordinates (x,y) to coordinates (r, theta) and returns the angle theta
     * as the couterclockwise angle in radians between -pi and pi of the point (x,y) to the positive x-axis.)
     *
     * TODO
     * läßt sich die Funktion atan2 nicht auch auf beliebige multivectors erweitern?
     * 
     * @param y scalar value
     * @return a scalar atan2(this, rhs)
     * @throws IllegalArgumentException if x, y != scalar
     */
    public MultivectorSymbolic atan2(MultivectorSymbolic y) {
        if (!impl.isScalar()) throw new IllegalArgumentException("The argument x of tang(x,y) is no scalar!");
        if (!y.impl.isScalar()) throw new IllegalArgumentException("The argument y of tang(x,y) is no scalar!");
        return get(impl.atan2(y.impl));
    }
    
    
    //======================================================
    // Base 1-ary operators
    //======================================================
    
    /**
     * negate
     *
     * @return -this
     */
    public MultivectorSymbolic negate() {
        return get(impl.gp(-1));
    }

    /**
     * general inverse
     *
     * @return this⁻¹
     */
    public MultivectorSymbolic generalInverse() {
        return get(impl.generalInverse());
    }
    public MultivectorSymbolic scalarInverse(){
        return get(impl.scalarInverse());
    }
    
    /**
     * TODO
     * eventuell wieder abschaffen und in generalInverse testen ob ein versor
     * vorliegt und dann versorInverse() der Implementierung aufrufen.
     * Dazu muss ich dann eine Methode implementierung um zu testen ob es sich 
     * bei einem multivector um einen Versor handelt.
     * 
     * @return 
     */
    public MultivectorSymbolic versorInverse(){
        return get(impl.versorInverse());
    }
    
    /**
     * Poincare duality operator.
     *
     * @return this*
     */
    public MultivectorSymbolic dual() {
        return get(impl.dual());
    }

    /**
     * Reverse/adjoint: reverse all multiplications (a sign change operation)
     *
     * @return this˜
     */
    public MultivectorSymbolic reverse() {
        return get(impl.reverse());
    }

    /**
     * Clifford conjugate (a sign change operation)
     *
     * @return this †
     */
    public MultivectorSymbolic cliffordConjugate() {
        return get(impl.conjugate());
    }

    
    //======================================================
    // Additional 1-ary operators
    //======================================================
    
    /**
     * undual
     *
     * @return this⁻*
     */
    public MultivectorSymbolic undual() {
        return get(impl.undual());
    }

    /**
     * square
     *
     * @return this²
     */
    public MultivectorSymbolic square() {
        return this.geometricProduct(this);
    }

    /**
     * grade involution/inversion (a sign change operation)
     *
     * @return this^
     */
    public MultivectorSymbolic gradeInversion() {
        return get(impl.gradeInversion());
    }

    //======================================================
    // Composite operators
    //======================================================
    
    /**
     * grade extraction, grade p=0-5 as subscript
     *
     * @param grade ₚ
     * @return {@code <this>ₚ (with ₚ ∈ {₀, ₁, ₂, ₃, ₄, ₅})}
     */
    public MultivectorSymbolic gradeExtraction(int grade) {
        return get(impl.gradeSelection(grade));
    }

    
    //======================================================
    // Built-in functions
    //======================================================
    
    /**
     * Returns a normalized (Euclidean) element.
     *
     * @return normalize(this)
     * @throws java.lang.Exception
     */
    public MultivectorSymbolic normalize() throws Exception {
        return get(impl.normalized());
    }

    /*public double scalarPart(){
        return impl.scalarPart();
    }*/
    
    /**
     * absolute value if this is a scalar
     *
     * Wer braucht das überhaupt? ja
     * 
     * @return abs(this)
     */
    public MultivectorSymbolic abs() {
        throw new UnsupportedOperationException("not yet implemented!");
    }


    /**
     * Negate the signs of the vector- and 4-vector parts of an multivector. 
     * 
     * Usable to implement gerneral inverse.
     *
     * @return negatate14(this)
     */
    public MultivectorSymbolic negate14() {
        return get(impl.negate14());
    }

    //======================================================
    // Other
    //======================================================
    
    // macht vermutlich nur Sinn für scalars
    /*public MultivectorSymbolic mul(MultivectorSymbolic b) throws Exception {
            return get(impl.mul(b.impl));
    }*/

    /**
     * norm.
     *
     * Calculate the Euclidean norm. (strict positive).
     */
    public MultivectorSymbolic norm() throws Exception {
        return get(impl.norm());
    }

    /**
     * inorm.
     *
     * Calculate the Ideal norm. (signed)
     */
    public MultivectorSymbolic inorm() throws Exception {
            return get(impl.inorm());
    }

    //--------------
    
    public ColumnVectorSparsity getSparsity() {
        return impl.getSparsity();
    }

    @Override
    public String toString() {
        return impl.toString();
    }

    public String getName() {
        return impl.getName();
    }
}
