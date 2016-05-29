package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import org.mindrot.jbcrypt.BCrypt;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Client;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Gender;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.ClientAlreadyExistsException;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.ClientService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.ClientServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationController extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService clientService = new ClientServiceImpl();

        Client client = parseParameters(req);

        try {
            clientService.create(client);
            resp.sendRedirect("/home");
        } catch (ClientAlreadyExistsException e) {
            String error = "Пользователь с введенным логином уже существует";
            req.setAttribute("error", error);
            req.getRequestDispatcher("registration.ftl").forward(req, resp);
        }
    }

    private Client parseParameters(HttpServletRequest req) {
        Client client = new Client();
        client.setLogin(req.getParameter("login"));
        client.setPassword(BCrypt.hashpw(req.getParameter("password"), BCrypt.gensalt()));
        client.setGender(Gender.valueOf(req.getParameter("gender")));

        return client;
    }
}
