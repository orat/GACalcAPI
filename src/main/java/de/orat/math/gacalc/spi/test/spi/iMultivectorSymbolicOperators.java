package de.orat.math.gacalc.spi.test.spi;

public interface iMultivectorSymbolicOperators<IMultivectorSymbolic extends iMultivectorSymbolic, IConstantSymbolic extends iConstantSymbolic<IMultivectorSymbolic, IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>, IConstantSymbolicService extends iConstantSymbolicService<IMultivectorSymbolic, IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>, IMultivectorSymbolicOperators extends iMultivectorSymbolicOperators<IMultivectorSymbolic, IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>>
    extends iMultivectorSymbolic {

    IConstantSymbolicService constants();

    default IMultivectorSymbolicOperators gp(IMultivectorSymbolicOperators b) {
        IConstantSymbolic cc = constants().getBaseVectorOrigin();
        return cc.gp(this.gp(b));
    }
}
