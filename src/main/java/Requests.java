import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Requests {
    private static String Link = "https://999.md/";

    private static Map<String, String> cookies999 = Cookies999.getCookies();

    public static void getAllLinks() {
        Document doc = null;
        try {
            doc = Jsoup.connect(Link).get();
            Elements links = doc.select("a[href]");
            Element link;

            for (int j = 0; j < 90; j++) {
                link = links.get(j);
                System.out.println("a= " + link.attr("abs:href").toString());
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String getRequest() throws IOException {
        Connection.Response resp = Jsoup.connect(Link).method(Connection.Method.GET).cookies(cookies999).execute();
        return resp.parse().html();
    }

    public static String headRequest() throws IOException {
        Connection.Response resp = Jsoup.connect(Link).method(Connection.Method.HEAD).cookies(cookies999).execute();
        return resp.contentType();
    }

    public static Map<String, List<String>> optionsRequest() throws IOException {
        Connection.Response resp = Jsoup.connect(Link).method(Connection.Method.OPTIONS).cookies(cookies999).execute();
        return resp.multiHeaders();
    }

    public static Map<String, String> postRequest() throws IOException {
        Connection.Response resp = Jsoup.connect(Link).method(Connection.Method.POST).cookies(cookies999).execute();
        return resp.cookies();
    }


    public static void main(String[] args) throws Exception {
        System.out.println(getRequest());
        getAllLinks();
        postRequest();
        System.out.println("Content-Type:" + headRequest());
        System.out.println("Options response:" + optionsRequest());
    }

}
