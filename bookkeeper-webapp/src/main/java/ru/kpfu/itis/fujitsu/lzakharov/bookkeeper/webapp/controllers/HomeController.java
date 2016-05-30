package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.AbstractModel;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Record;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.CategoryService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.RecordService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.CategoryServiceImpl;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.RecordServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

public class HomeController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RecordService recordService = new RecordServiceImpl();

        String login = req.getSession().getAttribute("login").toString();
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;

        req.setAttribute("balance", recordService.getBalance(login));
        req.setAttribute("income", recordService.getMonthlyIncome(login, month));
        req.setAttribute("expenditure", recordService.getMonthlyExpenditure(login, month));

        req.getRequestDispatcher("home.ftl").forward(req, resp);
    }
}
