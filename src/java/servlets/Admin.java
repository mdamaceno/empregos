/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import dao.EmpresaJpaController;
import dao.PessoaJpaController;
import dao.ReuniaoJpaController;
import dao.exceptions.NonexistentEntityException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Empresa;
import models.Pessoa;
import models.Reuniao;

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
            Object o = (Object) request.getSession().getAttribute("current_user");
            try {
                if (o.getClass().getSimpleName().toString().equals("Pessoa")) {
                    Pessoa p;
                    p = (Pessoa) o;
                } else {
                    Empresa e;
                    e = (Empresa) o;
                }

                injectPage(request, "dashboard");
                rd.forward(request, response);
            } catch (ServletException | IOException ex) {
                // Redireciona para /home se tenta acessar /admin sem estar logado
                response.sendRedirect("home");
            }

        } else if (acao.equalsIgnoreCase("edit_resume")) {
            request.setAttribute("user", request.getSession().getAttribute("current_user"));
            injectPage(request, "cadastrar_cv");
            rd.forward(request, response);

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
            int role = Integer.parseInt(request.getParameter(("resume_role")));
            Boolean working;

            if (request.getParameter("resume_working").equals("1")) {
                working = true;
            } else {
                working = false;
            }

            if (id == null) {
                Pessoa p = new Pessoa(
                        null, name, email, phone, cellphone, school, function_1,
                        function_2, function_3, password, working, role
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
                p.setPermissao(role);

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
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("view_companies")) {
            injectPage(request, "companies");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("view_users")) {
            injectPage(request, "users");
            List<Pessoa> lst = new PessoaJpaController(emf).findPessoaEntities();
            request.setAttribute("listUsers", lst);
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("view_interviews")) {
            Object o = (Object) request.getSession().getAttribute("current_user");

            if (o.getClass().getSimpleName().equals("Pessoa")) {
                Pessoa p;
                p = (Pessoa) o;
                List<Reuniao> lst = new ReuniaoJpaController(emf).getReuniaoByPessoaId(p);
                request.setAttribute("listInterviews", lst);
            } else {
                Empresa e;
                e = (Empresa) o;
                List<Reuniao> lst = new ReuniaoJpaController(emf).getReuniaoByEmpresaId(e);
                request.setAttribute("listInterviews", lst);
            }

            injectPage(request, "interviews");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("new_interview")) {
            injectPage(request, "new_interview");

            int candidate_id = Integer.parseInt(request.getParameter("candidate_id"));

            Pessoa p = new PessoaJpaController(emf).findPessoa(candidate_id);

            request.setAttribute("candidate", p);

            rd.forward(request, response);
            
        } else if (acao.equalsIgnoreCase("confirm_interview")) {           
            int interview_id = Integer.parseInt(request.getParameter("interview_id"));
            String yes_no = request.getParameter("yes_no");
            
            Reuniao r = new ReuniaoJpaController(emf).findReuniao(interview_id);
            
            r.setConfirmacao(yes_no);
            
            request.setAttribute("action", "view_interviews");
            
            Pessoa p = (Pessoa) request.getSession().getAttribute("current_user");
            
            try {
                new ReuniaoJpaController(emf).edit(r);
                injectPage(request, "interviews");
                
                List<Reuniao> lst = new ReuniaoJpaController(emf).getReuniaoByPessoaId(p);
                request.setAttribute("listInterviews", lst);
                
                rd.forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (acao.equalsIgnoreCase("save_interview")) {
            try {
                int candidate_id = Integer.parseInt(request.getParameter("candidate"));
                Pessoa pessoa = new PessoaJpaController(emf).findPessoa(candidate_id);
                Empresa empresa = (Empresa) request.getSession().getAttribute("current_user");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date interview_date = sdf.parse(request.getParameter("interview_date"));

                Reuniao r;
                r = new Reuniao(null, interview_date, empresa, pessoa);

                new ReuniaoJpaController(emf).create(r);

                injectPage(request, "interviews");
                
                rd.forward(request, response);
            } catch (ParseException ex) {
                Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (acao.equalsIgnoreCase("candidates")) {
            injectPage(request, "candidates");

            String opt = request.getParameter("opt_user");

            List<Pessoa> lst = null;

            if (opt == null || opt.equals("")) {
                lst = new PessoaJpaController(emf).findPessoaEntities();
            } else {
                lst = new PessoaJpaController(emf).getPessoaByFuncao(Integer.parseInt(opt));
            }

            request.setAttribute("listUsers", lst);
            request.setAttribute("filterSelected", opt);

            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("logout")) {
            logout(request, response);
        }
    }

    private void injectPage(HttpServletRequest request, String pagina) {
        request.setAttribute("pg", pagina);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        try {
            response.sendRedirect("home");
        } catch (IOException ex) {
            Logger.getLogger(Admin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listUsers(HttpServletRequest request, EntityManagerFactory emf) {
        request.setAttribute("pg", "users");
        List<Pessoa> list = new PessoaJpaController(emf).findPessoaEntities();
        request.setAttribute("listUsers", list);
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
