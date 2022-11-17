import java.math.BigInteger;
import java.util.Random;

public class Main {
	
	public static void main(String[] args) {
		// Paramaters
        int secpar = 4;
        BigInteger m = BigInteger.valueOf(5);
        
        BigInteger[] keys = rsa_keyGen(secpar);

        BigInteger sigma = rsa_sign(keys[0], keys[1], m);
        
        System.out.println(rsa_verify(keys[2], keys[1], m, sigma));
	}
	
	public static BigInteger[] rsa_keyGen(int secpar) {
		// Paramaters
		//BigInteger p = randomPrime(secpar);
		//BigInteger q = randomPrime(secpar);
		BigInteger p = BigInteger.valueOf(3);
        BigInteger q = BigInteger.valueOf(5);
        BigInteger N = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)); // phi = 2 * 4 = 8
        
        BigInteger e = BigInteger.valueOf(4);
        BigInteger d = inverse(e, phi);
        
        System.out.println("Inverses: " + e.multiply(d));
        
        // sk = e, N
        // pk = d, N
        return new BigInteger[] { e, N, d };
	}
	
	public static BigInteger rsa_sign(BigInteger e, BigInteger N, BigInteger m) {
		return exp(m, e, N);
	}
	
	public static boolean rsa_verify(BigInteger d, BigInteger N, BigInteger m, BigInteger sigma) {
		System.out.println(exp(sigma, d, N));
		System.out.println(m);
		return exp(sigma, d, N).equals(m);
	}
	
	public static BigInteger phi_inverse(BigInteger e, BigInteger p, BigInteger q) {
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger ord = 
				
	}

    public static void elGamal() {

        // Paramaters
        int secpar = 128;
        BigInteger p = randomPrime(secpar);
        BigInteger g = random(p);

        // Bob
        BigInteger[] keys = keyGen(p, g);
        BigInteger sk = keys[0];
        BigInteger pk = keys[1];

        // Alice
        BigInteger m = new BigInteger("100000");
        BigInteger[] ciphretexts = enc(pk, m, g, p);
        BigInteger c1 = ciphretexts[0];
        BigInteger c2 = ciphretexts[1];
        
        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);

        // Bob
        BigInteger mprime = dec(sk, c1, c2, p);

        System.out.println(mprime);
    }

    public static void diffieHellman() {
        // Parameters
        BigInteger p = randomPrime(8);
        BigInteger g = random(p);
        System.out.println("p: " + p);
        System.out.println("g: " + g + "\n");

        // Alice
        BigInteger x = random(p);
        System.out.println("x Alice: " + x);
        BigInteger X = exp(g, x, p);

        // Bob
        BigInteger y = random(p);
        System.out.println("y Bob: " + y + "\n");
        BigInteger Y = exp(g, y, p);
        BigInteger k_bob = exp(X, y, p);

        // Alice
        BigInteger k_alice = exp(Y, x, p);

        System.out.println("Key-Bob: " + k_bob);
        System.out.println("Key-Alice: " + k_alice);
    }

    public static BigInteger[] keyGen(BigInteger g, BigInteger p) {
        BigInteger x = random(p);
        BigInteger pk = exp(g, x, p);
        return new BigInteger[] { x, pk };
    }

    public static BigInteger[] enc(BigInteger pk, BigInteger m, BigInteger g, BigInteger p) {
        BigInteger y = random(p);
        BigInteger c1 = exp(g, y, p);

        BigInteger c2prime = exp(pk, y, p);
        BigInteger c2 = mul(c2prime, m, p);

        return new BigInteger[] { c1, c2 };
    }

    public static BigInteger inverse(BigInteger a, BigInteger p) {
        BigInteger phi = p.subtract(BigInteger.ONE);
        BigInteger b = exp(a, phi.subtract(BigInteger.ONE), p);
        return b;
    }

    public static BigInteger dec(BigInteger sk, BigInteger c1, BigInteger c2, BigInteger p) {
        BigInteger k = exp(c1, sk, p);
        BigInteger kinv = inverse(k, p);
        BigInteger m = mul(c2, kinv, p);
        return m;
    }

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

}

