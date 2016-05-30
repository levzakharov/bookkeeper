package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.enums.Type;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.CategoryService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.ClientService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.RecordService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.CategoryServiceImpl;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.ClientServiceImpl;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.RecordServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

public class ExpenditureController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService categoryService = new CategoryServiceImpl();
        RecordService recordService = new RecordServiceImpl();

        req.setAttribute("categories", categoryService.getAll());

        String login = req.getSession().getAttribute("login").toString();
        req.setAttribute("expenditures", recordService.getExpenditureList(login));

        req.getRequestDispatcher("expenditure.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ClientService clientService = new ClientServiceImpl();
        RecordService recordService = new RecordServiceImpl();

        String login = req.getSession().getAttribute("login").toString();

        Record record = create(req, clientService.find(login).getId());
        recordService.create(record);

        resp.sendRedirect("/expenditure");
    }

    private Record create(HttpServletRequest req, Long clientId) {
        Record record = new Record();

        record.setClientId(clientId);
        record.setCategoryId(Long.valueOf(req.getParameter("category")));
        record.setType(Type.EXPENDITURE);
        record.setAmount(Integer.parseInt(req.getParameter("amount")));
        record.setDescription(req.getParameter("description"));
        record.setCreationDate(Date.valueOf(req.getParameter("date")));

        return record;
    }
}
