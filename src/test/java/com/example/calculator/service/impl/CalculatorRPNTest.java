package com.example.calculator.service.impl;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorRPNTest {

    CalculatorRPN calculatorRPN = new CalculatorRPN();

  @Test
    void test(){
    System.out.println(calculatorRPN.calculate("4+55*(7+9)^2-sin(7)*5"));
  }

  @Test
  void test2(){
    System.out.println(calculatorRPN.calculate("4+55*(7+9)^2-sin7*5"));
  }



  @Test
    void test3(){
      calculatorRPN.calculate("4+55*(7+9)^2-cos(7)*5");
  }
  @Test
    void test4(){
      calculatorRPN.calculate("4+55*(7+9)^2-tg(7)*5");
  }
  @Test
    void test5(){
      calculatorRPN.calculate("4+55*(7+9)^2-ctg(7)*5");
  }


    @Test
    void test6(){
      System.out.println(calculatorRPN.calculate("-4+5*(-8*2)"));
    }



}