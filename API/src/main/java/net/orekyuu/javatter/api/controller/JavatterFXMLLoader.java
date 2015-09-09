package net.orekyuu.javatter.api.controller;

import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import java.lang.reflect.Field;
import java.net.URL;

/**
 * FXML ControllerへDIを行うFXMLLoader
 * @since 1.0.0
 */
public final class JavatterFXMLLoader extends FXMLLoader {

    private Stage ownerStage;

    /**
     * @since 1.0.0
     */
    public JavatterFXMLLoader() {
        setControllerFactory(this::createController);
    }

    /**
     * @since 1.0.0
     * @param url FXMLのURL
     */
    public JavatterFXMLLoader(URL url) {
        super(url);
        setControllerFactory(this::createController);
    }

    /**
     * FXMLの描画を行うStageを設定する
     * @param stage FXMLの描画を行うStage
     * @since 1.0.0
     */
    public void setOwnerStage(Stage stage) {
        ownerStage = stage;
    }

    private Object createController(Class<?> aClass) {
        Object result = null;
        try {
            result = aClass.getConstructor().newInstance();
            Lookup.inject(result);
            injectOwnerStage(aClass, result);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void injectOwnerStage(Class<?> aClass, Object instance) throws IllegalAccessException {
        for (Field field : aClass.getDeclaredFields()) {
            if (field.getAnnotation(OwnerStage.class) == null) {
                continue;
            }
            if (!field.getType().isAssignableFrom(Stage.class)) {
                throw new ClassCastException(field.getName());
            }
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            field.set(instance, ownerStage);
        }
    }
}
