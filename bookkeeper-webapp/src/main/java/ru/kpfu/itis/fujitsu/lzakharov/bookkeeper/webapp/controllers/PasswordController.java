package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.ClientService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PasswordController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService clientService = new ClientServiceImpl();

        String login = req.getSession().getAttribute("login").toString();
        String password = req.getParameter("password");
        String newPassword = req.getParameter("newPassword");

        Client client = clientService.find(login, password);
        if (client != null) {
            clientService.updatePassword(client, newPassword);
            String check = "Пароль успешно обновлен!";
            req.setAttribute("check", check);
            req.getRequestDispatcher("settings.ftl").forward(req, resp);
        } else {
            String error = "Неверный старый пароль!";
            req.setAttribute("error", error);
            req.getRequestDispatcher("settings.ftl").forward(req, resp);
        }

    }
}
