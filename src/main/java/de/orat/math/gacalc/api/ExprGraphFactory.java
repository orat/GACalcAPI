package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.SparseDoubleColumnVector;
import java.util.List;
//import de.orat.math.gacalc.spi.iEuclideanTypeConverter;
import org.jogamp.vecmath.Tuple3d;

public class ExprGraphFactory {

    //======================================================
    // SPI boilerplate code
    //======================================================
    protected final iExprGraphFactory impl;

    protected static ExprGraphFactory get(iExprGraphFactory impl) {
            ExprGraphFactory result = new ExprGraphFactory(impl);
            Callback callback = new Callback(result);
            impl.init(callback);
            return result;
    }

    protected ExprGraphFactory(iExprGraphFactory impl) {
            this.impl = impl;
    }

    public static final class Callback {

            private final ExprGraphFactory api;

            private Callback(ExprGraphFactory api) {
                this.api = api;
            }

            //TODO
            // add methods needed by the spi implementation
            public iExprGraphFactory getImpl() {
                return api.impl;
            }
    }

    //======================================================
    // Payload methods
    //======================================================
    public String getAlgebra() {
        return impl.getAlgebra();
    }

    public String getName() {
        return impl.getName();
    }

    
    //------- symbolic
    
    public MultivectorSymbolic createMultivectorSymbolic(String name) {
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name));
    }

    public MultivectorSymbolic createMultivectorSymbolic(String name, ColumnVectorSparsity sparsity) {
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name, sparsity));
    }

    public MultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleColumnVector sparseVector) {
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name, sparseVector));
    }

    public MultivectorSymbolic createMultivectorSymbolic(String name, int grade) {
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name, grade));
    }

    
    //------- numeric
    
    /**
     * Create a numeric multivector. Sparsity is created from zero values.
     */
    public MultivectorNumeric createMultivectorNumeric(double[] values) {
            return MultivectorNumeric.get(impl.createMultivectorNumeric(values));
    }

    public MultivectorNumeric createMultivectorNumeric(SparseDoubleColumnVector vec) {
            return MultivectorNumeric.get(impl.createMultivectorNumeric(vec.nonzeros(), vec.getSparsity().getrow()));
    }

    public MultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows) {
            return MultivectorNumeric.get(impl.createMultivectorNumeric(nonzeros, rows));
    }

    
    // random multivectors
    
    public MultivectorNumeric createRandomMultivectorNumeric() {
            return MultivectorNumeric.get(impl.createRandomMultivectorNumeric());
    }

    public double[] createRandomCGAMultivector() {
        return impl.createRandomCGAMultivector();
    }

    
    // functions
    
    public FunctionSymbolic createFunctionSymbolic(String name, List<MultivectorSymbolic> parameters,
        List<MultivectorSymbolic> returns) {
        var iParameters = parameters.stream().map(mvs -> mvs.impl).toList();
        var iReturns = returns.stream().map(mvs -> mvs.impl).toList();
        return FunctionSymbolic.get(impl.createFunctionSymbolic(name, iParameters, iReturns));
    }

    //======================================================
    // Symbolic scalar
    //======================================================
    public MultivectorSymbolic createScalarLiteral(String name, double scalar) {
        //return this.createMultivectorSymbolic(name, getEuclideanTypeConverter().scalar_opns(scalar));
        //return this.createMultivectorSymbolic(name, SparseCGAColumnVectorFactory.scalar_ipns(scalar));
        return createMultivectorSymbolic(name, impl.createScalar(scalar));
    }

    
    //======================================================
    // Symbolic constants
    //======================================================
    
    public MultivectorSymbolic createBaseVectorOrigin() {
        return createMultivectorSymbolic("e0", impl.createBaseVectorOrigin(1d));
    }

    public MultivectorSymbolic createBaseVectorInfinity() {
        return createMultivectorSymbolic("ei", impl.createBaseVectorInfinity(1d));
    }

    protected MultivectorSymbolic createBaseVectorX() {
        return createMultivectorSymbolic("e1", impl.createBaseVectorX(1d));
    }

    protected MultivectorSymbolic createBaseVectorY() {
        return createMultivectorSymbolic("e2", impl.createBaseVectorY(1d));
    }

    protected MultivectorSymbolic createBaseVectorZ() {
        return createMultivectorSymbolic("e3", impl.createBaseVectorZ(1d));
    }
    
    protected MultivectorSymbolic createEpsilonPlus() {
        return createMultivectorSymbolic("e+", impl.createEpsilonPlus());
    }

    protected MultivectorSymbolic createEpsilonMinus() {
        return createMultivectorSymbolic("e-", impl.createEpsilonMinus());
    }

    protected MultivectorSymbolic createBaseVectorInfinityDorst() {
        return createMultivectorSymbolic("inf", impl.createBaseVectorInfinityDorst()); 
    }

    protected MultivectorSymbolic createBaseVectorOriginDorst() {
        return createMultivectorSymbolic("o", impl.createBaseVectorOriginDorst()); 
    }

    protected MultivectorSymbolic createBaseVectorInfinityDoran() {
        return createMultivectorSymbolic("n_", impl.createBaseVectorInfinityDoran()); 
    }

    protected MultivectorSymbolic createBaseVectorOriginDoran() {
        return createMultivectorSymbolic("n", impl.createBaseVectorOriginDoran()); 
    }

    protected MultivectorSymbolic createPi() {
        return this.createScalarLiteral("Pi", Math.PI);
    }

    protected MultivectorSymbolic createMinkovskyBiVector() {
        return createMultivectorSymbolic("oi", impl.createMinkovskyBiVector());
    }

    protected MultivectorSymbolic createEuclideanPseudoscalar() {
        return createMultivectorSymbolic("e123", impl.createEuclideanPseudoscalar());
    }

    protected MultivectorSymbolic createPseudoscalar() {
        return createMultivectorSymbolic("e0123i", impl.createPseudoscalar());
    }
    

    // for up projection from euclidean space
    
    public SparseDoubleColumnVector createE(Tuple3d tuple3d){
        return impl.createE(tuple3d);
    }
    public SparseDoubleColumnVector createInf(double scalar){
        return impl.createBaseVectorInfinity(scalar);
    }
    public SparseDoubleColumnVector createOrigin(double scalar){
        return impl.createBaseVectorOrigin(scalar);
    }
}
