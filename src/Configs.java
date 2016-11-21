import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ivoribeiro on 19-11-2016.
 */
public class Configs {


    private Product __cup;
    private Product __sugar;
    private Product __spoon;
    private Product __coffe;
    private Product __choco;
    private Product __descafe;
    private Product __tea;

    private String __logPath;

    public Configs() {

        String configFile = "conf.txt";
        String line;
        String configSplitBy = "=";
        String productSplitBy = ";";
        try (BufferedReader br = new BufferedReader(new FileReader(configFile))) {
            String logPathLine = br.readLine();
            String[] logPathConf = logPathLine.split(configSplitBy);
            this.__logPath = logPathConf[1];

            while ((line = br.readLine()) != null) {
                String[] productConf = line.split(productSplitBy);
                switch (productConf[0]) {
                    case "choco":
                        this.__choco = new Product(Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]));
                    case "coffe":
                        this.__coffe = new Product(Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]));
                    case "tea":
                        this.__tea = new Product(Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]));
                    case "desca":
                        this.__descafe = new Product(Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]));
                    case "cup":
                        this.__cup = new Product(Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]));
                    case "spoon":
                        this.__spoon = new Product(Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]));
                    case "sugar":
                        this.__sugar = new Product(Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]));
                }
            }

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    //--------------GetMethods

    public Product getCup() {
        return __cup;
    }

    public Product getSugar() {
        return __sugar;
    }

    public Product getSpoon() {
        return __spoon;
    }

    public Product getCoffe() {
        return __coffe;
    }

    public Product getChoco() {
        return __choco;
    }

    public Product getDescafe() {
        return __descafe;
    }

    public Product getTea() {
        return __tea;
    }

    public String getLog() {
        return __logPath;
    }

    //-----------------------END
}
