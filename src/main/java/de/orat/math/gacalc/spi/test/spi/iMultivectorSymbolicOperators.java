package de.orat.math.gacalc.spi.test.spi;

public interface iMultivectorSymbolicOperators<IMultivectorSymbolic extends iMultivectorSymbolic, IConstantSymbolic extends iConstantSymbolic, IConstantSymbolicService extends iConstantSymbolicService<IConstantSymbolic>>
    extends iMultivectorSymbolic {

    IConstantSymbolicService constants();

    IMultivectorSymbolic gp(IMultivectorSymbolic b);
}
