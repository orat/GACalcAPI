package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic;

/**
 * Non-leaf node in expr tree.
 */
public interface iMultivectorSymbolic<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>>
    extends iMultivector<IMultivectorSymbolic> {

    void init(MultivectorSymbolic.Callback callback);
}
