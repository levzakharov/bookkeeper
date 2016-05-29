package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.ClientService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService clientService = new ClientServiceImpl();

        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        Client client = clientService.find(login, password);
        if (client != null) {
            if (remember != null) {
                // TODO: add cookies
            }

            req.getSession().setAttribute("login", login);

            resp.sendRedirect("/home");
        } else {
            String error = "Неверный логин или пароль";
            req.setAttribute("error", error);
            req.getRequestDispatcher("login.ftl").forward(req, resp);
        }
    }
}
