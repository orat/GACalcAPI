package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IConstantsValue;
import de.orat.math.gacalc.spi.IMultivectorValue;

public class ConstantsFactoryNumeric extends AbstractConstantsFactory<ConstantsFactoryNumeric, IConstantsValue, MultivectorNumeric, IMultivectorValue> {

    protected ConstantsFactoryNumeric(IConstantsValue impl) {
        super(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected static ConstantsFactoryNumeric get(IConstantsValue impl) {
        return new ConstantsFactoryNumeric(impl);
    }

    @Override
    protected MultivectorNumeric get_(IMultivectorValue impl) {
        return MultivectorNumeric.get(impl);
    }
}
