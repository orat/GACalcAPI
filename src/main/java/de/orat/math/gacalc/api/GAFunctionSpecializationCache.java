package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.IGAFunctionSpecializationCache;
import de.orat.math.gacalc.spi.IMultivectorExpression;
import de.orat.math.gacalc.spi.IMultivectorVariable;
import java.util.List;
import java.util.function.Function;

/**
 * API facade for a backend-owned symbolic function-specialization cache.
 */
public final class GAFunctionSpecializationCache {

    private final IGAFunctionSpecializationCache impl;

    GAFunctionSpecializationCache(IGAFunctionSpecializationCache impl) {
        this.impl = impl;
    }

    public List<MultivectorExpression> executeCached(
        List<? extends MultivectorExpression> arguments,
        String funcName,
        Function<List<MultivectorVariable>, List<MultivectorExpression>> creator
    ) {
        List<IMultivectorExpression> implArguments = arguments.stream()
            .map(MultivectorExpression::getImpl)
            .toList();
        Function<List<IMultivectorVariable>, List<IMultivectorExpression>> implCreator = formalVariables ->
            creator.apply(formalVariables.stream().map(MultivectorVariable::get).toList()).stream()
                .map(MultivectorExpression::getImpl)
                .toList();
        return executeCachedImpl(implArguments, funcName, implCreator);
    }

    /**
     * Localizes the raw SPI bridge inherited from {@link GAFactory}.  The
     * public facade only ever exposes API expressions.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private List<MultivectorExpression> executeCachedImpl(
        List<IMultivectorExpression> arguments,
        String funcName,
        Function<List<IMultivectorVariable>, List<IMultivectorExpression>> creator
    ) {
        List<IMultivectorExpression> results = impl.executeCached(arguments, funcName, creator);
        return results.stream().map(MultivectorExpression::get).toList();
    }

    public void clearCache() {
        impl.clearCache();
    }

    public int getCacheSize() {
        return impl.getCacheSize();
    }
}
