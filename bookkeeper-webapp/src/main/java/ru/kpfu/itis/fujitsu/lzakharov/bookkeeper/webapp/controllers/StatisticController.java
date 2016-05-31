package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.RecordService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.RecordServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatisticController extends AbstractController {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RecordService recordService = new RecordServiceImpl();
        String login = req.getSession().getAttribute("login").toString();

        req.setAttribute("income", recordService.getTotalIncome(login));
        req.setAttribute("expenditure", recordService.getTotalExpenditure(login));
        req.setAttribute("averageIncome", recordService.getTotalAverageIncome(login));
        req.setAttribute("averageExpenditure", recordService.getTotalAverageExpenditure(login));
        req.setAttribute("data", recordService.getTotalMonthlyBalanceData(login));

        req.getRequestDispatcher("statistic.ftl").forward(req, resp);
    }
}
