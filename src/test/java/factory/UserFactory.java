package factory;

import com.github.javafaker.Faker;
import models.data.UserModel;

public class UserFactory {
    private  static  final Faker faker = new Faker();
    public static UserModel correctUser(){
        return new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(),
                faker.name().firstName()
        );
    }
    public static UserModel userWithoutEmail(){
        return new UserModel(
                "",
                faker.internet().password(),
                faker.name().firstName());

    }
    public static UserModel userWithoutPassword() {
        return new UserModel(
                faker.internet().emailAddress(),
                "",
                faker.name().firstName()
        );
    }
    public static UserModel userWithoutName(){
        return new UserModel(
                faker.internet().emailAddress(),
                faker.internet().password(),
                ""
        );
    }


}
