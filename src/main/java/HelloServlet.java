import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String field = req.getParameter("field");
        PrintWriter out = resp.getWriter();

        ArrayList<String> files = Indexer.getInstance().search(field);

        out.println("<html>");
        out.println("<body>");
        for (String file : files) {
            out.println("<p> " + file + "</p>");
        }
        out.println("</body>");
        out.println("</html>");
    }
}
