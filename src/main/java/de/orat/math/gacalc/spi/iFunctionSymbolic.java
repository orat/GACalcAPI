package de.orat.math.gacalc.spi;

import de.orat.math.gacalc.api.FunctionSymbolic.Callback;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public interface iFunctionSymbolic<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>, IMultivectorNumeric extends iMultivectorNumeric> {

    void init(Callback callback);

    String getName();

    int getArity();

    int getResultCount();

    @Override
    String toString();

    List<IMultivectorSymbolic> callSymbolic(List<IMultivectorSymbolic> arguments);

    List<IMultivectorNumeric> callNumeric(List<IMultivectorNumeric> arguments);
}
