import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageDownloader extends Thread{

    private static String IMAGE_DESTINATION_FOLDER = "src\\\\main\\\\resources\\\\images";
    private static HashMap<String, String> cookies999;

    public static void main(String[] args) throws IOException {

        //replace it with your URL
        String strURL = "https://999.md/ro/";


        //connect to the website and get the document
        Document document = Jsoup
                .connect(strURL)
                .timeout(10 * 1000)
                .get();

        //select all img tags
        Elements imageElements = document.select("img");


        ExecutorService exec = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(5);

        //iterate over each image
        for(Element imageElement : imageElements){

            //make sure to get the absolute URL using abs: prefix
            String strImageURL = imageElement.attr("abs:src");

            //download image one by one
            downloadImage(strImageURL);

        }

    }

    public static void downloadImage(String strImageURL) {

        String strImageName =
                strImageURL.substring(strImageURL.lastIndexOf("/") + 1);

        System.out.println("Saving: " + strImageName + ", from: " + strImageURL);

        try {

            URL urlImage = new URL(strImageURL);
            InputStream in = urlImage.openStream();

            byte[] buffer = new byte[4096];
            int n = -1;

            OutputStream os =
                    new FileOutputStream("src\\main\\resources\\images" + "\\" + strImageName);

            while ((n = in.read(buffer)) != -1) {
                os.write(buffer, 0, n);
            }

            os.close();

            System.out.println("Image saved");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
