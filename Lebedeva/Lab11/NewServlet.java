import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class NewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] array = req.getParameter("array").split(",");
        String s = "";
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            integers.add(Integer.parseInt(array[i]));
        }
        Collections.sort(integers);
        for (int i = 0; i < integers.size(); i++) {
            s += integers.get(i) + "; ";
        }
        req.setAttribute("answer", s);
        req.getRequestDispatcher("array_answer.jsp").forward(req, resp);
    }
}