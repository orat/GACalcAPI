package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iLoopService;
import de.orat.math.gacalc.spi.iMultivectorSymbolic;
import de.orat.math.gacalc.spi.iMultivectorSymbolicArray;
import java.util.List;

public class LoopService {

    protected final iLoopService impl;

    protected static LoopService get(iLoopService impl) {
        LoopService result = new LoopService(impl);
        return result;
    }

    protected LoopService(iLoopService impl) {
        this.impl = impl;
    }

    protected iMultivectorSymbolicArray toiSymbolicArray(MultivectorSymbolicArray from) {
        var ifrom = from.stream().map(MultivectorSymbolic::getImpl).toList();
        iMultivectorSymbolicArray res = impl.toSymbolicArray(ifrom);
        return res;
    }

    protected static MultivectorSymbolicArray toSymbolicArray(iMultivectorSymbolicArray<?> ifrom) {
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

        List<iMultivectorSymbolicArray> ires = impl.map(iparamsSimple, iparamsArray, ireturnsArray, iargsSimple, iargsArray, iterations);

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

        iLoopService.AccumArrayListReturn<iMultivectorSymbolic, iMultivectorSymbolicArray> ires = impl.fold(iparamsAccum, iparamsSimple, iparamsArray, ireturnsAccum, ireturnsArray, iargsAccumInitial, iargsSimple, iargsArray, iterations);

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

        iLoopService.AccumArrayListReturn<iMultivectorSymbolicArray, iMultivectorSymbolicArray> ires = impl.mapaccum(iparamsAccum, iparamsSimple, iparamsArray, ireturnsAccum, ireturnsArray, iargsAccumInitial, iargsSimple, iargsArray, iterations);

        var iresAccum = ires.returnsAccum().stream().map(LoopService::toSymbolicArray).toList();
        var iresArray = ires.returnsArray().stream().map(LoopService::toSymbolicArray).toList();

        var results = new AccumArrayListReturn(iresAccum, iresArray);
        return results;
    }
}
