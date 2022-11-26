package cryptoapi.rsa;

import cryptoapi.MathLib;

import java.math.BigInteger;

public class RSAEncryption {
    public static RSAKeyPair keyGen(int secpar) {
        // Paramaters
        BigInteger p = MathLib.randomPrime(secpar);
        BigInteger q = MathLib.randomPrime(secpar);
        BigInteger N = p.multiply(q);
        BigInteger phi = MathLib.mul((p.subtract(BigInteger.ONE)), q.subtract(BigInteger.ONE), N);

        BigInteger e = sampleE(phi);
        BigInteger d = MathLib.inverseEGCD(e, phi);

        // sk = e, N
        // pk = d, N
        return new RSAKeyPair(d, e, N);
    }

    public static BigInteger sign(RSAKeyPair keyPair, BigInteger m) {
        return MathLib.exp(MathLib.hash(m), keyPair.secretKey.key, keyPair.secretKey.modul);
    }

    public static boolean verify(RSAKeyTuble publicKey, BigInteger m, BigInteger sigma) {
        return MathLib.exp(sigma, publicKey.key, publicKey.modul).equals(MathLib.hash(m).mod(publicKey.modul));
    }

    public static BigInteger sampleE(BigInteger phi) {
        BigInteger e;
        do {
            e = MathLib.random(phi);
        } while (!e.gcd(phi).equals(BigInteger.ONE) && !e.equals(BigInteger.ONE));
        return e;
    }
}
