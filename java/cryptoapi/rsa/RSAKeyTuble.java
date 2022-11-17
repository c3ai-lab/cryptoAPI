package cryptoapi.rsa;

import java.math.BigInteger;

public class RSAKeyTuble {
    public final BigInteger key;
    public final BigInteger modul;

    public RSAKeyTuble(BigInteger key, BigInteger modulo) {
        this.key = key;
        this.modul = modulo;
    }

    public String toString() {
        String s = "{";
        s += "key: " + this.key;
        s += ", modul: " + this.modul;
        s += "}";
        return s;
    }
}
