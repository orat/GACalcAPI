package de.orat.math.gacalc.api;

import java.util.List;
import de.orat.math.gacalc.spi.IGAFunction;
import de.orat.math.gacalc.spi.IMultivectorExpression;
import de.orat.math.gacalc.spi.IMultivectorValue;

public class GAFunction {

    protected final IGAFunction impl;

    protected static GAFunction get(IGAFunction impl) {
        GAFunction result = new GAFunction(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected GAFunction(IGAFunction impl) {
        this.impl = impl;
    }

    public static final class Callback {

        private final GAFunction api;

        Callback(GAFunction api) {
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

    public List<MultivectorExpression> callExpr(List<? extends MultivectorExpression> arguments) {
        var iArguments = arguments.stream().map(ims -> ims.getImpl()).toList();
        List<IMultivectorExpression> iResults = impl.callExpr(iArguments);
        var results = iResults.stream().map(ims -> MultivectorExpression.get(ims)).toList();
        return results;
    }

    public List<MultivectorValue> callValue(List<? extends MultivectorValue> arguments) {
        var iArguments = arguments.stream().map(ims -> ims.impl).toList();
        List<IMultivectorValue> iResults = impl.callValue(iArguments);
        var results = iResults.stream().map(imn -> MultivectorValue.get(imn)).toList();
        return results;
    }

    @Override
    public String toString() {
        return this.impl.toString();
    }
}
