# -*- coding: utf-8 -*-
"""
Created on Thu Oct 28 08:40:48 2021

@author: simon
"""

import crypto as c

h = c.H()
g = c.G()
#print(h.add_mod(2,3,6))

#print(g.pow_mod(2,3,10))

#print(c.factors(8))

#print(c.ext_factors(12))

#print(c.phi(c.ext_factors(6)))

#print(g.order(9))

print(g.mul_invert_mod(2, 5))