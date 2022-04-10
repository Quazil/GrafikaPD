

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    int platy;
    double[][][] k = new double[4][4][3];
    String sc1 = "C:\\Users\\Mateusz\\Documents\\GitHub\\GrafikaPD\\teacup1.txt";
    String sc2 = "C:\\Users\\Mateusz\\Documents\\GitHub\\GrafikaPD\\teapot1.txt";
    String sc3 = "C:\\Users\\Mateusz\\Documents\\GitHub\\GrafikaPD\\teaspoon1.txt";
    String sc4 = "C:\\Users\\Mateusz\\Documents\\GitHub\\GrafikaPD\\teacup1bezier.txt";
    String sc5 = "C:\\Users\\Mateusz\\Documents\\GitHub\\GrafikaPD\\teapot1bezier.txt";
    String sc6 = "C:\\Users\\Mateusz\\Documents\\GitHub\\GrafikaPD\\teaspoon1bezier.txt";

    Main() throws IOException {
        B(sc1, sc4);
        B(sc2, sc5);
        B(sc3, sc6);
    }
    public int dn(int n, int k)
    {
        if (k == 0 || k == n)
            return 1;
        else
            return dn(n-1, k-1) + dn(n-1, k);
    }
    public Double bB(int m, int i, double v){
        return Math.pow(v,i) * Math.pow(1-v,m-i) * dn(m,i);
    }
    public void B(String s1, String s2) throws IOException {
        File file1 = new File(s1);
        String tab1[];
        List<String> list1 = new ArrayList<String>();
        BufferedWriter bw = new BufferedWriter(new FileWriter(s2));
        int nr = 0;
        double Px2 = 0.0;
        double Py2 = 0.0;
        double Pz2 = 0.0;
        try (Scanner scan = new Scanner(file1)) {
            while (scan.hasNextLine()) {
                list1.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        tab1 = list1.get(nr).split(" ");
        platy = Integer.parseInt(tab1[0]);
        for (int i = 0; i < platy; i++) {
            nr++;
            for (int j = 0; j < 4; j++) {
                for (int l = 0; l < 4; l++) {
                    nr += 1;
                    tab1 = list1.get(nr).split(" ");
                    k[j][l][0] = Double.parseDouble(tab1[0]); //x
                    k[j][l][1] = Double.parseDouble(tab1[1]); //y
                    k[j][l][2] = Double.parseDouble(tab1[2]); //z
                }
            }
            for (double v = 0.0; v <= 1.0; v += 0.01) {
                for (double w = 0.0; w <= 1.0; w += 0.01) {
                    for (int a = 0; a <= 3; a++) {
                        for (int b = 0; b <= 3; b++) {
                            Px2 += k[a][b][0] * bB(3, a, w) * bB(3, b, v);
                            Py2 += k[a][b][1] * bB(3, a, w) * bB(3, b, v);
                            Pz2 += k[a][b][2] * bB(3, a, w) * bB(3, b, v);
                        }
                    }
                    bw.write(Px2 + "," + Py2 + "," + Pz2 + System.lineSeparator());
                    Px2 = 0.0;
                    Py2 = 0.0;
                    Pz2 = 0.0;
                }
            }
        }
        bw.close();
    }
    public static void main(String[] args) throws IOException {
        new Main();
    }
}
