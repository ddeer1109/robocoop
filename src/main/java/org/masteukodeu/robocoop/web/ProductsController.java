package org.masteukodeu.robocoop.web;

import org.masteukodeu.robocoop.db.CategoryDAO;
import org.masteukodeu.robocoop.model.Category;
import org.masteukodeu.robocoop.model.CoopService;
import org.masteukodeu.robocoop.model.ProductDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductsController {

    private final CoopService service;
    private final CategoryDAO categoryDAO;

    public ProductsController(CoopService service, CategoryDAO categoryDAO) {
        this.service = service;
        this.categoryDAO = categoryDAO;
    }

    @GetMapping("/products/list")
    public String doGet(Model model, String category) {
        Map<Category, List<ProductDetails>> productsByCategory = service.getProductsByCategory();
        Map<CategoryView, List<ProductDetails>> productsByCategoryView = new LinkedHashMap<>();
        for (Map.Entry<Category, List<ProductDetails>> categoryListEntry : productsByCategory.entrySet()) {
            if (StringUtils.isEmpty(category) || category.equals(categoryListEntry.getKey().getId())) {
                productsByCategoryView.put(
                        new CategoryView(categoryListEntry.getKey(), service.isCategoryBlocked(categoryListEntry.getKey())),
                        categoryListEntry.getValue()
                );
            }
        }
        model.addAttribute("productsByCategory", productsByCategoryView);
        model.addAttribute("orderingBlocked", service.isOrderingBlocked());
        model.addAttribute("categories", categoryDAO.allVisible());
        return "product/list";
    }

}
