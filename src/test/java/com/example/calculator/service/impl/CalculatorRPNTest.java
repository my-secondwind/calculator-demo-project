package com.example.calculator.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculatorRPNTest {

    @Test
    void simpleExpression() {
        CalculatorRPN calculatorRPN = new CalculatorRPN();
        String actualResult = calculatorRPN.calculate("2+2");
        assertEquals("4.0", actualResult);
    }

    @Test
    void complicateExpression() {
        CalculatorRPN calculatorRPN = new CalculatorRPN();
        String actualResult = calculatorRPN.calculate("4+55*(7+9)^2-sin(7)*5");
        assertEquals("14080.715067006406", actualResult);
    }


    @Test
    void unaryMinusCheck() {
        CalculatorRPN calculatorRPN = new CalculatorRPN();
        String actualResult = calculatorRPN.calculate("-4+5*(-8*2)");
        assertEquals("-84.0", actualResult);
    }

    @Test
    void divisionByZero() {
        CalculatorRPN calculatorRPN = new CalculatorRPN();
        String actualResult = calculatorRPN.calculate("5/0");
        assertEquals("Infinity", actualResult);
    }

    @Test
    void simpleExpressionWithDecimalPart() {
        CalculatorRPN calculatorRPN = new CalculatorRPN();
        String actualResult = calculatorRPN.calculate("2.5+2");
        assertEquals("4.5", actualResult);
    }
}