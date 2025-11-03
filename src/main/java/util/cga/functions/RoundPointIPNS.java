package util.cga.functions;

import de.orat.math.gacalc.api.GAFactory;
import de.orat.math.gacalc.api.GAFunction;
import de.orat.math.gacalc.api.GAServiceLoader;
import de.orat.math.gacalc.api.MultivectorValue;
import de.orat.math.gacalc.api.MultivectorVariable;
import de.orat.math.gacalc.api.MultivectorExpression;
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
        MultivectorVariable inf = fac.createVariableDense("inf");
        MultivectorVariable E = fac.createVariableDense("E");
        List<MultivectorVariable> parameters = new ArrayList<>();
        //parameters.add(o);
        parameters.add(inf);
        parameters.add(E);
        List<MultivectorExpression> returns = new ArrayList<>();
        //returns.add(o.addition(inf).addition(E));
        return new ParametersAndReturns(parameters, returns);
    }

    public MultivectorValue up(double x, double y, double z) throws Exception {
        double infValue = 0.5 * (x * x + y * y + z * z);
        MultivectorValue o = fac.createValue(fac.createOrigin(1d));
        MultivectorValue inf = fac.createValue(fac.createInf(infValue));
        MultivectorValue E = fac.createValue(fac.createE(x, y, z));

        List<MultivectorValue> arguments = new ArrayList<>();
        arguments.add(o);
        arguments.add(inf);
        arguments.add(E);
        return (MultivectorValue) upFunctionSymbolic.callValue(arguments).get(0);
    }

    final static ParametersAndReturns createDownParametersAndReturns() {
        MultivectorExpression mv = fac.createVariableDense("mv");
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
