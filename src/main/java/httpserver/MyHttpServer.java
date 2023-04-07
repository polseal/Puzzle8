package httpserver;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import com.sun.net.httpserver.*;
import src.A_star;
import src.HTMLupdate;
import src.Heuristic;
import src.Node;

import static java.lang.System.out;

public class MyHttpServer {

    public static void main(String[] args) throws Exception {
        com.sun.net.httpserver.HttpServer server = com.sun.net.httpserver.HttpServer.create(new InetSocketAddress(8090), 0);
        HttpContext context2 = server.createContext("/puzzle8/result", new ResultPage());
        HttpContext context1 = server.createContext("/puzzle8", new FirstPage());
        server.setExecutor(null);
        server.start();
    }

    static class FirstPage implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            ArrayList<Integer> state = new ArrayList<>(Arrays.asList(1,3,4,8,6,2,0,7,5));
            ArrayList<Integer> goal = new ArrayList<>(Arrays.asList(1,2,3,8,0,4,7,6,5));
            HTMLupdate.updateStart(state, goal);
            File htmlFile = new File("StartingPage_1.html");
            t.sendResponseHeaders(200, htmlFile.length());
            try (OutputStream os = t.getResponseBody())
            {
                Files.copy(htmlFile.toPath(), os);
            }
        }
    }

    static class ResultPage implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String[] input = getBody(t).split(" ");
            ArrayList<Integer> s = convertToArray(input[0]);
            ArrayList<Integer> g = convertToArray(input[1]);
            Heuristic m = Heuristic.MAN;
            Node start = new Node(s, g, m);
            A_star a_star = new A_star(m, start);
            a_star.search();
            File htmlFile = new File("FinalPage_1.html");
            t.sendResponseHeaders(200, htmlFile.length());
            try (OutputStream os = t.getResponseBody())
            {
                Files.copy(htmlFile.toPath(), os);
            }
        }
    }


    public static ArrayList<Integer> convertToArray(String str)
    {
        ArrayList<Integer> arrl = new ArrayList<>();

        for (int i = 0; i < str.length(); i++) {
            arrl.add(Character.getNumericValue(str.charAt(i)));
        }

        return arrl;
    }

    public static String getBody(HttpExchange exchange)
    {
        InputStream is = exchange.getRequestBody();
        String result = new BufferedReader(new InputStreamReader(is))
                .lines().collect(Collectors.joining("\n"));
        String digits = result.replaceAll("[^\\d]", "");
        StringBuilder sb = new StringBuilder(digits);
        sb.insert(9, ' ');
        return sb.toString();
    }

  private static void printRequestInfo(HttpExchange exchange) {
        /*System.out.println("-- headers --");
         Headers requestHeaders = exchange.getRequestHeaders();
         requestHeaders.entrySet().forEach(System.out::println);

         System.out.println("-- principle --");
         HttpPrincipal principal = exchange.getPrincipal();
         System.out.println(principal);

         System.out.println("-- HTTP method --");
         String requestMethod = exchange.getRequestMethod();
         System.out.println(requestMethod);

         System.out.println("-- query --");
         URI requestURI = exchange.getRequestURI();
         String query = requestURI.getQuery();
         System.out.println(query);
*/
         out.println("--body--");
         InputStream is = exchange.getRequestBody();
         String result = new BufferedReader(new InputStreamReader(is))
                 .lines().collect(Collectors.joining("\n"));
         out.println(result);
    }
}
