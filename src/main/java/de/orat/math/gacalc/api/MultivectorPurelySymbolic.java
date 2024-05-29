package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iMultivectorPurelySymbolic;

public class MultivectorPurelySymbolic extends MultivectorSymbolic {

    protected static MultivectorPurelySymbolic get(iMultivectorPurelySymbolic impl) {
        return new MultivectorPurelySymbolic(impl);
    }

    protected MultivectorPurelySymbolic(iMultivectorPurelySymbolic impl) {
        super(impl);
    }

    @Override
    protected iMultivectorPurelySymbolic getImpl() {
        return (iMultivectorPurelySymbolic) super.getImpl();
    }

}
