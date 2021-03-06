/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mohoncuti;

import bean.MohonCuti;
import bean.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jdbc.JDBCUtility;

/**
 *
 * @author USER
 */
@WebServlet(name = "InsertMohonCutiServlet", urlPatterns = {"/InsertCutiRehat"})
public class InsertMohonCutiServlet extends HttpServlet {
    
    private JDBCUtility jdbcUtility;
    private Connection con;
    
    public void init() throws ServletException
    {
        String driver = "com.mysql.jdbc.Driver";

        String dbName = "db_cutirehat";
        String url = "jdbc:mysql://localhost/" + dbName + "?";
        String userName = "root";
        String password = "";

        jdbcUtility = new JDBCUtility(driver,
                                      url,
                                      userName,
                                      password);

        jdbcUtility.jdbcConnect();
        con = jdbcUtility.jdbcGetConnection();
        jdbcUtility.prepareSQLStatement();
    } 

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        MohonCuti mohon = new MohonCuti();
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if(session != null){
            if (session.getAttribute("staffSession") != null) {
                // get MohonCuti object from session
                mohon = (MohonCuti) session.getAttribute("mohonSession");
                
                int id_sokonglulus = mohon.getId_sokonglulus();
                String tarikhMula = mohon.getTarikhMula();
                String tarikhTamat = mohon.getTarikhTamat();
                String tarikhMohon = mohon.getTarikhMohon();
                int bilanganCuti = mohon.getBilanganCuti();
                String catatan = mohon.getCatatan(); 
                String alamatCuti = mohon.getAlamatCuti();
                //request.setAttribute("tarikhMulaTamat", request.getParameter("rangeTarikh"));
                
               
            
                //INSERT PERMOHONAN CUTI REHAT INTO DB
                try {
                    /* TODO output your page here. You may use following sample code. */
                    PreparedStatement preparedStatement = jdbcUtility.getPsInsertMohonCutiRehat();
                    preparedStatement.setString(1, tarikhMula);
                    preparedStatement.setString(2, tarikhTamat);
                    preparedStatement.setString(3, tarikhMohon);
                    preparedStatement.setInt(4, id_sokonglulus);
                    preparedStatement.setString(5, catatan);
                    preparedStatement.setString(6, alamatCuti);
                    preparedStatement.setInt(7, bilanganCuti);
                    preparedStatement.executeUpdate();

                } catch (SQLException ex) {
                }
                
                //out.println("gg3");
                out.println("<script>");
                out.println("    alert('Permohonan telah berjaya dihantar');");
                out.println("</script>");
                   
                response.sendRedirect("MohonCuti");
            }
            else{
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
            
        }
        else{
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
    
     void sendPage(HttpServletRequest req, HttpServletResponse res, String fileName) throws ServletException, IOException
    {
        // Get the dispatcher; it gets the main page to the user
	RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(fileName);

	if (dispatcher == null)
	{
            System.out.println("There was no dispatcher");
	    // No dispatcher means the html file could not be found.
	    res.sendError(res.SC_NO_CONTENT);
	}
	else
	    dispatcher.forward(req, res);
    }


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
