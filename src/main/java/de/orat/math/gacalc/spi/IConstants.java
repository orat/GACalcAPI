package de.orat.math.gacalc.spi;

import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.function.Supplier;

public interface IConstants<MV extends IMultivector<MV>> {

    IGAFactory<?, ?, ?> fac();

    /**
     * Supposed to be only used by implementer.
     */
    MV cached(String name, Supplier<SparseDoubleMatrix> creator);

    // jede
    MV getSparseEmptyInstance();

    MV getDenseEmptyInstance();

    default MV getBaseVectorOrigin() {
        return cached("ε₀", () -> fac().createBaseVectorOrigin(1d));
    }

    default MV getBaseVectorInfinity() {
        return cached("εᵢ", () -> fac().createBaseVectorInfinity(1d));
    }

    default MV getBaseVectorX() {
        return cached("ε₁", () -> fac().createBaseVectorX(1d));
    }

    default MV getBaseVectorY() {
        return cached("ε₂", () -> fac().createBaseVectorY(1d));
    }

    default MV getBaseVectorZ() {
        return cached("ε₃", () -> fac().createBaseVectorZ(1d));
    }

    default MV getEpsilonPlus() {
        return cached("ε₊", () -> fac().createEpsilonPlus());
    }

    default MV getEpsilonMinus() {
        return cached("ε₋", () -> fac().createEpsilonMinus());
    }

    default MV getPi() {
        return cached("π", () -> fac().createScalar(Math.PI));
    }

    default MV getBaseVectorInfinityDorst() {
        return cached("∞", () -> fac().createBaseVectorInfinityDorst());
    }

    default MV getBaseVectorOriginDorst() {
        return cached("o", () -> fac().createBaseVectorOriginDorst());
    }

    default MV getBaseVectorInfinityDoran() {
        return cached("n", () -> fac().createBaseVectorInfinityDoran());
    }

    default MV getBaseVectorOriginDoran() {
        return cached("ñ", () -> fac().createBaseVectorOriginDoran());
    }

    default MV getMinkovskiBiVector() {
        return cached("E₀", () -> fac().createMinkovskiBiVector());
    }

    default MV getEuclideanPseudoscalar() {
        return cached("E₃", () -> fac().createEuclideanPseudoscalar());
    }

    default MV getPseudoscalar() {
        return cached("E", () -> fac().createPseudoscalar());
    }

    default MV getInversePseudoscalar() {
        return cached("E˜", () -> fac().createInversePseudoscalar());
    }

    default MV one() {
        return cached("1", () -> fac().createScalar(1d));
    }

    default MV half() {
        return cached("0.5", () -> fac().createScalar(0.5d));
    }
}
