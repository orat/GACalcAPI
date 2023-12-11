package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iMultivectorSymbolic;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MultivectorSymbolic {

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
        public iMultivectorSymbolic getImpl(){
            return api.impl;
        }
    }
    
    public MultivectorSymbolic gp(MultivectorSymbolic rhs) throws Exception {
        return get(impl.gp(rhs.impl));
    }

    public MultivectorSymbolic reverse() throws Exception{
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
    public MultivectorSymbolic dual() throws Exception{
        return get(impl.dual());
    }


    /**
     * Conjugate.
     *
     * Clifford Conjugation
     *
     * @param a
     * @return a.Conjugate()
     */
    public MultivectorSymbolic conjugate() throws Exception{
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
    public MultivectorSymbolic involute() throws Exception {
        return get(impl.involute());
    }
    
    public MultivectorSymbolic op(MultivectorSymbolic b) throws Exception {
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
    public MultivectorSymbolic vee (MultivectorSymbolic b) throws Exception {
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
    public MultivectorSymbolic dot(MultivectorSymbolic b) throws Exception {
         return get(impl.dot(b.impl));
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
    public MultivectorSymbolic sub(MultivectorSymbolic b) throws Exception {
        return get(impl.sub(b.impl));
    }

    // macht vermutlich nur Sinn für scalars
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
    
    
    @Override
    public String toString() {
        return impl.toString();
    }
}
