import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String q = req.getParameter("q");
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");
        out.println("The parameter q was \"" + q + "\".");
        out.println("</body>");
        out.println("</html>");
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String field = req.getParameter("field");
        PrintWriter out = resp.getWriter();

        if (!field.equals("")) {

            out.println("<html>");
            out.println("<body>");
            out.println("<p>The words: " + field + "</p>");

            String[] pathToFile = new String[]{
                    "src/main/TXTfiles/Arya.txt",
                    "src/main/TXTfiles/Bran.txt",
                    "src/main/TXTfiles/Catelyn.txt",
                    "src/main/TXTfiles/Daenerys.txt",
                    "src/main/TXTfiles/Eddard.txt",
                    "src/main/TXTfiles/John.txt",
                    "src/main/TXTfiles/Sansa.txt",
                    "src/main/TXTfiles/Tyrion.txt"
            };

            String[] namesOfFiles = new String[]{
                    "Arya.txt",
                    "Bran.txt",
                    "Catelyn.txt",
                    "Daenerys.txt",
                    "Eddard.txt",
                    "John.txt",
                    "Sansa.txt",
                    "Tyrion.txt"
            };

            String s;
            String[] arrayString;
            Map<String, HashSet<Integer>> map = new HashMap<String, HashSet<Integer>>();
            HashSet<Integer> extra;


            for (int i = 0; i < 8; i++) {
                File someFile = new File(pathToFile[i]);
                Scanner sc = new Scanner(someFile);

                HashSet<Integer> set = new HashSet<Integer>();
                set.add(i);

                while (sc.hasNextLine()) {
                    s = sc.nextLine();
                    arrayString = s.split(" ");
                    for (String word : arrayString) {
                        if (map.containsKey(word)) {
                            extra = map.get(word);
                            HashSet<Integer> some = new HashSet<Integer>();
                            some.addAll(extra);
                            some.add(i);
                            map.put(word, some);
                        } else {
                            map.put(word, set);
                        }
                    }
                }
            }

            String[] fullField = field.split(" ");
            boolean check = true;

            for (String aString : fullField) {
                if (!map.containsKey(aString)) {
                    check = false;
                }
            }

            if (check) {
                HashSet<Integer> wSet = new HashSet<Integer>(map.get(fullField[0]));

                for (String aFullField : fullField) {
                    HashSet<Integer> intersect = new HashSet<Integer>(map.get(aFullField));
                    wSet.retainAll(intersect);
                }

                for (Integer index : wSet) {
                    out.println("<p>" + namesOfFiles[index] + "</p>");
                }

            } else {
                out.println("<p> no words in all files </p>");
            }

        } else {
            out.println("<p> empty form </p>");
        }


        out.println("</body>");
        out.println("</html>");
    }
}
