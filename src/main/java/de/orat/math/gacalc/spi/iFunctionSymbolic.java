package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.FunctionSymbolic.Callback;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iFunctionSymbolic<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>> {

    void init(Callback callback);

    String getName();

    int getArity();

    int getResultCount();

    @Override
    String toString();

    List<? extends IMultivectorSymbolic> callSymbolic(List<? extends IMultivectorSymbolic> arguments);

    List<iMultivectorNumeric> callNumeric(List<iMultivectorNumeric> arguments);
}
