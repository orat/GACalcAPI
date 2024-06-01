package de.orat.math.gacalc.spi.test.spi;

public interface iMultivectorSymbolicOperators<IConstantSymbolic extends iConstantSymbolic<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>, IConstantSymbolicService extends iConstantSymbolicService<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>, IMultivectorSymbolicOperators extends iMultivectorSymbolicOperators<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolicOperators>>
    extends iMultivectorSymbolic {

    IConstantSymbolicService constants();

    default IMultivectorSymbolicOperators gp(IMultivectorSymbolicOperators b) {
        IConstantSymbolic cc = constants().getBaseVectorOrigin();
        return cc.gp(this.gp(b));
    }
}
