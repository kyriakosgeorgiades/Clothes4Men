
import inputValidation.ValidationCheck;
import databaseOperations.Queries;
import databaseOperations.db_connection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "Admin", urlPatterns = {"/Admin"})
@MultipartConfig(maxFileSize = 1073741824, maxRequestSize = 1024 * 1024 * 50,
        fileSizeThreshold = 1024 * 1024 * 2)
public class Admin extends HttpServlet {

    public static final String UPLOAD_DIR = "images\\products";
    public String dbFileName = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        List<String> errors = new ArrayList<String>();
        //Getting all the requests are Strings to check for input Validation
        String name = request.getParameter("name");
        String price_ = request.getParameter("price");
        String count_ = request.getParameter("count");
        String category = request.getParameter("category");
        String desc = request.getParameter("desc");
        String op = request.getParameter("op");
        //File required Part type and then check its actual fileName
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        //When the request is null throw error page
        //Check if there is no operation selected and prompt custom error
        if (request.getParameter("op") == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("<html><head><title>ERROR OCCURS!</title></head>");
            response.getWriter().print("<body>No operation selected<a href='admin.jsp'>Back</a></body>");
            response.getWriter().println("</html>");
        } else {
            //Which operation is selected
            switch (op) {
                //Case of adding new product. Check all required fileds
                //For input validation
                case "add":
                    if (ValidationCheck.TextString(name)) {
                        errors.add("Name of the product can't be empty.");
                    }
                    if (ValidationCheck.TextString(price_)) {
                        errors.add("Price of the product can't be empty.");
                    }
                    if (ValidationCheck.TextString(count_)) {
                        errors.add("Quantity of the product can't be empty.");
                    }
                    if (ValidationCheck.TextString(category)) {
                        errors.add("Category of the product can't be empty.");
                    }
                    if (ValidationCheck.TextString(desc)) {
                        errors.add("Description of the product can't be empty.");
                    }
                    if (ValidationCheck.TextString(op)) {
                        errors.add("Please select an operation before submit!");
                    }
                    if (ValidationCheck.TextString(fileName)) {
                        errors.add("Please select a file to upload!");
                    }
                    if (Float.parseFloat(price_) <= 0) {
                        errors.add("Price can not be negative or zero");
                    }
                    if (Integer.parseInt(count_) <= 0) {
                        errors.add("Quantity can not be negative or zero");
                    }
                    //Throw error if one of them were not correctly input
                    if (!errors.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.getWriter().print("<html><head><title>ERROR OCCURS!</title></head>");
                        response.getWriter().print("<body>" + errors + "</body>");
                        response.getWriter().println("</html>");
                    } else {
                        //Cast to the approriate types
                        Float price = Float.parseFloat(price_);
                        int count = Integer.parseInt(count_);
                        //Reading the file and setting the directory to be saved
                        InputStream fileInputStream = filePart.getInputStream();
                        String upload = getServletContext().getRealPath("") + "\\images\\products\\";
                        File fileToSave = new File(upload + filePart.getSubmittedFileName());
                        Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        fileInputStream.close();
                        try {
                            //Adding to the database the retrieve info
                            Queries q = new Queries(db_connection.getConnection());
                            int query = q.Add(name, price, count, category, desc, fileName);
                            out.println("<h3 style='color:crimson; text-align:center'>"
                                    + "Item added!<a href='admin.jsp'>Admin Operations</a>/<a href='index.jsp'>Home</a></h3>");
                            //response.sendRedirect(request.getContextPath() + "/admin.jsp");
                        } catch (Exception e) {
                            System.out.println(e);
                            out.println("<h3 style='color:crimson; text-align:center'>"
                                    + "Something went wrong try again!<a href='admin.jsp'>Back</a></h3>");
                        }

                    }
                    break;
                //Update unknonw number of fields.
                case "update":
                    boolean path_exists = false;
                    String id = request.getParameter("id");
                    if (ValidationCheck.TextString(id)) {
                        errors.add("Please ID of the product.");
                    }
                    if (!errors.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.getWriter().print("<html><head><title>ERROR OCCURS!</title></head>");
                        response.getWriter().print("<body>" + errors + "</body>");
                        response.getWriter().println("</html>");
                    } else {
                        String[] params = {};
                        String[] parametersNames = {};
                        String paramName;
                        String value_param;
                        //In order to find which parameters are to be updated
                        //as they can be of any number and any combination.
                        //They are retrieve by name and value
                        Enumeration<String> parameterNames = request.getParameterNames();
                        while (parameterNames.hasMoreElements()) {
                            paramName = parameterNames.nextElement();
                            if (!paramName.equals("op") && !paramName.equals("subop")) {
                                value_param = request.getParameter(paramName);
                                if (value_param != null && !value_param.equals("")) {
                                    params = Arrays.copyOf(params, params.length + 1);
                                    params[params.length - 1] = value_param;
                                    parametersNames = Arrays.copyOf(parametersNames, parametersNames.length + 1);
                                    parametersNames[parametersNames.length - 1] = paramName;

                                }
                            }
                        }
                        //Updating section of the picture of the selected product ID if selected
                        if (!ValidationCheck.TextString(fileName)) {
                            path_exists = true;
                            params = Arrays.copyOf(params, params.length + 1);
                            params[params.length - 1] = fileName;
                            //Adding the parameter of the file as well
                            parametersNames = Arrays.copyOf(parametersNames, parametersNames.length + 1);
                            parametersNames[parametersNames.length - 1] = "filename";

                            InputStream fileInputStream = filePart.getInputStream();
                            String upload = getServletContext().getRealPath("") + "\\images\\products\\";
                            File fileToSave = new File(upload + filePart.getSubmittedFileName());
                            Files.copy(fileInputStream, fileToSave.toPath(), StandardCopyOption.REPLACE_EXISTING);
                            fileInputStream.close();
                        }
                        try {
                            String upload = null;
                            //Update with passed the parameters and their values
                            //If a picture is selected for update it gets deleted
                            //and the new one is set to its place with matched
                            //ID product, price, etc...
                            if (path_exists) {
                                upload = getServletContext().getRealPath("") + "\\images\\products\\";
                            }
                            Queries q = new Queries(db_connection.getConnection());
                            q.Updates(parametersNames, params, upload);
                            out.println("<h3 style='color:crimson; text-align:center'>"
                                    + "Item updated!<a href='admin.jsp'>Admin Operations</a>/<a href='index.jsp'>Home</a></h3>");
                            //response.sendRedirect(request.getContextPath() + "/admin.jsp");
                        } catch (Exception e) {
                            System.out.println(e);
                            out.println("<h3 style='color:crimson; text-align:center'>"
                                    + "Something went wrong try again!<a href='admin.jsp'>Back</a></h3>");
                        }

                    }

                    break;
                //Case of deleting the product based on ID
                case "delete":
                    String id_ = request.getParameter("id");
                    if (ValidationCheck.TextString(id_)) {
                        errors.add("Please give ID of the product.");
                    }
                    if (!errors.isEmpty()) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.getWriter().print("<html><head><title>ERROR OCCURS!</title></head>");
                        response.getWriter().print("<body>" + errors + "</body>");
                        response.getWriter().println("</html>");
                    } else {
                        int ID = Integer.parseInt(request.getParameter("id"));
                        Queries q = null;
                        try {
                            q = new Queries(db_connection.getConnection());
                            String path = getServletContext().getRealPath("");
                            q.Delete(ID, path);
                            out.println("<h3 style='color:crimson; text-align:center'>"
                                    + "Item deleted!<a href='admin.jsp'>Admin Operations</a>/<a href='index.jsp'>Home</a></h3>");
                            //response.sendRedirect(request.getContextPath() + "/admin.jsp");
                            break;
                        } catch (Exception e) {
                            System.out.println(e);
                            out.println("<h3 style='color:crimson; text-align:center'>"
                                    + "Something went wrong try again!<a href='admin.jsp'>Back</a></h3>");

                        }

                    }
            }
        }
    }
}
