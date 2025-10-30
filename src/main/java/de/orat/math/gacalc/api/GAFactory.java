package de.orat.math.gacalc.api;

import de.orat.math.sparsematrix.MatrixSparsity;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.List;
import de.orat.math.gacalc.spi.IGAFactory;

public class GAFactory {

    //======================================================
    // SPI boilerplate code
    //======================================================
    protected final IGAFactory impl;

    protected static GAFactory get(IGAFactory impl) {
        GAFactory result = new GAFactory(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected GAFactory(IGAFactory impl) {
        this.impl = impl;
    }

    public static final class Callback {

        private final GAFactory api;

        private Callback(GAFactory api) {
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

    public ConstantsExpression constantsExpr() {
        return ConstantsExpression.get(impl.constantsExpr());
    }

    public ConstantsValue constantsValue() {
        return ConstantsValue.get(impl.constantsValue());
    }

    //------- symbolic
    public MultivectorVariable createVariable(String name, MultivectorExpression from) {
        return MultivectorVariable.get(impl.createVariable(name, from.getImpl()));
    }

    public MultivectorVariable createVariableDense(String name) {
        return MultivectorVariable.get(impl.createVariableDense(name));
    }

    public MultivectorVariable createVariableSparse(String name) {
        return MultivectorVariable.get(impl.createVariableSparse(name));
    }

    public MultivectorVariable createVariable(String name, MatrixSparsity sparsity) {
        return MultivectorVariable.get(impl.createVariable(name, sparsity));
    }

    public MultivectorVariable createVariable(String name, int grade) {
        return MultivectorVariable.get(impl.createVariable(name, grade));
    }
    
    public MultivectorVariable createVariable(String name, int[] grades) {
        return MultivectorVariable.get(impl.createVariable(name, grades));
    }

    //------- numeric
    public MultivectorValue createValue(SparseDoubleMatrix vec) {
        return MultivectorValue.get(impl.createValue(vec));
    }

    public MultivectorValue createValue(double scalar) {
        return MultivectorValue.get(impl.createValue(scalar));
    }

    // random multivectors
    public MultivectorValue createValueRandom() {
        return MultivectorValue.get(impl.createValueRandom());
    }

    public MultivectorValue createValueRandom(int[] grades) {
        return MultivectorValue.get(impl.createValueRandom(grades));
    }
    
    // functions
    public GAFunction createFunction(String name, List<? extends MultivectorVariable> parameters,
        List<? extends MultivectorExpression> returns) {
        var iParameters = parameters.stream().map(MultivectorVariable::getImpl).toList();
        var iReturns = returns.stream().map(MultivectorExpression::getImpl).toList();
        return GAFunction.get(impl.createFunction(name, iParameters, iReturns));
    }

    //======================================================
    // Symbolic scalar
    //======================================================
    public MultivectorExpression createExpr(String name, double scalar) {
        return MultivectorExpression.get(impl.createExpr(name, scalar));
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
