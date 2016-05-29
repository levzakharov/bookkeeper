package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Category;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.model.Income;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.CategoryService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.IncomeService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.CategoryServiceImpl;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.IncomeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoryService categoryService = new CategoryServiceImpl();
        IncomeService incomeService = new IncomeServiceImpl();

        String login = req.getSession().getAttribute("login").toString();

        List<Income> incomes = incomeService.find(login);
        List<Category> categories = categoryService.getAll();
        Map<String, Integer> data = new HashMap<>();

        for (Category category: categories) {
            data.put(category.getName(), 0);
        }
        for (Income income: incomes) {
            String category = income.getCategory().getName();
            int price = data.get(category);
            data.put(category, price + income.getPrice());
        }

        req.setAttribute("data", data);

        req.setAttribute("amount", incomeService.getClientMonthAmount(login,
                Calendar.getInstance().get(Calendar.MONTH) + 1));

        req.getRequestDispatcher("home.ftl").forward(req, resp);
    }
}
