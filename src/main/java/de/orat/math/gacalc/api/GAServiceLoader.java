package de.orat.math.gacalc.api;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.ServiceLoader.Provider;
import de.orat.math.gacalc.spi.IGAFactory;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public final class GAServiceLoader {

    private static GAServiceLoader instance;
    private final ServiceLoader<IGAFactory> loader;

    public static synchronized GAServiceLoader instance() {
        if (instance == null) {
            instance = new GAServiceLoader();
        }
        return instance;
    }

    private GAServiceLoader() {
        loader = ServiceLoader.load(IGAFactory.class);
    }

    //TODO
    // Erweiterung um für eine spezifische Algebra anhand der Parametrisierung
    // zu beschaffen, also 4,1,1 oder 4,1,1.
    // Es braucht auch einen Mechanismus um mit einer Implementierung umzugehen die
    // mit beliebigen Algebren Rpqr umgehen kann
    public Optional<GAFactory> getGAFactory(String algebra, String implementation) {
        List<IGAFactory> impls = loader.stream().map(Provider::get)
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
        return Optional.of(GAFactory.get(impls.get(0)));
    }

    public Optional<GAFactory> getGAFactory(String algebra) {
        return loader.stream().map(Provider::get)
            .filter(f -> f.getAlgebra().equals(algebra))
            .findFirst()
            .map(GAFactory::get);
    }

    public static GAFactory getGAFactoryThrowing(String algebra, String implementation) throws NoSuchElementException {
        return GAServiceLoader.instance().getGAFactory(algebra, implementation).orElseThrow();
    }

    public static GAFactory getGAFactoryThrowing(String algebra) throws NoSuchElementException {
        return GAServiceLoader.instance().getGAFactory(algebra).orElseThrow();
    }
}
