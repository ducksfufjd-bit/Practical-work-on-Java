public class Matrix {
    private Complex[][] data;
    private int rows;
    private int cols;

    public Matrix(Complex[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = data;
    }

    // Сложение матриц
    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }

        Complex[][] result = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = this.data[i][j].add(other.data[i][j]);
            }
        }
        return new Matrix(result);
    }

    // Умножение матриц
    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Матрицы нельзя умножить: несовместимые размеры");
        }

        Complex[][] result = new Complex[this.rows][other.cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex sum = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.add(this.data[i][k].multiply(other.data[k][j]));
                }
                result[i][j] = sum;
            }
        }
        return new Matrix(result);
    }

    // Транспонирование матрицы
    public Matrix transpose() {
        Complex[][] result = new Complex[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[j][i] = data[i][j];
            }
        }
        return new Matrix(result);
    }

    // Вспомогательный метод для получения минора
    private Matrix getMinor(int excludeRow, int excludeCol) {
        Complex[][] minor = new Complex[rows - 1][cols - 1];
        int r = 0;
        for (int i = 0; i < rows; i++) {
            if (i == excludeRow) continue;
            int c = 0;
            for (int j = 0; j < cols; j++) {
                if (j == excludeCol) continue;
                minor[r][c] = data[i][j];
                c++;
            }
            r++;
        }
        return new Matrix(minor);
    }

    // Вычисление определителя
    public Complex determinant() {
        if (rows != cols) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }

        if (rows == 1) {
            return data[0][0];
        }

        if (rows == 2) {
            return data[0][0].multiply(data[1][1])
                    .subtract(data[0][1].multiply(data[1][0]));
        }

        Complex det = new Complex(0, 0);
        for (int j = 0; j < cols; j++) {
            Complex term = data[0][j].multiply(getMinor(0, j).determinant());
            if (j % 2 == 0) {
                det = det.add(term);
            } else {
                det = det.subtract(term);
            }
        }
        return det;
    }

    // Нахождение обратной матрицы
    public Matrix inverse() {
        if (rows != cols) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }

        Complex det = determinant();
        if (det.isZero()) {
            throw new ArithmeticException("Определитель равен нулю, обратной матрицы не существует");
        }

        if (rows == 1) {
            return new Matrix(new Complex[][]{{new Complex(1, 0).divide(det)}});
        }

        Complex[][] res = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Complex cofactor = getMinor(i, j).determinant();
                if ((i + j) % 2 != 0) {
                    cofactor = cofactor.multiply(new Complex(-1, 0));
                }
                // Записываем сразу транспонированно (j, i)
                res[j][i] = cofactor.divide(det);
            }
        }
        return new Matrix(res);
    }

    // Деление матриц
    public Matrix divide(Matrix other) {
        return this.multiply(other.inverse());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}