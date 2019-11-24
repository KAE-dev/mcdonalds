//package ru.rosbank.javaschool.web.repository;
//
//
//import ru.rosbank.javaschool.util.SQLTemplate;
//import ru.rosbank.javaschool.util.RowMapper;
//import ru.rosbank.javaschool.web.constant.Constants;
//import ru.rosbank.javaschool.web.exception.DataAccessException;
//import ru.rosbank.javaschool.web.model.ProductModel;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//
//public class ProductRepositoryJdbcImpl implements ProductRepository {
//    private final DataSource ds;
//    private final SQLTemplate template;
//    private final RowMapper<ProductModel> mapper = rs -> new ProductModel(
//            rs.getInt(Constants.ID),
//            rs.getString(Constants.NAME),
//            rs.getInt(Constants.PRICE),
//            rs.getString(Constants.STATUS),
//            rs.getString(Constants.DESCRIPTION),
//            rs.getInt(Constants.KILOCALORIES),
//            rs.getString(Constants.IMAGEURL)
//    );
//
//    public ProductRepositoryJdbcImpl(DataSource ds, SQLTemplate template) {
//        this.ds = ds;
//        this.template = template;
//
//        try {
//            template.update(ds, "CREATE TABLE IF NOT EXISTS products (\n" +
//                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
//                    "name TEXT NOT NULL, priceInRub INTEGER NOT NULL CHECK (priceInRub >= 0),\n" +
//                    "status TEXT NOT NULL, description TEXT NOT NULL,\n" +
//                    "kilocalories INTEGER NOT NULL,\n" +
//                    "imageUrl TEXT\n" +
//                    ");");
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }
//
//    @Override
//    public List<ProductModel> getAll() {
//        try {
//            return template.queryForList(ds, "SELECT id, name, priceInRub, status, description, kilocalories, imageUrl FROM products;", mapper);
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }
//
//    @Override
//    public Optional<ProductModel> getById(int id) {
//        try {
//            return template.queryForObject(ds, "SELECT id, name, priceInRub, status, description, kilocalories, imageUrl FROM products WHERE id = ?;", stmt -> {
//                stmt.setInt(1, id);
//                return stmt;
//            }, mapper);
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }
//
//    @Override
//    public void save(ProductModel model) {
//        try {
//            if (model.getId() == 0) {
//                int id = template.<Integer>updateForId(ds, "INSERT INTO products(name, priceInRub, status, description, kilocalories, imageUrl) VALUES (?, ?, ?, ?, ?, ?);", stmt -> {
//                    int nextIndex = 1;
//                    stmt.setString(nextIndex++, model.getName());
//                    stmt.setInt(nextIndex++, model.getPriceInRub());
//                    stmt.setString(nextIndex++, model.getStatus());
//                    stmt.setString(nextIndex++, model.getDescription());
//                    stmt.setInt(nextIndex++, model.getKilocalories());
//                    stmt.setString(nextIndex++, model.getImageUrl());
//                    return stmt;
//                });
//                model.setId(id);
//            } else {
//                template.update(ds, "UPDATE products SET name = ?, priceInRub = ?, status = ?, description = ?, kilocalories = ?, imageUrl = ? WHERE id = ?;", stmt -> {
//                    int nextIndex = 1;
//                    stmt.setString(nextIndex++, model.getName());
//                    stmt.setInt(nextIndex++, model.getPriceInRub());
//                    stmt.setString(nextIndex++, model.getStatus());
//                    stmt.setString(nextIndex++, model.getDescription());
//                    stmt.setInt(nextIndex++, model.getKilocalories());
//                    stmt.setString(nextIndex++, model.getImageUrl());
//                    stmt.setInt(nextIndex++, model.getId());
//                    return stmt;
//                });
//            }
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }
//
//    @Override
//    public void removeById(int id) {
//        try {
//            template.update(ds, "DELETE FROM products WHERE id = ?;", stmt -> {
//                stmt.setInt(1, id);
//                return stmt;
//            });
//        } catch (SQLException e) {
//            throw new DataAccessException(e);
//        }
//    }
//}
