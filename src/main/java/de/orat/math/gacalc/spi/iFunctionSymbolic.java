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
}
