package util.cga.functions;

import de.orat.math.gacalc.api.ExprGraphFactory;
import de.orat.math.gacalc.api.FunctionSymbolic;
import de.orat.math.gacalc.api.GAExprGraphFactoryService;
import de.orat.math.gacalc.api.MultivectorPurelySymbolic;
import de.orat.math.gacalc.api.MultivectorSymbolic;
import java.util.List;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class EuclideanTypeFunction {

    final static ExprGraphFactory fac = GAExprGraphFactoryService.instance().getExprGraphFactory().get();

    final FunctionSymbolic upFunctionSymbolic;
    final FunctionSymbolic downFunctionSymbolic;

    record ParametersAndReturns(List<MultivectorPurelySymbolic> parameters,
        List<MultivectorSymbolic> returns) {

    }

    EuclideanTypeFunction(String name, ParametersAndReturns upParametersAndReturns,
        ParametersAndReturns downParametersAndReturns) {
        upFunctionSymbolic = fac.createFunctionSymbolic(name,
            upParametersAndReturns.parameters, upParametersAndReturns.returns);
        downFunctionSymbolic = fac.createFunctionSymbolic(name,
            downParametersAndReturns.parameters, downParametersAndReturns.returns);
    }
}
