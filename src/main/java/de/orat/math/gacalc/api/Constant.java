package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iConstant;

public class Constant extends MultivectorSymbolic {

    protected static Constant get(iConstant impl) {
        return new Constant(impl);
    }

    protected Constant(iConstant impl) {
        super(impl);
    }

    @Override
    protected iConstant getImpl() {
        return (iConstant) super.getImpl();
    }
}
