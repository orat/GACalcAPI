package de.orat.math.gacalc.spi.test.spi;

public interface iConstantSymbolic<IConstantSymbolic extends iConstantSymbolic<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic>, IConstantSymbolicService extends iConstantSymbolicService<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic>, IMultivectorSymbolic extends iMultivectorSymbolic<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic>>
    extends iMultivectorSymbolic<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic> {

    IMultivectorSymbolic toIMV();
}
