package org.masteukodeu.robocoop.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {
    @Test
    public void testGetTotal_empty() {
        Cart cart = new Cart(new ArrayList<>());

        BigDecimal result = cart.getTotal();

        assertThat(result).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void testGetTotal_notEmpty() {

        ArrayList<Cart.Item> items = new ArrayList<>();
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(100), null, null, 0), new BigDecimal(5)));
        Cart cart = new Cart(items);

        BigDecimal result = cart.getTotal();

        assertThat(result).isEqualTo(new BigDecimal(500));
    }

    @Test
    public void testGetTotal_multipleItems() {

        ArrayList<Cart.Item> items = new ArrayList<>();
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(100), null, null, 0), new BigDecimal(5)));
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(200), null, null, 0), new BigDecimal(5)));
        Cart cart = new Cart(items);

        BigDecimal result = cart.getTotal();

        assertThat(result).isEqualTo(new BigDecimal(1500));
    }

    @Test
    public void testGetTotalWithCommunityFund() {

        ArrayList<Cart.Item> items = new ArrayList<>();
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(100), null, null, 0), new BigDecimal(5)));
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(200), null, null, 0), new BigDecimal(5)));
        Cart cart = new Cart(items);

        BigDecimal result = cart.getTotalWithCommunityFund();

        assertThat(result).isEqualTo(new BigDecimal(1500));
    }
}