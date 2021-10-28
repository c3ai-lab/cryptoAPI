from math import floor, sqrt


def add(x,y):
    return x + y

def mul(x,y):
    return x * y

def sub(x,y):
    return x - y

def div(x,y):
    return x / y

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
        result *= primes[0][i]**(primes[1][i]-1) * (primes[0][i]-1)
        
    return result

#Additive group
class H:
    def add_mod(self,x,y,m):
        return add(x, y) % m

    def add_mod2(self,x,y,m):
        return ((x % m) + (y % m)) % m

    def sub_mod(self,x, y, m):
        return H.add_mod(x, H.add_invert_mod(y, m), m)

    def add_invert_mod(self,x, m):
        return add(-x,m) %m
    
    #skalar multiplication: x=3,y=2, result = x+x = 6
    def pow_mod(self, x, y, m):
        result = 0
        for i in range(y):
            result = add(result, x)
        return result % m
    
    #Order is the number of elements, if H is a group by definition each element is invertable so we count the number of elements
    def order(self, m):
        return m

#Multiplikative group
class G:
    def mul_mod(self,x,y,m):
        return mul(x,y) % m

    def mul_mod2(self,x,y,m):
        return mul(x %m, y %m) %m
    
    def pow_mod(self, x, y, m):
        result = x
        for i in range(y-1):
            result = mul(result, x)
        return result % m
    
    def order(self, m):
        return phi(ext_factors(m))
    
    def mul_invert_mod(self, x, m):
        return x**(self.order(m)-1) % m
    
