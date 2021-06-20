package org.masteukodeu.robocoop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.masteukodeu.robocoop.db.CategoryDAO;
import org.masteukodeu.robocoop.db.DeliveryDAO;
import org.masteukodeu.robocoop.db.OrderDAO;
import org.masteukodeu.robocoop.db.ProductDAO;
import org.masteukodeu.robocoop.db.JdbcRoundDAO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoopServiceTest {

    public static final String ROUND_ID = "ROUND_ID";

    private final ProductDAO productDAO = mock(ProductDAO.class);
    private final OrderDAO orderDAO = mock(OrderDAO.class);
    private final CategoryDAO categoryDAO = mock(CategoryDAO.class);
    private final JdbcRoundDAO roundDAO = mock(JdbcRoundDAO.class);
    private final DeliveryDAO deliveryDAO = mock(DeliveryDAO.class);

    private final CoopService coopService = new CoopService(productDAO, orderDAO, null, roundDAO, null, categoryDAO, deliveryDAO);

    @BeforeEach
    public void setUp() {
        when(roundDAO.current()).thenReturn(new Round(ROUND_ID, null, null));
    }

    @Test
    public void returnsEmptyMap_whenThereAreNoProductsAndCategories  () {

        Map<Category, List<ProductDetails>> result = coopService.getProductsByCategory();

        assertThat(result).isEmpty();
    }


    @Test
    public void returnsMapWithEmptyCategory_whenThereAreNoProducts() {

        Category category = new Category("", "", false, BigDecimal.ZERO);
        given(categoryDAO.all()).willReturn(List.of(category));

        Map<Category, List<ProductDetails>> result = coopService.getProductsByCategory();

        assertThat(result).hasSize(1);
        assertThat(result.get(category)).isEmpty();

    }


    @Test
    public void returnsMapWithCategoryAsAKeyAndProductDetailsListAsAValue() {

        Category category = new Category("CATEGORY_ID", "", false, BigDecimal.ZERO);
        given(categoryDAO.all()).willReturn(List.of(category));
        Product product = new Product("", "", null, "", "CATEGORY_ID", 1);
        given(productDAO.findAll()).willReturn(List.of(product));

        Map<Category, List<ProductDetails>> result = coopService.getProductsByCategory();

        assertThat(result).hasSize(1);
        assertThat(result.get(category)).hasSize(1);
        ProductDetails productDetails = result.get(category).get(0);
        assertThat(productDetails.getProduct()).isEqualTo(product);
        assertThat(productDetails.getTotalQuantity()).isEqualTo("0");
        assertThat(productDetails.getMissingToTransactionalQuantity()).isEqualTo("1");
        assertThat(productDetails.getStatus()).isEqualTo("empty");
    }


    @Test
    public void addsQuantityOfOrderedProductsInAGivenRoundToProductDetails() {

        Category category = new Category("CATEGORY_ID", "", false, BigDecimal.ZERO);
        given(categoryDAO.all()).willReturn(List.of(category));
        Product product = new Product("PRODUCT_ID", "", null, "", "CATEGORY_ID", 1);
        given(productDAO.findAll()).willReturn(List.of(product));
        given(orderDAO.byRound(ROUND_ID)).willReturn(List.of(new Order("", "PRODUCT_ID", "", "", new BigDecimal("100.00"))));

        Map<Category, List<ProductDetails>> result = coopService.getProductsByCategory();

        assertThat(result).hasSize(1);
        assertThat(result.get(category)).hasSize(1);
        ProductDetails productDetails = result.get(category).get(0);
        assertThat(productDetails.getProduct()).isEqualTo(product);
        assertThat(productDetails.getTotalQuantity()).isEqualByComparingTo("100.00");
        assertThat(productDetails.getMissingToTransactionalQuantity()).isEqualByComparingTo("1");
        assertThat(productDetails.getStatus()).isEqualTo("complete");
    }

    @Test
    public void addDeliveryOfOrderedProductsInAGivenRoundToProductDetails() {

        Category category = new Category("CATEGORY_ID", "", false, BigDecimal.ZERO);
        given(categoryDAO.all()).willReturn(List.of(category));
        Product product = new Product("PRODUCT_ID", "", null, "", "CATEGORY_ID", 1);
        given(productDAO.findAll()).willReturn(List.of(product));
        given(orderDAO.byRound(ROUND_ID)).willReturn(List.of(new Order("", "PRODUCT_ID", "", "", new BigDecimal("100.00"))));
        Delivery delivery = new Delivery("", "PRODUCT_ID", new BigDecimal("123.5"), new BigDecimal("50"));
        given(deliveryDAO.byRound(ROUND_ID)).willReturn(List.of(delivery));

        Map<Category, List<ProductDetails>> result = coopService.getOrderedProductsByCategoryForRound("ROUND_ID");

        assertThat(result).hasSize(1);
        assertThat(result.get(category)).hasSize(1);
        ProductDetails productDetails = result.get(category).get(0);
        assertThat(productDetails.getProduct()).isEqualTo(product);
        assertThat(productDetails.getTotalQuantity()).isEqualByComparingTo("100.00");
        assertThat(productDetails.getMissingToTransactionalQuantity()).isEqualByComparingTo("1");
        assertThat(productDetails.getStatus()).isEqualTo("complete");

        assertThat(productDetails.getDelivery()).isEqualTo(delivery);
    }

}