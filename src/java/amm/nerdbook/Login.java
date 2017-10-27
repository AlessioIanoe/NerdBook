/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;

import amm.nerdbook.classi.Utente;
import amm.nerdbook.classi.UtenteFactory;
import amm.nerdbook.classi.GruppoFactory;
import amm.nerdbook.classi.PostFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ianuk
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"}, loadOnStartup = 0)
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final String JDBC_DRIVER ="org.apache.derby.jdbc.ClientDriver";
    //private static final String JDBC_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_CLEAN_PATH = "../../web/WEB-INF/db/ammdb";
    private static final String DB_BUILD_PATH = "WEB-INF/db/ammdb";
    
    @Override
    public void init() {
        String dbConnection = "jdbc:derby://localhost:1527/ammdb";
        //String dbConnection = "jdbc:derby:" + this.getServletContext().getRealPath("/") + DB_BUILD_PATH;
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

        UtenteFactory.getInstance().setConnectionString(dbConnection);
        GruppoFactory.getInstance().setConnectionString(dbConnection);
        PostFactory.getInstance().setConnectionString(dbConnection);
        
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        //Sessione
        HttpSession session = request.getSession();
        
        int loggedUserID;
        Utente loggato = null;
        
        //Distrugge la sessione
        if(request.getParameter("logout")!=null)
        {
            session.invalidate();
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        
        //Utente già loggato
        if (session.getAttribute("loggedIn") != null &&
            session.getAttribute("loggedIn").equals(true)) {
            
            
            
            loggedUserID = (Integer)session.getAttribute("loggedUserID");
            loggato = UtenteFactory.getInstance().getUtenteById(loggedUserID);
            
            if(loggato.getNome() == null || loggato.getCognome() == null ||
                            loggato.getUrlFotoProfilo() == null ||
                            loggato.getBiografia() == null)
            {
                request.getRequestDispatcher("Profilo").forward(request, response);
            }
            else
            {
                request.getRequestDispatcher("Bacheca").forward(request, response);
            }

            
            return;
        
        //Utente non loggato
        } else {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
        
            if (email != null &&
                password != null) 
            {
                
                loggedUserID = UtenteFactory.getInstance().getIdByEmailAndPassword( email, password);
                
                if(loggedUserID >=0)
                {
                    
                    session.setAttribute("loggedIn", true);
                    session.setAttribute("loggedUserID", loggedUserID);
                    
                    loggato = UtenteFactory.getInstance().getUtenteById(loggedUserID);
            
                    if(loggato.getNome() == null || loggato.getCognome() == null ||
                            loggato.getUrlFotoProfilo() == null ||
                            loggato.getBiografia() == null)
                    {
                        request.getRequestDispatcher("Profilo").forward(request, response);
                    }
                    else
                    {
                        request.getRequestDispatcher("Bacheca").forward(request, response);
                    }
                    
                    
                    return;
                } else { 
                    
                    //ritorno alla login ,dati sbagliati
                    request.setAttribute("invalidData", loggedUserID);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }               
            }
        }        
        /*
          Se non si verifica nessuno degli altri casi
         
        */
        request.getRequestDispatcher("login.jsp").forward(request, response);
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
