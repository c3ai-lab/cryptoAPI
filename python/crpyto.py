def add(x,y):
    return x + y

def mul(x,y):
    return x * y

def sub(x,y):
    return x - y

def div(x,y):
    return x / y

class H:
    def add_mod(x,y,m):
        return add(x, y) % m

    def add_mod2(x,y,m):
        return ((x % m) + (y % m)) % m

    def sub_mod(x, y, m):
        return H.add_mod(x, H.add_invert_mod(y, m), m)

    def add_invert_mod(x, m):
        return add(-x,m) %m

class G:
    def mul_mod(x,y,m):
        return mul(x,y) % m

    def mul_mod2(x,y,m):
        return mul(x %m, y %m) %m
