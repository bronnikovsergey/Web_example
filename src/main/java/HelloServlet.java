import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
        System.out.println(field);
        PrintWriter out = resp.getWriter();

        out.println("<html>");
        out.println("<body>");
        out.println("<p>\"" + field + "\"<p>");

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

        String[] namesOfFiles = new String[] {
                "Arya.txt",
                "Bran.txt",
                "Catelyn.txt",
                "Daenerys.txt",
                "Eddard.txt",
                "John.txt",
                "Sansa.txt",
                "Tyrion.txt"
        };

        for (int i = 0; i < 8; i++) {
            FileInputStream someFile = new FileInputStream(new File(pathToFile[i]));
            byte[] content = new byte[someFile.available()];
            someFile.read(content);
            someFile.close();
            String[] lines = new String(content, "UTF-8").split("\n");

            int k = 1;
            boolean find = false;
            for (String line : lines) {
                if (!find) {
                    String[] words = line.split(" ");
                    for (String word : words) {
                        if (word.equalsIgnoreCase(field)) {
                            out.println("<p> in file \"" + namesOfFiles[i] + "\" at line " + k + "</p>");
                            find = true;
                            break;
                        }
                    }
                    k++;
                }
            }
            if (!find) {
                out.println("<p>not in file \"" + namesOfFiles[i] + "\"</p>");
            }
        }

        out.println("</body>");
        out.println("</html>");

    }
}
