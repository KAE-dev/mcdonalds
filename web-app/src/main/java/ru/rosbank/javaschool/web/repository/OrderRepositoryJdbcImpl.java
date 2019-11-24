package ru.rosbank.javaschool.web.repository;


import ru.rosbank.javaschool.util.RowMapper;
import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.exception.DataAccessException;
import ru.rosbank.javaschool.web.model.OrderModel;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryJdbcImpl implements OrderRepository {
    private final DataSource ds;
    private final SQLTemplate template;
    private final RowMapper<OrderModel> mapper = rs -> new OrderModel(
            rs.getInt("id"),
            rs.getBoolean("isPaid")
    );

    public OrderRepositoryJdbcImpl(DataSource ds, SQLTemplate template) {
        this.ds = ds;
        this.template = template;

        try {
            template.update(ds, "CREATE TABLE IF NOT EXISTS orders (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "isPaid INTEGER NOT NULL\n" +
                    ");");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<OrderModel> getAll() {
        try {
            return template.queryForList(ds, "SELECT id, isPaid FROM orders;", mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<OrderModel> getById(int id) {
        try {
            return template.queryForObject(ds, "SELECT id, isPaid FROM orders WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            }, mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void save(OrderModel model) {
        try {
            if (model.getId() == 0) {
                int id = template.<Integer>updateForId(ds, "INSERT INTO orders (isPaid) VALUES (?);", stmt -> {
                    int nextIndex = 1;
                    stmt.setBoolean(nextIndex, model.isPaid());
                    return stmt;
                });
                model.setId(id);
                return;
            } else {
                template.update(ds, "UPDATE orders SET isPaid = ? WHERE id = ?;", stmt -> {
                    int nextIndex = 1;
                    stmt.setBoolean(nextIndex++, model.isPaid());
                    stmt.setInt(nextIndex, model.getId());
                    return stmt;
                });
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void removeById(int id) {
        try {
            template.update(ds, "DELETE FROM orders WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
