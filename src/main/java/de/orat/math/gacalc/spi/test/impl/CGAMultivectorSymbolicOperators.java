package de.orat.math.gacalc.spi.test.impl;

import de.orat.math.gacalc.spi.test.spi.iMultivectorSymbolicOperators;

public class CGAMultivectorSymbolicOperators extends CGAMultivectorSymbolic implements iMultivectorSymbolicOperators<CGAMultivectorSymbolic, CGAConstantSymbolic, CGAConstantSymbolicService> {

    protected CGAMultivectorSymbolicOperators() {
        super();
    }

    protected CGAMultivectorSymbolicOperators(CGAMultivectorSymbolic other) {
        super(other);
    }

    @Override
    public CGAConstantSymbolicService constants() {
        return new CGAConstantSymbolicService();
    }

    @Override
    public CGAMultivectorSymbolic gp(CGAMultivectorSymbolic b) {
        CGAConstantSymbolic cc = constants().getBaseVectorOrigin();
        return cc.gp(this.gp(b));
    }
}
