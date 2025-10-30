package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IConstantsValue;
import de.orat.math.gacalc.spi.IMultivectorValue;

public class ConstantsValue extends AbstractConstants<ConstantsValue, IConstantsValue, MultivectorValue, IMultivectorValue> {

    protected ConstantsValue(IConstantsValue impl) {
        super(impl);
    }

    /**
     * To be used by other classes in the package.
     */
    protected static ConstantsValue get(IConstantsValue impl) {
        return new ConstantsValue(impl);
    }

    @Override
    protected MultivectorValue get_(IMultivectorValue impl) {
        return MultivectorValue.get(impl);
    }
}
