package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Income;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.CategoryService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.ClientService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.IncomeService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.CategoryServiceImpl;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.ClientServiceImpl;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.IncomeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class IncomeController extends HttpServlet {
    private ClientService clientService = new ClientServiceImpl();
    private CategoryService categoryService = new CategoryServiceImpl();
    private IncomeService incomeService = new IncomeServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("categories", categoryService.getAll());


        String login = req.getSession().getAttribute("login").toString();
        req.setAttribute("incomes", incomeService.find(login));

        req.getRequestDispatcher("income.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getSession().getAttribute("login").toString();

        Income income = parseParameters(req);
        income.setClientId(clientService.find(login).getId());
        incomeService.create(income);

        resp.sendRedirect("/income");
    }

    private Income parseParameters(HttpServletRequest req) {
        Income income = new Income();

        income.setCategoryId(Long.valueOf(req.getParameter("category")));
        income.setPrice(Integer.parseInt(req.getParameter("price")));
        income.setDescription(req.getParameter("description"));
        income.setCreationDate(Date.valueOf(req.getParameter("date")));

        return income;
    }
}
