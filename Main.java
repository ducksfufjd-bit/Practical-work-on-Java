import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Matrix lastResult = null; // Хранит результат последней операции

        while (true) {
            System.out.println("\n===== РАБОТА С МАТРИЦАМИ =====");
            System.out.println("1. Сложение матриц");
            System.out.println("2. Умножение матриц");
            System.out.println("3. Транспонирование матрицы");
            System.out.println("4. Вычисление определителя");
            System.out.println("5. Деление матриц");
            System.out.println("0. Выход");

            int choice = readInt(scanner, "Выберите операцию: ");

            if (choice == 0) {
                System.out.println("Программа завершена.");
                break;
            }

            try {
                Matrix a, b;
                switch (choice) {
                    case 1:
                        a = getMatrix(scanner, lastResult);
                        System.out.println("Ввод второй матрицы:");
                        b = readMatrix(scanner);
                        lastResult = a.add(b);
                        System.out.println("Результат:\n" + lastResult);
                        break;
                    case 2:
                        a = getMatrix(scanner, lastResult);
                        System.out.println("Ввод второй матрицы:");
                        b = readMatrix(scanner);
                        lastResult = a.multiply(b);
                        System.out.println("Результат:\n" + lastResult);
                        break;
                    case 3:
                        a = getMatrix(scanner, lastResult);
                        lastResult = a.transpose();
                        System.out.println("Результат:\n" + lastResult);
                        break;
                    case 4:
                        a = getMatrix(scanner, lastResult);
                        System.out.println("Определитель: " + a.determinant());
                        break;
                    case 5:
                        a = getMatrix(scanner, lastResult);
                        System.out.println("Ввод второй матрицы:");
                        b = readMatrix(scanner);
                        lastResult = a.divide(b);
                        System.out.println("Результат:\n" + lastResult);
                        break;
                    default:
                        System.out.println("Неверная операция");
                }
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }

    // Выбор: взять предыдущий результат или ввести новую матрицу
    public static Matrix getMatrix(Scanner scanner, Matrix lastResult) {
        if (lastResult != null) {
            int useLast = readInt(scanner, "Использовать предыдущий результат? (1 - да, 0 - ввести новую): ");
            if (useLast == 1) {
                return lastResult;
            }
        }
        return readMatrix(scanner);
    }

    public static Matrix readMatrix(Scanner scanner) {
        int rows = readPositiveInt(scanner, "Количество строк: ");
        int cols = readPositiveInt(scanner, "Количество столбцов: ");

        Complex[][] data = new Complex[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println("Элемент [" + (i + 1) + "][" + (j + 1) + "]:");
                double real = readDouble(scanner, "  Действительная часть: ");
                double imaginary = readDouble(scanner, "  Мнимая часть: ");
                data[i][j] = new Complex(real, imaginary);
            }
        }

        return new Matrix(data);
    }

    public static int readInt(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число");
            }
        }
    }

    public static int readPositiveInt(Scanner scanner, String message) {
        while (true) {
            int number = readInt(scanner, message);
            if (number > 0) {
                return number;
            }
            System.out.println("Число должно быть больше нуля");
        }
    }

    public static double readDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Введите корректное число");
            }
        }
    }
}