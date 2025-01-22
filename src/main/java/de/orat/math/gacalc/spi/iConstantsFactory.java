package de.orat.math.gacalc.spi;

import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.function.Supplier;

/**
 * Parent interface. Not intended to be implemented directly.
 */
public interface iConstantsFactory<IMV extends iMultivector<IMV>> {

    iExprGraphFactory<?, ?, ?> fac();

    IMV newConstant(String name, SparseDoubleMatrix definition);

    IMV newConstant(String name, IMV definition);

    IMV cached(Supplier<IMV> creator);

    IMV getSparseEmptyInstance();

    IMV getDenseEmptyInstance();

    default IMV getBaseVectorOrigin() {
        return cached(() -> newConstant("ε₀", fac().createBaseVectorOrigin(1d)));
    }

    default IMV getBaseVectorInfinity() {
        return cached(() -> newConstant("εᵢ", fac().createBaseVectorInfinity(1d)));
    }

    default IMV getBaseVectorX() {
        return cached(() -> newConstant("ε₁", fac().createBaseVectorX(1d)));
    }

    default IMV getBaseVectorY() {
        return cached(() -> newConstant("ε₂", fac().createBaseVectorY(1d)));
    }

    default IMV getBaseVectorZ() {
        return cached(() -> newConstant("ε₃", fac().createBaseVectorZ(1d)));
    }

    default IMV getEpsilonPlus() {
        return cached(() -> newConstant("ε₊", fac().createEpsilonPlus()));
    }

    default IMV getEpsilonMinus() {
        return cached(() -> newConstant("ε₋", fac().createEpsilonMinus()));
    }

    default IMV getPi() {
        return cached(() -> newConstant("π", fac().createScalar(Math.PI)));
    }

    default IMV getBaseVectorInfinityDorst() {
        return cached(() -> newConstant("∞", fac().createBaseVectorInfinityDorst()));
    }

    default IMV getBaseVectorOriginDorst() {
        return cached(() -> newConstant("o", fac().createBaseVectorOriginDorst()));
    }

    default IMV getBaseVectorInfinityDoran() {
        return cached(() -> newConstant("n", fac().createBaseVectorInfinityDoran()));
    }

    default IMV getBaseVectorOriginDoran() {
        return cached(() -> newConstant("ñ", fac().createBaseVectorOriginDoran()));
    }

    default IMV getMinkovskiBiVector() {
        return cached(() -> newConstant("E₀", fac().createMinkovskiBiVector()));
    }

    default IMV getEuclideanPseudoscalar() {
        return cached(() -> newConstant("E₃", fac().createEuclideanPseudoscalar()));
    }

    default IMV getPseudoscalar() {
        return cached(() -> newConstant("E", fac().createPseudoscalar()));
    }

    IMV getInversePseudoscalar();

    default IMV one() {
        return cached(() -> newConstant("1", fac().createScalar(1d)));
    }

    default IMV half() {
        return cached(() -> newConstant("0.5", fac().createScalar(0.5d)));
    }
}
