from math import floor, sqrt
import random
import hashlib

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
    p = genPrime(10000, 90000)
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



class CramerShoup(DiffieHellman):

    def __init__(self):
        self.H = hashlib.sha3_512()

        alpha, g_ = self.sample()
        x, gx = self.sample()
        z, gz = self.sample()
        z_, gz_ = self.sample()

        y = random.randint(0, self.p - 1)
        w = random.randint(0, self.p - 1)
        w_ = random.randint(0, self.p - 1)

        # secret key
        self._sK = {
            "alpha": alpha,
            "x" : x,
            "y": y,
            "z" : z,
            "z_": z_,
            "w": w,
            "w_": w_
        }

        # public key
        self._pK = {
            "A": self.mul_mod(gx, self.pow_mod(g_,y, self.p), self.p),
            "B": self.mul_mod(gz, self.pow_mod(g_,w, self.p), self.p),
            "B_": self.mul_mod(gz_, self.pow_mod(g_,w_, self.p), self.p),
            "g": self.g,
            "g_": g_,
            "p": self.p
        }

    def getPublicKey(self):
        return self._pK

    def enc(self, pub, m):
        r = random.randint(0, self.p - 1)
        R = self.pow_mod(pub['g'], r, pub['p'])
        R_ = self.pow_mod(pub['g_'], r, pub['p'])
        P = self.mul_mod( self.pow_mod(pub['A'], r, pub['p']),m, pub['p'])
        beta = (str(R_)+str(R)+str(P)).encode()
        self.H.update(beta)
        h = int(self.H.hexdigest(), 16) % self.order(pub['p'])
        T = self.pow_mod(
                self.mul_mod(
                    pub['B'],
                    self.pow_mod(pub['B_'], h, pub['p']),
                    pub['p']
                ),
                r,
                pub['p']
        )
        return {"R": R, "R_": R_, "P": P, "T": T}


    def dec(self, c):
        beta_ = (str(c['R_'])+str(c['R'])+str(c['P'])).encode()
        self.H.update(beta_)
        h = int(self.H.hexdigest(), 16) % self.order(self.p)

        # T validation part 1
        Tv1 = self.mul_mod(
            self.pow_mod(c['R'], self._sK['z'], self.p),
            self.pow_mod(c['R'], self.mul_mod(h,self._sK['z_'], self.order(self.p)),self.p),
            self.p
        )

        # T validation part 2
        Tv2 = self.mul_mod(
            self.pow_mod(c['R_'], self._sK['w'], self.p),
            self.pow_mod(c['R_'], self.mul_mod(h, self._sK['w_'], self.order(self.p)), self.p),
            self.p
        )

        if c['T'] == self.mul_mod(Tv1, Tv2, self.p):
            Rx = self.pow_mod(c['R'], self._sK['x'], self.p)
            R_y = self.pow_mod(c['R_'], self._sK['y'], self.p)
            invRxR_y = self.mul_invert_mod(self.mul_mod(Rx, R_y, self.p),self.p)
            return self.mul_mod(c['P'], invRxR_y, self.p)
        else:
            raise Exception("Validation error")


class Threshhold_ElGamal(ElGamal):
    def __init__(self, players, secPar):
        self.players = players
        self.degree = players - 1
        self.secPar = secPar
        self.xCoords = [x for x in range(0, self.players + 1)]

    # Coords for Players start at 1 and increase by 1 each
    # Coords for x0 is 0
    def lagrangeCoeff(self):
        delta = [1 for x in range(0, self.players)]
        for i in range(1, self.players + 1):
            for j in range(1, self.players + 1):
                if i != j:
                    delta[i] *= (self.xCoords[0] - self.xCoords[j]) / (self.xCoords[i] - self.xCoords[j])

    def keyGen(self):
        pA = [random.randint(0, self.secPar) for x in range(0,self.players)]

        pX = [0 for x in range(0, self.players)]
        for n in range(0, self.players + 1):
            for i in range(0, self.players):
                pX[n] += pA[i] * (self.xCoords[n] ^ i)

        pKey = self.g ^ pX[0]
        pX[0] = None
        sKey = pX
        
        return pKey, sKey

    def encrypt(self, pKey, m):
        return self.enc(self, pKey, m)

    # Decrypt fehlt
    # Funktion, um mit Secret-Key c1 zu berechnen
    # Funktion um Secret - Key zu rekonstruieren


