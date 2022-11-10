package cryptoapi.diffiehellman;

import cryptoapi.MathLib;

import java.math.BigInteger;
public class DiffieHellmanKeyExchange {
    private final BigInteger public_prime;
    private final BigInteger publicKey;
    private final BigInteger secretKey;
    private BigInteger sharedKey = null;

    public DiffieHellmanKeyExchange(BigInteger prime, BigInteger generator) {
        this.public_prime = prime;
        this.secretKey = MathLib.random(prime);
        this.publicKey = MathLib.exp(generator, secretKey, prime);
    }

    public void generateShareKey(BigInteger otherPublicKey) {
        this.sharedKey = MathLib.exp(otherPublicKey, this.secretKey, this.public_prime);
    }

    public BigInteger getSharedKey() {
        return this.sharedKey;
    }

    public BigInteger getPublicKey() {
        return this.publicKey;
    }

    public BigInteger getSecretKey() {
        return this.secretKey;
    }


    @Override
    public String toString() {
        String s = "{";
        s += "sharedKey: " + this.sharedKey;
        s += ", publicKey: " + this.publicKey;
        s += ", secretKey: " + this.secretKey;
        s += "}";
        return s;
    }
}
