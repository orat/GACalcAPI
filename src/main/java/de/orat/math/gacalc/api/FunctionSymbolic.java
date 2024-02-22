package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iFunctionSymbolic;
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

        Callback(FunctionSymbolic api){
            this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation

    }

    /*public static FunctionSymbolic get(iFunctionSymbolic impl, String name, List<MultivectorSymbolic> parameters, 
                                           List<MultivectorSymbolic> returns){ 
          FunctionSymbolic result = new FunctionSymbolic(impl, name, parameters.size());
          Callback callback = new Callback(result);
          impl.init(callback);
          //set(impl, parameters, returns);
          return result;
    }*/
    /*private FunctionSymbolic(iFunctionSymbolic impl, String name, int arity){
          this.impl = impl;
          this.name = name;
          this.arity = arity;
    }*/
    
    /*private static void set(iFunctionSymbolic impl, List<MultivectorSymbolic> parameters, 
                                           List<MultivectorSymbolic> returns) {
          impl.set(parameters.stream().map(mvs -> ((iMultivectorSymbolic) mvs.impl)).collect(Collectors.toCollection(ArrayList::new)),  
                            returns.stream().map(mvs -> ((iMultivectorSymbolic) mvs.impl)).collect(Collectors.toCollection(ArrayList::new)));
    }*/
 
    public String getName() {
        return impl.getName();
    }

    public int getArity() {
        return impl.getArity();
    }

    public int getResultCount() {
        return impl.getResultCount();
    }

    public List<MultivectorSymbolic> callSymbolic(List<MultivectorSymbolic> arguments) {
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
