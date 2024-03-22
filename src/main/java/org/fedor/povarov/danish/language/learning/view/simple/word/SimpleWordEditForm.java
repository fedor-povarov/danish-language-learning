package org.fedor.povarov.danish.language.learning.view.simple.word;

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
import org.fedor.povarov.danish.language.learning.entity.SimpleWord;

public class SimpleWordEditForm extends FormLayout {
    private SimpleWord simpleWord;

    private final TextField id = new TextField("ID");
    private final TextField english = new TextField("English");
    private final TextField danish = new TextField("Danish");
    private final Binder<SimpleWord> binder = new BeanValidationBinder<>(SimpleWord.class);
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    public SimpleWordEditForm() {
        binder.forField(id)
                .withNullRepresentation("")
                .withConverter(new StringToUuidConverter("unable to convert id to uuid"))
                .bind(SimpleWord::getId, SimpleWord::setId);
        binder.bindInstanceFields(this);

        add(
                id,
                danish,
                english,
                createButtonsLayout()
        );
    }

    public void setSimpleWord(SimpleWord simpleWord) {
        this.simpleWord = simpleWord;
        binder.readBean(simpleWord);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, simpleWord)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(simpleWord);
            fireEvent(new SaveEvent(this, simpleWord));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public static abstract class SimpleWordFormEvent extends ComponentEvent<SimpleWordEditForm> {
        private final SimpleWord simpleWord;

        protected SimpleWordFormEvent(SimpleWordEditForm source, SimpleWord simpleWord) {
            super(source, false);
            this.simpleWord = simpleWord;
        }

        public SimpleWord getSimpleWord() {
            return simpleWord;
        }
    }

    public static class SaveEvent extends SimpleWordFormEvent {
        SaveEvent(SimpleWordEditForm source, SimpleWord simpleWord) {
            super(source, simpleWord);
        }
    }

    public static class DeleteEvent extends SimpleWordFormEvent {
        DeleteEvent(SimpleWordEditForm source, SimpleWord simpleWord) {
            super(source, simpleWord);
        }

    }

    public static class CloseEvent extends SimpleWordFormEvent {
        CloseEvent(SimpleWordEditForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}