package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorNumeric.Callback;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iMultivectorNumeric {
    
    public void init(Callback callback);
    public String toString();
    
    public double[] elements();
}
