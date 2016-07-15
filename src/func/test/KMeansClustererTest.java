package func.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import dist.Distribution;
import dist.MultivariateGaussian;
import func.KMeansClusterer;
import shared.DataSet;
import shared.Instance;
import util.linalg.DenseVector;
import util.linalg.RectangularMatrix;

/**
 * Testing
 * @author Andrew Guillory gtg008g@mail.gatech.edu
 * @version 1.0
 */
public class KMeansClustererTest implements Runnable {
    
    private int num_instances;
    private int num_attr;
    private String file_name;
    
    public KMeansClustererTest(int num_instances, int num_attr, String file) {
        this.num_instances = num_instances;
        this.num_attr = num_attr;
        this.file_name = file;
    }
    
    public static void main(String[] args) {
        KMeansClustererTest test = new KMeansClustererTest(4601,57,"src/opt/test/spam.txt.csv");
        test.run();
    }
    
    /**
     * The test main
     * @param args ignored
     */
    public void run() {
        Instance[] instances = initializeInstances();
        DataSet set = new DataSet(instances);
        KMeansClusterer km = new KMeansClusterer();
        km.estimate(set);
        System.out.println(km);
    }
    
    private Instance[] initializeInstances() {

        double[][][] attributes = new double[this.num_instances][][];

        try {
            // "src/opt/test/adult.txt"
            BufferedReader br = new BufferedReader(new FileReader(new File(this.file_name)));
            for(int i = 0; i < attributes.length; i++) {
                Scanner scan = new Scanner(br.readLine());
                scan.useDelimiter(",");

                attributes[i] = new double[2][];
                attributes[i][0] = new double[this.num_attr]; // num_attributes
                attributes[i][1] = new double[1];

                for(int j = 0; j < this.num_attr; j++)
                    attributes[i][0][j] = Double.parseDouble(scan.next());

                attributes[i][1][0] = Double.parseDouble(scan.next());
                scan.close();
            }
            br.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        Instance[] instances = new Instance[attributes.length];

        for(int i = 0; i < instances.length; i++) {
            instances[i] = new Instance(attributes[i][0]);
            // classifications range from 0 to 30; split into 0 - 14 and 15 - 30
            instances[i].setLabel(new Instance(attributes[i][1][0] < 15 ? 0 : 1));
        }

        return instances;
    }
}
