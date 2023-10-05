import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пример: ");
        String input = scanner.nextLine();
        scanner.close();

        try {
            String[] parts = input.split(" ");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Недопустимый формат ввода.");
            }
            String operand1 = parts[0];
            String operator = parts[1];
            String operand2 = parts[2];
            boolean isRoman = isRomanNumber(operand1) && isRomanNumber(operand2);
            int num1 = isRoman ? romanToArabic(operand1) : Integer.parseInt(operand1);
            int num2 = isRoman ? romanToArabic(operand2) : Integer.parseInt(operand2);
            if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10)) {
                throw new IllegalArgumentException("Числа должны быть в диапазоне от 1 до 10.");
            }
            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new ArithmeticException("Деление на ноль не допускается.");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая операция: " + operator);
            }
            String output = isRoman ? arabicToRoman(result) : Integer.toString(result);
            System.out.println("Результат: " + output);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Недопустимые числа.");
        } catch (IllegalArgumentException | ArithmeticException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private static boolean isRomanNumber(String s) {
        return s.matches("^[IVXLC]+$");
    }
    private static int romanToArabic(String roman) {
        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            char c = roman.charAt(i);
            int value = romanDigitToValue(c);
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }
        return result;
    }
    private static String arabicToRoman(int arabic) {
        if (arabic <= 0) {
            throw new IllegalArgumentException("Результат не может быть меньше или равен нулю.");
        }
        String[] romanNumerals = {"I", "IV", "V", "IX", "X", "C"};
        if (arabic <= 5) {
            return romanNumerals[arabic - 1];
        }
        StringBuilder roman = new StringBuilder();
        while (arabic >= 100) {
            roman.append("C");
            arabic -= 100;
        }
        if (arabic >= 90) {
            roman.append("XC");
            arabic -= 90;
        }
        if (arabic >= 50) {
            roman.append("L");
            arabic -= 50;
        }
        if (arabic >= 40) {
            roman.append("XL");
            arabic -= 40;
        }
        while (arabic >= 10) {
            roman.append("X");
            arabic -= 10;
        }
        return roman.toString();
    }
    private static int romanDigitToValue(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            default:
                throw new IllegalArgumentException("Недопустимая римская цифра: " + c);
        }
    }
}