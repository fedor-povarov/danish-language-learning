package org.fedor.povarov.danish.language.learning.view.noun;

import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToUuidConverter;
import com.vaadin.flow.shared.Registration;
import org.fedor.povarov.danish.language.learning.entity.Noun;

public class NounEditForm extends FormLayout {

    private final TextField nounId = new TextField("ID");
    private final TextField english = new TextField("English");
    private final TextField gender = new TextField("Gender");
    private final TextField singularIndefinite = new TextField("singularIndefinite");
    private final TextField singularDefinite = new TextField("singularDefinite");
    private final TextField pluralIndefinite = new TextField("pluralIndefinite");
    private final TextField pluralDefinite = new TextField("pluralDefinite");
    private final Checkbox isIrregular = new Checkbox("isIrregular");
    private final Binder<Noun> binder = new BeanValidationBinder<>(Noun.class);
    private final Button save = new Button("Save");
    private final Button delete = new Button("Delete");
    private final Button close = new Button("Cancel");

    private Noun noun;

    public NounEditForm() {
        binder.forField(nounId)
                .withNullRepresentation("")
                .withConverter(new StringToUuidConverter("unable to convert id to uuid"))
                .bind(Noun::getId, Noun::setId);
        binder.bindInstanceFields(this);

        add(
                english,
                gender,
                nounId,
                isIrregular,
                singularIndefinite,
                singularDefinite,
                pluralIndefinite,
                pluralDefinite,
                createButtonsLayout()
        );
    }

    public void setNoun(Noun noun) {
        this.noun = noun;
        binder.readBean(noun);
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, noun)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(noun);
            fireEvent(new SaveEvent(this, noun));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }


    public static abstract class NounFormEvent extends ComponentEvent<NounEditForm> {
        private final Noun noun;

        protected NounFormEvent(NounEditForm source, Noun noun) {
            super(source, false);
            this.noun = noun;
        }

        public Noun getNoun() {
            return noun;
        }
    }

    public static class SaveEvent extends NounFormEvent {
        SaveEvent(NounEditForm source, Noun noun) {
            super(source, noun);
        }
    }

    public static class DeleteEvent extends NounFormEvent {
        DeleteEvent(NounEditForm source, Noun noun) {
            super(source, noun);
        }

    }

    public static class CloseEvent extends NounFormEvent {
        CloseEvent(NounEditForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType, ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}