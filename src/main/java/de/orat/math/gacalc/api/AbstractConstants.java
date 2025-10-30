package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IConstants;
import de.orat.math.gacalc.spi.IMultivector;

// Package-private
abstract class AbstractConstants<ACM extends AbstractConstants<ACM, IConstantsFactory, AMV, IMV>, IConstantsFactory extends IConstants, AMV extends AbstractMultivector<AMV, IMV>, IMV extends IMultivector> {

    /**
     * Only to be used within this class and subclasses.
     */
    protected final IConstantsFactory impl;

    /**
     * Only to be used by subclasses.
     */
    protected AbstractConstants(IConstantsFactory impl) {
        this.impl = impl;
    }

    /**
     * Only to be used within this class and subclasses.
     */
    protected abstract AMV get_(IMV impl);

    public AMV getSparseEmptyInstance() {
        return get_((IMV) this.impl.getSparseEmptyInstance());
    }

    public AMV getDenseEmptyInstance() {
        return get_((IMV) this.impl.getDenseEmptyInstance());
    }

    public AMV getBaseVectorOrigin() {
        return get_((IMV) this.impl.getBaseVectorOrigin());
    }

    public AMV getBaseVectorInfinity() {
        return get_((IMV) this.impl.getBaseVectorInfinity());
    }

    public AMV getBaseVectorX() {
        return get_((IMV) this.impl.getBaseVectorX());
    }

    public AMV getBaseVectorY() {
        return get_((IMV) this.impl.getBaseVectorY());
    }

    public AMV getBaseVectorZ() {
        return get_((IMV) this.impl.getBaseVectorZ());
    }

    public AMV getEpsilonPlus() {
        return get_((IMV) this.impl.getEpsilonPlus());
    }

    public AMV getEpsilonMinus() {
        return get_((IMV) this.impl.getEpsilonMinus());
    }

    public AMV getPi() {
        return get_((IMV) this.impl.getPi());
    }

    public AMV getBaseVectorInfinityDorst() {
        return get_((IMV) this.impl.getBaseVectorInfinityDorst());
    }

    public AMV getBaseVectorOriginDorst() {
        return get_((IMV) this.impl.getBaseVectorOriginDorst());
    }

    public AMV getBaseVectorInfinityDoran() {
        return get_((IMV) this.impl.getBaseVectorInfinityDoran());
    }

    public AMV getBaseVectorOriginDoran() {
        return get_((IMV) this.impl.getBaseVectorOriginDoran());
    }

    public AMV getMinkovskyBiVector() {
        return get_((IMV) this.impl.getMinkovskiBiVector());
    }

    public AMV getEuclideanPseudoscalar() {
        return get_((IMV) this.impl.getEuclideanPseudoscalar());
    }

    public AMV getPseudoscalar() {
        return get_((IMV) this.impl.getPseudoscalar());
    }

    public AMV getInversePseudoscalar() {
        return get_((IMV) this.impl.getInversePseudoscalar());
    }

    public AMV one() {
        return get_((IMV) this.impl.one());
    }

    public AMV half() {
        return get_((IMV) this.impl.half());
    }
}
