package com.example.calculator.service.validator;

import com.example.calculator.domain.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OperationValidator implements Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return Operation.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Operation operation = (Operation) o;
        LOGGER.debug("start expression validation {}", operation);

        String expression = operation.getExpression().replaceAll("//s+", "");

        Pattern pattern = Pattern.compile("[^+\\-*/\\d()\\.^(sin)(cos)(tg)(ctg)]");
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            errors.rejectValue("expression", "Expression contains invalid symbols", "Expression contains invalid symbols");
            return;
        }

        pattern = Pattern.compile("[+\\-*/\\.^]{2,}");
        matcher = pattern.matcher(expression);
        if (matcher.find()) {
            errors.rejectValue("expression", "One or more operands are missed", "One or more operands are missed");
            return;
        }

        pattern = Pattern.compile("\\(\\)");
        matcher = pattern.matcher(expression);
        if (matcher.find()
                || !checkForParentheses(expression)) {
            errors.rejectValue("expression", "Error in parentheses", "Error in parentheses");
            return;
        }


        pattern = Pattern.compile("\\d+\\(\\-*\\d+| \n" +
                                  "\\d+\\)\\d+");
        matcher = pattern.matcher(expression);
        if (matcher.find()) {
            errors.rejectValue("expression", "One or more operators are missed", "One or more operators are missed");
            return;
        }

        pattern = Pattern.compile("\\.[^\\d]|\n" +
                                  "[^\\d]\\.");
        matcher = pattern.matcher(expression);
        if (matcher.find()) {
            errors.rejectValue("expression", "Invalid symbol before/after decimal point", "Invalid symbol before/after decimal point");
        }

    }

    private boolean checkForParentheses(String expression) {
        int openParenthesisCount = 0;
        int closeParenthesisCount = 0;

        for (char ch :
                expression.toCharArray()) {
            if ('(' == ch) {
                openParenthesisCount++;
            } else if (')' == ch) {
                closeParenthesisCount++;
            }

            if (closeParenthesisCount > openParenthesisCount) {
                return false;
            }
        }

        return closeParenthesisCount == openParenthesisCount;
    }
}
