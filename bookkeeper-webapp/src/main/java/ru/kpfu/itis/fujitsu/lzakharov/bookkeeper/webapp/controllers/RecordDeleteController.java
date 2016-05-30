package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.controllers;

import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.RecordService;
import ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.service.impl.RecordServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RecordDeleteController extends AbstractController {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RecordService recordService = new RecordServiceImpl();
        Long id = Long.parseLong(req.getParameter("id"));

        recordService.remove(id);

        resp.sendRedirect("/home");
    }
}
