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

        assertThat(result).isEqualByComparingTo(new BigDecimal(1650));
    }

    @Test
    public void testGetTotalZeroWithCommunityFund() {

        ArrayList<Cart.Item> items = new ArrayList<>();
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(100), null, null, 0), new BigDecimal(0)));
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(200), null, null, 0), new BigDecimal(0)));
        Cart cart = new Cart(items);

        BigDecimal result = cart.getTotalWithCommunityFund();

        assertThat(result).isEqualByComparingTo(new BigDecimal(0));
    }

    @Test
    public void testGetTotalWithDecimal() {

        ArrayList<Cart.Item> items = new ArrayList<>();
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(100), null, null, 0), new BigDecimal(0.5)));
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(200), null, null, 0), new BigDecimal(0.5)));
        Cart cart = new Cart(items);

        BigDecimal result = cart.getTotal();

        assertThat(result).isEqualByComparingTo(new BigDecimal(150));
    }

    @Test
    public void testGetTotalWithDecimalAndTomato() {

        ArrayList<Cart.Item> items = new ArrayList<>();
        items.add(new Cart.Item("", new Product(null, "Pomidory", new BigDecimal(100), null, null, 0), new BigDecimal(0.5)));
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(200), null, null, 0), new BigDecimal(0.5)));
        Cart cart = new Cart(items);

        BigDecimal result = cart.getTotal();

        assertThat(result).isEqualByComparingTo(new BigDecimal(150));
    }

    @Test
    public void testGetTotalWithDecimalAndTomatoWithUnit() {

        ArrayList<Cart.Item> items = new ArrayList<>();
        items.add(new Cart.Item("", new Product(null, "Pomidory", new BigDecimal(100), "kg", null, 0), new BigDecimal(1)));
        items.add(new Cart.Item("", new Product(null, null, new BigDecimal(200), null, null, 0), new BigDecimal(1)));
        Cart cart = new Cart(items);

        BigDecimal result = cart.getTotal();

        assertThat(result).isEqualByComparingTo(new BigDecimal(300));
    }

    @Test
    public void testGetTotalWithDecimalAndID() {

        ArrayList<Cart.Item> items = new ArrayList<>();
        items.add(new Cart.Item("", new Product("44", null, new BigDecimal(100), null, null, 0), new BigDecimal(1)));
        items.add(new Cart.Item("", new Product("56", null, new BigDecimal(200), null, null, 0), new BigDecimal(1)));
        Cart cart = new Cart(items);

        BigDecimal result = cart.getTotal();

        assertThat(result).isEqualByComparingTo(new BigDecimal(300));
    }
}