package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorNumeric.Callback;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorNumeric {

	void init(Callback callback);

	@Override
	String toString();

	double[] elements();
}
