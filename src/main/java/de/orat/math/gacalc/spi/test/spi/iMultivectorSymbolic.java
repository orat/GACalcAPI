package de.orat.math.gacalc.spi.test.spi;

public interface iMultivectorSymbolic<IConstantSymbolic extends iConstantSymbolic<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic>, IConstantSymbolicService extends iConstantSymbolicService<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic>, IMultivectorSymbolic extends iMultivectorSymbolic<IConstantSymbolic, IConstantSymbolicService, IMultivectorSymbolic>> {

    String getName();

    IConstantSymbolicService constants();

    default IMultivectorSymbolic gp(IMultivectorSymbolic b) {
        IConstantSymbolic cc = constants().getBaseVectorOrigin();
        return cc.gp(this.gp(b));
    }
}
