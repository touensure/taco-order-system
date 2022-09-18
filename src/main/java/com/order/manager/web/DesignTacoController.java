package com.order.manager.web;

import com.order.manager.config.security.User;
import com.order.manager.model.Ingredient;
import com.order.manager.model.Order;
import com.order.manager.model.Taco;
import com.order.manager.repository.IngredientRepository;
import com.order.manager.repository.OrderRepository;
import com.order.manager.repository.TacoRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
@Controller
@RequestMapping("/design")
@SessionAttributes({"order", "ingredients"})//make sure order can be used in multiple requests, to get multiple Tacos
public class DesignTacoController {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private TacoRepository tacoRepository;

    @Autowired
    private OrderRepository orderRepository;

    //make sure that there is an Order be set to Model, which is to be extracted by HTML code
    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    //make sure that there is an Taco be set to Model, which is to be extracted by HTML code
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute(name = "ingredients")
    public ArrayList<Ingredient> ingredients() {
        return new ArrayList<>();
    }

    @GetMapping
    public String showDesignForm(Model model, @AuthenticationPrincipal User user){
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        categorizeIngredientsByTypeAndAddToModel(ingredients, model);

        /*add ingredients to model, which is used to reproduce ingredients
         *when encountered bindingErrors in processDesign method */
        model.addAttribute("ingredients", ingredients);
        model.addAttribute("user", user);

        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco tacoDesign,
                                Errors bindingErrors,
                                @ModelAttribute Order order,
                                Model model){/*if there are binding errors, including
                                              *validation error, the errors will be caught
                                              *and saved into the bindingErrors*/

        if(bindingErrors.hasErrors()){
            @SuppressWarnings ("unchecked")
            List<Ingredient> ingredients = (List<Ingredient>) model.getAttribute("ingredients");
            categorizeIngredientsByTypeAndAddToModel(ingredients, model);
            return "design";
        }

//        Taco saved = tacoRepository.save(tacoDesign);
        order.addDesign(tacoDesign);
        tacoDesign.setTacoOrder(order);
        log.info("process taco design finished, Taco saved");

        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(
            List<Ingredient> ingredients, Ingredient.Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }

    private void categorizeIngredientsByTypeAndAddToModel(List<Ingredient> ingredients, Model model){
        Ingredient.Type[] types = Ingredient.Type.values();
        for (Ingredient.Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                  filterByType(ingredients, type));
        }
    }
}
