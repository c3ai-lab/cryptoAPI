package cryptoapi;

import java.math.BigInteger;
import java.util.Random;

public class MathLib {

    public static BigInteger random(BigInteger p) {
        return new BigInteger(p.bitLength(), new Random()).mod(p);
    }

    public static BigInteger randomPrime(int secpar) {
        return BigInteger.probablePrime(secpar, new Random());
    }

    public static BigInteger mul(BigInteger a, BigInteger b, BigInteger p) {
        return a.multiply(b).mod(p);
    }

    public static BigInteger exp(BigInteger base, BigInteger exponent, BigInteger p) {
        return base.modPow(exponent, p);
    }

    public static BigInteger inverse(BigInteger a, BigInteger p) {
        BigInteger phi = p.subtract(BigInteger.ONE);
        BigInteger b = exp(a, phi.subtract(BigInteger.ONE), p);
        return b;
    }
}
