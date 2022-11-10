import cryptoapi.MathLib;
import cryptoapi.diffiehellman.DiffieHellmanKeyExchange;
import cryptoapi.elgamal.ElGamalKeyPair;
import cryptoapi.elgamal.ElgamalEncryption;
import cryptoapi.elgamal.ElgamalPublicParams;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        //diffieHellman();
        elGamal();
    }

    public static void diffieHellman() {
        System.out.println("Start Diffie-Hellman key exchange example...");

        // Public Parameters
        final int securityParam = 64;
        BigInteger p = MathLib.randomPrime(securityParam);
        BigInteger g = MathLib.random(p);
        System.out.println("prime: " + p);
        System.out.println("generator: " + g + "\n");

        // Alice
        DiffieHellmanKeyExchange alice = new DiffieHellmanKeyExchange(p,g);

        // Bob
        DiffieHellmanKeyExchange bob = new DiffieHellmanKeyExchange(p,g);

        // Alice shared key
        alice.generateShareKey(bob.getPublicKey());
        System.out.println("Alice: " + alice);

        //Bob shared key
        bob.generateShareKey(alice.getPublicKey());
        System.out.println("Bob: " + bob);

        compare(alice.getSharedKey(), bob.getSharedKey());

        //Separation to the following output
        System.out.println();
        System.out.println();

    }

    public static void elGamal() {
        System.out.println("Start ElGamal example...");
        // Public Parameters
        final int secpar = 128;
        BigInteger p = MathLib.randomPrime(secpar);
        ElgamalPublicParams publicParams = new ElgamalPublicParams(MathLib.random(p), p);
        System.out.println("publicParams: " + publicParams);

        // Bob
        ElGamalKeyPair bob = ElgamalEncryption.keyGen(publicParams);

        // Alice
        BigInteger m = new BigInteger("100000");
        BigInteger[] ciphretexts = ElgamalEncryption.enc(bob.publicKey, publicParams, m);
        BigInteger c1 = ciphretexts[0];
        BigInteger c2 = ciphretexts[1];

        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);

        // Bob
        BigInteger mprime = ElgamalEncryption.dec(bob, c1, c2);

        compare(mprime, m);

        //Separation to the following output
        System.out.println();
        System.out.println();
    }

    static<T> void compare(T firstValue, T secondValue) {
        if (firstValue.equals(secondValue)) {
            System.out.println(firstValue + " is equals to " + secondValue);
        } else {
            System.out.println("ERROR");
            System.out.println( firstValue + " doesn't match " + secondValue);
        }
    }
}
