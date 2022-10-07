package com.order.manager.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.order.manager.config.security.UserRepository;
import com.order.manager.repository.IngredientRepository;

@Profile("!prod")//only when the profile is not prod, then this bean will be created
@Configuration
public class DevelopmentConfig {

	@Bean
	public CommandLineRunner dataLoader(IngredientRepository repo,
			UserRepository userRepo, PasswordEncoder encoder) { // user repo for ease of testing with a built-in user
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
//				repo.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
//				repo.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
//				repo.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
//				repo.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
//				repo.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
//				repo.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
//				repo.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
//				repo.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
//				repo.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
//				repo.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
//
//
//				userRepo.save(new User("habuma", encoder.encode("password"),
//						"Craig Walls", "123 North Street", "Cross Roads", "TX",
//						"76227", "123-123-1234"));
			}
		};
	}

}

