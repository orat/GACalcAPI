package de.orat.math.gacalc.spi;

import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.function.Supplier;

/**
 * Parent interface. Not intended to be implemented directly.
 */
public interface iConstantsFactory<IMV extends iMultivector<IMV>> {

    iExprGraphFactory<?, ?, ?> fac();

    /**
     * Supposed to be only used by implementer.
     */
    IMV cached(String name, Supplier<SparseDoubleMatrix> creator);

    IMV getSparseEmptyInstance();

    IMV getDenseEmptyInstance();

    default IMV getBaseVectorOrigin() {
        return cached("ε₀", () -> fac().createBaseVectorOrigin(1d));
    }

    default IMV getBaseVectorInfinity() {
        return cached("εᵢ", () -> fac().createBaseVectorInfinity(1d));
    }

    default IMV getBaseVectorX() {
        return cached("ε₁", () -> fac().createBaseVectorX(1d));
    }

    default IMV getBaseVectorY() {
        return cached("ε₂", () -> fac().createBaseVectorY(1d));
    }

    default IMV getBaseVectorZ() {
        return cached("ε₃", () -> fac().createBaseVectorZ(1d));
    }

    default IMV getEpsilonPlus() {
        return cached("ε₊", () -> fac().createEpsilonPlus());
    }

    default IMV getEpsilonMinus() {
        return cached("ε₋", () -> fac().createEpsilonMinus());
    }

    default IMV getPi() {
        return cached("π", () -> fac().createScalar(Math.PI));
    }

    default IMV getBaseVectorInfinityDorst() {
        return cached("∞", () -> fac().createBaseVectorInfinityDorst());
    }

    default IMV getBaseVectorOriginDorst() {
        return cached("o", () -> fac().createBaseVectorOriginDorst());
    }

    default IMV getBaseVectorInfinityDoran() {
        return cached("n", () -> fac().createBaseVectorInfinityDoran());
    }

    default IMV getBaseVectorOriginDoran() {
        return cached("ñ", () -> fac().createBaseVectorOriginDoran());
    }

    default IMV getMinkovskiBiVector() {
        return cached("E₀", () -> fac().createMinkovskiBiVector());
    }

    default IMV getEuclideanPseudoscalar() {
        return cached("E₃", () -> fac().createEuclideanPseudoscalar());
    }

    default IMV getPseudoscalar() {
        return cached("E", () -> fac().createPseudoscalar());
    }

    default IMV getInversePseudoscalar() {
        return cached("E˜", () -> fac().createInversePseudoscalar());
    }

    default IMV one() {
        return cached("1", () -> fac().createScalar(1d));
    }

    default IMV half() {
        return cached("0.5", () -> fac().createScalar(0.5d));
    }
}
