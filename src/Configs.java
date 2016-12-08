import java.io.BufferedReader;
import java.io.FileReader;

class Configs {


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
                        this.__choco = new Product(productConf[0], Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]), 0);
                        break;
                    case "coffe":
                        this.__coffe = new Product(productConf[0], Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]), Integer.parseInt(productConf[3]));
                        break;
                    case "tea":
                        this.__tea = new Product(productConf[0], Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]), 0);
                        break;
                    case "desca":
                        this.__descafe = new Product(productConf[0], Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]), Integer.parseInt(productConf[3]));
                        break;
                    case "cup":
                        this.__cup = new Product(productConf[0], Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]), 0);
                        break;
                    case "spoon":
                        this.__spoon = new Product(productConf[0], Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]), 0);
                        break;
                    case "sugar":
                        this.__sugar = new Product(productConf[0], Double.parseDouble(productConf[1]), Integer.parseInt(productConf[2]), 0);
                        break;

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
