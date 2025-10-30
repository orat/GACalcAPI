package de.orat.math.gacalc.api;

import java.util.List;
import de.orat.math.gacalc.spi.ILoopService;
import de.orat.math.gacalc.spi.IMultivectorExpression;
import de.orat.math.gacalc.spi.IMultivectorExpressionArray;

public class LoopService {

    protected final ILoopService impl;

    protected static LoopService get(ILoopService impl) {
        LoopService result = new LoopService(impl);
        return result;
    }

    protected LoopService(ILoopService impl) {
        this.impl = impl;
    }

    protected IMultivectorExpressionArray toiSymbolicArray(MultivectorSymbolicArray from) {
        var ifrom = from.stream().map(MultivectorSymbolic::getImpl).toList();
        IMultivectorExpressionArray res = impl.toExprArray(ifrom);
        return res;
    }

    protected static MultivectorSymbolicArray toSymbolicArray(IMultivectorExpressionArray<?> ifrom) {
        var mvList = ifrom.stream().map(MultivectorSymbolic::get).toList();
        var mvArray = new MultivectorSymbolicArray(mvList);
        return mvArray;
    }

    public List<MultivectorSymbolicArray> map(
        List<MultivectorPurelySymbolic> paramsSimple,
        List<MultivectorPurelySymbolic> paramsArray,
        List<MultivectorSymbolic> returnsArray,
        List<MultivectorSymbolic> argsSimple,
        List<MultivectorSymbolicArray> argsArray,
        int iterations) {
        var iparamsSimple = paramsSimple.stream().map(MultivectorPurelySymbolic::getImpl).toList();
        var iparamsArray = paramsArray.stream().map(MultivectorPurelySymbolic::getImpl).toList();
        var ireturnsArray = returnsArray.stream().map(MultivectorSymbolic::getImpl).toList();
        var iargsSimple = argsSimple.stream().map(MultivectorSymbolic::getImpl).toList();
        var iargsArray = argsArray.stream().map(arr -> toiSymbolicArray(arr)).toList();

        List<IMultivectorExpressionArray> ires = impl.map(iparamsSimple, iparamsArray, ireturnsArray, iargsSimple, iargsArray, iterations);

        var results = ires.stream().map(LoopService::toSymbolicArray).toList();
        return results;
    }

    public static record AccumArrayListReturn<K, V>(List<K> returnsAccum, List<V> returnsArray) {

    }

    public AccumArrayListReturn<MultivectorSymbolic, MultivectorSymbolicArray> fold(
        List<MultivectorPurelySymbolic> paramsAccum,
        List<MultivectorPurelySymbolic> paramsSimple,
        List<MultivectorPurelySymbolic> paramsArray,
        List<MultivectorSymbolic> returnsAccum,
        List<MultivectorSymbolic> returnsArray,
        List<MultivectorSymbolic> argsAccumInitial,
        List<MultivectorSymbolic> argsSimple,
        List<MultivectorSymbolicArray> argsArray,
        int iterations) {
        var iparamsAccum = paramsAccum.stream().map(MultivectorPurelySymbolic::getImpl).toList();
        var iparamsSimple = paramsSimple.stream().map(MultivectorPurelySymbolic::getImpl).toList();
        var iparamsArray = paramsArray.stream().map(MultivectorPurelySymbolic::getImpl).toList();
        var ireturnsAccum = returnsAccum.stream().map(MultivectorSymbolic::getImpl).toList();
        var ireturnsArray = returnsArray.stream().map(MultivectorSymbolic::getImpl).toList();
        var iargsAccumInitial = argsAccumInitial.stream().map(MultivectorSymbolic::getImpl).toList();
        var iargsSimple = argsSimple.stream().map(MultivectorSymbolic::getImpl).toList();
        var iargsArray = argsArray.stream().map(arr -> toiSymbolicArray(arr)).toList();

        ILoopService.AccumArrayListReturn<IMultivectorExpression, IMultivectorExpressionArray> ires = impl.fold(iparamsAccum, iparamsSimple, iparamsArray, ireturnsAccum, ireturnsArray, iargsAccumInitial, iargsSimple, iargsArray, iterations);

        var iresAccum = ires.returnsAccum().stream().map(MultivectorSymbolic::get).toList();
        var iresArray = ires.returnsArray().stream().map(LoopService::toSymbolicArray).toList();

        var results = new AccumArrayListReturn(iresAccum, iresArray);
        return results;
    }

    public AccumArrayListReturn<MultivectorSymbolicArray, MultivectorSymbolicArray> mapaccum(
        List<MultivectorPurelySymbolic> paramsAccum,
        List<MultivectorPurelySymbolic> paramsSimple,
        List<MultivectorPurelySymbolic> paramsArray,
        List<MultivectorSymbolic> returnsAccum,
        List<MultivectorSymbolic> returnsArray,
        List<MultivectorSymbolic> argsAccumInitial,
        List<MultivectorSymbolic> argsSimple,
        List<MultivectorSymbolicArray> argsArray,
        int iterations) {
        var iparamsAccum = paramsAccum.stream().map(MultivectorPurelySymbolic::getImpl).toList();
        var iparamsSimple = paramsSimple.stream().map(MultivectorPurelySymbolic::getImpl).toList();
        var iparamsArray = paramsArray.stream().map(MultivectorPurelySymbolic::getImpl).toList();
        var ireturnsAccum = returnsAccum.stream().map(MultivectorSymbolic::getImpl).toList();
        var ireturnsArray = returnsArray.stream().map(MultivectorSymbolic::getImpl).toList();
        var iargsAccumInitial = argsAccumInitial.stream().map(MultivectorSymbolic::getImpl).toList();
        var iargsSimple = argsSimple.stream().map(MultivectorSymbolic::getImpl).toList();
        var iargsArray = argsArray.stream().map(arr -> toiSymbolicArray(arr)).toList();

        ILoopService.AccumArrayListReturn<IMultivectorExpressionArray, IMultivectorExpressionArray> ires = impl.mapaccum(iparamsAccum, iparamsSimple, iparamsArray, ireturnsAccum, ireturnsArray, iargsAccumInitial, iargsSimple, iargsArray, iterations);

        var iresAccum = ires.returnsAccum().stream().map(LoopService::toSymbolicArray).toList();
        var iresArray = ires.returnsArray().stream().map(LoopService::toSymbolicArray).toList();

        var results = new AccumArrayListReturn(iresAccum, iresArray);
        return results;
    }
}
