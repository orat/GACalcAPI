package de.orat.math.gacalc.util;

import java.util.List;

/**
 *
 * @author Oliver Rettig (Oliver.Rettig@orat.de)
 */
public class Algebra {

    private final int p, q, r;
    //private final String[] basisNames;

    public Algebra(int p, int q, int r) {
        this.p = p;
        this.q = q;
        this.r = r;
        //this.basisNames = createBasisNames();
    }

    /**
     * Calculate the total number of dimensions.
     */
    // var tot = options.tot = (options.tot||(p||0)+(q||0)+(r||0)||(options.basis&&options.basis.length))|0;
    private int tot() {
        return p + q + r;
    }

    /**
     * Create Basis names.
     *
     * Unless specified, generate a full set of Clifford basis names. We generate them as an array of strings
     * by starting from numbers in binary representation and changing the set bits into their relative
     * position. Basis names are ordered first per grade, then lexically (not cyclic!). For 10 or more
     * dimensions all names will be double digits ! 1e01 instead of 1e1 ..
     */
    /*var basis=(options.basis&&(options.basis.length==2**tot||r<0||options.Cayley)&&options.basis)||[...Array(2**tot)]           // => [undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined]
              .map((x,xi)=>(((1<<30)+xi).toString(2)).slice(-tot||-1)                                                           // => ["000", "001", "010", "011", "100", "101", "110", "111"]  (index of array in base 2)
              .replace(/./g,(a,ai)=>a=='0'?'':String.fromCharCode(66+ai-(r!=0))))                                               // => ["", "3", "2", "23", "1", "13", "12", "123"] (1 bits replaced with their positions, 0's removed)
              .sort((a,b)=>(a.toString().length==b.toString().length)?(a>b?1:b>a?-1:0):a.toString().length-b.toString().length) // => ["", "1", "2", "3", "12", "13", "23", "123"] (sorted numerically)
              .map(x=>x&&'e'+(x.replace(/./g,x=>('0'+(x.charCodeAt(0)-65)).slice(tot>9?-2:-1) ))||'1')                          // => ["1", "e1", "e2", "e3", "e12", "e13", "e23", "e123"] (converted to commonly used basis names)
     */
    public String[] createBladeNames(List<String> baseNames) {
        String[] result = new String[(int) Math.pow(2, tot())];
        /*var basis=(options.basis&&(options.basis.length==2**tot||r<0||options.Cayley)&&options.basis)||[...Array(2**tot)]           // => [undefined, undefined, undefined, undefined, undefined, undefined, undefined, undefined]
              .map((x,xi)=>(((1<<30)+xi).toString(2)).slice(-tot||-1)                                                           // => ["000", "001", "010", "011", "100", "101", "110", "111"]  (index of array in base 2)
              .replace(/./g,(a,ai)=>a=='0'?'':String.fromCharCode(66+ai-(r!=0))))                                               // => ["", "3", "2", "23", "1", "13", "12", "123"] (1 bits replaced with their positions, 0's removed)
              .sort((a,b)=>(a.toString().length==b.toString().length)?(a>b?1:b>a?-1:0):a.toString().length-b.toString().length) // => ["", "1", "2", "3", "12", "13", "23", "123"] (sorted numerically)
              .map(x=>x&&'e'+(x.replace(/./g,x=>('0'+(x.charCodeAt(0)-65)).slice(tot>9?-2:-1) ))||'1')
         */
        //TODO
        return result;
    }

    // String-simplify a concatenation of two basis blades. (and supports custom basis names e.g. e21 instead of e12)
    // This is the function that implements e1e1 = +1/-1/0 and e1e2=-e2e1. The brm function creates the remap dictionary.
    /*var simplify = (s,p,q,r)=>{
          var sign=1,c,l,t=[],f=true,ss=s.match(tot>9?/(\d\d)/g:/(\d)/g);if (!ss) return s; s=ss; l=s.length;
          while (f) { f=false;
          // implement Ex*Ex = metric.
            for (var i=0; i<l;) if (s[i]===s[i+1]) { if (options.metric) sign*=options.metric[s[i]-basis[1][1]]; else if ((s[i]-low)>=(p+r)) sign*=-1; else if ((s[i]-low)<r) sign=0;i+=2; f=true; } else t.push(s[i++]);
          // implement Ex*Ey = -Ey*Ex while sorting basis vectors.
            for (var i=0; i<t.length-1; i++) if (t[i]>t[i+1]) { c=t[i];t[i]=t[i+1];t[i+1]=c;sign*=-1;f=true; break;} if (f) { s=t;t=[];l=s.length; }
          }
          var ret=(sign==0)?'0':((sign==1)?'':'-')+(t.length?'e'+t.join(''):'1'); return (brm&&brm[ret])||(brm&&brm['-'+ret]&&'-'+brm['-'+ret])||ret;
        },
        brm=(x=>{ var ret={}; for (var i in basis) ret[basis[i]=='1'?'1':simplify(basis[i],p,q,r)] = basis[i]; return ret; })(basis);
     */
    static void simplify(String s, int p, int q, int r) {
        int sign = 1, c, l;
        int[] t;
        boolean f = true;
    }

    // Faster and degenerate-metric-resistant dualization. (a remapping table that maps items into their duals).
    /*var drm=basis.map((a,i)=>{ return {a:a,i:i} })
                 .sort((a,b)=>a.a.length>b.a.length?1:a.a.length<b.a.length?-1:(+a.a.slice(1).split('').sort().join(''))-(+b.a.slice(1).split('').sort().join('')) )
                 .map(x=>x.i).reverse(),
        drms=drm.map((x,i)=>(x==0||i==0)?1:simplify(basis[x]+basis[i])[0]=='-'?-1:1);
     */
    /**
     * Faster and degenerate-metric-resistant dualization.
     *
     * (a remapping table that maps items into their duals).
     */
    /*static String[] drm(){
    
    }*/
}
