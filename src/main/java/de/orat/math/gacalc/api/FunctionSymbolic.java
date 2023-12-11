package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iFunctionSymbolic;
import de.orat.math.gacalc.spi.iMultivectorNumeric;
import de.orat.math.gacalc.spi.iMultivectorSymbolic;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionSymbolic  {

    final iFunctionSymbolic impl;
    
    protected String name;
    protected int arity;

    public static final class Callback {

        private final FunctionSymbolic api;

        Callback(FunctionSymbolic api){
            this.api = api;
        }
        
        //TODO
        // add methods needed by the spi implementation
        public iFunctionSymbolic getImpl(){
            return api.impl;
        }
    }

    public static FunctionSymbolic get(iFunctionSymbolic impl){ /*throws Exception */
        FunctionSymbolic result = new FunctionSymbolic(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }
    private FunctionSymbolic(iFunctionSymbolic impl){
        this.impl = impl;
    }
    public void setSymbolic(String name, List<MultivectorSymbolic> parameters, 
                                         List<MultivectorSymbolic> returns) {
        impl.set(name, parameters.stream().map(mvs -> ((iMultivectorSymbolic) mvs.impl)).collect(Collectors.toCollection(ArrayList::new)),  
                          returns.stream().map(mvs -> ((iMultivectorSymbolic) mvs.impl)).collect(Collectors.toCollection(ArrayList::new)));
        this.name = name;
        this.arity = parameters.size();
    }
    public void setNumeric(String name, List<MultivectorSymbolic> parameters, 
                                         List<MultivectorNumeric> returns) {
        impl.set(name, parameters.stream().map(mvs -> ((iMultivectorSymbolic) mvs.impl)).collect(Collectors.toCollection(ArrayList::new)),  
                          returns.stream().map(mvs -> ((iMultivectorSymbolic) mvs.impl)).collect(Collectors.toCollection(ArrayList::new)));
        this.name = name;
        this.arity = parameters.size();
    }

    public String getName() {
        return this.name;
    }

    public int getArity() {
        return this.arity;
    }

    public List<MultivectorSymbolic> callSymbolic(List<MultivectorSymbolic> arguments) {
       List<iMultivectorSymbolic> result = impl.callSymbolic(arguments.stream().
               map(ims -> ((iMultivectorSymbolic) ims.impl)).collect(Collectors.toList())); // toCollection(ArrayList::new)
       //TODO
       // unklar ob iMultivectorSymbolic oder iFunctionSymbolic übergeben werden soll
       return result.stream().map(ms -> MultivectorSymbolic.get(ms)).collect(Collectors.toList());
    }

    public List<MultivectorNumeric> callNumeric(List<MultivectorNumeric> arguments) throws Exception {
        List<iMultivectorNumeric> result = impl.
                callNumeric(arguments.stream().map(mvn-> ((iMultivectorNumeric) mvn.impl)).collect(Collectors.toList()));
        return result.stream().map(imn -> MultivectorNumeric.get(imn)).collect(Collectors.toCollection(ArrayList::new));
    }

    public String toString() {
        return this.impl.toString();
    }
}
