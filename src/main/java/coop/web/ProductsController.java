package coop.web;

import coop.db.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ProductsController {
    private final ProductDAO productDAO;

    public ProductsController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/products/list")
    public String doGet(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("products", productDAO.findAll());
        return "product/list";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
