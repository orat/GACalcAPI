package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public final class GAExprGraphFactoryService {

    private static GAExprGraphFactoryService service;
    private final ServiceLoader<iExprGraphFactory> loader;

    public static synchronized GAExprGraphFactoryService instance() {
        if (service == null) {
            service = new GAExprGraphFactoryService();
        }
        return service;
    }

    private GAExprGraphFactoryService() {
        loader = ServiceLoader.load(iExprGraphFactory.class);
    }

    //TODO
    // Erweiterung um für eine spezifische Algebra anhand der Parametrisierung
    // zu beschaffen, also 4,1,1 oder 4,1,1.
    // Es braucht auch einen Mechanismus um mit einer Implementierung umzugehen die
    // mit beliebigen Algebren Rpqr umgehen kann
    public Optional<ExprGraphFactory> getExprGraphFactory(String algebra, String implementation) {
        List<iExprGraphFactory> impls = loader.stream().map(Provider::get)
            .filter(f -> f.getAlgebra().equals(algebra))
            .filter(f -> f.getImplementationName().equals(implementation))
            .toList();
        if (impls.isEmpty()) {
            return Optional.empty();
        }
        if (impls.size() > 1) {
            throw new RuntimeException(String.format("Found %s implementations of algebra \"%s\" with name \"%s\".",
                impls.size(), algebra, implementation));
        }
        return Optional.of(ExprGraphFactory.get(impls.get(0)));
    }

    public Optional<ExprGraphFactory> getExprGraphFactory(String algebra) {
        return loader.stream().map(Provider::get)
            .filter(f -> f.getAlgebra().equals(algebra))
            .findFirst()
            .map(ExprGraphFactory::get);
    }

    public static ExprGraphFactory getExprGraphFactoryThrowing(String algebra, String implementation) throws NoSuchElementException {
        return GAExprGraphFactoryService.instance().getExprGraphFactory(algebra, implementation).orElseThrow();
    }

    public static ExprGraphFactory getExprGraphFactoryThrowing(String algebra) throws NoSuchElementException {
        return GAExprGraphFactoryService.instance().getExprGraphFactory(algebra).orElseThrow();
    }
}
