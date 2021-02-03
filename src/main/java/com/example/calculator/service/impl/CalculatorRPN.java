package com.example.calculator.service.impl;

import com.example.calculator.service.Calculator;
import com.example.calculator.service.enums.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static com.example.calculator.service.impl.TokenAnalyzer.REGEX_FOR_TRIG_OPERATIONS;
import static com.example.calculator.service.impl.TokenAnalyzer.TRIGONOMETRIC_OPERATIONS;

@Service
public class CalculatorRPN implements Calculator {
    private List<String> expressionByList;
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorRPN.class);

    @Override
    public String calculate(String expression) {
        String result = "0";
        try {
            convertStringToTokens(expression);
            convertToReversePolishNotation();
            result = evaluateReversePolishNotation();
            System.out.println(result);
        } catch (Exception e) {
            LOGGER.error("Error during calculation ", e);
        }
        return result;
    }

    private void convertStringToTokens(String expression) {
        expression = expression.replaceAll("[+\\-*/()^]" + REGEX_FOR_TRIG_OPERATIONS, " $0 ");
        expressionByList = List.of(expression.split("\\s+"));
        System.out.println(expressionByList);
    }

    private void convertToReversePolishNotation() {
        Stack<String> stack = new Stack<>();
        List<String> outputTokenList = new ArrayList<>();
        for (String token :
                expressionByList) {
            TokenType tokenType = TokenAnalyzer.getTokenType(token);
            switch (tokenType) {
                case Number: {
                    outputTokenList.add(token);
                    break;
                }
                case OpenParenthesis: {
                    stack.push(token);
                    break;
                }
                case Operator: {
                    int operationPriority = TokenAnalyzer.operatorPriority(token);
                    while (!stack.empty() && TokenAnalyzer.getTokenType(stack.peek()).equals(TokenType.Operator)) {
                        int stackOperationPriority = TokenAnalyzer.operatorPriority(stack.peek());
                        if (operationPriority <= stackOperationPriority) {
                            outputTokenList.add(stack.pop());
                        } else {
                            break;
                        }
                    }
                    stack.push(token);
                    break;
                }
                case CloseParenthesis: {
                    while (!stack.empty() && !"(".equals(stack.peek())) {
                        outputTokenList.add(stack.pop());
                    }
                    if ("(".equals(stack.peek())) stack.pop();
                }
            }
        }
        while (!stack.empty()) {
            outputTokenList.add(stack.pop());
        }
        System.out.println(outputTokenList);
        expressionByList = outputTokenList;
    }

    private String evaluateReversePolishNotation() {
        Stack<String> stack = new Stack<>();
        for (String token :
                expressionByList) {
            TokenType tokenType = TokenAnalyzer.getTokenType(token);
            if (TokenType.Number.equals(tokenType)) {
                stack.push(token);
                continue;
            }
            if (TokenType.Operator.equals(tokenType) && TRIGONOMETRIC_OPERATIONS.contains(token)) {
                double value = Double.parseDouble(stack.pop());
                Double result = TokenAnalyzer.evaluateOperation(token, value);
                stack.push(result.toString());
                System.out.println(value + " " + token + " " + result);
                continue;
            }
            if (TokenType.Operator.equals(tokenType)) {
                double firstValue = Double.parseDouble(stack.pop());
                double secondValue = Double.parseDouble(stack.pop());
                Double result = TokenAnalyzer.evaluateOperation(token, firstValue, secondValue);
                System.out.println(firstValue + " " + secondValue + " " + token + " " + result);
                stack.push(result.toString());
            }
        }
        return stack.pop();
    }
}
