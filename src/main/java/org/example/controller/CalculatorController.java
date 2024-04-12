package org.example.controller;

import org.example.service.CalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping
    public String hello() {
        return "Добро пожаловать в калькулятор!";
    }

    @GetMapping("/plus")
    public String plus(@RequestParam(value = "num1", required = false) Integer num1,
                       @RequestParam(value = "num2", required = false) Integer num2) {
        return calculate(num1, num2, "+");
    }

    @GetMapping("/minus")
    public String minus(@RequestParam(value = "num1", required = false) Integer num1,
                        @RequestParam(value = "num2", required = false) Integer num2) {
        return calculate(num1, num2, "-");
    }

    @GetMapping("/multiply")
    public String multiply(@RequestParam(value = "num1", required = false) Integer num1,
                           @RequestParam(value = "num2", required = false) Integer num2) {
        return calculate(num1, num2, "*");
    }

    @GetMapping("/divide")
    public String divide(@RequestParam(value = "num1", required = false) Integer num1,
                         @RequestParam(value = "num2", required = false) Integer num2) {
        return calculate(num1, num2, "/");
    }

    private String calculate(Integer num1, Integer num2, String operation) {
        if (num1 == null || num2 == null) {
            return "Какой-то из параметров не передан";
        }
        if (operation.equals("/") && num2 == 0){
            return "На ноль делить нельзя!";
        }
        int result = switch (operation) {
            case "-" -> calculatorService.minus(num1, num2);
            case "*" -> calculatorService.multiply(num1, num2);
            case "/" -> calculatorService.divide(num1, num2);
            default -> calculatorService.plus(num1, num2);
        };
        return String.format("%d %s %d = %d", num1, operation, num2, result);
    }
}
