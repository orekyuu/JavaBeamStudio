package net.orekyuu.javatter.core.service;

import javafx.application.Application;
import net.orekyuu.javatter.api.service.ApplicationService;

public class ApplicationServiceImpl implements ApplicationService {

    private final Application application;

    public ApplicationServiceImpl(Application application) {
        this.application = application;
    }

    @Override
    public Application getApplication() {
        return application;
    }
}
