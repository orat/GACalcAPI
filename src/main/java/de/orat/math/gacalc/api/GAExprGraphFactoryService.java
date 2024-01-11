package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import java.util.Optional;
import java.util.ServiceLoader;

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
	// Erweiterung um für eine spezifische Algebra eine spezifische Implementierung
	// zu beschaffen, also z.B. 4,1,1 und casadimx oder 4,1,1 und casadisx oder extvahlenmx
	// Es braucht auch einen Mechanismus um mit einer Implementierung umzugehen die
	// mit beliebigen Algebren Rpqr umgehen kann
	public Optional<ExprGraphFactory> getExprGraphFactory() {
		return loader.findFirst().map(impl -> ExprGraphFactory.get(impl));
	}
}
