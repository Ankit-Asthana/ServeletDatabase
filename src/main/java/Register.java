
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


@WebServlet("/regForm")
public class Register extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
//  step 1 -> Storing the request in the variables
        String name = req.getParameter("name1");
        String email = req.getParameter("email1");
        String password = req.getParameter("pass1");
        String gender = req.getParameter("gender1");
        String city = req.getParameter("city1");

//  step 2 -> Connection to the DB
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb","root","root");

            PreparedStatement ps = con.prepareStatement("insert into register values(?,?,?,?,?)");
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,password);
            ps.setString(4,gender);
            ps.setString(5,city);

            int count = ps.executeUpdate();
            if(count>0){
                resp.setContentType("text/html");
                pw.print("<h4>User registered Successfully</h4>");
                RequestDispatcher requestDispatcher= req.getRequestDispatcher("/index.jsp"); //redirect to the register page automatically
                requestDispatcher.include(req,resp);
            }
            else {
                resp.setContentType("text/html");
                pw.print("<h4>User not registered Successfully</h4>");
                RequestDispatcher requestDispatcher= req.getRequestDispatcher("/index.jsp");
                requestDispatcher.include(req,resp);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            resp.setContentType("text/html");
            pw.print("<h4> Exception " +e.getMessage()+"</h4>");
            RequestDispatcher requestDispatcher= req.getRequestDispatcher("/index.jsp");
            requestDispatcher.include(req,resp);
        }
    }
}
