package com.example.calculator.controller;

import com.example.calculator.domain.Operation;
import com.example.calculator.service.OperationService;
import com.example.calculator.service.validator.OperationValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OperationController {
    private final OperationService operationService;
    private final OperationValidator operationValidator;
    private static final Logger LOGGER = LoggerFactory.getLogger(OperationController.class);

    public OperationController(OperationService operationService, OperationValidator operationValidator) {
        this.operationService = operationService;
        this.operationValidator = operationValidator;
    }

    @GetMapping(value = "/")
    public RedirectView redirect(){
        return new RedirectView("/operation");
    }

    @PostMapping(value = "/operation")
    public ResponseEntity<?> create(@RequestBody @Validated Operation operation,
                                    BindingResult result,
                                    @AuthenticationPrincipal User user) {
        if (result.hasErrors()) {
            LOGGER.warn("error occurs during expression validation {}", operation);
            return new ResponseEntity<>(
                    getErrorsMap(result),
                    HttpStatus.BAD_REQUEST);
        }

        Operation savedOperation = operationService.create(operation, user);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOperation.getId())
                .toUri();
        return new ResponseEntity<>(Map.of(HttpHeaders.LOCATION, location), HttpStatus.CREATED);
    }

    @GetMapping(value = "/operation")
    public ResponseEntity<List<Operation>> read(@RequestParam(required = false) String expression,
                                                @RequestParam(required = false, defaultValue = "today") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
                                                @RequestParam(required = false, defaultValue = "today") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate,
                                                @RequestParam(required = false) String username) {
        final List<Operation> operations = operationService.readFiltered(expression, startDate, endDate, username);

        return operations != null && !operations.isEmpty()
                ? new ResponseEntity<>(operations, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/operation/{id}")
    public ResponseEntity<Operation> read(@PathVariable(name = "id") UUID id) {
        final Operation contract = operationService.read(id);

        return contract != null
                ? new ResponseEntity<>(contract, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(operationValidator);
        binder.registerCustomEditor(Date.class, getDateEditor());
    }

    private Map<String, String> getErrorsMap(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    private CustomDateEditor getDateEditor() {
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new CustomDateEditor(dateFormat, true) {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                if ("today".equals(text)) {
                    setValue(new Date());
                } else {
                    super.setAsText(text);
                }
            }
        };
    }
}
