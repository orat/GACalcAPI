package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import de.orat.math.gacalc.spi.iMultivectorSymbolic;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;
import java.util.function.Supplier;

public class ExprGraphFactory<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>> {

    //======================================================
    // SPI boilerplate code
    //======================================================
    protected final iExprGraphFactory<IMultivectorSymbolic> impl;

    protected static <IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>> ExprGraphFactory<IMultivectorSymbolic> get(iExprGraphFactory<IMultivectorSymbolic> impl) {
        ExprGraphFactory<IMultivectorSymbolic> result = new ExprGraphFactory<>(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected ExprGraphFactory(iExprGraphFactory<IMultivectorSymbolic> impl) {
        this.impl = impl;
    }

    public static final class Callback<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>> {

        private final ExprGraphFactory<IMultivectorSymbolic> api;

        private Callback(ExprGraphFactory<IMultivectorSymbolic> api) {
            this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation
        public iExprGraphFactory<IMultivectorSymbolic> getImpl() {
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
    public MultivectorSymbolic<IMultivectorSymbolic> createMultivectorSymbolic(String name) {
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name));
    }

    public MultivectorSymbolic<IMultivectorSymbolic> createMultivectorSymbolic(String name, /*ColumnVectorSparsity*/ MatrixSparsity sparsity) {
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name, sparsity));
    }

    public MultivectorSymbolic<IMultivectorSymbolic> createMultivectorSymbolic(String name, SparseDoubleMatrix sparseVector) {
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name, sparseVector));
    }

    public MultivectorSymbolic<IMultivectorSymbolic> createMultivectorSymbolic(String name, int grade) {
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name, grade));
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
    public FunctionSymbolic<IMultivectorSymbolic> createFunctionSymbolic(String name, List<MultivectorSymbolic<IMultivectorSymbolic>> parameters,
        List<MultivectorSymbolic<IMultivectorSymbolic>> returns) {
        var iParameters = parameters.stream().map(mvs -> mvs.impl).toList();
        var iReturns = returns.stream().map(mvs -> mvs.impl).toList();
        return FunctionSymbolic.get(impl.createFunctionSymbolic(name, iParameters, iReturns));
    }

    //======================================================
    // Symbolic scalar
    //======================================================
    public MultivectorSymbolic<IMultivectorSymbolic> createScalarLiteral(String name, double scalar) {
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

    public MultivectorSymbolic<IMultivectorSymbolic> getBaseVectorOrigin() {
        return baseVectorOrigin.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> baseVectorOrigin = new Lazy(() -> createBaseVectorOrigin());

    protected MultivectorSymbolic<IMultivectorSymbolic> createBaseVectorOrigin() {
        return createMultivectorSymbolic("ε₀", impl.createBaseVectorOrigin(1d));
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getBaseVectorInfinity() {
        return baseVectorInfinity.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> baseVectorInfinity = new Lazy(() -> createBaseVectorInfinity());

    protected MultivectorSymbolic<IMultivectorSymbolic> createBaseVectorInfinity() {
        return createMultivectorSymbolic("εᵢ", impl.createBaseVectorInfinity(1d));
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getBaseVectorX() {
        return baseVectorX.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> baseVectorX = new Lazy(() -> createBaseVectorX());

    protected MultivectorSymbolic<IMultivectorSymbolic> createBaseVectorX() {
        return createMultivectorSymbolic("ε₁", impl.createBaseVectorX(1d));
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getBaseVectorY() {
        return baseVectorY.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> baseVectorY = new Lazy(() -> createBaseVectorY());

    protected MultivectorSymbolic<IMultivectorSymbolic> createBaseVectorY() {
        return createMultivectorSymbolic("ε₂", impl.createBaseVectorY(1d));
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getBaseVectorZ() {
        return baseVectorZ.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> baseVectorZ = new Lazy(() -> createBaseVectorZ());

    protected MultivectorSymbolic<IMultivectorSymbolic> createBaseVectorZ() {
        return createMultivectorSymbolic("e3", impl.createBaseVectorZ(1d));
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getEpsilonPlus() {
        return epsilonPlus.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> epsilonPlus = new Lazy(() -> createEpsilonPlus());

    protected MultivectorSymbolic<IMultivectorSymbolic> createEpsilonPlus() {
        return createMultivectorSymbolic("ε₊", impl.createEpsilonPlus());
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getEpsilonMinus() {
        return epsilonMinus.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> epsilonMinus = new Lazy(() -> createEpsilonMinus());

    protected MultivectorSymbolic<IMultivectorSymbolic> createEpsilonMinus() {
        return createMultivectorSymbolic("ε₋", impl.createEpsilonMinus());
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getPi() {
        return pi.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> pi = new Lazy(() -> createPi());

    protected MultivectorSymbolic<IMultivectorSymbolic> createPi() {
        return this.createScalarLiteral("π", Math.PI);
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getBaseVectorInfinityDorst() {
        return baseVectorInfinityDorst.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> baseVectorInfinityDorst = new Lazy(() -> createBaseVectorInfinityDorst());

    protected MultivectorSymbolic<IMultivectorSymbolic> createBaseVectorInfinityDorst() {
        return createMultivectorSymbolic("∞", impl.createBaseVectorInfinityDorst());
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getBaseVectorOriginDorst() {
        return baseVectorOriginDorst.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> baseVectorOriginDorst = new Lazy(() -> createBaseVectorOriginDorst());

    protected MultivectorSymbolic<IMultivectorSymbolic> createBaseVectorOriginDorst() {
        return createMultivectorSymbolic("o", impl.createBaseVectorOriginDorst());
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getBaseVectorInfinityDoran() {
        return baseVectorInfinityDoran.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> baseVectorInfinityDoran = new Lazy(() -> createBaseVectorInfinityDoran());

    protected MultivectorSymbolic<IMultivectorSymbolic> createBaseVectorInfinityDoran() {
        return createMultivectorSymbolic("n", impl.createBaseVectorInfinityDoran());
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getBaseVectorOriginDoran() {
        return baseVectorOriginDoran.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> baseVectorOriginDoran = new Lazy(() -> createBaseVectorOriginDoran());

    protected MultivectorSymbolic<IMultivectorSymbolic> createBaseVectorOriginDoran() {
        return createMultivectorSymbolic("ñ", impl.createBaseVectorOriginDoran());
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getMinkovskyBiVector() {
        return minkovskyBiVector.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> minkovskyBiVector = new Lazy(() -> createMinkovskyBiVector());

    protected MultivectorSymbolic<IMultivectorSymbolic> createMinkovskyBiVector() {
        return createMultivectorSymbolic("E₀", impl.createMinkovskyBiVector());
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getEuclideanPseudoscalar() {
        return euclideanPseudoscalar.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> euclideanPseudoscalar = new Lazy(() -> createEuclideanPseudoscalar());

    protected MultivectorSymbolic<IMultivectorSymbolic> createEuclideanPseudoscalar() {
        return createMultivectorSymbolic("E₃", impl.createEuclideanPseudoscalar());
    }

    public MultivectorSymbolic<IMultivectorSymbolic> getPseudoscalar() {
        return pseudoscalar.get();
    }
    protected final Supplier<MultivectorSymbolic<IMultivectorSymbolic>> pseudoscalar = new Lazy(() -> createPseudoscalar());

    protected MultivectorSymbolic<IMultivectorSymbolic> createPseudoscalar() {
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
