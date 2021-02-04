package com.example.calculator.service.impl;

import com.example.calculator.exceptions.TokenException;
import com.example.calculator.service.enums.TokenType;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TokenAnalyzer {
    public static final Set<String> TRIGONOMETRIC_OPERATIONS = Set.of("sin", "cos", "tg", "ctg");
    public static final String REGEX_FOR_TRIG_OPERATIONS = TRIGONOMETRIC_OPERATIONS.stream().map(it -> "|(" + it + ")").collect(Collectors.joining(""));


    public static TokenType getTokenType(String token) {
        if (Pattern.matches("\\d+", token)) return TokenType.Number;
        if (Pattern.matches("[+\\-*/^]|" + REGEX_FOR_TRIG_OPERATIONS, token)) return TokenType.Operator;
        if (Pattern.matches("[(]", token)) return TokenType.OpenParenthesis;
        if (Pattern.matches("[)]", token)) return TokenType.CloseParenthesis;

        throw new TokenException("Invalid type of token");
    }

    public static int operatorPriority(String operation) {
        if ("+".equals(operation) || "-".equals(operation)) return 1;
        if ("*".equals(operation) || "/".equals(operation)) return 2;
        if (TRIGONOMETRIC_OPERATIONS.contains(operation) || "^".equals(operation)) return 3;

        throw new TokenException("Invalid operator type" + operation);
    }

    public static Double evaluateOperation(String operator, double firstValue, double secondValue) {
        switch (operator) {
            case "+": {
                return firstValue + secondValue;
            }
            case "-": {
                return secondValue - firstValue;
            }
            case "*": {
                return firstValue * secondValue;
            }
            case "/": {
                return secondValue * 1. / firstValue;
            }
            case "^": {
                return Math.pow(secondValue, firstValue);
            }
            default:
                throw new TokenException("Unsupported operator type");
        }
    }

    public static Double evaluateOperation(String function, double value) {
        switch (function) {
            case "sin": {
                return Math.sin(value);
            }
            case "cos": {
                return Math.cos(value);
            }
            case "tg": {
                return Math.tan(value);
            }
            case "ctg": {
                return 1. / Math.tan(value);
            }
            default:
                throw new TokenException("Unsupported function type");
        }
    }
}
