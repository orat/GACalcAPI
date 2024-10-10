package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iConstantsFactorySymbolic;
import de.orat.math.gacalc.spi.iMultivectorSymbolic;

public class ConstantsFactorySymbolic extends AbstractConstantsFactory<ConstantsFactorySymbolic, iConstantsFactorySymbolic, MultivectorSymbolic, iMultivectorSymbolic> {

    protected ConstantsFactorySymbolic(iConstantsFactorySymbolic impl) {
        super(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected static ConstantsFactorySymbolic get(iConstantsFactorySymbolic impl) {
        return new ConstantsFactorySymbolic(impl);
    }

    @Override
    protected MultivectorSymbolic get_(iMultivectorSymbolic impl) {
        return MultivectorSymbolic.get(impl);
    }
}
