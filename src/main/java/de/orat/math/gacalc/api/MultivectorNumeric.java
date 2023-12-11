package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iFunctionSymbolic;
import de.orat.math.gacalc.spi.iMultivectorNumeric;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class MultivectorNumeric {
    
    final iMultivectorNumeric impl;
    
    public static MultivectorNumeric get(iMultivectorNumeric impl) {
        MultivectorNumeric result = new MultivectorNumeric(impl);
        Callback callback = new Callback(result);
        impl.init(callback);
        return result;
    }
    
    private MultivectorNumeric(iMultivectorNumeric impl){
        this.impl = impl;
    }
    
    //protected MultivectorNumeric(DM dm) {
    //        this.dm = dm;
    //}

    
    public static final class Callback {

        private final MultivectorNumeric api;

        Callback(MultivectorNumeric api){
            this.api = api;
        }
        
        //TODO
        // add methods needed by the spi implementation
        
        public iMultivectorNumeric getImpl(){
            return api.impl;
        }
        
    }
     
    /*public MultivectorNumeric() {
        this.dm = new DM(2, 2);
    }

    public MultivectorNumeric(MultivectorNumeric mn) {
        this.dm = new DM(mn.dm);
    }

    public void set(double num, int x, int y) {
        this.dm.at(x, y).assign(new DM(num));
    }*/

    @Override
    public String toString() {
        return impl.toString();
        //return this.dm.toString();
    }
}