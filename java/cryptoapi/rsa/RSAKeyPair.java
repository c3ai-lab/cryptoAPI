package cryptoapi.rsa;

import java.math.BigInteger;

public class RSAKeyPair {

    public final RSAKeyTuble publicKey;

    protected final RSAKeyTuble secretKey;

    public RSAKeyPair(BigInteger publicKey, BigInteger secretKey, BigInteger modul) {
        this.publicKey = new RSAKeyTuble(publicKey, modul);
        this.secretKey = new RSAKeyTuble(secretKey, modul);
    }

    public String toString() {
        String s = "{";
        s += "publicKey: " + this.publicKey;
        s += ", secrectKey: " + this.secretKey;
        s += "}";
        return s;
    }
}
