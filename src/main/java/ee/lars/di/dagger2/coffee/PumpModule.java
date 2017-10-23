package ee.lars.di.dagger2.coffee;

import dagger.Binds;
import dagger.Module;

@Module
abstract class PumpModule {

    @Binds
    abstract Pump providePump(Thermosiphon pump);
}
