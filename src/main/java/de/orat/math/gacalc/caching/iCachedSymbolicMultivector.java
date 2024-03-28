package de.orat.math.gacalc.caching;

import de.orat.math.gacalc.spi.iMultivectorSymbolic;

public interface iCachedSymbolicMultivector<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>, ICachedSymbolicMultivector extends iCachedSymbolicMultivector<IMultivectorSymbolic, ICachedSymbolicMultivector>> extends iMultivectorSymbolic<ICachedSymbolicMultivector> {

    IMultivectorSymbolic getDelegate();
}
