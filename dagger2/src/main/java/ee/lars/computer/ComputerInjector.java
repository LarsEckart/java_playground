package ee.lars.computer;

import dagger.Component;

@Component(modules = ProvidersModule.class)
public interface ComputerInjector {

    Computer computer();
}
