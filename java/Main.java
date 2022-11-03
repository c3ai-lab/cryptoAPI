import java.math.BigInteger;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Parameters
        BigInteger p = randomPrime();
        BigInteger g = random(p);
        System.out.println("p: " + p);
        System.out.println("g: " + g + "\n");

        // Alice
        BigInteger x = random(p);
        System.out.println("x Alice: " + x);
        BigInteger X = exp(g,x,p);

        // Bob
        BigInteger y = random(p);
        System.out.println("y Bob: " + y + "\n");
        BigInteger Y = exp(g,y,p);
        BigInteger k_bob = exp(X,y,p);

        // Alice
        BigInteger k_alice = exp(Y,x,p);

        System.out.println("Key-Bob: " + k_bob);
        System.out.println("Key-Alice: " + k_alice);
    }

    public static BigInteger random(BigInteger p) {
        return new BigInteger(p.bitLength(), new Random()).mod(p);
    }

    public static BigInteger randomPrime() {
        int secpar = 64;
        return BigInteger.probablePrime(secpar, new Random());
    }

    public static BigInteger mul(BigInteger a, BigInteger b, BigInteger p) {
        return a.multiply(b).mod(p);
    }

    public static BigInteger exp(BigInteger base, BigInteger exponent, BigInteger p) {
        return base.modPow(exponent, p);
    }
}