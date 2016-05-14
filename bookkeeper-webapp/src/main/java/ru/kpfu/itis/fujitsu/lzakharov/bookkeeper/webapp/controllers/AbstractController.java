package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class AbstractController extends HttpServlet {
    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("HSQLD driver was not found", e);
        }
    }
}
