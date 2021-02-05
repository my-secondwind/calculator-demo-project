package com.example.calculator.service.impl;

import com.example.calculator.exceptions.TokenException;
import com.example.calculator.service.enums.TokenType;
import org.junit.jupiter.api.Test;

import static com.example.calculator.service.impl.TokenAnalyzer.TRIGONOMETRIC_OPERATIONS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

class TokenAnalyzerTest {

    @Test
    void getTokenTypeNumber() {
        assertEquals(TokenType.Number, TokenAnalyzer.getTokenType("5"));
        assertEquals(TokenType.Number, TokenAnalyzer.getTokenType("77"));
        assertEquals(TokenType.Number, TokenAnalyzer.getTokenType("0"));
        assertEquals(TokenType.Number, TokenAnalyzer.getTokenType("7.5"));
    }

    @Test
    void getTokenTypeOperator() {
        assertEquals(TokenType.Operator, TokenAnalyzer.getTokenType("-"));
        assertEquals(TokenType.Operator, TokenAnalyzer.getTokenType("+"));
        assertEquals(TokenType.Operator, TokenAnalyzer.getTokenType("*"));
        assertEquals(TokenType.Operator, TokenAnalyzer.getTokenType("/"));
        assertEquals(TokenType.Operator, TokenAnalyzer.getTokenType("^"));
        assertEquals(TokenType.Operator, TokenAnalyzer.getTokenType("sin"));
        assertEquals(TokenType.Operator, TokenAnalyzer.getTokenType("cos"));
        assertEquals(TokenType.Operator, TokenAnalyzer.getTokenType("tg"));
        assertEquals(TokenType.Operator, TokenAnalyzer.getTokenType("ctg"));
    }

    @Test
    void getTokenTypeOpenParenthesis() {
        assertEquals(TokenType.OpenParenthesis, TokenAnalyzer.getTokenType("("));
    }

    @Test
    void getTokenTypeCloseParenthesis() {
        assertEquals(TokenType.CloseParenthesis, TokenAnalyzer.getTokenType(")"));
    }

    @Test
    void getTokenTypeException(){
        Throwable thrown = catchThrowable(() -> TokenAnalyzer.getTokenType("s"));
        assertThat(thrown).isInstanceOf(TokenException.class);
        assertThat(thrown.getMessage()).isNotBlank();
    }

    @Test
    void operatorPriorityOne() {
        assertEquals(1, TokenAnalyzer.operatorPriority("-"));
        assertEquals(1, TokenAnalyzer.operatorPriority("+"));
    }

    @Test
    void operatorPriorityTwo() {
        assertEquals(2, TokenAnalyzer.operatorPriority("*"));
        assertEquals(2, TokenAnalyzer.operatorPriority("/"));
    }

    @Test
    void operatorPriorityThree() {
        TRIGONOMETRIC_OPERATIONS.forEach(
                it -> assertEquals(3, TokenAnalyzer.operatorPriority(it))
        );
        assertEquals(3, TokenAnalyzer.operatorPriority("^"));
    }

    @Test
    void operatorPriorityException() {
        Throwable thrown = catchThrowable(() -> assertEquals(3, TokenAnalyzer.operatorPriority("%")));
        assertThat(thrown).isInstanceOf(TokenException.class);
        assertThat(thrown.getMessage()).isNotBlank();
    }

    @Test
    void evaluateOperationPlus(){
        assertEquals(3, TokenAnalyzer.evaluateOperation("+", 2, 1));
    }

    @Test
    void evaluateOperationMinus(){
        assertEquals(-1, TokenAnalyzer.evaluateOperation("-", 2, 1));
    }

    @Test
    void evaluateOperationMultiply(){
        assertEquals(2, TokenAnalyzer.evaluateOperation("*", 2, 1));
    }

    @Test
    void evaluateOperationDivision(){
        assertEquals(0.5, TokenAnalyzer.evaluateOperation("/", 2, 1));
    }

    @Test
    void evaluateOperationExp(){
        assertEquals(9, TokenAnalyzer.evaluateOperation("^", 2, 3));
    }

    @Test
    void evaluateOperationException(){
        Throwable thrown = catchThrowable(() -> assertEquals(9, TokenAnalyzer.evaluateOperation("%", 2, 3)));
        assertThat(thrown).isInstanceOf(TokenException.class);
        assertThat(thrown.getMessage()).isNotBlank();
    }

    @Test
    void evaluateOperationSin(){
        assertEquals(0.8414709848078965, TokenAnalyzer.evaluateOperation("sin", 1));
    }

    @Test
    void evaluateOperationCos(){
        assertEquals(0.5403023058681398, TokenAnalyzer.evaluateOperation("cos", 1));
    }

    @Test
    void evaluateOperationTg(){
        assertEquals(1.5574077246549023, TokenAnalyzer.evaluateOperation("tg", 1));
    }

    @Test
    void evaluateOperationCtg(){
        assertEquals(0.6420926159343306, TokenAnalyzer.evaluateOperation("ctg", 1));
    }

    @Test
    void evaluateOperationExceptionTrig(){
        Throwable thrown = catchThrowable(() -> assertEquals(1, TokenAnalyzer.evaluateOperation("^", 1)));
        assertThat(thrown).isInstanceOf(TokenException.class);
        assertThat(thrown.getMessage()).isNotBlank();
    }
}