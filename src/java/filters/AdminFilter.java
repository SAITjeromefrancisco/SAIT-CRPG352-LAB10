/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

/**
 *
 * @author jerom
 */
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Integer adminRole = 1;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResposne = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        
        String email = (String) session.getAttribute("email");
        AccountService accountService = new AccountService();
        User user = accountService.get(email);
        
        if (user == null){
            httpResposne.sendRedirect("login");
            return;
        }else if (user.getRole().getRoleId().equals(adminRole)){
            chain.doFilter(request, response);
        }else{
            httpResposne.sendRedirect("notes");
            return;
        }
    }

    @Override
    public void destroy() {}
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
 
}
