package de.orat.math.gacalc.api;

import de.orat.math.gacalc.spi.iExprGraphFactory;
import de.orat.math.sparsematrix.ColumnVectorSparsity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class TestService {
    
    iExprGraphFactory exprGraphFactory;
    
    public TestService() {
        Optional<iExprGraphFactory> service = Service.instance().getExprGraphFactory();
        if (service.isPresent()){
            exprGraphFactory = service.get();
        } else {
            System.out.println("No implementation of iExprGraphFactory found!");
        }
    }
    
    private boolean equals(double[] a, double[] b){
        if (a.length != b.length) throw new IllegalArgumentException("a.length != b.length");
        for (int i=0;i<a.length;i++){
            if (a[i] != b[i]) return false;
        }
        return true;
    }
    
    private boolean equals(double[] a, double[] b, ColumnVectorSparsity sparsity){
        if (a.length != b.length) throw new IllegalArgumentException("a.length != b.length");
        int[] rows = sparsity.getrow();
        for (int i=0;i<rows.length;i++){
            if (a[rows[i]] != b[rows[i]]) return false;
        }
        return true;
    }
    
    @Test
    public void testReverse(){
       
        MultivectorSymbolic mv = exprGraphFactory.createMultivectorSymbolic("mv", 1);
        System.out.println("mv (sparsity): "+mv.getSparsity().toString());
        System.out.println("mv: "+mv.toString());
        MultivectorSymbolic result = mv.reverse();
        System.out.println("result (sym): "+result.toString());
        
        List<MultivectorSymbolic> parameters = new ArrayList<>();
        parameters.add(mv);
        
        List<MultivectorSymbolic> res = new ArrayList<>();
        res.add(result);
        FunctionSymbolic f = exprGraphFactory.createFunctionSymbolic("f", parameters, res);
        
        List<MultivectorNumeric> arguments = new ArrayList<>();
        double[] randomValues = exprGraphFactory.createRandomCGAMultivector();
        MultivectorNumeric arg = exprGraphFactory.createMultivectorNumeric(randomValues);
        arguments.add(arg);
        
        try {
            List<MultivectorNumeric> result2 = f.callNumeric(arguments);
            MultivectorNumeric out = result2.iterator().next();
            //System.out.println("b=reverse(a)="+out.toString());
            double[] values = out.elements();
            assertTrue(equals(values, reverse(randomValues), mv.getSparsity()));
        } catch (Exception e){}
    }
    private double[] reverse(double[] a){
        double[] res = new double[32];
        res[0]=a[0];
	res[1]=a[1];
	res[2]=a[2];
	res[3]=a[3];
	res[4]=a[4];
	res[5]=a[5];
	res[6]=-a[6];
	res[7]=-a[7];
	res[8]=-a[8];
	res[9]=-a[9];
	res[10]=-a[10];
	res[11]=-a[11];
	res[12]=-a[12];
	res[13]=-a[13];
	res[14]=-a[14];
	res[15]=-a[15];
	res[16]=-a[16];
	res[17]=-a[17];
	res[18]=-a[18];
	res[19]=-a[19];
	res[20]=-a[20];
	res[21]=-a[21];
	res[22]=-a[22];
	res[23]=-a[23];
	res[24]=-a[24];
	res[25]=-a[25];
	res[26]=a[26];
	res[27]=a[27];
	res[28]=a[28];
	res[29]=a[29];
	res[30]=a[30];
	res[31]=a[31];
        return res;
    }
}
