/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.PessoaJpaController;
import dao.exceptions.NonexistentEntityException;
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
public class Admin extends HttpServlet {

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
        rd = request.getRequestDispatcher("admin.jsp");

        String acao = request.getParameter("action");

        HttpSession session = request.getSession();

        EntityManagerFactory emf;
        emf = Persistence.createEntityManagerFactory("EmpregosPU");

        if (acao == null) {
            injectPage(request, "dashboard");

        } else if (acao.equalsIgnoreCase("edit_resume")) {
            request.setAttribute("user", request.getSession().getAttribute("current_user"));
            injectPage(request, "cadastrar_cv");

        } else if (acao.equalsIgnoreCase("create_resume")) {
            String id = request.getParameter("resume_id");
            String name = request.getParameter("resume_name");
            String email = request.getParameter("resume_email");
            String phone = request.getParameter("resume_phone");
            String cellphone = request.getParameter("resume_cellphone");
            String school = request.getParameter("resume_school");
            int function_1 = Integer.parseInt(request.getParameter("resume_function_1"));
            int function_2 = Integer.parseInt(request.getParameter("resume_function_2"));
            int function_3 = Integer.parseInt(request.getParameter("resume_function_3"));
            String password = request.getParameter("resume_password");
            String password_confirmation = request.getParameter("resume_password_confirmation");
            Boolean working;
            
            if (request.getParameter("resume_working").equals("1")) {
                working = true;
            } else {
                working = false;
            }
            
            if (id == null) {
                Pessoa p = new Pessoa(
                        null, name, email, phone, cellphone, school, function_1,
                        function_2, function_3, password, working
                );
                new PessoaJpaController(emf).create(p);
            } else {
                int id1 = Integer.parseInt(id);
                Pessoa p = new PessoaJpaController(emf).findPessoa(id1);

                p.setNome(name);
                p.setEmail(email);
                p.setTelefone(phone);
                p.setCelular(cellphone);
                p.setEscolaridade(school);
                p.setFuncao1(function_1);
                p.setFuncao2(function_2);
                p.setFuncao3(function_3);

                if (password.equals(password_confirmation) && password != "" && password_confirmation != "") {
                    p.setSenha(password);
                }

                try {
                    new PessoaJpaController(emf).edit(p);
                    request.getSession().setAttribute("current_user", p);
                } catch (NonexistentEntityException ex) {
                } catch (Exception ex) {
                }
            }
            injectPage(request, "dashboard");
        }

        rd.forward(request, response);
    }

    private void injectPage(HttpServletRequest request, String pagina) {
        request.setAttribute("pg", pagina);
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
