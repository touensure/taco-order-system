package com.order.manager.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.order.manager.model.Ingredient;
import com.order.manager.model.Taco;
import com.order.manager.repository.interfaces.TacoRepository;


@Repository
public class JdbcTacoRepository implements TacoRepository
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Taco save(Taco taco){
		saveTacoInfo(taco);

		for(String ingredientId:taco.getIngredients()){
			saveIngredientToTaco(taco.getId(), ingredientId);
		}

		return taco;
	}

	private void saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		jdbcTemplate.update("insert into Taco (id, name, createdAt) values (?, ?, ?)",
				taco.getId(), taco.getName(), taco.getCreatedAt());
//		PreparedStatementCreator psc =
//				new PreparedStatementCreatorFactory(
//						"insert into Taco (name, createdAt) values (?, ?)",
//						Types.VARCHAR, Types.TIMESTAMP
//				).newPreparedStatementCreator(
//						Arrays.asList(
//								taco.getName(),
//								new Timestamp(taco.getCreatedAt().getTime())));
//
//		KeyHolder keyHolder = new GeneratedKeyHolder();
//		jdbcTemplate.update(psc, keyHolder);
//
//		return Objects.requireNonNull(keyHolder.getKey()).longValue();
	}

	private void saveIngredientToTaco(String tacoId, String ingredientId) {
		jdbcTemplate.update(
				"insert into Taco_Ingredients (tacoId, ingredientId) " +
						"values (?, ?)",
				tacoId, ingredientId);
	}
}
