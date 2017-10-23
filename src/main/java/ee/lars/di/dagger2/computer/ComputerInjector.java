package ee.lars.di.dagger2.computer;

import dagger.Component;

@Component(modules = ProvidersModule.class)
public interface ComputerInjector {

    Computer computer();
}
