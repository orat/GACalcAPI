package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iConstantsProvider;

public class ConstantsProvider {

    protected final iConstantsProvider impl;

    protected static ConstantsProvider get(iConstantsProvider impl) {
        ConstantsProvider result = new ConstantsProvider(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }

    protected ConstantsProvider(iConstantsProvider impl) {
        this.impl = impl;
    }

    public static final class Callback {

        private final ConstantsProvider api;

        private Callback(ConstantsProvider api) {
            this.api = api;
        }

        //TODO
        // add methods needed by the spi implementation
    }

    public MultivectorSymbolic getBaseVectorOrigin() {
        return MultivectorSymbolic.get(this.impl.getBaseVectorOrigin());
    }

    public MultivectorSymbolic getBaseVectorInfinity() {
        return MultivectorSymbolic.get(this.impl.getBaseVectorInfinity());
    }

    public MultivectorSymbolic getBaseVectorX() {
        return MultivectorSymbolic.get(this.impl.getBaseVectorX());
    }

    public MultivectorSymbolic getBaseVectorY() {
        return MultivectorSymbolic.get(this.impl.getBaseVectorY());
    }

    public MultivectorSymbolic getBaseVectorZ() {
        return MultivectorSymbolic.get(this.impl.getBaseVectorZ());
    }

    public MultivectorSymbolic getEpsilonPlus() {
        return MultivectorSymbolic.get(this.impl.getEpsilonPlus());
    }

    public MultivectorSymbolic getEpsilonMinus() {
        return MultivectorSymbolic.get(this.impl.getEpsilonMinus());
    }

    public MultivectorSymbolic getPi() {
        return MultivectorSymbolic.get(this.impl.getPi());
    }

    public MultivectorSymbolic getBaseVectorInfinityDorst() {
        return MultivectorSymbolic.get(this.impl.getBaseVectorInfinityDorst());
    }

    public MultivectorSymbolic getBaseVectorOriginDorst() {
        return MultivectorSymbolic.get(this.impl.getBaseVectorOriginDorst());
    }

    public MultivectorSymbolic getBaseVectorInfinityDoran() {
        return MultivectorSymbolic.get(this.impl.getBaseVectorInfinityDoran());
    }

    public MultivectorSymbolic getBaseVectorOriginDoran() {
        return MultivectorSymbolic.get(this.impl.getBaseVectorOriginDoran());
    }

    public MultivectorSymbolic getMinkovskyBiVector() {
        return MultivectorSymbolic.get(this.impl.getMinkovskyBiVector());
    }

    public MultivectorSymbolic getEuclideanPseudoscalar() {
        return MultivectorSymbolic.get(this.impl.getEuclideanPseudoscalar());
    }

    public MultivectorSymbolic getPseudoscalar() {
        return MultivectorSymbolic.get(this.impl.getPseudoscalar());
    }

    public MultivectorSymbolic getInversePseudoscalar() {
        return MultivectorSymbolic.get(this.impl.getInversePseudoscalar());
    }

    public MultivectorSymbolic one() {
        return MultivectorSymbolic.get(this.impl.one());
    }

    public MultivectorSymbolic half() {
        return MultivectorSymbolic.get(this.impl.half());
    }
}
