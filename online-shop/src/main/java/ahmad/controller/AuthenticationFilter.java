package ahmad.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 
 * AuthenticationFilter, um nicht-authentifizierte Zugriffe auf geschützte Seiten zu verhindern
 * CSRF-Schutz soll noch implementiert werden
 * noch Passwort-Reset-Funktionalität.
 * HTTPS für die Produktionsumgebung.
 * 
 * @author Ahmad Alrefai
 */

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            
            // Erlaubt direkten Zugriff auf die Login-Seite
            if (reqURI.contains("/login.xhtml") 
            		|| reqURI.contains("/index_1.xhtml") 
            		|| reqURI.contains("/index_2.xhtml") 
            		|| reqURI.contains("/index.xhtml") 
            		|| reqURI.contains("/carousek.xhtml") 
            		|| reqURI.contains("/simpleTabel.xhtml") 
            		|| reqURI.contains("/validator.xhtml") 
                    || reqURI.contains("/public/") 
                    || reqURI.contains("jakarta.faces.resource")) {
                chain.doFilter(request, response);
                return;
            }

            // Überprüft, ob der Benutzer angemeldet ist
            if (ses != null && ses.getAttribute("user") != null) {
                chain.doFilter(request, response);
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/login.xhtml");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {
    }
}