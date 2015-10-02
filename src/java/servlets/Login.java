/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.PessoaJpaController;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Pessoa;

/**
 *
 * @author Marco
 */
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("index.jsp");
        
        String acao = request.getParameter("action");
        
        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("EmpregosPU");
        
        HttpSession session = request.getSession();
        
        if (acao == null) {
            response.sendRedirect("home");
        } else if (acao.equalsIgnoreCase("make_login")) {
            String email = request.getParameter("pessoa_email");
            String password = request.getParameter("pessoa_senha");
            
            Pessoa pessoa = new PessoaJpaController(emf).getPessoaByLoginAndPassword(email, password);
            
            if (pessoa == null) {
                request.getRequestDispatcher("login");
                request.setAttribute("pg", "login");
                request.setAttribute("warning", "Login ou senha incorreto!");
                rd.forward(request, response);
            } else {
                Pessoa p = new PessoaJpaController(emf).findPessoa(pessoa.getId());
                request.getSession().setAttribute("current_user", p);
                response.sendRedirect("admin");
            }
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
