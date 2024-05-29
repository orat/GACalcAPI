package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;
import java.util.function.Supplier;

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
    public MultivectorPurelySymbolic createMultivectorPurelySymbolic(String name) {
        return MultivectorPurelySymbolic.get(impl.createMultivectorPurelySymbolic(name));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, /*ColumnVectorSparsity*/ MatrixSparsity sparsity) {
        return MultivectorPurelySymbolic.get(impl.createMultivectorPurelySymbolic(name, sparsity));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, int grade) {
        return MultivectorPurelySymbolic.get(impl.createMultivectorPurelySymbolic(name, grade));
    }

    public MultivectorSymbolic createMultivectorSymbolic(String name, SparseDoubleMatrix sparseVector) {
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name, sparseVector));
    }

    //------- numeric
    /**
     * Create a numeric multivector. Sparsity is created from zero values.
     */
    public MultivectorNumeric createMultivectorNumeric(double[] values) {
        return MultivectorNumeric.get(impl.createMultivectorNumeric(values));
    }

    public MultivectorNumeric createMultivectorNumeric(SparseDoubleMatrix vec) {
        return MultivectorNumeric.get(impl.createMultivectorNumeric(vec.nonzeros(), vec.getSparsity().getrow()));
    }

    public MultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows) {
        return MultivectorNumeric.get(impl.createMultivectorNumeric(nonzeros, rows));
    }

    // random multivectors
    public MultivectorNumeric createRandomMultivectorNumeric() {
        return MultivectorNumeric.get(impl.createRandomMultivectorNumeric());
    }

    public double[] createRandomMultivector() {
        return impl.createRandomMultivector();
    }

    public double[] createRandomMultivector(int grade) {
        return impl.createRandomKVector(grade);
    }

    // functions
    public FunctionSymbolic createFunctionSymbolic(String name, List<MultivectorPurelySymbolic> parameters,
        List<MultivectorSymbolic> returns) {
        var iParameters = parameters.stream().map(mvs -> mvs.getImpl()).toList();
        var iReturns = returns.stream().map(mvs -> mvs.getImpl()).toList();
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
    protected final static class Lazy<T> implements Supplier<T> {

        private Supplier<T> supplier;
        private T value;

        private Lazy(Supplier<T> supplier) {
            this.supplier = supplier;
        }

        public static <T> Lazy<T> of(Supplier<T> supplier) {
            return new Lazy<>(supplier);
        }

        @Override
        public T get() {
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
    protected final Supplier<MultivectorSymbolic> baseVectorOrigin = Lazy.of(() -> createBaseVectorOrigin());

    protected MultivectorSymbolic createBaseVectorOrigin() {
        return createMultivectorSymbolic("ε₀", impl.createBaseVectorOrigin(1d));
    }

    public MultivectorSymbolic getBaseVectorInfinity() {
        return baseVectorInfinity.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorInfinity = Lazy.of(() -> createBaseVectorInfinity());

    protected MultivectorSymbolic createBaseVectorInfinity() {
        return createMultivectorSymbolic("εᵢ", impl.createBaseVectorInfinity(1d));
    }

    public MultivectorSymbolic getBaseVectorX() {
        return baseVectorX.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorX = Lazy.of(() -> createBaseVectorX());

    protected MultivectorSymbolic createBaseVectorX() {
        return createMultivectorSymbolic("ε₁", impl.createBaseVectorX(1d));
    }

    public MultivectorSymbolic getBaseVectorY() {
        return baseVectorY.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorY = Lazy.of(() -> createBaseVectorY());

    protected MultivectorSymbolic createBaseVectorY() {
        return createMultivectorSymbolic("ε₂", impl.createBaseVectorY(1d));
    }

    public MultivectorSymbolic getBaseVectorZ() {
        return baseVectorZ.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorZ = Lazy.of(() -> createBaseVectorZ());

    protected MultivectorSymbolic createBaseVectorZ() {
        return createMultivectorSymbolic("e3", impl.createBaseVectorZ(1d));
    }

    public MultivectorSymbolic getEpsilonPlus() {
        return epsilonPlus.get();
    }
    protected final Supplier<MultivectorSymbolic> epsilonPlus = Lazy.of(() -> createEpsilonPlus());

    protected MultivectorSymbolic createEpsilonPlus() {
        return createMultivectorSymbolic("ε₊", impl.createEpsilonPlus());
    }

    public MultivectorSymbolic getEpsilonMinus() {
        return epsilonMinus.get();
    }
    protected final Supplier<MultivectorSymbolic> epsilonMinus = Lazy.of(() -> createEpsilonMinus());

    protected MultivectorSymbolic createEpsilonMinus() {
        return createMultivectorSymbolic("ε₋", impl.createEpsilonMinus());
    }

    public MultivectorSymbolic getPi() {
        return pi.get();
    }
    protected final Supplier<MultivectorSymbolic> pi = Lazy.of(() -> createPi());

    protected MultivectorSymbolic createPi() {
        return this.createScalarLiteral("π", Math.PI);
    }

    public MultivectorSymbolic getBaseVectorInfinityDorst() {
        return baseVectorInfinityDorst.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorInfinityDorst = Lazy.of(() -> createBaseVectorInfinityDorst());

    protected MultivectorSymbolic createBaseVectorInfinityDorst() {
        return createMultivectorSymbolic("∞", impl.createBaseVectorInfinityDorst());
    }

    public MultivectorSymbolic getBaseVectorOriginDorst() {
        return baseVectorOriginDorst.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorOriginDorst = Lazy.of(() -> createBaseVectorOriginDorst());

    protected MultivectorSymbolic createBaseVectorOriginDorst() {
        return createMultivectorSymbolic("o", impl.createBaseVectorOriginDorst());
    }

    public MultivectorSymbolic getBaseVectorInfinityDoran() {
        return baseVectorInfinityDoran.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorInfinityDoran = Lazy.of(() -> createBaseVectorInfinityDoran());

    protected MultivectorSymbolic createBaseVectorInfinityDoran() {
        return createMultivectorSymbolic("n", impl.createBaseVectorInfinityDoran());
    }

    public MultivectorSymbolic getBaseVectorOriginDoran() {
        return baseVectorOriginDoran.get();
    }
    protected final Supplier<MultivectorSymbolic> baseVectorOriginDoran = Lazy.of(() -> createBaseVectorOriginDoran());

    protected MultivectorSymbolic createBaseVectorOriginDoran() {
        return createMultivectorSymbolic("ñ", impl.createBaseVectorOriginDoran());
    }

    public MultivectorSymbolic getMinkovskyBiVector() {
        return minkovskyBiVector.get();
    }
    protected final Supplier<MultivectorSymbolic> minkovskyBiVector = Lazy.of(() -> createMinkovskyBiVector());

    protected MultivectorSymbolic createMinkovskyBiVector() {
        return createMultivectorSymbolic("E₀", impl.createMinkovskyBiVector());
    }

    public MultivectorSymbolic getEuclideanPseudoscalar() {
        return euclideanPseudoscalar.get();
    }
    protected final Supplier<MultivectorSymbolic> euclideanPseudoscalar = Lazy.of(() -> createEuclideanPseudoscalar());

    protected MultivectorSymbolic createEuclideanPseudoscalar() {
        return createMultivectorSymbolic("E₃", impl.createEuclideanPseudoscalar());
    }

    public MultivectorSymbolic getPseudoscalar() {
        return pseudoscalar.get();
    }
    protected final Supplier<MultivectorSymbolic> pseudoscalar = Lazy.of(() -> createPseudoscalar());

    protected MultivectorSymbolic createPseudoscalar() {
        return createMultivectorSymbolic("E", impl.createPseudoscalar());
    }

    //======================================================
    // For up projection from euclidean space
    //======================================================
    public SparseDoubleMatrix createE(double x, double y, double z) {
        return impl.createE(x, y, z);
    }

    public SparseDoubleMatrix createInf(double scalar) {
        return impl.createBaseVectorInfinity(scalar);
    }

    public SparseDoubleMatrix createOrigin(double scalar) {
        return impl.createBaseVectorOrigin(scalar);
    }
}
