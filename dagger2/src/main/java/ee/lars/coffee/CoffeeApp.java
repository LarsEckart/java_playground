package ee.lars.coffee;

import dagger.Component;

import javax.inject.Singleton;

public class CoffeeApp {

    public static void main(String[] args) {
        Coffee coffee = DaggerCoffeeApp_Coffee.builder().build();
        coffee.maker().brew();
    }

    @Singleton
    @Component(modules = {DripCoffeeModule.class})
    public interface Coffee {

        CoffeeMaker maker();
    }
}