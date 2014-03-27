/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pt.uc.dei.ar.proj3.grupob.view;

import java.io.IOException;
import java.io.PrintWriter;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pt.uc.dei.ar.proj3.grupob.facades.MusicFacade;

/**
 *
 * @author Bruno Maricato
 */
@WebServlet(name = "PopularServlet", urlPatterns = {"/popularservlet"})
public class PopularServlet extends HttpServlet {
    
    @Inject
    private MusicFacade musicFacade;

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

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            out.println("<table>");
            out.println("<thead>");
            out.println("<tr>");
            out.println("<td> TITLE </td>");
            out.println("<td> ARTIST </td>");
            out.println("<td> ALBUM </td>");
            out.println("<td> YEAR </td>");
            out.println("<td> Nº UTILIZAÇÕES </td>");
            out.println("<td> USERNAME </td>");
            out.println("</tr>");
            out.println("</thead>");

            for (int i = 0; i < musicFacade.mostPopular().size(); i++) {
                out.println("<tr>");
                out.println("<td>" + musicFacade.mostPopular().get(i).getTitle() + "</td>");
                out.println("<td>" + musicFacade.mostPopular().get(i).getArtist() + "</td>");
                out.println("<td>" + musicFacade.mostPopular().get(i).getAlbum() + "</td>");
                out.println("<td>" + musicFacade.mostPopular().get(i).getYearAlbum() + "</td>");
                out.println("<td>" + musicFacade.mostPopular().get(i).getPlaylistCollection().size() + "</td>");
                out.println("<td>" + musicFacade.mostPopular().get(i).getUserPlayid().getName() + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
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
