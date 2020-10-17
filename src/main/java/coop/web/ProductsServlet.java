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
public class ProductsServlet {
    private final ProductDAO productDAO;

    public ProductsServlet(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/products/list")
    public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("products", productDAO.findAll());
        return "product/list";
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }
}
