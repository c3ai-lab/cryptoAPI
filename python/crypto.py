from math import floor, sqrt
import random


def add(x, y):
    return x + y


def mul(x, y):
    return x * y


def sub(x, y):
    return x - y


def div(x, y):
    return x / y


def gen_generator(m: int) -> int:
    g = None

    if not is_prime(m):
        return g
    else:
        r = random.choice(range(1, m // 4))
        found = False
        while not found:
            g = 2 * r + 1
            if g < m and is_prime(g):
                found = True
            else:
                r += 1

    return g


# trivial algorithm to test conditions of an element
# being a generator of the group
def is_generator_simple(g: int, m: int) -> bool:
    if g >= m:
        return False
    elif not is_prime(m):
        return False
    elif is_prime(g):
        return True
    return False


def is_prime(x: int) -> bool:
    if x < 2:
        return False
    if x == 2 or x == 3:
        return True
    if x % 2 == 0 or x % 3 == 0:
        return False

    max_q = floor(sqrt(x))
    cur_q, step = 5, 2

    while cur_q <= max_q:
        if x % cur_q == 0:
            return False
        cur_q += step
        step = 6 - step

    return True


def factors(x: int) -> [int]:
    max_q = floor(sqrt(x))
    cur_q, step = 5, 2

    if x % 2 == 0:
        cur_q = 2
    elif x % 3 == 0:
        cur_q = 3
    else:
        while cur_q <= max_q:
            if x % cur_q == 0:
                break
            cur_q += step
            step = 6 - step

    if cur_q <= max_q:
        return [cur_q] + factors(x // cur_q)
    else:
        return [x]


def ext_factors(x):
    primeSet = []
    primeSetCount = []
    fact = factors(x)
    for y in fact:
        if y not in primeSet:
            primeSet.append(y)
            primeSetCount.append(fact.count(y))
    return primeSet, primeSetCount


def phi(primes):
    result = 1
    for i in range(len(primes[0])):
        result *= primes[0][i] ** (primes[1][i] - 1) * (primes[0][i] - 1)

    return result


def genPrime(a, b):
    resultList = []
    for i in range(a, b):
        if is_prime(i):
            resultList.append(i)

    return random.choice(resultList)


# Additive group
class H:
    def add_mod(self, x, y, m):
        return add(x, y) % m

    def add_mod2(self, x, y, m):
        return ((x % m) + (y % m)) % m

    def sub_mod(self, x, y, m):
        return H.add_mod(x, H.add_invert_mod(y, m), m)

    def add_invert_mod(self, x, m):
        return add(-x, m) % m

    # scalar multiplication: x=3,y=2, result = x+x = 6
    def pow_mod(self, x, y, m):
        result = 0
        for i in range(y):
            result = add(result, x)
        return result % m

    # Order is the number of elements, if H is a group by definition each element is invertable
    # so we count the number of elements
    def order(self, m):
        return m


# Multiplicative group
class G:
    def mul_mod(self, x, y, m):
        return mul(x, y) % m

    def mul_mod2(self, x, y, m):
        return mul(x % m, y % m) % m

    def pow_mod(self, x, y, m):
        result = x
        for i in range(y - 1):
            result = mul(result, x)
        return result % m

    def order(self, m):
        return phi(ext_factors(m))

    def mul_invert_mod(self, x, m):
        return x ** (self.order(m) - 1) % m


class DiffieHellman(G):
    p = genPrime(1000000, 2000000)
    g = gen_generator(p)

    print("Generator", g)
    print("Modulus", p)

    def sample(self):
        x = random.randint(0, self.p - 1)
        bigX = self.pow_mod(self.g, x, self.p)
        return x, bigX

    def key(self, pub, sec):
        return self.pow_mod(pub, sec, self.p)


class ElGamal(DiffieHellman):
    def enc(self, pub, m):
        (y, gY) = self.sample()
        c2 = self.mul_mod(self.key(pub, y), m, self.p)
        c1 = gY
        return c1, c2

    def dec(self, sec, c):
        c1, c2 = c
        sharedKey = self.key(c1, sec)
        sharedKeyInv = self.mul_invert_mod(sharedKey, self.p)
        return self.mul_mod(c2, sharedKeyInv, self.p)

    def keyGen(self):
        return self.sample()
