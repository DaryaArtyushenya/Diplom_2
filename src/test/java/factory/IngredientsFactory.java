package factory;

import com.github.javafaker.Faker;
import models.pojo.pojoOrder.CreateOrderRequest;

import java.util.List;


public class IngredientsFactory {
    private  static  final Faker faker = new Faker();
    public static CreateOrderRequest validOrder(){
        return new CreateOrderRequest(
                List.of("61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa73")
        );
    }
    public static CreateOrderRequest nullIngredients(){
        return new CreateOrderRequest(
                List.of("", "")
        );
    }
    public static CreateOrderRequest invalidIngredientHash(){
        return new CreateOrderRequest(
                List.of(
                        faker.regexify("[a-f0-9]{24}"),
                        faker.regexify("[a-f0-9]{24}")

                )
        );
    }
    public static CreateOrderRequest orderWithoutIngredients(){
        return new CreateOrderRequest(
                List.of(


                )
        );
    }

}
