package com.gra.recist.presentation.util;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.gra.recist.infrastructure.di.scope.hp.HangingProtocolScope;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HangingProtocolCoordinator {

    @Inject
    @Named("hangingProtocolScope")
    private HangingProtocolScope hpScope;

    @Inject
    private MainFxLoader mainFxLoader;

    private final int hpInstances = 2;

    public <CONTROLLER> List<Stage> prepareViews(Class<CONTROLLER> clazz, Consumer<CONTROLLER> postConstruct) throws IOException {
        try {
            hpScope.enter();
            return IntStream.range(0, hpInstances).mapToObj(idx -> {
                try {
                    MainFxLoader.ViewControllerReference<Parent, CONTROLLER> presenterReference = mainFxLoader.load(clazz);
                    postConstruct.accept(presenterReference.controller());
                    Stage stage = new Stage();
                    stage.setScene(new Scene(presenterReference.viewNode(), 800, 500));
                    return stage;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        } finally {
            hpScope.exit();
        }
    }
}
