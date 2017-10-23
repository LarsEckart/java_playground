package ee.lars.di.dagger2.computer;

import dagger.Module;
import dagger.Provides;

@Module
public class ProvidersModule {

    @Provides
    Printer printer() {
        return new PrinterImpl();
    }
}
