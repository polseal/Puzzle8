package src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class HTMLupdate {

    public static void updateFinal(ArrayList<Node> visited) throws IOException {
        String tag ="";
        String table;
        File htmlTemplateFile = new File("FinalPage.html");
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);
        for (int i = 0; i < visited.size(); i++){tag += addTag(i);}
        htmlString = htmlString.replace("$tags", tag);
        for (int i = 0; i < visited.size(); i++)
        {
            table = getTable(i, visited.get(i).getState());
            htmlString = htmlString.replace(String.format("$tag%d$", i), table);
        }
        File newHtmlFile = new File("FinalPage_1.html");
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
    }

    public static void updateStart(ArrayList<Integer> state, ArrayList<Integer> goal) throws IOException {
        File htmlTemplateFile = new File("StartingPage.html");
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);
        String table1 = getTable(0, state);
        htmlString = htmlString.replace("$table1", table1);
        String table2 = getTable(0, goal);
        htmlString = htmlString.replace("$table2", table2);
        File newHtmlFile = new File("StartingPage_1.html");
        FileUtils.writeStringToFile(newHtmlFile, htmlString);
    }

    private static String getTable(int aIntVar,ArrayList<Integer> state) {
        return String.format("<table id=\"matrix%d\" border=\"1px;\">\n" +
                "  <thead>\n" +
                "  <tr>\n" +
                "    <th>%d</th><th>%d</th><th>%d</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <th>%d</th><th>%d</th><th>%d</th>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <th>%d</th><th>%d</th><th>%d</th>\n" +
                "  </tr>\n" +
                "  </thead>\n" +
                "  <tbody>\n" +
                "  </tbody>\n" +
                "</table>", aIntVar, state.get(0),state.get(1), state.get(2), state.get(3), state.get(4),
                state.get(5), state.get(6), state.get(7), state.get(8));
    }

    public static String addTag(int index)
    {
        return String.format("<p>$tag%d$</p>\n", index);
    }

}
