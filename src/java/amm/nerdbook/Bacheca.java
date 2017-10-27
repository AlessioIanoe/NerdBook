/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amm.nerdbook;

import amm.nerdbook.classi.Gruppo;
import amm.nerdbook.classi.GruppoFactory;
import amm.nerdbook.classi.Post;
import amm.nerdbook.classi.PostFactory;
import amm.nerdbook.classi.Utente;
import amm.nerdbook.classi.UtenteFactory;
import java.io.IOException;
import java.util.List;
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

@WebServlet(name = "Bacheca", urlPatterns = {"/Bacheca"})
public class Bacheca extends HttpServlet {

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
        String user = request.getParameter("user");
        String group = request.getParameter("group");

        int userID;
        int groupID;
        int utenteLoggato;
        Utente loggato = null;
        Utente proprietarioBacheca = null;
        Gruppo gruppoBacheca = null;

        HttpSession session = request.getSession(false);

        if (session != null
                && session.getAttribute("loggedIn") != null
                && session.getAttribute("loggedIn").equals(true)) {

            utenteLoggato = (Integer) session.getAttribute("loggedUserID");

            if (user != null) {

                userID = Integer.parseInt(user);
                proprietarioBacheca = UtenteFactory.getInstance().getUtenteById(userID);

            } else if (group != null) {
                groupID = Integer.parseInt(group);
                gruppoBacheca = GruppoFactory.getInstance().getGruppoById(groupID);

            } else {
                proprietarioBacheca = UtenteFactory.getInstance().getUtenteById(utenteLoggato);
            }

            loggato = UtenteFactory.getInstance().getUtenteById(utenteLoggato);

            List<Utente> utenti = UtenteFactory.getInstance().getListaUtenti();
            List<Gruppo> gruppi = GruppoFactory.getInstance().getListaGruppi();

            request.setAttribute("utenteLoggato", loggato);
            request.setAttribute("utenti", utenti);
            request.setAttribute("gruppi", gruppi);

            if (request.getParameter("thereIsPost") != null) {

                String thereIsPost = request.getParameter("thereIsPost");
                String userd = request.getParameter("userd");
                String groupd = request.getParameter("groupd");
                String userp = request.getParameter("userp");
                String s = request.getParameter("testo");
                String testo = new String(s.getBytes("ISO-8859-1"), "UTF-8");
                String allegato = request.getParameter("allegato");

                if (userp != null) {
                    userID = Integer.parseInt(userp);
                } else {
                    userID = utenteLoggato;
                }

                if (userID == utenteLoggato) {

                    if (thereIsPost.equals("needConfirm")) {

                        request.setAttribute("userp2", userp);
                        request.setAttribute("userd2", userd);
                        request.setAttribute("groupd2", groupd);
                        request.setAttribute("testo2", testo);
                        request.setAttribute("allegato2", allegato);

                        if ((testo == null || testo.equals("")) && (allegato == null || allegato.equals(""))) {
                            request.setAttribute("errorMessage", "non puoi pubblicare un post vuoto!");

                        } else {
                            request.setAttribute("newpost", "true");
                        }

                    } else {

                        request.setAttribute("newpost", "false");

                        Post post = new Post();
                        post.setAutore(loggato);
                        if (groupd != null && gruppoBacheca != null) {
                            post.setUser(null);
                            post.setGroup(gruppoBacheca);
                        } else if (userd != null && proprietarioBacheca != null) {
                            post.setUser(proprietarioBacheca);
                            post.setGroup(null);
                        } else {
                            response.sendError(500, "gruppo o utente non trovato");
                        }

                        post.setContent(testo);

                        post.setUrlAllegato(allegato);
                        post.riconoscimentoPost();
                        PostFactory.getInstance().addNewPost(post);

                    }

                } else {
                    response.sendError(400, "stai tentando di utilizzare un profilo che non ti appartiene!");
                    return;
                }
            }

            if (proprietarioBacheca != null) {

                List<Post> posts = PostFactory.getInstance().getPostByUtente(proprietarioBacheca);

                request.setAttribute("proprietario", proprietarioBacheca);
                request.setAttribute("posts", posts);

            } else if (gruppoBacheca != null) {

                List<Post> posts = PostFactory.getInstance().getPostByGruppo(gruppoBacheca);

                request.setAttribute("gruppo", gruppoBacheca);
                request.setAttribute("posts", posts);

            } else {

                response.sendError(404, "pagina non trovata");

            }

            request.getRequestDispatcher("bacheca.jsp").forward(request, response);

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
