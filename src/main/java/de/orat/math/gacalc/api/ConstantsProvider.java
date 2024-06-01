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

    public Constant getBaseVectorOrigin() {
        return Constant.get(this.impl.getBaseVectorOrigin());
    }

    public Constant getBaseVectorInfinity() {
        return Constant.get(this.impl.getBaseVectorInfinity());
    }

    public Constant getBaseVectorX() {
        return Constant.get(this.impl.getBaseVectorX());
    }

    public Constant getBaseVectorY() {
        return Constant.get(this.impl.getBaseVectorY());
    }

    public Constant getBaseVectorZ() {
        return Constant.get(this.impl.getBaseVectorZ());
    }

    public Constant getEpsilonPlus() {
        return Constant.get(this.impl.getEpsilonPlus());
    }

    public Constant getEpsilonMinus() {
        return Constant.get(this.impl.getEpsilonMinus());
    }

    public Constant getPi() {
        return Constant.get(this.impl.getPi());
    }

    public Constant getBaseVectorInfinityDorst() {
        return Constant.get(this.impl.getBaseVectorInfinityDorst());
    }

    public Constant getBaseVectorOriginDorst() {
        return Constant.get(this.impl.getBaseVectorOriginDorst());
    }

    public Constant getBaseVectorInfinityDoran() {
        return Constant.get(this.impl.getBaseVectorInfinityDoran());
    }

    public Constant getBaseVectorOriginDoran() {
        return Constant.get(this.impl.getBaseVectorOriginDoran());
    }

    public Constant getMinkovskyBiVector() {
        return Constant.get(this.impl.getMinkovskyBiVector());
    }

    public Constant getEuclideanPseudoscalar() {
        return Constant.get(this.impl.getEuclideanPseudoscalar());
    }

    public Constant getPseudoscalar() {
        return Constant.get(this.impl.getPseudoscalar());
    }
}
