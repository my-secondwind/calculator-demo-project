package com.example.calculator.service.impl;

import com.example.calculator.service.Calculator;
import com.example.calculator.service.enums.TokenType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            expression = unaryMinusCorrection(expression);
            convertStringToTokens(expression);
            System.out.println(expressionByList);
            convertToReversePolishNotation();
            System.out.println(expressionByList);
            result = evaluateReversePolishNotation();
        } catch (Exception e) {
            LOGGER.error("Error during calculation ", e);
        }
        return result;
    }

    private void convertStringToTokens(String expression) {
        expression = expression.replaceAll("[+\\-*/()^]" + REGEX_FOR_TRIG_OPERATIONS, " $0 ");
        expression = expression.replaceAll("^\\s", "");
        expressionByList = List.of(expression.split("\\s+"));
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
                continue;
            }
            if (TokenType.Operator.equals(tokenType)) {
                double firstValue = Double.parseDouble(stack.pop());
                double secondValue = Double.parseDouble(stack.pop());
                Double result = TokenAnalyzer.evaluateOperation(token, firstValue, secondValue);
                stack.push(result.toString());
            }
        }
        return stack.pop();
    }

    private String unaryMinusCorrection(String expression) {
        expression = expression.replaceAll("(^-\\d)", "(0$0)");
        Pattern pattern = Pattern.compile("(\\(-\\d)");
        Matcher matcher = pattern.matcher(expression);
        while (matcher.find()){
            String str = matcher.group();
            String newStr = "((0" + str.substring(1) + ")";
            expression = expression.replace(str, newStr);
        }
        System.out.println(expression);
        return expression;
    }
}
