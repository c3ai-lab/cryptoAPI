from itertools import count
import crypto as c
def prGreen(skk): print("\033[92m {}\033[00m".format(skk))
# h = c.H()
# g = c.G()

# print(h.add_mod(2,3,6))
# print(g.pow_mod(2,3,10))
# print(c.factors(8))
# print(c.ext_factors(12))
# print(c.phi(c.ext_factors(6)))
# print(g.order(9))
# print(g.mul_invert_mod(2, 5))
# print(test.order(mod))
# print(len(set([test.pow_mod(g, x, mod) for x in range(mod)])))

# mod = 877
# order = c.G().order(mod)
# g = c.gen_generator(mod)

# print(g)
# q = set([c.G().pow_mod(g, y, mod) for y in range(mod)])
# print(len(q), order, q)

# for x in range(1, mod):
#     if not c.is_generator_simple(x, mod):
#         continue
#     r = pow(x, order, mod)
#     g = set([c.G().pow_mod(x, y, mod) for y in range(mod)])
#     if r == 1:
#         print(x, g)

# , [pow(x, y, mod) for y in factors]
# for mod in range(1,20):
#     for g in range(2,mod):
#         if c.is_generator_simple(g, mod):
#             print(g, mod)


# for mod in range(20, 50):
#     generators = [y for y in range(mod) if len(set([test.pow_mod(y, x, mod) for x in range(mod)])) == mod - 1]
#     if len(generators) > 0:
#         print(c.factors(mod - 1))
#         print(mod, generators)

# dh = c.DiffieHellman()
# (x, gX) = dh.sample()
# print("x", x)
# print("gX", gX)
#
# (y, gY) = dh.sample()
# print("y", y)
# print("gY", gY)
#
# aliceKey = dh.key(gY, x)
# bobKey = dh.key(gX, y)
#
# print("alice", aliceKey)
# print("bob", bobKey)



# Alice = c.CramerShoup()
# Bob = c.CramerShoup()

# m1 = 7983 % Bob.p
# m2 = 8880 % Bob.p
# print("m1: ", m1)
# print("m2: ", m2)

# C1 = Alice.enc(Bob.getPublicKey(), m1)
# C2 = Alice.enc(Bob.getPublicKey(), m2)
# C3 = {}
# for key in C1:
#     C3[key] = c.CramerShoup.mul_mod(Bob, C1[key], C2[key], Bob.p)

# print("C1: ", C1)
# print("C2: ", C2)
# print("C3: ", C3)

# dec_m1 = Bob.dec(C1)
# print("dec m1: ", dec_m1)
# dec_m2 = Bob.dec(C2)
# print("dec m2: ", dec_m2)

# print("homomorphism - Test")
# dec_m3 = Bob.dec(C3)

# print("dec m3: ", dec_m3)


# eg = c.ElGamal()
# sec, pub = eg.keyGen()
# print ("Public Key: ", pub)
# print ("Secret Key: ", sec)
# m = 32
# print("Original Message", m)

# mEnc = eg.enc(pub, m)
# print("Encrypted", mEnc)

# mDec = eg.dec(sec, mEnc)
# print("Decrypted", mDec)

# ------------------------------- Threshhold El-Gamal -------------------------------------------
playerCount = 3
securityParameter = 100


thElGamal = c.Threshhold_ElGamal(playerCount,securityParameter)
pKey, sKeys = thElGamal.keyGen()

print("Secret Keys: ", sKeys)
print("Public Key: ", pKey)

sKey = thElGamal.calcSecretKey(sKeys)
print("Calculated Secret Key:", sKey)

m = 2345
print ("Original Message: ", m)

mEnc = thElGamal.encrypt(pKey, m)
print ("Encrypted Message: ", mEnc)

mDec = thElGamal.decrypt(sKeys, mEnc)
print ("Decrypted Message:", mDec)

