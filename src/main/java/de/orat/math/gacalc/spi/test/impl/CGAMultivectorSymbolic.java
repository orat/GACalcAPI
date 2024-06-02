package de.orat.math.gacalc.spi.test.impl;

import de.orat.math.gacalc.spi.test.spi.iMultivectorSymbolic;

public class CGAMultivectorSymbolic implements iMultivectorSymbolic<CGAConstantSymbolic, CGAConstantSymbolicService, CGAMultivectorSymbolic> {

    protected CGAMultivectorSymbolic() {
    }

    protected CGAMultivectorSymbolic(CGAMultivectorSymbolic other) {
    }

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public CGAConstantSymbolicService constants() {
        return new CGAConstantSymbolicService();
    }

    @Override
    public CGAMultivectorSymbolic gp(CGAMultivectorSymbolic b) {
        CGAConstantSymbolic cc = constants().getBaseVectorOrigin();
        return b.gp(this.gp(cc));
    }
}
