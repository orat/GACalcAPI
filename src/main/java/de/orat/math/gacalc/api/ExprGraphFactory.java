package de.orat.math.gacalc.api;

import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;
import de.orat.math.gacalc.spi.IGAFactory;

public class ExprGraphFactory {

    //======================================================
    // SPI boilerplate code
    //======================================================
    protected final IGAFactory impl;

    protected static ExprGraphFactory get(IGAFactory impl) {
        ExprGraphFactory result = new ExprGraphFactory(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected ExprGraphFactory(IGAFactory impl) {
        this.impl = impl;
    }

    public static final class Callback {

        private final ExprGraphFactory api;

        private Callback(ExprGraphFactory api) {
            this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation
        public IGAFactory getImpl() {
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
        return ConstantsFactorySymbolic.get(impl.constantsExpr());
    }

    public ConstantsFactoryNumeric constantsNumeric() {
        return ConstantsFactoryNumeric.get(impl.constantsValue());
    }

    //------- symbolic
    public MultivectorPurelySymbolic createMultivectorPurelySymbolicFrom(String name, MultivectorSymbolic from) {
        return MultivectorPurelySymbolic.get(impl.createVariable(name, from.getImpl()));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolicDense(String name) {
        return MultivectorPurelySymbolic.get(impl.createVariableDense(name));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolicSparse(String name) {
        return MultivectorPurelySymbolic.get(impl.createVariableSparse(name));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, MatrixSparsity sparsity) {
        return MultivectorPurelySymbolic.get(impl.createVariable(name, sparsity));
    }

    public MultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, int grade) {
        return MultivectorPurelySymbolic.get(impl.createVariable(name, grade));
    }
    
    public MultivectorPurelySymbolic createMultivectorPurelySymbolic(String name, int[] grades) {
        return MultivectorPurelySymbolic.get(impl.createVariable(name, grades));
    }

    //------- numeric
    /**
     * Create a numeric multivector. Sparsity is created from zero values.
     */
    public MultivectorNumeric createMultivectorNumeric(double[] values) {
        return MultivectorNumeric.get(impl.createValue(values));
    }

    public MultivectorNumeric createMultivectorNumeric(SparseDoubleMatrix vec) {
        return MultivectorNumeric.get(impl.createValue(vec));
    }

    public MultivectorNumeric createMultivectorNumeric(double[] nonzeros, int[] rows) {
        return MultivectorNumeric.get(impl.createValue(nonzeros, rows));
    }

    public MultivectorNumeric createMultivectorNumeric(double scalar) {
        return MultivectorNumeric.get(impl.createValue(scalar));
    }

    // random multivectors
    public MultivectorNumeric createRandomMultivectorNumeric() {
        return MultivectorNumeric.get(impl.createRandomValue());
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
    public FunctionSymbolic createFunctionSymbolic(String name, List<? extends MultivectorPurelySymbolic> parameters,
        List<? extends MultivectorSymbolic> returns) {
        var iParameters = parameters.stream().map(MultivectorPurelySymbolic::getImpl).toList();
        var iReturns = returns.stream().map(MultivectorSymbolic::getImpl).toList();
        return FunctionSymbolic.get(impl.createFunction(name, iParameters, iReturns));
    }

    //======================================================
    // Symbolic scalar
    //======================================================
    public MultivectorSymbolic createScalarLiteral(String name, double scalar) {
        return MultivectorSymbolic.get(impl.createExpr(name, scalar));
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
