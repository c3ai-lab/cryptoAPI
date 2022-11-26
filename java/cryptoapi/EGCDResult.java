package cryptoapi;

import java.math.BigInteger;

public class EGCDResult{
    public final BigInteger gcd;
    public final BigInteger s;
    public final BigInteger t;

    public EGCDResult(BigInteger gcd,BigInteger s,BigInteger t){
        this.gcd = gcd;
        this.s = s;
        this.t = t;
    }
}
