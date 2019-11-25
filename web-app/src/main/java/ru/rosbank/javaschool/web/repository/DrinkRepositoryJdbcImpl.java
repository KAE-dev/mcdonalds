package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.util.RowMapper;
import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.exception.DataAccessException;
import ru.rosbank.javaschool.web.model.DrinkModel;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public class DrinkRepositoryJdbcImpl implements DrinkRepository {

    private final DataSource ds;
    private final SQLTemplate template;
    private final RowMapper<DrinkModel> mapper = rs -> new DrinkModel(
            rs.getInt(Constants.ID),
            rs.getString(Constants.NAME),
            rs.getInt(Constants.PRICE),
            rs.getString(Constants.DESCRIPTION),
            rs.getString(Constants.IMAGEURL),
            rs.getInt(Constants.VOLUME)
    );

    public DrinkRepositoryJdbcImpl(DataSource ds, SQLTemplate template) {
        this.ds = ds;
        this.template = template;

        try {
            template.update(ds, "CREATE TABLE IF NOT EXISTS drinks (\n"
                    +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    +
                    "name TEXT NOT NULL, priceRub INTEGER NOT NULL CHECK (priceRub >= 0),\n"
                    +
                    "description Text NOT NULL,\n"
                    +
                    "imageUrl TEXT,\n"
                    +
                    "volumeMl INTEGER NOT NULL CHECK (volumeMl >= 0)\n"
                    +
                    ");");
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public List<DrinkModel> getAll() {
        try {
            return template.queryForList(ds, "SELECT id, name, priceRub, description, imageUrl, volumeMl FROM drinks;", mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public Optional<DrinkModel> getById(int id) {
        try {
            return template.queryForObject(ds, "SELECT id, name, priceRub, description, imageUrl, volumeMl FROM drinks WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            }, mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    @Override
    public void save(DrinkModel model) {
        try {
            if (model.getId() == 0) {
                int id = template.<Integer>updateForId(ds, "INSERT INTO drinks(name, priceRub, description, imageUrl, volumeMl) VALUES (?, ?, ?, ?, ?);", stmt -> {
                    int nextIndex = 1;
                    stmt.setString(nextIndex++, model.getName());
                    stmt.setInt(nextIndex++, model.getPriceRub());
                    stmt.setString(nextIndex++, model.getDescription());
                    stmt.setString(nextIndex++, model.getImageUrl());
                    stmt.setInt(nextIndex, model.getVolumeMl());

                    return stmt;
                });
                model.setId(id);
            } else {
                template.update(ds, "UPDATE drinks SET name = ?, priceRub = ?, description = ?, imageUrl = ?, volumeMl = ? WHERE id = ?;", stmt -> {
                    int nextIndex = 1;
                    stmt.setString(nextIndex++, model.getName());
                    stmt.setInt(nextIndex++, model.getPriceRub());
                    stmt.setString(nextIndex++, model.getDescription());
                    stmt.setString(nextIndex++, model.getImageUrl());
                    stmt.setInt(nextIndex++, model.getVolumeMl());
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
            template.update(ds, "DELETE FROM drinks WHERE id = ?;", stmt -> {
                stmt.setInt(1, id);
                return stmt;
            });
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
}
