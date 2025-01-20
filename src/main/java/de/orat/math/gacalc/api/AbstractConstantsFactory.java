package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iConstantsFactory;
import de.orat.math.gacalc.spi.iMultivector;

// Package-private
abstract class AbstractConstantsFactory<ACM extends AbstractConstantsFactory<ACM, IConstantsFactory, AMV, IMultivector>, IConstantsFactory extends iConstantsFactory, AMV extends AbstractMultivector<AMV, IMultivector>, IMultivector extends iMultivector> {

    /**
     * Only to be used within this class and subclasses.
     */
    protected final IConstantsFactory impl;

    /**
     * Only to be used by subclasses.
     */
    protected AbstractConstantsFactory(IConstantsFactory impl) {
        this.impl = impl;
    }

    /**
     * Only to be used within this class and subclasses.
     */
    protected abstract AMV get_(IMultivector impl);

    public AMV getBaseVectorOrigin() {
        return get_((IMultivector) this.impl.getBaseVectorOrigin());
    }

    public AMV getBaseVectorInfinity() {
        return get_((IMultivector) this.impl.getBaseVectorInfinity());
    }

    public AMV getBaseVectorX() {
        return get_((IMultivector) this.impl.getBaseVectorX());
    }

    public AMV getBaseVectorY() {
        return get_((IMultivector) this.impl.getBaseVectorY());
    }

    public AMV getBaseVectorZ() {
        return get_((IMultivector) this.impl.getBaseVectorZ());
    }

    public AMV getEpsilonPlus() {
        return get_((IMultivector) this.impl.getEpsilonPlus());
    }

    public AMV getEpsilonMinus() {
        return get_((IMultivector) this.impl.getEpsilonMinus());
    }

    public AMV getPi() {
        return get_((IMultivector) this.impl.getPi());
    }

    public AMV getBaseVectorInfinityDorst() {
        return get_((IMultivector) this.impl.getBaseVectorInfinityDorst());
    }

    public AMV getBaseVectorOriginDorst() {
        return get_((IMultivector) this.impl.getBaseVectorOriginDorst());
    }

    public AMV getBaseVectorInfinityDoran() {
        return get_((IMultivector) this.impl.getBaseVectorInfinityDoran());
    }

    public AMV getBaseVectorOriginDoran() {
        return get_((IMultivector) this.impl.getBaseVectorOriginDoran());
    }

    public AMV getMinkovskyBiVector() {
        return get_((IMultivector) this.impl.getMinkovskiBiVector());
    }

    public AMV getEuclideanPseudoscalar() {
        return get_((IMultivector) this.impl.getEuclideanPseudoscalar());
    }

    public AMV getPseudoscalar() {
        return get_((IMultivector) this.impl.getPseudoscalar());
    }

    public AMV getInversePseudoscalar() {
        return get_((IMultivector) this.impl.getInversePseudoscalar());
    }

    public AMV one() {
        return get_((IMultivector) this.impl.one());
    }

    public AMV half() {
        return get_((IMultivector) this.impl.half());
    }
}
