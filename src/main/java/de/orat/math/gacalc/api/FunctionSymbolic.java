package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iFunctionSymbolic;
import de.orat.math.gacalc.spi.iMultivectorSymbolic;
import java.util.List;

public class FunctionSymbolic<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>> {

    protected final iFunctionSymbolic<IMultivectorSymbolic> impl;

    protected static <IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>> FunctionSymbolic<IMultivectorSymbolic> get(iFunctionSymbolic<IMultivectorSymbolic> impl) {
        FunctionSymbolic<IMultivectorSymbolic> result = new FunctionSymbolic<>(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected FunctionSymbolic(iFunctionSymbolic<IMultivectorSymbolic> impl) {
        this.impl = impl;
    }

    public static final class Callback<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>> {

        private final FunctionSymbolic<IMultivectorSymbolic> api;

        Callback(FunctionSymbolic<IMultivectorSymbolic> api) {
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

    public List<MultivectorSymbolic<IMultivectorSymbolic>> callSymbolic(List<MultivectorSymbolic<IMultivectorSymbolic>> arguments) {
        var iArguments = arguments.stream().map(ims -> ims.impl).toList();
        var iResults = impl.callSymbolic(iArguments);
        var results = iResults.stream().map(ims -> MultivectorSymbolic.get(ims)).toList();
        return results;
    }

    public List<MultivectorNumeric> callNumeric(List<MultivectorNumeric> arguments) throws Exception {
        var iArguments = arguments.stream().map(ims -> ims.impl).toList();
        var iResults = impl.callNumeric(iArguments);
        var results = iResults.stream().map(imn -> MultivectorNumeric.get(imn)).toList();
        return results;
    }

    @Override
    public String toString() {
        return this.impl.toString();
    }

}
