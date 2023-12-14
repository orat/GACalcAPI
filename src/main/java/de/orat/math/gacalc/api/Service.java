package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import de.orat.math.gacalc.spi.iFunctionSymbolic;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Service {
     
    private static Service service;
    private final ServiceLoader<iExprGraphFactory> loader;
    
    public static synchronized Service instance(){
        if (service == null){
            service = new Service();
        }
        return service;
    }
    
    private Service(){
        loader = ServiceLoader.load(iExprGraphFactory.class);
    }

    public Optional<iExprGraphFactory> getExprGraphFactory(){
        return loader.findFirst();
    }
    
}
