package com.order.manager.repository;

import com.order.manager.model.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

@Repository
public class IngredientJDBCRepositoryImpl implements IngredientJDBCRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Iterable<Ingredient> findAll() {//if write List<Ingredient> the query result will be a lIst
        String orderQuerySql = "select * from INGREDIENT";

        //jdbcTemplate.query(orderQuerySql,this::mapRowToIngredient) also valid
        /**jdbcTemplate.query(orderQuerySql, new RowMapper<Ingredient>() {
            public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Ingredient(rs.getString("id"), rs.getString("name"),
                        Ingredient.Type.valueOf(rs.getString("type")));
            }
        })  also valid*/
        return jdbcTemplate.query(orderQuerySql, new BeanPropertyRowMapper<>(Ingredient.class));
    }

    @Override
    public Ingredient findById(String id) {
        String orderQuerySql = "select * from INGREDIENT where id=?";

        return jdbcTemplate.queryForObject(orderQuerySql,
            new BeanPropertyRowMapper<>(Ingredient.class), id);
    }

    @Override
    public void deleteById(String id) {
        String orderQuerySql = "delete from INGREDIENT where id=?";
        jdbcTemplate.update(orderQuerySql, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        //clear existing one
        String id = ingredient.getId();
        Ingredient originalIngredient = this.findById(id);
        if (Objects.nonNull(originalIngredient)) {
            this.deleteById(id);
        }

        //insert new ingredient
        jdbcTemplate.update("insert into INGREDIENT (id, name, type) values (?,?,?)",
            ingredient.getId(), ingredient.getName(), ingredient.getType().toString());

        return ingredient;
    }

    private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
        return new Ingredient(rs.getString("id"), rs.getString("name"),
            Ingredient.Type.valueOf(rs.getString("type")));
    }
}
