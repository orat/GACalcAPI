package de.orat.math.gacalc.spi;

import java.util.List;

public interface iLoopService<IMVSymbolic extends iMultivectorSymbolic<IMVSymbolic>, IMVPurelySymbolic extends iMultivectorPurelySymbolic<IMVSymbolic>, IMVSArray extends iMultivectorSymbolicArray<IMVSymbolic>> {

    // Only to be used in the gacalc implementation. Not part of the gacalc API.
    // Method only needed by LoopService internally.
    iMultivectorSymbolicArray<IMVSymbolic> toSymbolicArray(List<IMVSymbolic> from);

    List<IMVSArray> map(
        List<IMVPurelySymbolic> paramsSimple,
        List<IMVPurelySymbolic> paramsArray,
        List<IMVSymbolic> returnsArray,
        List<IMVSymbolic> argsSimple,
        List<IMVSArray> argsArray,
        int iterations);

    public static record AccumArrayListReturn<K, V>(List<K> returnsAccum, List<V> returnsArray) {

    }

    AccumArrayListReturn<IMVSymbolic, IMVSArray> fold(
        List<IMVPurelySymbolic> paramsAccum,
        List<IMVPurelySymbolic> paramsSimple,
        List<IMVPurelySymbolic> paramsArray,
        List<IMVSymbolic> returnsAccum,
        List<IMVSymbolic> returnsArray,
        List<IMVSymbolic> argsAccumInitial,
        List<IMVSymbolic> argsSimple,
        List<IMVSArray> argsArray,
        int iterations);

    AccumArrayListReturn<IMVSArray, IMVSArray> mapaccum(
        List<IMVPurelySymbolic> paramsAccum,
        List<IMVPurelySymbolic> paramsSimple,
        List<IMVPurelySymbolic> paramsArray,
        List<IMVSymbolic> returnsAccum,
        List<IMVSymbolic> returnsArray,
        List<IMVSymbolic> argsAccumInitial,
        List<IMVSymbolic> argsSimple,
        List<IMVSArray> argsArray,
        int iterations);
}
