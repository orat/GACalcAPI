package de.orat.math.gacalc.spi.test.spi;

public interface iConstantSymbolicService<IConstantSymbolic extends iConstantSymbolic<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic>, IConstantSymbolicService extends iConstantSymbolicService<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic>, IMultivectorSymbolic extends iMultivectorSymbolic<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic>> {

    IConstantSymbolic getBaseVectorOrigin();
}
