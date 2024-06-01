package de.orat.math.gacalc.spi.test.impl;

import de.orat.math.gacalc.spi.test.spi.iConstantSymbolicService;

public class CGAConstantSymbolicService implements iConstantSymbolicService<CGAConstantSymbolic, CGAConstantSymbolicService, CGAMultivectorSymbolic> {

    @Override
    public CGAConstantSymbolic getBaseVectorOrigin() {
        return new CGAConstantSymbolic();
    }

}
