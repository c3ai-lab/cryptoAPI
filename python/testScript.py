import crypto as c

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

# eg = c.ElGamal()
# sec, pub = eg.keyGen()
# m = 32
# print("Original Message", m)
#
# mEnc = eg.enc(pub, m)
# print("Encrypted", mEnc)
#
# mDec = eg.dec(sec, mEnc)
# print("Decrypted", mDec)
