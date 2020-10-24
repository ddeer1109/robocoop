package coop.web;

import coop.db.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UsersController {

    private final UserDAO userDAO;

    public UsersController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/admin/users/list")
    protected String doGet(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", userDAO.findAll());
        return "/user/list";
    }
}
