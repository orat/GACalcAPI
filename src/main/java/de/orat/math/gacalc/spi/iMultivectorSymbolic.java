package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.MultivectorSymbolic;

/**
 * Non-leaf node in expr tree.
 */
public interface iMultivectorSymbolic<IMVSymbolic extends iMultivectorSymbolic<IMVSymbolic>>
    extends iMultivector<IMVSymbolic> {

    void init(MultivectorSymbolic.Callback callback);
}
