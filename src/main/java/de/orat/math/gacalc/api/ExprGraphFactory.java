package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;

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

    public String getImplementationName() {
        return impl.getImplementationName();
    }

    public LoopService getLoopService() {
        return LoopService.get(impl.getLoopService());
    }

    public ConstantsFactorySymbolic constantsSymbolic() {
        return ConstantsFactorySymbolic.get(impl.constantsSymbolic());
    }

    public ConstantsFactoryNumeric constantsNumeric() {
        return ConstantsFactoryNumeric.get(impl.constantsNumeric());
    }

    //------- symbolic
    public MultivectorPurelySymbolic createMultivectorPurelySymbolicFrom(String name, MultivectorSymbolic from) {
        return MultivectorPurelySymbolic.get(impl.createMultivectorPurelySymbolicFrom(name, from.getImpl()));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolicDense(String name) {
        return MultivectorPurelySymbolic.get(impl.createMultivectorPurelySymbolicDense(name));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolicSparse(String name) {
        return MultivectorPurelySymbolic.get(impl.createMultivectorPurelySymbolicSparse(name));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, MatrixSparsity sparsity) {
        return MultivectorPurelySymbolic.get(impl.createMultivectorPurelySymbolic(name, sparsity));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, int grade) {
        return MultivectorPurelySymbolic.get(impl.createMultivectorPurelySymbolic(name, grade));
    }
    
    public MultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, int[] grades) {
        return MultivectorPurelySymbolic.get(impl.createMultivectorPurelySymbolic(name, grades));
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

    public MultivectorNumeric createMultivectorNumeric(double scalar) {
        return MultivectorNumeric.get(impl.createMultivectorNumeric(scalar));
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
    public double[] createRandomMultivector(int[] indizes){
        return impl.createRandomMultivector(indizes);
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
        return MultivectorSymbolic.get(impl.createMultivectorSymbolic(name, scalar));
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
