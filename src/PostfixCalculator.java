import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PostfixCalculator {

    // Evaluates a postfix expression using a stack
    public int evaluatePostfix(String postfixExpression) {
        // Stqack used to hold operands during evaluation
        Stack<Integer> stack = new Stack<>();

        // Hnadles empty or null expressions
        if (postfixExpression == null || postfixExpression.isEmpty()) {
            System.out.println("Error: Empty expression");
            return Integer.MIN_VALUE;
        }

        // Loop through each character ion the postfix expression
        for (int i = 0; i < postfixExpression.length(); i++) {
            char ch = postfixExpression.charAt(i);

            // Skip whitespace characters
            if (Character.isWhitespace(ch)) {
                continue;
            }

            // Push single-digit operand to the stack
            if (Character.isDigit(ch)) {
                stack.push(Character.getNumericValue(ch));

            }

            // Handle arithmetic operators
            else if ("+-*/%".indexOf(ch) != -1) {
                // Needs at least two operands to apply to an operator
                if (stack.size() < 2) {
                    System.out.println("Error: Invalid postfix expression (not enough operands)");
                    return Integer.MIN_VALUE;
                }

                // Pop operands in reverse order (b is top of stack)
                int b = stack.pop();
                int a = stack.pop();
                int result = 0;

                // Apply the operator to operands a and b
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

                // Finish by pushing the result to the stack
                stack.push(result);
            }

            // Handles unknown character logic
            else {
                System.out.println("Error: unknown character '" + ch + "'");
                return Integer.MIN_VALUE;
            }
        }

        // After evaluation, exactly one result should remain
        if (stack.size() != 1) {
            System.out.println("Error: Invalid postfix expression (too many operands)");
            return Integer.MIN_VALUE;
        }

        return stack.pop();
    }

    // Reads postfix expressions from a file and evaluates them line by line
    public void evaluateFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 1;
            // Read and evaluate each line as a postfix expression
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

        // Main method for manual and file-based testing of postfix calculator
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

        // Evaulate multiple postfix expressions from a file
        System.out.println("\nEvaluating from file:");
        calculator.evaluateFromFile("expressions.txt");
    }
}