package com.ascend;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;

import javax.swing.*;

/**
 * Created by mrkirkland on 2/27/2017.
 */
public class Ascend extends JApplet {
    protected Scene scene;
    protected Group root;

    @Override
    public final void init() { // This method is invoked when applet is loaded
        SwingUtilities.invokeLater( () -> startSwing() );
    }

    private void startSwing() { // This method is invoked on Swing thread
        final JFXPanel Panel = new JFXPanel();
        add(Panel);

        Platform.runLater( () ->{
            initFX(Panel);
            initApplet();
        } );
    }

    private void initFX(JFXPanel Panel) { // This method is invoked on JavaFX thread
        root = new Group();
        scene = new Scene(root);
        Panel.setScene(scene);
    }

    public void initApplet() {
        // Add custom initialization code here
    }
}
