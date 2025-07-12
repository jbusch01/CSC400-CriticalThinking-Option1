import java.util.Stack;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PostfixCalculator {

    // Evaluates a postfix expression using a stack
    public int evaluatePostfix(String postfixExpression) {
        // Stack used to hold operands during evaluation
        Stack<Integer> stack = new Stack<>();

        // Handles empty or null expressions
        if (postfixExpression == null || postfixExpression.isEmpty()) {
            System.out.println("Error: Empty expression");
            return Integer.MIN_VALUE;
        }

        // If expression ccontains spaces then treat tokens as full numbers or operators
        if (postfixExpression.contains(" ")) {
            String[] tokens = postfixExpression.trim().split("\\s+");

            for (String token : tokens) {
                if (token.matches("-?\\d+")) {
                    stack.push(Integer.parseInt(token));                
                } else if ("+-*/%".contains(token)) {
                    if (stack.size() < 2) {
                        System.out.println("Error: not enough operands.");
                        return Integer.MIN_VALUE;
                        }
                        int b = stack.pop();
                        int a = stack.pop();
                    stack.push(applyOperator(a, b, token.charAt(0)));
                } else {
                    System.out.println("Error: unknown token '" + token + "'");
                    return Integer.MIN_VALUE;
                }
            }
        }

        else {
            for (int i = 0; i < postfixExpression.length(); i++) {
                char ch = postfixExpression.charAt(i);

                // Skip whitespace
                if (Character.isWhitespace(ch)) {
                    continue;
                } else if(Character.isDigit(ch)) {
                    stack.push(Character.getNumericValue(ch));
                } else if ("+-*/%".indexOf(ch) >= 0) {
                    if (stack.size() < 2) {
                        System.out.println("Error: Not enough operands.");
                        return Integer.MIN_VALUE;
                    }
                    int b = stack.pop();
                    int a = stack.pop();
                    stack.push(applyOperator(a, b, ch));
                } else {
                    System.out.println("Error: unknown character '" + ch + "'");
                    return Integer.MIN_VALUE;
                }
            }
        }

        if (stack.size() != 1) {
            System.out.println("Error: Invalid postfix expression (too many operands)");
            return Integer.MIN_VALUE;
        }

        return stack.pop();
    }

    // Applies operator to two operands
    private int applyOperator(int a, int b, char operator) {
        switch (operator) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) {
                    System.out.println("Error: Division by zero.");
                    return Integer.MIN_VALUE;
                }
                return a / b;
            case '%':
                if (b == 0) {
                    System.out.println("Error: Division by zero.");
                    return Integer.MIN_VALUE;
                }
                return a % b;
            default:
                System.out.println("Error: Unsupported operator '" + operator + "'");
                return Integer.MIN_VALUE;

        }
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
        String expression2 = "53+7*";
        String expression3 = "42*+";
        String expression4 = "10 2 / 3 +";

        int result1 = calculator.evaluatePostfix(expression1);
        if (result1 != Integer.MIN_VALUE) System.out.println("Result 1: " + result1);
        System.out.println();
                
        int result2 = calculator.evaluatePostfix(expression2);
        if (result2 != Integer.MIN_VALUE) System.out.println("Result 2: " + result2);
        System.out.println();

        int result3 = calculator.evaluatePostfix(expression3);
        if (result3 != Integer.MIN_VALUE) System.out.println("Result 3: " + result3);
        System.out.println();

        int result4 = calculator.evaluatePostfix(expression4);
        if (result4 != Integer.MIN_VALUE) System.out.println("Result 4: " + result4);
        System.out.println();

        // Evaluate multiple postfix expressions from a file
        System.out.println("\nEvaluating from file:");
        calculator.evaluateFromFile("expressions.txt");
    }
}