package net.orekyuu.javatter.api.service;

import javafx.application.Application;

/**
 * ApplicationへアクセスするためのService
 * @since 1.0.0
 */
@Service
public interface ApplicationService {

    /**
     * @return Application
     * @since 1.0.0
     */
    Application getApplication();
}
