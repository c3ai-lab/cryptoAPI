package cryptoapi;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    /**
     * Uses extended euclidean algorithm to
     * calculate the inverse of an element.
     *
     * @param a group element
     * @param modulo
     * @return
     */
    public static BigInteger inverseEGCD(BigInteger a, BigInteger modulo) {
        return egcd(a,modulo).s.mod(modulo);
    }

    /**
     * Uses Fermat's little theorem to
     * calculate the inverse of an element
     *
     * @param a group element
     * @param p prime modulo
     * @return inverse of a
     */
    public static BigInteger inverseFermat(BigInteger a,BigInteger p) {
        BigInteger phi = p.subtract(BigInteger.ONE);
        BigInteger b = exp(a, phi.subtract(BigInteger.ONE), p);
        return b;
    }

    /**
     * Berechnet den ggt(a, b) sowie zwei ganze Zahlen s und t
     * die die Gleichung s*a + t*b = ggt(a,b) lösen.
     *
     * @example:
     * a= 4
     * b = 6
     * s*4 + t*6 = ggt(4,6)
     * s*4 + t*6 = 2
     * -1*4 + 1*6 = 2
     * ----------------------------------
     * a=5
     * b=7
     * s*5 + t*7 = ggt(5,7)
     * s*5 + t*7 = 1
     * 3*5 + -2*7 = 1 | mod 7
     * 3*5 = 1 mod 7
     *
     * 3*5 + -2*7 = 1 | mod 5
     * 3*7 = 1 mod 5
     *
     *
     *
     * Wenn a = 0 : Return gcd=b, s=0, t=1
     * Wenn a < 0:
     *  (gcd, s, t) = egcd(b mod a, a);
     *  newX = t-(b/a*s)
     *
     * @param a
     * @param b
     * @return GCD and Bézout coefficients
     */
    public static EGCDResult egcd(BigInteger a, BigInteger b) {
        if (a.equals(BigInteger.ZERO)) {
            return new EGCDResult(b, BigInteger.ZERO, BigInteger.ONE);
        } else {
            EGCDResult res = egcd(b.mod(a), a);
            BigInteger newX = res.t.subtract(b.divide(a).multiply(res.s));
            return new EGCDResult(res.gcd, newX, res.s);
        }
    }

    public static BigInteger hash(BigInteger message) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            return new BigInteger(messageDigest.digest(message.toByteArray()));
        } catch (NoSuchAlgorithmException ex) {
            //Should be thrown
            return null;
        }
    }
}
