package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import de.orat.math.sparsematrix.SparseDoubleColumnVector;
import java.util.List;
import util.cga.SparseCGAColumnVectorFactory;

public class ExprGraphFactory {

    //======================================================
    // SPI boilerplate code
    //======================================================
    protected final iExprGraphFactory impl;

    // public damit ich von Test-classes dran komme
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

    public MultivectorNumeric createRandomMultivectorNumeric() {
            return MultivectorNumeric.get(impl.createRandomMultivectorNumeric());
    }

    public double[] createRandomCGAMultivector() {
            return impl.createRandomCGAMultivector();
    }

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
        return this.createMultivectorSymbolic(name, SparseCGAColumnVectorFactory.scalar_ipns(scalar));
    }

    
    //======================================================
    // Symbolic constants
    //======================================================
    
    // public final MultivectorSymbolic baseVectorOrigin = createBaseVectorOrigin();
    protected MultivectorSymbolic createBaseVectorOrigin() {
        return MultivectorSymbolic.get(impl.createBaseVectorOrigin());
    }

    // public final MultivectorSymbolic baseVectorInfinity = createBaseVectorInfinity();
    protected MultivectorSymbolic createBaseVectorInfinity() {
        return MultivectorSymbolic.get(impl.createBaseVectorInfinity());
    }

    // public final MultivectorSymbolic baseVectorX = createBaseVectorX();
    protected MultivectorSymbolic createBaseVectorX() {
        return MultivectorSymbolic.get(impl.createBaseVectorX());
    }

    // public final MultivectorSymbolic baseVectorY = createBaseVectorY();
    protected MultivectorSymbolic createBaseVectorY() {
        return MultivectorSymbolic.get(impl.createBaseVectorY());
    }

    // public final MultivectorSymbolic baseVectorZ = createBaseVectorZ();
    protected MultivectorSymbolic createBaseVectorZ() {
        return MultivectorSymbolic.get(impl.createBaseVectorZ());
    }

    // public final MultivectorSymbolic epsilonPlus = createEpsilonPlus();
    protected MultivectorSymbolic createEpsilonPlus() {
        return MultivectorSymbolic.get(impl.createEpsilonPlus());
    }

    // public final MultivectorSymbolic epsilonMinus = createEpsilonMinus();
    protected MultivectorSymbolic createEpsilonMinus() {
        return MultivectorSymbolic.get(impl.createEpsilonMinus());
    }

    // public final MultivectorSymbolic baseVectorInfinityDorst = createBaseVectorInfinityDorst();
    protected MultivectorSymbolic createBaseVectorInfinityDorst() {
        return MultivectorSymbolic.get(impl.createMinkovskyBiVector());
    }

    // public final MultivectorSymbolic baseVectorOriginDorst = createBaseVectorOriginDorst();
    protected MultivectorSymbolic createBaseVectorOriginDorst() {
            throw new UnsupportedOperationException();
    }

    // public final MultivectorSymbolic baseVectorInfinityDoran = createBaseVectorInfinityDoran();
    protected MultivectorSymbolic createBaseVectorInfinityDoran() {
            throw new UnsupportedOperationException();
    }

    // public final MultivectorSymbolic baseVectorOriginDoran = createBaseVectorOriginDoran();
    protected MultivectorSymbolic createBaseVectorOriginDoran() {
            throw new UnsupportedOperationException();
    }

    protected MultivectorSymbolic createPi() {
        return this.createScalarLiteral("Pi", Math.PI);
    }

    // public final MultivectorSymbolic minkovskyBiVector = createMinkovskyBiVector();
    protected MultivectorSymbolic createMinkovskyBiVector() {
        return MultivectorSymbolic.get(impl.createMinkovskyBiVector());
    }

    // public final MultivectorSymbolic euclideanPseudoscalar = createEuclideanPseudoscalar();
    protected MultivectorSymbolic createEuclideanPseudoscalar() {
        return MultivectorSymbolic.get(impl.createEuclideanPseudoscalar());
    }

    // public final MultivectorSymbolic pseudoscalar = createPseudoscalar();
    protected MultivectorSymbolic createPseudoscalar() {
        return MultivectorSymbolic.get(impl.createPseudoscalar());
    }
}
