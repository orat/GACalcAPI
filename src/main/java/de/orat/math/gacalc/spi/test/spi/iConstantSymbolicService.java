package de.orat.math.gacalc.spi.test.spi;

public interface iConstantSymbolicService<IConstantSymbolic extends iConstantSymbolic<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>, IConstantSymbolicService extends iConstantSymbolicService<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>, IMultivectorSymbolicOperators extends iMultivectorSymbolicOperators<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>> {

    IConstantSymbolic getBaseVectorOrigin();
}
