package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iMultivectorSymbolic;
import de.orat.math.sparsematrix.ColumnVectorSparsity;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MultivectorSymbolic {

    // muss ich nun ärgerlichereise public machen
    final iMultivectorSymbolic impl;
    
    public static MultivectorSymbolic get(iMultivectorSymbolic impl) {
        MultivectorSymbolic result = new MultivectorSymbolic(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }
    private MultivectorSymbolic(iMultivectorSymbolic impl){
        this.impl = impl;
    }
    
    public static final class Callback {

        private final MultivectorSymbolic api;

        Callback(MultivectorSymbolic api){
            this.api = api;
        }
        
        //TODO
        // add methods needed by the spi implementation
    }
    
    
    // operators
    
    public MultivectorSymbolic gp(MultivectorSymbolic rhs) {
        return get(impl.gp(rhs.impl));
    }

    public MultivectorSymbolic reverse() {
        return get(impl.reverse());
    }

    /**
     * Dual.
     *
     * Poincare duality operator.
     *
     * @param a
     * @return !a
     */
    public MultivectorSymbolic dual() {
        return get(impl.dual());
    }
    
    public MultivectorSymbolic gradeSelection(int grade){
        return get(impl.gradeSelection(grade));
    }

    /**
     * Conjugate.
     *
     * Clifford Conjugation
     *
     * @param a
     * @return a.Conjugate()
     */
    public MultivectorSymbolic conjugate() {
        return get(impl.conjugate());
    }

    /**
     * Involute.
     *
     * Main involution
     *
     * @param a
     * @return a.Involute()
     */
    public MultivectorSymbolic gradeInversion()  {
        return get(impl.gradeInversion());
    }
    
    public MultivectorSymbolic op(MultivectorSymbolic b) {
        return get(impl.op(b.impl));
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
    public MultivectorSymbolic vee (MultivectorSymbolic b)  {
        return get(impl.vee(b.impl));
    }

    /**
     * Dot.
     *
     * The inner product.
     *
     * @param a
     * @param b
     * @return a | b
     */
    public MultivectorSymbolic lc(MultivectorSymbolic b) {
        return get(impl.lc(b.impl));
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
    public MultivectorSymbolic add(MultivectorSymbolic b)/* throws Exception */{
        return get(impl.add(b.impl));
    }

    /**
     * Sub.
     *
     * Multivector subtraction
     *
     * @param a
     * @param b
     * @return a - b
     */
    public MultivectorSymbolic sub(MultivectorSymbolic b) {
        return get(impl.sub(b.impl));
    }

    // wer braucht das überhaupt?
    //FIXME
    public MultivectorSymbolic mul(MultivectorSymbolic b) throws Exception {
        return get(impl.mul(b.impl));
    }
    
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

    /**
     * normalized.
     *
     * Returns a normalized (Euclidean) element.
     */
    public MultivectorSymbolic normalized() throws Exception {
        return get(impl.normalized());
    }
    
    //--------------
    
    public ColumnVectorSparsity getSparsity(){
        return impl.getSparsity();
    }
    
    @Override
    public String toString() {
        return impl.toString();
    }
    
    public String getName(){
        return impl.getName();
    }
}
