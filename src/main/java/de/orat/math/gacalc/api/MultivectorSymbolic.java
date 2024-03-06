package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iMultivectorSymbolic;
import de.orat.math.sparsematrix.MatrixSparsity;
import java.util.Collections;
//import de.orat.math.sparsematrix.ColumnVectorSparsity;

/**
 * Following:
 * - Leo Dorst, "The inner products of geometric algebra", 2002
 * 
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MultivectorSymbolic {

    protected final iMultivectorSymbolic impl;

    private static ExprGraphFactory fac = GAExprGraphFactoryService.instance().getExprGraphFactory().get();
            
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

        /*public ExprGraphFactory getExprGraphFactory(){
            return fac;
        }*/
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
    private static MultivectorSymbolic half = fac.createScalarLiteral("1/2", 0.5d);
        
    public MultivectorSymbolic commutatorProduct(MultivectorSymbolic rhs){
        return geometricProduct(rhs).subtraction(rhs.geometricProduct(this)).geometricProduct(half);
    }
    /**
     * Outer product (join, span for no common subspace)
     *
     * @param rhs
     * @return this ∧ rhs
     */
    public MultivectorSymbolic outerProduct(MultivectorSymbolic rhs) {
        return get(impl.op(rhs.impl));
    }

    /**
     * Addition of arbitray multivectors (not only blades).
     *
     * @param rhs
     * @return this + rhs
     */
    public MultivectorSymbolic addition(MultivectorSymbolic rhs)/* throws Exception */ {
        return get(impl.add(rhs.impl));
    }

    /**
     * Subtraction of arbitray multivectors (not only blades).
     *
     * @param rhs
     * @return this - rhs
     */
    public MultivectorSymbolic subtraction(MultivectorSymbolic rhs) {
        return get(impl.sub(rhs.impl));
    }

    /**
     * Projection of blades.
     * 
     * Geometric interpretation: Projection of a blade this into rhs to yield a
     * result which lays totally in rhs.<p>
     * 
     * Only usefull if the arguments are blades.<p>
     * 
     * @param rhs
     * @return projection of this into rhs.
     */
    public MultivectorSymbolic projection(MultivectorSymbolic rhs){
        if (impl.grade() == -1) throw new IllegalArgumentException("projection only defined for k-vectors!");
        return leftContraction(rhs.generalInverse()).leftContraction(rhs);
    }
    
    /**
     * Left contraction.
     *
     * Geometric interpretation: The leftcontraction this ⌋ rhs is a sub-blade of
     * rhs of grade = grad(rhs)-grad(this), which is perpendicular to this and
     * linear in both arguments.<p>
     * 
     * @param rhs
     * @return this ⌋ rhs
     */
    public MultivectorSymbolic leftContraction(MultivectorSymbolic rhs) {
        return get(impl.lc(rhs.impl));
    }

    /**
     * Right contraction.
     *
     * @param rhs
     * @return this ⌊ rhs
     */
    public MultivectorSymbolic rightContraction(MultivectorSymbolic rhs) {
        return get(impl.rc(rhs.impl));
    }

    /**
     * Regressive or vee product.
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
     * Inner product.
     * <strong>Implemented as {@link #dotProduct dot product}.</strong>
     * </pre>
     *
     * The inner product does not fit with left/rightContraction. Better use the
     * dot-product.
     * 
     * @param rhs
     * @return rhis ⋅ rhs
     */
    public MultivectorSymbolic innerProduct(MultivectorSymbolic rhs) {
        return get(impl.ip(rhs.impl));
    }
    public MultivectorSymbolic dotProduct(MultivectorSymbolic rhs){
        return get(impl.dot(rhs.impl));
    }

    /**
     * Scalar product.
     * 
     * It is symmetrical and reversible.<p>
     * 
     * TODO<br>
     * eigenes Operator-Symbol einführen?<p>
     * 
     * @param rhs
     * @return 
     */
    public MultivectorSymbolic scalarProduct(MultivectorSymbolic rhs){
        return get(impl.scp(rhs.impl));
    }
    
    
    // non linear operators
    
    /**
     * Meet (intersection) = largest common subspace/devisor (useful only if the arguments are blades):
     *
     * @param rhs
     * @return this ∩ rhs
     */
    public MultivectorSymbolic meet(MultivectorSymbolic rhs) {
        throw new UnsupportedOperationException();
    }

    /**
     * Join (union) of two subspaces is there smallest superspace = 
     * smallest space containing them both, least common multiple (useful only if
     * the arguments are blades).
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
     * @return exp()
     * @throws IllegalArgumentException if this is no bivector
     */
    public MultivectorSymbolic exp() {
        if (impl.grade() != 2) throw new IllegalArgumentException("exp() defined for bivectors only!");
        return get(impl.exp());
    }
    public MultivectorSymbolic sqrt(){
        return get(impl.sqrt());
    }
    public MultivectorSymbolic log(){
        return get(impl.log());
    }
    
    /**
     * Square root.
     *
     * @return scalarSqrt(this)
     */
    public MultivectorSymbolic scalarSqrt() {
        if (!impl.isScalar()) throw new IllegalArgumentException("This is no scalar!");
        return get(impl.scalarSqrt());
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
    public MultivectorSymbolic scalarAtan2(MultivectorSymbolic y) {
        if (!impl.isScalar()) throw new IllegalArgumentException("The argument x of tang(x,y) is no scalar!");
        if (!y.impl.isScalar()) throw new IllegalArgumentException("The argument y of tang(x,y) is no scalar!");
        return get(impl.scalarAtan2(y.impl));
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
        if (!impl.isScalar()) throw new IllegalArgumentException("This is no scalar!");
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
     * Reverse/adjoint: Reverse all multiplications/the order of every product
     * of vectors (a sign change operation).
     *
     * @return this˜
     */
    public MultivectorSymbolic reverse() {
        return get(impl.reverse());
        //return get(impl.getReverseFunction().callSymbolic(Collections.singletonList(
        //        this.impl)).iterator().next());
    }

    /**
     * Clifford conjugate (a sign change operation)
     *
     * @return this † (| in ganja.js?)
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
     * Square.
     * 
     * Square of k-blades are always scalars.
     *
     * TODO
     * dafür sollte ich einen Test schreiben
     * 
     * @return this²
     */
    public MultivectorSymbolic square() {
        return this.geometricProduct(this);
    }

    /**
     * Grade involution/inversion (a sign change operation).
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
        return get(impl.normalize());
    }

    /**
     * Absolute value if this is a scalar.
     *
     * Wer braucht das überhaupt? ja
     * 
     * @return scalarAbs(this)
     */
    public MultivectorSymbolic scalarAbs() {
        if (!impl.isScalar()) throw new IllegalArgumentException("This is no scalar!");
        return get(impl.scalarAbs());
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
    /*public MultivectorSymbolic inorm() throws Exception {
            return get(impl.inorm());
    }*/

    //--------------
    
    public MatrixSparsity/*ColumnVectorSparsity*/ getSparsity() {
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
