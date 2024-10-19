import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Main
{
    public static JSONObject readJsonFile(String filePath)
    {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            String json = scanner.useDelimiter("\\Z").next();
            scanner.close();
            return new JSONObject(json);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            return null;
        }
    }

    public static int decodeValue(int base, String value) {
        return Integer.parseInt(value, base);
    }

    public static long lagrangeInterpolation(int[][] points) {
        int n = points.length;
        double c = 0;

        for (int i = 0; i < n; i++) {
            int xi = points[i][0];
            int yi = points[i][1];
            double li = 1.0;

            for (int j = 0; j < n; j++) {
                if (i != j) {
                    int xj = points[j][0];
                    li *= (0.0 - xj) / (xi - xj);
                }
            }
            c += li * yi;
        }

        return Math.round(c);
    }

    public static void main(String[] args) {
        String filePath1 = "testcase1.json";
        String filePath2 = "testcase2.json";

        JSONObject input1 = readJsonFile(filePath1);
        JSONObject input2 = readJsonFile(filePath2);

        if (input1 != null && input2 != null) {
            int n1 = input1.getJSONObject("keys").getInt("n");
            int k1 = input1.getJSONObject("keys").getInt("k");

            int n2 = input2.getJSONObject("keys").getInt("n");
            int k2 = input2.getJSONObject("keys").getInt("k");

            int[][] points1 = new int[n1][2];
            int[][] points2 = new int[n2][2];

            for (int i = 1; i <= n1; i++) {
                JSONObject point = input1.getJSONObject(String.valueOf(i));
                int base = Integer.parseInt(point.getString("base"));
                String value = point.getString("value");
                int x = i;
                int y = decodeValue(base, value);
                points1[i - 1][0] = x;
                points1[i - 1][1] = y;
            }

            for (int i = 1; i <= n2; i++) {
                JSONObject point = input2.getJSONObject(String.valueOf(i));
                int base = Integer.parseInt(point.getString("base"));
                String value = point.getString("value");
                int x = i;
                int y = decodeValue(base, value);
                points2[i - 1][0] = x;
                points2[i - 1][1] = y;
            }

            long secretC1 = lagrangeInterpolation(points1);
            long secretC2 = lagrangeInterpolation(points2);
            System.out.println("The constant term c for testcase1 is: " + secretC1);
            System.out.println("The constant term c for testcase2 is: " + secretC2);
        }
    }
}