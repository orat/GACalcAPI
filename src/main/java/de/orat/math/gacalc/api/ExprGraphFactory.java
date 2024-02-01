package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.SparseDoubleColumnVector;
import java.util.List;
import java.util.function.Supplier;
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
	return createMultivectorSymbolic(name, impl.createScalar(scalar));
    }

    //======================================================
    // Symbolic constants
    //======================================================
    protected final static class Lazy implements Supplier<MultivectorSymbolic> {

        private Supplier<MultivectorSymbolic> supplier;
        private MultivectorSymbolic value;

        private Lazy(Supplier<MultivectorSymbolic> supplier) {
            this.supplier = supplier;
        }

        @Override
        public MultivectorSymbolic get() {
            if (supplier == null) {
                return value;
            } else {
                this.value = supplier.get();
                supplier = null;
                return this.value;
            }
        }
    }

    public MultivectorSymbolic getBaseVectorOrigin() {
        return baseVectorOrigin.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorOrigin = new Lazy(() -> createBaseVectorOrigin());
    protected MultivectorSymbolic createBaseVectorOrigin() {
	return createMultivectorSymbolic("ε₀", impl.createBaseVectorOrigin(1d));
    }

    public MultivectorSymbolic getBaseVectorInfinity() {
        return baseVectorInfinity.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorInfinity = new Lazy(() -> createBaseVectorInfinity());
    protected MultivectorSymbolic createBaseVectorInfinity() {
        return createMultivectorSymbolic("εᵢ", impl.createBaseVectorInfinity(1d));
    }

    public MultivectorSymbolic getBaseVectorX() {
        return baseVectorX.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorX = new Lazy(() -> createBaseVectorX());
    protected MultivectorSymbolic createBaseVectorX() {
        return createMultivectorSymbolic("ε₁", impl.createBaseVectorX(1d));
    }

    public MultivectorSymbolic getBaseVectorY() {
        return baseVectorY.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorY = new Lazy(() -> createBaseVectorY());
    protected MultivectorSymbolic createBaseVectorY() {
        return createMultivectorSymbolic("ε₂", impl.createBaseVectorY(1d));
    }

    public MultivectorSymbolic getBaseVectorZ() {
        return baseVectorZ.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorZ = new Lazy(() -> createBaseVectorZ());
    protected MultivectorSymbolic createBaseVectorZ() {
        return createMultivectorSymbolic("e3", impl.createBaseVectorZ(1d));
    }

    public MultivectorSymbolic getEpsilonPlus() {
        return epsilonPlus.get();
    }
    protected final Supplier<MultivectorSymbolic> epsilonPlus = new Lazy(() -> createEpsilonPlus());
    protected MultivectorSymbolic createEpsilonPlus() {
        return createMultivectorSymbolic("ε₊", impl.createEpsilonPlus());
    }

    public MultivectorSymbolic getEpsilonMinus() {
        return epsilonMinus.get();
    }
    protected final Supplier<MultivectorSymbolic> epsilonMinus = new Lazy(() -> createEpsilonMinus());
    protected MultivectorSymbolic createEpsilonMinus() {
        return createMultivectorSymbolic("ε₋", impl.createEpsilonMinus());
    }

    public MultivectorSymbolic getPi() {
        return pi.get();
    }
    protected final Supplier<MultivectorSymbolic> pi = new Lazy(() -> createPi());
    protected MultivectorSymbolic createPi() {
	return this.createScalarLiteral("π", Math.PI);
    }

    public MultivectorSymbolic getBaseVectorInfinityDorst() {
        return baseVectorInfinityDorst.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorInfinityDorst = new Lazy(() -> createBaseVectorInfinityDorst());
    protected MultivectorSymbolic createBaseVectorInfinityDorst() {
	return createMultivectorSymbolic("∞", impl.createBaseVectorInfinityDorst());
    }

    public MultivectorSymbolic getBaseVectorOriginDorst() {
        return baseVectorOriginDorst.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorOriginDorst = new Lazy(() -> createBaseVectorOriginDorst());
    protected MultivectorSymbolic createBaseVectorOriginDorst() {
	return createMultivectorSymbolic("o", impl.createBaseVectorOriginDorst());
    }

    public MultivectorSymbolic getBaseVectorInfinityDoran() {
        return baseVectorInfinityDoran.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorInfinityDoran = new Lazy(() -> createBaseVectorInfinityDoran());
    protected MultivectorSymbolic createBaseVectorInfinityDoran() {
	return createMultivectorSymbolic("n", impl.createBaseVectorInfinityDoran());
    }

    public MultivectorSymbolic getBaseVectorOriginDoran() {
        return baseVectorOriginDoran.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorOriginDoran = new Lazy(() -> createBaseVectorOriginDoran());
    protected MultivectorSymbolic createBaseVectorOriginDoran() {
	return createMultivectorSymbolic("ñ", impl.createBaseVectorOriginDoran());
    }

    public MultivectorSymbolic getMinkovskyBiVector() {
        return minkovskyBiVector.get();
    }
    protected final Supplier<MultivectorSymbolic> minkovskyBiVector = new Lazy(() -> createMinkovskyBiVector());
    protected MultivectorSymbolic createMinkovskyBiVector() {
	return createMultivectorSymbolic("E₀", impl.createMinkovskyBiVector());
    }

    public MultivectorSymbolic getEuclideanPseudoscalar() {
        return euclideanPseudoscalar.get();
    }
    protected final Supplier<MultivectorSymbolic> euclideanPseudoscalar = new Lazy(() -> createEuclideanPseudoscalar());
    protected MultivectorSymbolic createEuclideanPseudoscalar() {
	return createMultivectorSymbolic("E₃", impl.createEuclideanPseudoscalar());
    }

    public MultivectorSymbolic getPseudoscalar() {
        return pseudoscalar.get();
    }
    protected final Supplier<MultivectorSymbolic> pseudoscalar = new Lazy(() -> createPseudoscalar());
    protected MultivectorSymbolic createPseudoscalar() {
	return createMultivectorSymbolic("E", impl.createPseudoscalar());
    }

    
    //======================================================
    // For up projection from euclidean space
    //======================================================
    
    public SparseDoubleColumnVector createE(Tuple3d tuple3d) {
        return impl.createE(tuple3d);
    }
    public SparseDoubleColumnVector createInf(double scalar){
        return impl.createBaseVectorInfinity(scalar);
    }
    public SparseDoubleColumnVector createOrigin(double scalar){
        return impl.createBaseVectorOrigin(scalar);
    }
}
