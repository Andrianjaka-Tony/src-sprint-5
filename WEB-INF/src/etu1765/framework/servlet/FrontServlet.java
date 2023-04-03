package etu1765.framework.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import use.Package;
import use.Utility;
import use.View;
import etu1765.framework.Mapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class FrontServlet extends HttpServlet {

  HashMap<String, Mapping> mappingUrls;

  public void init() {
    ServletContext context = getServletContext();
    String absolutePath = context.getRealPath("");
    absolutePath += "WEB-INF/classes";
    try {
      this.mappingUrls = Package.scanPackages(absolutePath);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, Exception {
    
    PrintWriter out = resp.getWriter();
    String url = String.valueOf(req.getRequestURL());
    url = Utility.getUrl(url);
    
    try {
      if (url.compareToIgnoreCase("/") == 0) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
      }
      String view = View.loadView(url, this.mappingUrls);
      RequestDispatcher dispatcher = req.getRequestDispatcher(view);  
      dispatcher.forward(req, resp);
    } catch (Exception e) {
      out.println(e.getMessage());
    }

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      processRequest(req, resp);
    } catch (Exception e) {
      PrintWriter out = resp.getWriter();
      out.println(e.getMessage());
    }
  }
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      processRequest(req, resp);
    } catch (Exception e) {
      PrintWriter out = resp.getWriter();
      out.println(e.getMessage());
    }
  }
  
}
