package util.cga.functions;

import de.orat.math.gacalc.api.GAFactory;
import de.orat.math.gacalc.api.GAFunction;
import de.orat.math.gacalc.api.GAServiceLoader;
import de.orat.math.gacalc.api.MultivectorVariable;
import de.orat.math.gacalc.api.MultivectorExpression;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class EuclideanTypeFunction {

    final static GAFactory fac = GAServiceLoader.getGAFactoryThrowing("cga");

    final GAFunction upFunctionSymbolic;
    final GAFunction downFunctionSymbolic;

    record ParametersAndReturns(List<MultivectorVariable> parameters,
        List<MultivectorExpression> returns) {

    }

    EuclideanTypeFunction(String name, ParametersAndReturns upParametersAndReturns,
        ParametersAndReturns downParametersAndReturns) {
        upFunctionSymbolic = fac.createFunction(name,
            upParametersAndReturns.parameters, upParametersAndReturns.returns);
        downFunctionSymbolic = fac.createFunction(name,
            downParametersAndReturns.parameters, downParametersAndReturns.returns);
    }
}
