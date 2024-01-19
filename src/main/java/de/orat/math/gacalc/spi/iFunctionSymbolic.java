package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.FunctionSymbolic.Callback;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iFunctionSymbolic {

	void init(Callback callback)/* throws */;

	String getName();

	int getArity();

	int getResultCount();

	@Override
	String toString();

	List<iMultivectorSymbolic> callSymbolic(List<iMultivectorSymbolic> arguments);

	List<iMultivectorNumeric> callNumeric(List<iMultivectorNumeric> arguments);

  // old: warum keine set_method mehr?
  // getName()/getArity() und getResultant() ist doch besser in der API aufgehoben?
  /**
    public void init(Callback callback)/* throws */;
    public String toString();
    
    public void set(/*String name,*/ List<iMultivectorSymbolic> parameters, 
                                         List<iMultivectorSymbolic> returns);
    
    public List<iMultivectorSymbolic> callSymbolic(List<iMultivectorSymbolic> arguments);
    public List<iMultivectorNumeric> callNumeric(List<iMultivectorNumeric> arguments);
*/
}
