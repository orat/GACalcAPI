package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.FunctionSymbolic.Callback;
import de.orat.math.gacalc.api.MultivectorNumeric;
import de.orat.math.gacalc.api.MultivectorSymbolic;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iFunctionSymbolic {
    
    public void init(Callback callback)/* throws */;
    public String toString();
    
    public void set(String name, List<iMultivectorSymbolic> parameters, 
                                         List<iMultivectorSymbolic> returns);
    
    public List<iMultivectorSymbolic> callSymbolic(List<iMultivectorSymbolic> arguments);
    public List<iMultivectorNumeric> callNumeric(List<iMultivectorNumeric> arguments);
}
