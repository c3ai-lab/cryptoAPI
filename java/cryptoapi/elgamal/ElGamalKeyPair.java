package cryptoapi.elgamal;

import java.math.BigInteger;

public class ElGamalKeyPair {
    public final ElgamalKeyParams publicParams;
    public final BigInteger publicKey;
    private final BigInteger secretKey;

    protected ElGamalKeyPair(ElgamalKeyParams publicParams, BigInteger secretKey, BigInteger publicKey) {
        this.secretKey = secretKey;
        this.publicKey = publicKey;
        this.publicParams = publicParams;
    }

    protected BigInteger getSecretKey() {
        return this.secretKey;
    }

    @Override
    public String toString() {
        String s = "{";
        s += "publicParams: " + this.publicParams.toString();
        s += ", publicKey: " + this.publicKey;
        s += ", secretKey: " + this.secretKey;
        s += "}";
        return s;
    }
}
