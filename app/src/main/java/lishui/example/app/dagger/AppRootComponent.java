package lishui.example.app.dagger;

import android.content.ContentProvider;

import javax.inject.Singleton;

import dagger.Component;
import lishui.example.app.DemoAppComponentFactory;
import lishui.example.app.DemoDaggerFactory;
import lishui.example.app.Dependency;

/**
 * @author lishui.lin
 * Created it on 21-2-21
 * <p>
 * Root component for Dagger injection.
 */
@Singleton
@Component(modules = {
        DefaultComponentBinder.class,
        DependencyProvider.class,
        SystemServicesModule.class,
        AppDemoModule.class,
        DemoDaggerFactory.ContextHolder.class})
public interface AppRootComponent {

    /**
     * Main dependency providing module.
     */
    @Singleton
    Dependency.DependencyInjector createDependency();

    /**
     * Member injection into the supplied argument.
     */
    void inject(DemoAppComponentFactory factory);

    /**
     * Member injection into the supplied argument.
     */
    void inject(ContentProvider contentProvider);
}
