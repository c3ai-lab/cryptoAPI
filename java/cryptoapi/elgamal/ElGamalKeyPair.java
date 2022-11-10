package cryptoapi.elgamal;

import cryptoapi.MathLib;

import java.math.BigInteger;

public class ElGamalKeyPair {
    public final ElgamalPublicParams publicParams;
    public final BigInteger publicKey;
    private final BigInteger secretKey;

    protected ElGamalKeyPair(ElgamalPublicParams publicParams) {
        this.secretKey = MathLib.random(publicParams.prime);
        this.publicKey = MathLib.exp(publicParams.generator, secretKey, publicParams.prime);
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
