package numbers

import java.math.BigInteger

fun BigInteger.lcm(other: BigInteger): BigInteger =
    multiply(other).abs().div(gcd(other))