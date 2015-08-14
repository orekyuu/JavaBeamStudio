package net.orekyuu.javatter.api.service;

import javafx.application.Application;

/**
 * ApplicationへアクセスするためのService
 */
@Service
public interface ApplicationService {

    /**
     * @return Application
     * @since 1.0.0
     */
    Application getApplication();
}
