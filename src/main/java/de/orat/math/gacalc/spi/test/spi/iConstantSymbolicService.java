package de.orat.math.gacalc.spi.test.spi;

public interface iConstantSymbolicService<IMultivectorSymbolic extends iMultivectorSymbolic, IConstantSymbolic extends iConstantSymbolic<IMultivectorSymbolic, IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>, IConstantSymbolicService extends iConstantSymbolicService<IMultivectorSymbolic, IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>, IMultivectorSymbolicOperators extends iMultivectorSymbolicOperators<IMultivectorSymbolic, IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>> {

    IConstantSymbolic getBaseVectorOrigin();
}
