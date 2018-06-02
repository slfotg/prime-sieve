# prime-sieve

[![Build Status](https://api.travis-ci.org/slfotg/prime-sieve.svg?branch=master)](http://travis-ci.org/slfotg/prime-sieve)
[![Alert Status](https://sonarcloud.io/api/project_badges/measure?project=com.github.slfotg%3Aprime-sieve&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.slfotg%3Aprime-sieve)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=com.github.slfotg%3Aprime-sieve&metric=bugs)](https://sonarcloud.io/dashboard?id=com.github.slfotg%3Aprime-sieve)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.github.slfotg%3Aprime-sieve&metric=coverage)](https://sonarcloud.io/dashboard?id=com.github.slfotg%3Aprime-sieve)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.github.slfotg%3Aprime-sieve&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.github.slfotg%3Aprime-sieve)

An implementation of a prime sieve in Java.

The algorithm used is a modified version Sieve of Eratosthenes called Wheel Factorization. Modulus operations were attempted to be minimized. The prime collection is also backed by a BitSet to minimize memory needed to hold all primes. This does not work well after about 1 billion.
