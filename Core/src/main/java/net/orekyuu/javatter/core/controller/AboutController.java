package net.orekyuu.javatter.core.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;

import java.awt.*;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        version.setText(String.format("JavaBeamStudio %s", "0.0.1"));
        apiVersion.setText(String.format("JavaBeamStudioAPI %s", "0.0.1"));
        release.setText(String.format("Release %d/%d/%d", 2015, 5, 29));
        webSiteLink.setOnMouseClicked(e -> {
            try {
                Desktop.getDesktop()
                        .browse(new URL(webSiteLink.getText()).toURI());
            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        });
        jreInfo.setText(String
                .format("%s %s", System.getProperty("java.runtime.name"), System.getProperty("java.runtime.version")));
    }
}
