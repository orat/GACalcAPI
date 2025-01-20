package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iMultivector;
import de.orat.math.sparsematrix.MatrixSparsity;

/**
 * Following: - Leo Dorst, "The inner products of geometric algebra", 2002
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
/*
Policy:
Methods here can only be delegates to iMultivector. Don't use this class for default implementations.
- Default implementations can only be cached properly if in iMultivectorSymbolic.
- Only in iMultivectorSymbolic operators can be used by other operators in iMultivector or in the implementations.
- It disturbs overview to mix default implementations between iMultivector and iMultivector.
- Checks need to be within the implementations to accommodate if they are directly used without the MultivectorSymbolic proxy. In tests for example.
 */
// Package-private
abstract class AbstractMultivector<AMV extends AbstractMultivector<AMV, IMultivector>, IMultivector extends iMultivector> {

    /**
     * Only to be used within this class and subclasses.
     */
    protected final IMultivector impl;

    /**
     * Only to be used by subclasses.
     */
    protected AbstractMultivector(IMultivector impl) {
        this.impl = impl;
    }

    /**
     * Only to be used within this class and subclasses.
     */
    protected abstract AMV get_(IMultivector impl);

    //======================================================
    // Base 2-ary operators
    //======================================================
    /**
     * geometric product
     *
     * @param rhs
     * @return this rhs
     */
    public AMV geometricProduct(AMV rhs) {
        return get_((IMultivector) impl.gp(rhs.impl));
    }

    public AMV commutatorProduct(AMV rhs) {
        return get_((IMultivector) impl.commutatorProduct(rhs.impl));
    }

    /**
     * Outer product (join, span for no common subspace)
     *
     * @param rhs
     * @return this ∧ rhs
     */
    public AMV outerProduct(AMV rhs) {
        return get_((IMultivector) impl.op(rhs.impl));
    }

    /**
     * Addition of arbitray multivectors (not only blades).
     *
     * @param rhs
     * @return this + rhs
     */
    public AMV addition(AMV rhs)/* throws Exception */ {
        return get_((IMultivector) impl.add(rhs.impl));
    }

    /**
     * Subtraction of arbitray multivectors (not only blades).
     *
     * @param rhs
     * @return this - rhs
     */
    public AMV subtraction(AMV rhs) {
        return get_((IMultivector) impl.sub(rhs.impl));
    }

    /**
     * Projection of blades.
     *
     * Geometric interpretation: Projection of a blade this into rhs to yield a result which lays totally in
     * rhs.<p>
     *
     * Only usefull if the arguments are blades.<p>
     *
     * @param rhs
     * @return projection of this into rhs.
     */
    public AMV projection(AMV rhs) {
        return get_((IMultivector) impl.projection(rhs.impl));
    }

    /**
     * Left contraction.
     *
     * Geometric interpretation: The leftcontraction this ⌋ rhs is a sub-blade of rhs of grade =
     * grad(rhs)-grad(this), which is perpendicular to this and linear in both arguments.<p>
     *
     * @param rhs
     * @return this ⌋ rhs
     */
    public AMV leftContraction(AMV rhs) {
        return get_((IMultivector) impl.lc(rhs.impl));
    }

    /**
     * Right contraction.
     *
     * @param rhs
     * @return this ⌊ rhs
     */
    public AMV rightContraction(AMV rhs) {
        return get_((IMultivector) impl.rc(rhs.impl));
    }

