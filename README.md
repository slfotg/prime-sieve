# prime-sieve

An implementation of a prime sieve in Java.

The algorithm used is a modified version Sieve of Eratosthenes called Wheel Factorization. Modulus operations were attempted to be minimized. The prime collectin is also backed by a BitSet to minimize memory needed to hold all primes. This does not work well after about 1 billion.
