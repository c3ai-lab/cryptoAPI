package cryptoapi.elgamal;

import cryptoapi.MathLib;

import java.math.BigInteger;

public class ElgamalEncryption {

    public static ElGamalKeyPair keyGen(ElgamalPublicParams publicParams) {
        return new ElGamalKeyPair(publicParams);
    }

    public static BigInteger[] enc(BigInteger otherPublicKey, ElgamalPublicParams publicParams, BigInteger message) {
        BigInteger y = MathLib.random(publicParams.prime);
        BigInteger c1 = MathLib.exp(publicParams.generator, y, publicParams.prime);

        BigInteger c2prime = MathLib.exp(otherPublicKey, y, publicParams.prime);
        BigInteger c2 = MathLib.mul(c2prime, message, publicParams.prime);

        return new BigInteger[] { c1, c2 };
    }

    public static BigInteger dec(ElGamalKeyPair keypair, BigInteger c1, BigInteger c2) {
        BigInteger k = MathLib.exp(c1, keypair.getSecretKey(), keypair.publicParams.prime);
        BigInteger kinv = MathLib.inverse(k, keypair.publicParams.prime);
        BigInteger m = MathLib.mul(c2, kinv, keypair.publicParams.prime);
        return m;
    }
}
