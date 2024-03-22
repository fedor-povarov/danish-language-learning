package org.fedor.povarov.danish.language.learning.view.verb;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToUuidConverter;
import com.vaadin.flow.shared.Registration;
import org.fedor.povarov.danish.language.learning.entity.Verb;

public class VerbEditForm extends FormLayout {
    private final TextField id = new TextField("ID");
    private final TextField english = new TextField("English");
    private final TextField danish = new TextField("Danish");
    private final Binder<Verb> binder = new BeanValidationBinder<>(Verb.class);
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private Verb verb;

    public VerbEditForm() {
        binder.forField(id)
                .withNullRepresentation("")
                .withConverter(new StringToUuidConverter("unable to convert id to uuid"))
                .bind(Verb::getId, Verb::setId);
        binder.bindInstanceFields(this);

        add(
                id,
                danish,
                english,
                createButtonsLayout()
        );
    }

    public void setVerb(Verb verb) {
        this.verb = verb;
        binder.readBean(verb);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, verb)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(verb);
            fireEvent(new SaveEvent(this, verb));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    public static abstract class VerbFormEvent extends ComponentEvent<VerbEditForm> {
        private final Verb verb;

        protected VerbFormEvent(VerbEditForm source, Verb verb) {
            super(source, false);
            this.verb = verb;
        }

        public Verb getVerb() {
            return verb;
        }
    }

    public static class SaveEvent extends VerbFormEvent {
        SaveEvent(VerbEditForm source, Verb verb) {
            super(source, verb);
        }
    }

    public static class DeleteEvent extends VerbFormEvent {
        DeleteEvent(VerbEditForm source, Verb verb) {
            super(source, verb);
        }

    }

    public static class CloseEvent extends VerbFormEvent {
        CloseEvent(VerbEditForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}