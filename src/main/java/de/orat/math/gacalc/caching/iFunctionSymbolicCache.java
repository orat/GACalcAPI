package de.orat.math.gacalc.caching;

import de.orat.math.gacalc.spi.iMultivectorSymbolic;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public interface iFunctionSymbolicCache<IMultivectorSymbolic extends iMultivectorSymbolic<IMultivectorSymbolic>, ICachedSymbolicMultivector extends iCachedSymbolicMultivector<IMultivectorSymbolic, ICachedSymbolicMultivector>> {

    ICachedSymbolicMultivector getOrCreateSymbolicFunction(String name, List<IMultivectorSymbolic> args, Function<List<IMultivectorSymbolic>, IMultivectorSymbolic> res);

    /**
     * @return Unmodifiable Set
     */
    Set<String> getCachedFunktionNames();

    String createBipedFuncName(String name, int[] arg1Grades, int[] arg2Grades);

    // String createBipedFuncName(String name, int[] arg1Grades, String constName);
}
