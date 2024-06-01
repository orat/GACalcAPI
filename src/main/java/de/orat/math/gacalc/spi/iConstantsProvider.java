package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.ConstantsProvider;
import de.orat.math.sparsematrix.SparseDoubleMatrix;
import java.util.function.Supplier;

public interface iConstantsProvider<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>, IConstant extends iConstant<IMultivectorSymbolic>> {

    default void init(ConstantsProvider.Callback callback) {

    }

    iExprGraphFactory<?, ?> fac();

    IConstant newConstant(String name, SparseDoubleMatrix definition);

    IConstant cached(Supplier<IConstant> creator);

    default IConstant getBaseVectorOrigin() {
        return cached(() -> newConstant("ε₀", fac().createBaseVectorOrigin(1d)));
    }

    default IConstant getBaseVectorInfinity() {
        return cached(() -> newConstant("εᵢ", fac().createBaseVectorInfinity(1d)));
    }

    default IConstant getBaseVectorX() {
        return cached(() -> newConstant("ε₁", fac().createBaseVectorX(1d)));
    }

    default IConstant getBaseVectorY() {
        return cached(() -> newConstant("ε₂", fac().createBaseVectorY(1d)));
    }

    default IConstant getBaseVectorZ() {
        return cached(() -> newConstant("ε₃", fac().createBaseVectorZ(1d)));
    }

    default IConstant getEpsilonPlus() {
        return cached(() -> newConstant("ε₊", fac().createEpsilonPlus()));
    }

    default IConstant getEpsilonMinus() {
        return cached(() -> newConstant("ε₋", fac().createEpsilonMinus()));
    }

    default IConstant getPi() {
        return cached(() -> newConstant("π", fac().createScalar(Math.PI)));
    }

    default IConstant getBaseVectorInfinityDorst() {
        return cached(() -> newConstant("∞", fac().createBaseVectorInfinityDorst()));
    }

    default IConstant getBaseVectorOriginDorst() {
        return cached(() -> newConstant("o", fac().createBaseVectorOriginDorst()));
    }

    default IConstant getBaseVectorInfinityDoran() {
        return cached(() -> newConstant("n", fac().createBaseVectorInfinityDoran()));
    }

    default IConstant getBaseVectorOriginDoran() {
        return cached(() -> newConstant("ñ", fac().createBaseVectorOriginDoran()));
    }

    default IConstant getMinkovskyBiVector() {
        return cached(() -> newConstant("E₀", fac().createMinkovskyBiVector()));
    }

    default IConstant getEuclideanPseudoscalar() {
        return cached(() -> newConstant("E₃", fac().createEuclideanPseudoscalar()));
    }

    default IConstant getPseudoscalar() {
        return cached(() -> newConstant("E", fac().createPseudoscalar()));
    }

    default IConstant one() {
        return cached(() -> newConstant("1", fac().createScalar(1d)));
    }

    IConstant getInversePseudoscalar();
}
