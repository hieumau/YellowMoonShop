/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mh.controller;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.mh.controller.ProcessLib.*;
import static com.mh.controller.Constants.*;
/**
 *
 * @author saost
 */
public class MainController implements Filter {

    private static final Map<String, String> mappedResources = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

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

        mappedResources.put("Login", LOGIN_CONTROLLER);
        mappedResources.put("Logout", LOGOUT_CONTROLLER);
        mappedResources.put("Create cake", CREATE_CAKE_CONTROLLER);
        mappedResources.put("View cake shop", VIEW_CAKE_SHOP_CONTROLLER);
        mappedResources.put("Register", REGISTER_CONTROLLER);

        guestResource.add(LOGIN_PAGE);
        guestResource.add(LOGIN_CONTROLLER);
        guestResource.add(SHOPPING_PAGE);
        guestResource.add(VIEW_CAKE_SHOP_CONTROLLER);
        guestResource.add(REGISTER_CONTROLLER);

        adminResource.add(CREATE_CAKE_PAGE);
        adminResource.add(CREATE_CAKE_CONTROLLER);


    }

    private boolean isUnrestrictedResource(String resource) {
        return guestResource.contains(resource) || resource.toLowerCase().matches("(.*?)\\.(js|css|png|jpeg|jpg)");
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

    }


    public void doFilter(ServletRequest rq, ServletResponse rs,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) rq;
        HttpServletResponse response = (HttpServletResponse) rs;

        String resource = getResource(request);
        log("resource: " + resource);

        try {
            String action = request.getParameter("action");
            String directedResource = mappedResources.get(action);

            if (directedResource != null){
                request.getRequestDispatcher(directedResource).forward(request, response);
                return;
            }

            if (isUnrestrictedResource(resource)){
                chain.doFilter(request, response);
                return;
            }


            //check User role and return is having right to access or not
            if (isHavingAccessRight(request, resource)){
                chain.doFilter(request, response);
                return;
            }

            response.sendRedirect(LOGIN_PAGE);


        } catch (Exception e){
            log(e.getMessage());
            e.printStackTrace();
        }
    }

    //get resource (cut uri)
    public String getResource(HttpServletRequest request){
        return request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
    }


    //authen check function
    public boolean isHavingAccessRight(HttpServletRequest httpServletRequest, String resource){

        //get user role
        int role = getUserRole(httpServletRequest);
        if (role == MEMBER && memberResource.contains(resource)){
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
