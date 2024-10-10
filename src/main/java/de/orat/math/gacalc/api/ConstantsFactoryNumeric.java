package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iConstantsFactoryNumeric;
import de.orat.math.gacalc.spi.iMultivectorNumeric;

public class ConstantsFactoryNumeric extends AbstractConstantsFactory<ConstantsFactoryNumeric, iConstantsFactoryNumeric, MultivectorNumeric, iMultivectorNumeric> {

    protected ConstantsFactoryNumeric(iConstantsFactoryNumeric impl) {
        super(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected static ConstantsFactoryNumeric get(iConstantsFactoryNumeric impl) {
        return new ConstantsFactoryNumeric(impl);
    }

    @Override
    protected MultivectorNumeric get_(iMultivectorNumeric impl) {
        return MultivectorNumeric.get(impl);
    }
}
