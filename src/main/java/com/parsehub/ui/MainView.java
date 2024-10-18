package com.parsehub.ui;

import com.nimbusds.jose.Header;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@Route("")  // Maps this view to the root path
public class MainView extends VerticalLayout {
    public MainView() {
        Button button = new Button("Click me",
                e -> Notification.show("Button was clicked"));
        add(button);
    }
}
