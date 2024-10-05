package com.gra.recist.presentation.util;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.name.Named;
import com.gra.recist.infrastructure.di.scope.window.WindowScope;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Window;

import java.io.IOException;

public class MainFxLoader {

    @Inject
    @Named("windowScope")
    private WindowScope windowScope;

    @Inject
    private Injector injector;


    public <T extends Parent, CONTROLLER> ViewControllerReference<T, CONTROLLER> load(Parent parent, Class<CONTROLLER> clazz) throws IOException {
        return load(parent.getScene().getWindow(), clazz);
    }

    public <T extends Parent, CONTROLLER> ViewControllerReference<T, CONTROLLER> load(Window window, Class<CONTROLLER> clazz) throws IOException {
        try {
            windowScope.enter(window);
            FXMLLoader fxmlLoader = new FXMLLoader(clazz.getResource(clazz.getSimpleName() + ".fxml"));
            fxmlLoader.setControllerFactory(injector::getInstance);
            return new ViewControllerReference<>(fxmlLoader.load(), fxmlLoader.getController());
        } finally {
            windowScope.exit();
        }
    }

    public record ViewControllerReference<T extends Parent, CONTROLLER>(T viewNode, CONTROLLER controller) {
    }
}
