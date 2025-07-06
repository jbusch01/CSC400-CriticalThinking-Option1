import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PostfixCalculator {

    public int evaluatePostfix(String postfixExpression) {
        Stack<Integer> stack = new Stack<>();

        if (postfixExpression == null || postfixExpression.isEmpty()) {
            System.out.println("Error: Empty expression");
            return Integer.MIN_VALUE;
        }

        for (int i = 0; i < postfixExpression.length(); i++) {
            char ch = postfixExpression.charAt(i);

            if (Character.isWhitespace(ch)) {
                continue;
            }

            if (Character.isDigit(ch)) {
                stack.push(Character.getNumericValue(ch));

            }

            else if ("+-*/%".indexOf(ch) != -1) {
                if (stack.size() < 2) {
                    System.out.println("Error: Invalid postfix expression (not enough operands)");
                    return Integer.MIN_VALUE;
                }

                int b = stack.pop();
                int a = stack.pop();
                int result = 0;

                switch (ch) {
                    case '+': result = a + b; break;
                    case '-': result = a - b; break;
                    case '*': result = a * b; break;
                    case '/':
                        if (b == 0) {
                            System.out.println("Error: Division by zero");
                            return Integer.MIN_VALUE;
                        }
                        result = a / b;
                        break;
                    case '%':
                        if (b == 0) {
                            System.out.println("Error: Modulo by zero");
                            return Integer.MIN_VALUE;
                        }
                        result = a % b;
                        break;
                }

                stack.push(result);
            }

            else {
                System.out.println("Error: unknown character '" + ch + "'");
                return Integer.MIN_VALUE;
            }
        }

        if (stack.size() != 1) {
            System.out.println("Error: Invalid postfix expression (too many operands)");
            return Integer.MIN_VALUE;
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        PostfixCalculator calculator = new PostfixCalculator();

        String expression1 = "42*3+";
        int result1 = calculator.evaluatePostfix(expression1);
        if (result1 != Integer.MIN_VALUE) {
            System.out.println("Result 1: " + result1);
        }
        
        String expression2 = "53+7*";
        int result2 = calculator.evaluatePostfix(expression2);
        if (result2 != Integer.MIN_VALUE) {
            System.out.println("Result 2: " + result2);
        }

        String expression3 = "42*+";
        int result3 = calculator.evaluatePostfix(expression3);
        if (result3 != Integer.MIN_VALUE) {
            System.out.println("Result 3: " + result3);
        }

        System.out.println("\nEvaluating from file:");
        calculator.evaluateFromFile("expressions.txt");
    }

    public void evaluateFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                int result = evaluatePostfix(line);
                if (result != Integer.MIN_VALUE) {
                    System.out.println("Line " + lineNumber + ": " + line + " = " + result);
                }
                lineNumber++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}