package de.orat.math.gacalc.api;

import java.util.List;
import de.orat.math.gacalc.spi.IGAFunction;
import de.orat.math.gacalc.spi.IMultivectorExpression;
import de.orat.math.gacalc.spi.IMultivectorValue;

public class FunctionSymbolic {

    protected final IGAFunction impl;

    protected static FunctionSymbolic get(IGAFunction impl) {
        FunctionSymbolic result = new FunctionSymbolic(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected FunctionSymbolic(IGAFunction impl) {
        this.impl = impl;
    }

    public static final class Callback {

        private final FunctionSymbolic api;

        Callback(FunctionSymbolic api) {
            this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation
    }

    public String getName() {
        return impl.getName();
    }

    public int getArity() {
        return impl.getArity();
    }

    public int getResultCount() {
        return impl.getResultCount();
    }

    public List<MultivectorSymbolic> callSymbolic(List<? extends MultivectorSymbolic> arguments) {
        var iArguments = arguments.stream().map(ims -> ims.getImpl()).toList();
        List<IMultivectorExpression> iResults = impl.callExpr(iArguments);
        var results = iResults.stream().map(ims -> MultivectorSymbolic.get(ims)).toList();
        return results;
    }

    public List<MultivectorNumeric> callNumeric(List<? extends MultivectorNumeric> arguments) {
        var iArguments = arguments.stream().map(ims -> ims.impl).toList();
        List<IMultivectorValue> iResults = impl.callValue(iArguments);
        var results = iResults.stream().map(imn -> MultivectorNumeric.get(imn)).toList();
        return results;
    }

    @Override
    public String toString() {
        return this.impl.toString();
    }
}
