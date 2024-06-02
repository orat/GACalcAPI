package de.orat.math.gacalc.spi.test.impl;

import de.orat.math.gacalc.spi.test.spi.iConstantSymbolic;

public class CGAConstantSymbolic extends CGAMultivectorSymbolic implements iConstantSymbolic<CGAConstantSymbolic, CGAConstantSymbolicService, CGAMultivectorSymbolic> {

    protected CGAConstantSymbolic() {
        super();
    }

    protected CGAConstantSymbolic(CGAMultivectorSymbolic other) {
        super(other);
    }

    @Override
    public CGAMultivectorSymbolic toIMV() {
        return this;
    }

}
