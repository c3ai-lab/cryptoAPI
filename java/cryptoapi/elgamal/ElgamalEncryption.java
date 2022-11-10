package cryptoapi.elgamal;

import cryptoapi.MathLib;

import java.math.BigInteger;

public class ElgamalEncryption {
    private final ElgamalPublicParams publicParams;
    private final BigInteger publicKey;
    private final BigInteger secretKey;


    public ElgamalEncryption(ElgamalPublicParams publicParams) {
        this.secretKey = MathLib.random(publicParams.prime);
        this.publicKey = MathLib.exp(publicParams.generator, secretKey, publicParams.prime);
        this.publicParams = publicParams;
    }

    public static ElgamalEncryption keyGen(ElgamalPublicParams publicParams) {
        return new ElgamalEncryption(publicParams);
    }

    public static BigInteger[] enc(BigInteger otherPublicKey, ElgamalPublicParams publicParams, BigInteger message) {
        BigInteger y = MathLib.random(publicParams.prime);
        BigInteger c1 = MathLib.exp(publicParams.generator, y, publicParams.prime);

        BigInteger c2prime = MathLib.exp(otherPublicKey, y, publicParams.prime);
        BigInteger c2 = MathLib.mul(c2prime, message, publicParams.prime);

        return new BigInteger[] { c1, c2 };
    }

    public BigInteger dec(BigInteger c1, BigInteger c2) {
        BigInteger k = MathLib.exp(c1, this.secretKey, this.publicParams.prime);
        BigInteger kinv = MathLib.inverse(k, this.publicParams.prime);
        BigInteger m = MathLib.mul(c2, kinv, this.publicParams.prime);
        return m;
    }

    public BigInteger getPublicKey() {
        return this.publicKey;
    }
}
