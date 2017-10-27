/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;
/**
 *
 * @author ianuk
 */
import amm.nerdbook.classi.Gruppo;
import amm.nerdbook.classi.GruppoFactory;
import amm.nerdbook.classi.Utente;
import amm.nerdbook.classi.UtenteFactory;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;


@WebServlet(name = "Profilo", urlPatterns = {"/Profilo"})
public class Profilo extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");

        int userID;

        int utenteLoggato;

        Utente loggato = null;
        Utente proprietarioBacheca = null;
        Gruppo gruppoBacheca = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        HttpSession session = request.getSession(false);

        if (session != null
                && session.getAttribute("loggedIn") != null
                && session.getAttribute("loggedIn").equals(true)) {

            utenteLoggato = (Integer) session.getAttribute("loggedUserID");

            loggato = UtenteFactory.getInstance().getUtenteById(utenteLoggato);

            List<Utente> utenti = UtenteFactory.getInstance().getListaUtenti();
            List<Gruppo> gruppi = GruppoFactory.getInstance().getListaGruppi();

            request.setAttribute("utenteLoggato", loggato);
            request.setAttribute("utenti", utenti);
            request.setAttribute("gruppi", gruppi);

            if (request.getParameter("modificaProfilo") != null) {

                request.setAttribute("click", 0);

                String newNome = request.getParameter("name");

                String newCognome = request.getParameter("surname");
                String newDataNascita = request.getParameter("data");
                String newImg = request.getParameter("img");
                String newBio = request.getParameter("bio");
                String newEmail = request.getParameter("email");
                String newPassword = request.getParameter("pswd");
                String confPsw = request.getParameter("confpswd");
                String userp = request.getParameter("userp");
                String modificaProfilo = request.getParameter("modificaProfilo");

                if (userp != null) {
                    userID = Integer.parseInt(userp);
                } else {
                    userID = utenteLoggato;
                }

                //modifica dati
                if (userID == utenteLoggato) {
                    if (modificaProfilo.equals("needConfirm")) {
                        if (newPassword != null && confPsw != null && newPassword.equals(confPsw)) {
                            request.setAttribute("newNome", newNome);
                            loggato.setNome(newNome);
                            UtenteFactory.getInstance().updateNome(newNome, userID);
                            request.setAttribute("newCognome", newCognome);
                            loggato.setCognome(newCognome);
                            UtenteFactory.getInstance().updateCognome(newCognome, userID);
                            try {
                                Date date = df.parse(newDataNascita);
                                String dn = date.getDate() + "/" + date.getMonth() + "/" + date.getYear();
                                request.setAttribute("newDataNascita", dn);
                                loggato.setDataNascita(newDataNascita);
                                UtenteFactory.getInstance().updateDataNascita(newDataNascita, userID);
                            } catch (ParseException ex) {
                                Logger.getLogger(Profilo.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            request.setAttribute("newImg", newImg);
                            loggato.setUrlFotoProfilo(newImg);
                            UtenteFactory.getInstance().updateImgProfilo(newImg, userID);
                            if(loggato.getUrlFotoProfilo().equals("")){
                                loggato.setUrlFotoProfilo("img/profilo.jpg");
                                UtenteFactory.getInstance().updateImgProfilo("img/profilo.jpg", userID);
                            }
                            request.setAttribute("newBio", newBio);
                            loggato.setBiografia(newBio);
                            UtenteFactory.getInstance().updateBiografia(newBio, userID);
                            request.setAttribute("newEmail", newEmail);
                            loggato.setEmail(newEmail);
                            UtenteFactory.getInstance().updateEmail(newEmail, userID);
                            request.setAttribute("newPassword", newPassword);
                            loggato.setPassword(newPassword);
                            UtenteFactory.getInstance().updatePassword(newPassword, userID);

                            request.setAttribute("click", 1);

                        } else {
                            request.setAttribute("click", 2);

                        }

                    }

                } else {
                    response.sendError(400, "stai tentando di modificare i dati di un profilo che non ti appartiene!");

                }

                //cancella profilo
            } else if (request.getParameter("cancellaProfilo") != null) {
                String userp = request.getParameter("userp");
                String cancellaProfilo = request.getParameter("cancellaProfilo");
                request.setAttribute("click", 0);

                if (userp != null) {
                    userID = Integer.parseInt(userp);
                } else {
                    userID = utenteLoggato;
                }

                if (userID == utenteLoggato) {
                    if (cancellaProfilo.equals("deleteProfile")) {
                        request.setAttribute("userp2", userp);
                        request.setAttribute("click", 3);

                    } else {
                        request.setAttribute("click", 0);
                        UtenteFactory.getInstance().deleteUser(loggato);
                        request.getRequestDispatcher("Login?logout=1").forward(request, response);
                    }
                } else {

                    response.sendError(400, "stai tentando di cancellare un profilo che non ti appartiene!");
                }

            }

            request.getRequestDispatcher("profilo.jsp").forward(request, response);
        } else {

            response.sendError(400, "accesso non consentito agli utenti non autenticati");
        }

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
