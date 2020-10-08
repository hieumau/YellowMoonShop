/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.controller;

import com.mh.entity.Users;
import com.mh.user.controller.LoginController;
import com.mh.user.controller.LogoutController;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.mh.controller.ProcessLib.*;

/**
 *
 * @author saost
 */
public class MainController implements Filter {
    private static final String SHOP_PAGE = "shop.jsp";
    private static final String LOGIN_PAGE = "login.jsp";
    private static final String LOGOUT = LogoutController.class.getSimpleName();
    private static final String LOGIN = LoginController.class.getSimpleName();


    private static List<String> adminResource;
    private static List<String> memberResource;
    private static List<String> guestResource;
    private static final boolean debug = true;
    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public MainController() {
        adminResource = new ArrayList<>();
        memberResource = new ArrayList<>();
        guestResource = new ArrayList<>();


    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MainController:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MainController:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }


    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String uri = httpServletRequest.getRequestURI();
        String url = SHOP_PAGE;

        try {
            if ((uri.contains(".js")
                    || uri.contains(".css")
                    || uri.contains(".eot")
                    || uri.contains(".svg")
                    || uri.contains(".ttf")
                    || uri.contains(".woff")
                    || uri.contains(".woff2")
                    || uri.contains(".jpg")
                    || uri.contains(".png")
                    || uri.contains(".jpeg"))
                    && !uri.contains(".jsp")) {
                chain.doFilter(request, response);
                return;
            } else {
                // all user can access
                if (uri.contains(LOGIN_PAGE)
                    || uri.contains(SHOP_PAGE)){
                    chain.doFilter(request, response);
                     return;
                }
            }


            //MainController
            String action = httpServletRequest.getParameter("btnAction");
            if(action == null)action = "";
            switch (action){
                case "Login":
                    url = LOGIN;
                    break;
                case "Logout":
                    url = LOGOUT;
                    break;
                default:
                    url = SHOP_PAGE;
                    break;
            }
            httpServletRequest.getRequestDispatcher(url).forward(request, response);


            //Authen Filter
            String resource = url;

            //get user role
            int role = getUserRole(httpServletRequest);
            if (role == GUEST && guestResource.contains(resource)){
                chain.doFilter(request, response);
            } else if (role == MEMBER && memberResource.contains(resource)){
                chain.doFilter(request, response);
            } else if (role == ADMIN && adminResource.contains(resource)){
                chain.doFilter(request, response);
            } else {
                httpServletResponse.sendRedirect(LOGIN_PAGE);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //authen check function
    public boolean isHavingAccessRight(HttpServletRequest httpServletRequest, String resource){

        //get user role
        int role = getUserRole(httpServletRequest);
        if (role == GUEST && guestResource.contains(resource)){
            return true;
        } else if (role == MEMBER && memberResource.contains(resource)){
            return true;
        } else if (role == ADMIN && adminResource.contains(resource)){
            return true;
        }
        return false;
    }

    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }



    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("MainController:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("MainController()");
        }
        StringBuffer sb = new StringBuffer("MainController(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
