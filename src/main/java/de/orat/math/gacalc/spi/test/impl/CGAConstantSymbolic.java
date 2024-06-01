package de.orat.math.gacalc.spi.test.impl;

import de.orat.math.gacalc.spi.test.spi.iConstantSymbolic;

public class CGAConstantSymbolic extends CGAMultivectorSymbolicOperators implements iConstantSymbolic<CGAConstantSymbolic, CGAConstantSymbolicService, CGAMultivectorSymbolicOperators> {

    protected CGAConstantSymbolic() {
        super();
    }

    protected CGAConstantSymbolic(CGAMultivectorSymbolic other) {
        super(other);
    }

}
