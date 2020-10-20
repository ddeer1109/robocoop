package coop.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {

    @Test
    public void testComplete() {
        Cart.Item item = new Cart.Item(new Product("", "", null, "", "", 10), new BigDecimal(10));

        String status = item.getStatus();

        assertThat(status).isEqualTo("complete");
    }

    @Test
    public void testEmpty() {
        Cart.Item item = new Cart.Item(new Product("", "", null, "", "", 10), new BigDecimal(0));

        String status = item.getStatus();

        assertThat(status).isEqualTo("empty");
    }

    @Test
    public void testIncomplete() {
        Cart.Item item = new Cart.Item(new Product("", "", null, "", "", 10), new BigDecimal(5));

        String status = item.getStatus();

        assertThat(status).isEqualTo("incomplete");
    }

    @Test
    public void testCompleteIncomplete() {
        Cart.Item item = new Cart.Item(new Product("", "", null, "", "", 10), new BigDecimal(15));

        String status = item.getStatus();

        assertThat(status).isEqualTo("complete-incomplete");
    }

}