    /**
     * Regressive or vee product.
     *
     * @param rhs
     * @return this ∨ rhs
     */
    public AMV regressiveProduct(AMV rhs) {
        return get_((IMultivector) impl.vee(rhs.impl));
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
    public AMV division(AMV rhs) {
        return get_((IMultivector) impl.div(rhs.impl));
    }

    /**
     * <pre>
     * Inner product.
     * <strong>Implemented as {@link #dotProduct dot product}.</strong>
     * </pre>
     *
     * The inner product does not fit with left/rightContraction. Better use the dot-product.
     *
     * @param rhs
     * @return rhis ⋅ rhs
     */
    public AMV innerProduct(AMV rhs) {
        return get_((IMultivector) impl.ip(rhs.impl));
    }

    public AMV dotProduct(AMV rhs) {
        return get_((IMultivector) impl.dot(rhs.impl));
    }

    /**
     * Scalar product.
     *
     * It is symmetrical and reversible.<p>
     *
     * TODO<br>
     * eigenes Operator-Symbol einführen?
     * <p>
     *
     * @param rhs
     * @return
     */
    public AMV scalarProduct(AMV rhs) {
        return get_((IMultivector) impl.scp(rhs.impl));
    }

    // non linear operators
    /**
     * Meet (intersection) = largest common subspace/devisor (useful only if the arguments are blades):
     *
     * @param rhs
     * @return this ∩ rhs
     */
    public AMV meet(AMV rhs) {
        return get_((IMultivector) impl.meet(rhs.impl));
    }

    /**
     * Join (union) of two subspaces is there smallest superspace = smallest space containing them both, least
     * common multiple (useful only if the arguments are blades).
     *
     * @param rhs
     * @return this ∪ rhs
     */
    public AMV join(AMV rhs) {
        return get_((IMultivector) impl.join(rhs.impl));
    }

    /**
     * Exponential to implement rotors.
     *
     * @return exp()
     * @throws IllegalArgumentException if this is no bivector
     */
    public AMV exp() {
        return get_((IMultivector) impl.exp());
    }

    public AMV sqrt() {
        return get_((IMultivector) impl.sqrt());
    }

    public AMV log() {
        return get_((IMultivector) impl.log());
    }

    public AMV up(){
         return get_((IMultivector) impl.up());
    }
    public AMV down(){
         return get_((IMultivector) impl.down());
    }
    
    /**
     * Square root.
     *
     * @return scalarSqrt(this)
     */
    public AMV scalarSqrt() {
        return get_((IMultivector) impl.scalarSqrt());
    }

    // new scalar functions
    
    public AMV scalarSign(){
        return get_((IMultivector) impl.scalarSign());
    }
    public AMV scalarSin(){
        return get_((IMultivector) impl.scalarSin());
    }
    public AMV scalarCos(){
        return get_((IMultivector) impl.scalarCos());
    }
    public AMV scalarTan(){
        return get_((IMultivector) impl.scalarTan());
    }
    public AMV scalarAtan(){
        return get_((IMultivector) impl.scalarAtan());
    }
    public AMV scalarAsin(){
        return get_((IMultivector) impl.scalarAsin());
    }
    public AMV scalarAcos(){
        return get_((IMultivector) impl.scalarAcos());
    }
    /**
     * Arcus tansgens 2 (Converts the coordinates (x,y) to coordinates (r, theta) and returns the angle theta
     * as the couterclockwise angle in radians between -pi and pi of the point (x,y) to the positive x-axis.)
     *
     * TODO lässt sich die Funktion atan2 nicht auch auf beliebige multivectors erweitern?
     *
     * @param y scalar value
     * @return a scalar atan2(this, rhs)
     * @throws IllegalArgumentException if x, y != scalar
     */
    public AMV scalarAtan2(AMV y) {
        return get_((IMultivector) impl.scalarAtan2(y.impl));
    }

    //======================================================
    // Base 1-ary operators
    //======================================================
    /**
     * negate
     *
     * @return -this
     */
    public AMV negate() {
        return get_((IMultivector) impl.negate());
    }

    /**
     * general inverse
     *
     * @return this⁻¹
     */
    public AMV generalInverse() {
        return get_((IMultivector) impl.generalInverse());
    }

    /**
     * @throws IllegalArgumentException if the multivector is no scalar or is 0
     * @return
     */
    public AMV scalarInverse() {
        return get_((IMultivector) impl.scalarInverse());
    }

    /**
     * TODO eventuell wieder abschaffen und in generalInverse testen ob ein versor vorliegt und dann
     * versorInverse() der Implementierung aufrufen. Dazu muss ich dann eine Methode implementierung um zu
     * testen ob es sich bei einem multivector um einen Versor handelt.
     *
     * @return
     */
    public AMV versorInverse() {
        return get_((IMultivector) impl.versorInverse());
    }

    /**
     * Poincare duality operator.
     *
     * @return this*
     */
    public AMV dual() {
        return get_((IMultivector) impl.dual());
    }

    /**
     * Reverse/adjoint: Reverse all multiplications/the order of every product of vectors (a sign change
     * operation).
     *
     * @return this˜
     */
    public AMV reverse() {
        return get_((IMultivector) impl.reverse());
    }

    /**
     * Clifford conjugate (a sign change operation)
     *
     * @return this † (| in ganja.js?)
     */
    public AMV cliffordConjugate() {
        return get_((IMultivector) impl.conjugate());
    }

    //======================================================
    // Additional 1-ary operators
    //======================================================
    /**
     * undual
     *
     * @return this⁻*
     */
    public AMV undual() {
        return get_((IMultivector) impl.undual());
    }

    /**
     * Square.
     *
     * Square of k-blades are always scalars.
     *
     * TODO dafür sollte ich einen Test schreiben
     *
     * @return this²
     */
    public AMV square() {
        return get_((IMultivector) impl.square());
    }

    /**
     * Grade involution/inversion (a sign change operation).
     *
     * @return this^
     */
    public AMV gradeInversion() {
        return get_((IMultivector) impl.gradeInversion());
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
    public AMV gradeExtraction(int grade) {
        return get_((IMultivector) impl.gradeSelection(grade));
    }

    //======================================================
    // Built-in functions
    //======================================================
    /**
     * Returns a normalized (Euclidean) element.
     *
     * @return normed(this)
     * @throws java.lang.IllegalArgumentException if the multivector is 0.
     */
    public AMV normalize() throws IllegalArgumentException {
        return get_((IMultivector) impl.normalizeBySquaredNorm());
    }
    public AMV normalizeRotor() throws IllegalArgumentException {
        return get_((IMultivector) impl.normalizeRotor());
    }
    
    /**
     * Absolute value if this is a scalar.
     *
     * Wer braucht das überhaupt? ja
     *
     * @return scalarAbs(this)
     */
    public AMV scalarAbs() {
        return get_((IMultivector) impl.scalarAbs());
    }

    /**
     * Negate the signs of the vector- and 4-vector parts of an multivector.
     *
     * Usable to implement gerneral inverse.
     *
     * @return negatate14(this)
     */
    public AMV negate14() {
        return get_((IMultivector) impl.negate14());
    }

    //======================================================
    // Other
    //======================================================
    /**
     * Euclidean/reverse norm. (strict positive).
     */
    public AMV norm() {
        return get_((IMultivector) impl.norm());
    }

    /**
     * inorm.
     *
     * Calculate the Ideal norm. (signed)
     */
    /*public IMultivectorApi inorm() throws Exception {
            return get((IMultivector) impl.inorm());
    }*/
    //--------------
    public MatrixSparsity getSparsity() {
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
