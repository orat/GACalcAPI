package util.cga.functions;

import de.orat.math.gacalc.api.ExprGraphFactory;
import de.orat.math.gacalc.api.FunctionSymbolic;
import de.orat.math.gacalc.api.GAExprGraphFactoryService;
import de.orat.math.gacalc.api.MultivectorNumeric;
import de.orat.math.gacalc.api.MultivectorPurelySymbolic;
import de.orat.math.gacalc.api.MultivectorSymbolic;
import java.util.ArrayList;
import java.util.List;

/**
 * Versuch, der sich nicht bewährt hat.
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 * @Deprecated
 */
public class RoundPointIPNS extends EuclideanTypeFunction {

    public RoundPointIPNS() {
        super("round_point_ipns", createUpParametersAndReturns(),
            createDownParametersAndReturns());
    }

    final static ParametersAndReturns createUpParametersAndReturns() {
        //ColumnVectorSparsity osparsity
        //MultivectorSymbolic o = fac.createMultivectorSymbolic("o", osparsity);
        MultivectorPurelySymbolic inf = fac.createMultivectorPurelySymbolicDense("inf");
        MultivectorPurelySymbolic E = fac.createMultivectorPurelySymbolicDense("E");
        List<MultivectorPurelySymbolic> parameters = new ArrayList<>();
        //parameters.add(o);
        parameters.add(inf);
        parameters.add(E);
        List<MultivectorSymbolic> returns = new ArrayList<>();
        //returns.add(o.addition(inf).addition(E));
        return new ParametersAndReturns(parameters, returns);
    }

    public MultivectorNumeric up(double x, double y, double z) throws Exception {
        double infValue = 0.5 * (x * x + y * y + z * z);
        MultivectorNumeric o = fac.createMultivectorNumeric(fac.createOrigin(1d));
        MultivectorNumeric inf = fac.createMultivectorNumeric(fac.createInf(infValue));
        MultivectorNumeric E = fac.createMultivectorNumeric(fac.createE(x, y, z));

        List<MultivectorNumeric> arguments = new ArrayList<>();
        arguments.add(o);
        arguments.add(inf);
        arguments.add(E);
        return (MultivectorNumeric) upFunctionSymbolic.callNumeric(arguments).get(0);
    }

    final static ParametersAndReturns createDownParametersAndReturns() {
        MultivectorSymbolic mv = fac.createMultivectorPurelySymbolicDense("mv");
        //MultivectorSymbolic mv1 = mv.negate().div(inf.lc(mn));
        // euclidean part rausziehen, scheint zu funktionieren
        //MultivectorSymbolic resultEuclidean = o.op(inf).ip(o.op(inf).op(result));
        return null;
    }

    /*public Point3d down(MultivectorNumeric mv){
        List<MultivectorNumeric> arguments = new ArrayList<>();
        arguments.add(o);
        return downFunctionSymbolic.callNumeric(arguments).get(0);
    }*/
}
