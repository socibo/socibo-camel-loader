package com.github.dulanov.cbrru

import groovy.util.GroovyTestCase

//import static com.github.dulanov.cbrru.CurrencyCode.*
//
//class CurrencyRatesTest extends GroovyTestCase {
//    void testWithPreviousForEquals() {
//        CurrencyRates rates = [USD: 31.3538, EUR: 38.5119, CNY: 45.8966]
//        assertEquals([USD: 31.3538, EUR: 38.5119, CNY: 45.8966] as CurrencyRates, rates.withPrevious(rates))
//    }
//
//    void testWithPreviousForDifferents() {
//        CurrencyRates cur = [USD: 1.3250, CNY: 42.6907]
//        CurrencyRates prev = [USD: 1.2000, EUR: 5.4321]
//        assertEquals([USD: [1.3250, +0.1250], CNY: 42.6907] as CurrencyRates, cur.withPrevious(prev))
//        assertEquals([USD: 1.3250, CNY: 42.6907] as CurrencyRates, cur)
//        assertEquals([USD: 1.2000, EUR: 5.4321] as CurrencyRates, prev)
//    }
//
//    void testEquals() {
//        assertEquals([USD: 1.2345, EUR: [5.4321, 0.1234]] as CurrencyRates, [EUR: [5.4321, 0.1234], USD: 1.2345] as CurrencyRates)
//        assertFalse(new CurrencyRates([USD: [1.2345, 0.1234], EUR: 5.4321]) == new CurrencyRates([EUR: [5.4321, 0.1234], USD: 1.2345]))
//        assertFalse(new CurrencyRates([USD: 1.2345, EUR: 5.4321]) == new CurrencyRates([USD: 1.2345, EUR: 5.4321, CNY: 42.6907]))
//        assertFalse(new CurrencyRates([USD: 1.2345, EUR: 5.4321]) == new CurrencyRates([USD: 1.2345, CNY: 42.6907]))
//        assertFalse(new CurrencyRates([USD: 1.2345, EUR: 5.4321]) == null)
//        assertFalse(new CurrencyRates([USD: 1.2345, EUR: 5.4321]) == 123)
//        assertFalse(new CurrencyRates([USD: 1.2345, EUR: 5.4321]) =="abc")
//    }
//
//    void testToString() {
//        CurrencyRates rates = [EUR: 39.1866, USD: [29.2940, 0.123], CNY: [42.6907, -0.12345]]
//        assertToString rates, '1$ = 29.2940 (+0.1230) р. | 1€ = 39.1866 (+0.0000) р. | 1¥ = 42.6907 (-0.1235) р.'
//    }
//}
