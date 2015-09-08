package net.orekyuu.javatter.core.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import net.orekyuu.javatter.api.service.ApplicationService;
import net.orekyuu.javatter.api.service.EnvironmentService;
import net.orekyuu.javatter.api.util.lookup.Lookup;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutController implements Initializable {
    public Text version;
    public Text release;
    public Text jreInfo;
    public Hyperlink webSiteLink;
    public Text apiVersion;
    public Text osInfo;

    @Inject
    private ApplicationService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EnvironmentService environmentService = Lookup.lookup(EnvironmentService.class);

        version.setText(String.format("JavaBeamStudio %s", environmentService.getJavaBeamStudioVersion()));
        apiVersion.setText(String.format("JavaBeamStudioAPI %s", environmentService.getJavaBeamStudioApiVersion()));
        release.setText(String.format("Release %d/%d/%d", 2015, 5, 29));
        webSiteLink.setOnMouseClicked(e -> {
            try {
                service.getApplication()
                        .getHostServices()
                        .showDocument(new URL(webSiteLink.getText()).toURI().toString());
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        });
        jreInfo.setText(String
                .format("%s %s", System.getProperty("java.runtime.name"), System.getProperty("java.runtime.version")));
        osInfo.setText(System.getProperty("os.name"));
    }
}
