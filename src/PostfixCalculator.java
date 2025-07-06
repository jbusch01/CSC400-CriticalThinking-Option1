import java.util.Stack;

public class PostfixCalculator {

    public int evaluatePostfix(String postfixExpression) {
        // To be implemented in next step
        Stack<Integer> stack = new Stack<>();

        if (postfixExpression == null || postfixExpression.isEmpty()) {
            System.out.println("Error: Empty expression");
            return Integer.MIN_VALUE;
        }

        StringBuilder number = new StringBuilder();

        for (int i = 0; i < postfixExpression.length(); i++) {
            char ch = postfixExpression.charAt(i);

            if (Character.isWhitespace(ch)) {
                continue;
            }

            if (Character.isDigit(ch)) {
                number.append(ch);
                if (i == postfixExpression.length() - 1 || !Character.isDigit(postfixExpression.charAt(i + 1))) {
                    stack.push(Integer.parseInt(number.toString()));
                    number.setLength(0);
                }
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
                    case '+':
                        result = a + b;
                        break;
                    case '-':
                        result = a - b;
                        break;
                    case '*':
                        result = a * b;
                        break;
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
        System.out.println("Result 1: " + calculator.evaluatePostfix(expression1));
        
        String expression2 = "53+7*";
        System.out.println("Result 2: " + calculator.evaluatePostfix(expression2));

        String expression3 = "42*+";
        System.out.println("Result 3: " + calculator.evaluatePostfix(expression3));;
    }
}