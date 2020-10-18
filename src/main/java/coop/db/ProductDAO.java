package coop.db;


import coop.model.Category;
import coop.model.Product;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAO {

    private DataSource dataSource;

    public ProductDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> findAll() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM spoldzielnia_produkty")) {
            ResultSet rs = ps.executeQuery();
            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                list.add(mapProduct(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Product mapProduct(ResultSet rs) throws SQLException {
        return new Product(
                rs.getString("id"),
                rs.getString("nazwa"),
                rs.getBigDecimal("cena_za_jednostke"),
                rs.getString("jednostka"),
                rs.getString("kategoria"));
    }

    public Product byId(String productId) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM spoldzielnia_produkty WHERE id = ?")) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return mapProduct(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> categories() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM spoldzielnia_kategorie")) {
            ResultSet rs = ps.executeQuery();
            List<Category> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Category(rs.getString("id"), rs.getString("nazwa")));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}