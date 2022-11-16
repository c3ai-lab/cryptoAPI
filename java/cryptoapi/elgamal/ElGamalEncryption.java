package cryptoapi.elgamal;

import cryptoapi.MathLib;

import java.math.BigInteger;

public class ElGamalEncryption {

    public static ElGamalKeyPair keyGen(int secPar) {
        BigInteger p = MathLib.randomPrime(secPar);
        ElGamalKeyParams publicParams = new ElGamalKeyParams(MathLib.random(p), p);
        BigInteger secretKey = MathLib.random(publicParams.prime);
        BigInteger publicKey = MathLib.exp(publicParams.generator, secretKey, publicParams.prime);

        return new ElGamalKeyPair(publicParams, secretKey, publicKey);
    }

    public static BigInteger[] enc(BigInteger otherPublicKey, ElGamalKeyParams keyParams, BigInteger message) {
        BigInteger y = MathLib.random(keyParams.prime);
        BigInteger c1 = MathLib.exp(keyParams.generator, y, keyParams.prime);

        BigInteger c2prime = MathLib.exp(otherPublicKey, y, keyParams.prime);
        BigInteger c2 = MathLib.mul(c2prime, message, keyParams.prime);

        return new BigInteger[] { c1, c2 };
    }

    public static BigInteger dec(ElGamalKeyPair keypair, BigInteger c1, BigInteger c2) {
        BigInteger k = MathLib.exp(c1, keypair.getSecretKey(), keypair.keyParams.prime);
        BigInteger kinv = MathLib.inverse(k, keypair.keyParams.prime);
        BigInteger m = MathLib.mul(c2, kinv, keypair.keyParams.prime);
        return m;
    }
}
