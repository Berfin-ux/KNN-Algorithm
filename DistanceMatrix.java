
import java.util.Random;

public class DistanceMatrix {
    public DistanceMatrix() {
    }

    public static void main(String[] args) {
        double[][] points = producePoint(100, 100, 10); //en, boy ve n değerlerini alarak rastgele n tane nokta üretiyor.
        System.out.println(" n |     x   |     y");
        System.out.println("______________________");

        for(int i = 0; i < points.length; ++i) { // üretilen  noktaların x ve y değerlerini yazdırıyor.
            System.out.print(" |");
            System.out.format("%8.2f", points[i][0]);
            System.out.print(" |");
            System.out.format("%8.2f", points[i][1]);
            System.out.println();
        }

        System.out.println();
        double[][] values = distanceMatrix(points); //nokta matrisini alarak bunların uzaklık ilişkisini döndürüyor.

        for (int z = 0; z < values.length; z++) {System.out.format("%14d",  z );}


        System.out.println();

        for(int i = 0; i < values.length; ++i) {   //uzaklıklar yazdırılıyor.
            System.out.format(  "%4d", i );

            for(int j = 0; j < values.length; ++j) {

                System.out.format("%11.2f", values[i][j]);
                System.out.print("   ");
            }

            System.out.println();
        }

    }

    public static double[][] producePoint(int width, int height, int n) {
        double[][] matrix = new double[n][2];          //nx2 boyutunda matris oluşturuyor.
        Random r = new Random();

        for(int i = 0; i < n; ++i) {                   //n tane rastgele sayı üretiyor.
            double x = r.nextDouble() * (double)width;
            double y = r.nextDouble() * (double)height;
            matrix[i][0] = x;                          //nx2 boyutlu matrise ekleme yapıyor.
            matrix[i][1] = y;
        }

        return matrix;
    }

    public static double[][] distanceMatrix(double[][] matrix) {
        int n = matrix.length;
        double[][] DM = new double[n][n];              //nxn kare matrisi oluşturuyor.

        for(int i = 0; i < n; ++i) {                  //her noktanın bütün noktalara göre uzaklığı hesaplanıyor.
            double first_x = matrix[i][0];
            double first_y = matrix[i][1];

            for(int j = i; j < n; ++j) {
                double last_x = matrix[j][0];
                double last_y = matrix[j][1];
                double distance = Math.sqrt(Math.pow(last_x - first_x, 2.0D) + Math.pow(last_y - first_y, 2.0D));
                DM[i][j] = distance;                  //ilgili indexlere bu uzaklıklar ekleniyor.
                DM[j][i] = distance;
            }
        }

        return DM;
    }
}
