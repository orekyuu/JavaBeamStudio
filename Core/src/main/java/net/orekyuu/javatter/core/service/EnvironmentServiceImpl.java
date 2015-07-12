package net.orekyuu.javatter.core.service;

import net.orekyuu.javatter.api.service.EnvironmentService;

public class EnvironmentServiceImpl implements EnvironmentService {
    @Override
    public String getJavaBeamStudioVersion() {
        return "1.0.0";
    }

    @Override
    public String getJavaBeamStudioApiVersion() {
        return "1.0.0";
    }
}
