package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iFunctionSymbolic;
import de.orat.math.gacalc.spi.iMultivectorNumeric;
import de.orat.math.gacalc.spi.iMultivectorSymbolic;
import java.util.List;

public class FunctionSymbolic {

    protected final iFunctionSymbolic impl;

    protected static FunctionSymbolic get(iFunctionSymbolic impl) {
        FunctionSymbolic result = new FunctionSymbolic(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected FunctionSymbolic(iFunctionSymbolic impl) {
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
        List<iMultivectorSymbolic> iResults = impl.callSymbolic(iArguments);
        var results = iResults.stream().map(ims -> MultivectorSymbolic.get(ims)).toList();
        return results;
    }

    public List<MultivectorNumeric> callNumeric(List<? extends MultivectorNumeric> arguments) {
        var iArguments = arguments.stream().map(ims -> ims.impl).toList();
        List<iMultivectorNumeric> iResults = impl.callNumeric(iArguments);
        var results = iResults.stream().map(imn -> MultivectorNumeric.get(imn)).toList();
        return results;
    }

    @Override
    public String toString() {
        return this.impl.toString();
    }
}
