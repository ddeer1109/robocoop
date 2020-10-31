package coop.web;

import coop.db.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsersController {

    private final UserDAO userDAO;

    public UsersController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/admin/users/list")
    protected String doGet(Model model) {
        model.addAttribute("users", userDAO.findAll());
        return "admin/users_list";
    }
}
