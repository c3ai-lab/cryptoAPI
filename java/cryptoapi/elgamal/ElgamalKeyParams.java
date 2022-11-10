package cryptoapi.elgamal;

import java.math.BigInteger;

public class ElgamalKeyParams {
    public final BigInteger generator;
    public final BigInteger prime;

    protected ElgamalKeyParams(BigInteger generator, BigInteger prime) {
        this.generator = generator;
        this.prime = prime;
    }

    @Override
    public String toString() {
        String s = "{";
        s += "generator: " + this.generator;
        s += ", prime: " + this.prime;
        s += "}";
        return s;
    }
}
