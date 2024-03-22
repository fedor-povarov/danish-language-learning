package org.fedor.povarov.danish.language.learning.view.simple.word;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.codec.binary.StringUtils;
import org.fedor.povarov.danish.language.learning.entity.SimpleWord;
import org.fedor.povarov.danish.language.learning.service.SimpleWordService;
import org.fedor.povarov.danish.language.learning.view.MainLayout;

@Route(value = "simple-word/learning", layout = MainLayout.class)
@PageTitle("Simple Word Learning")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class SimpleWordLearningView extends VerticalLayout {

    private final FormLayout formLayout = new FormLayout();
    private final H1 header = new H1("Simple Word Learning");
    private final TextField englishWord = new TextField();
    private final Button checkSimpleWord = new Button("Check");
    private final Button nextSimpleWord = new Button("Next");
    private final TextField suggestedDanishWord = new TextField();
    private final TextField actualDanishWord = new TextField();
    private final Checkbox isValidDanishWord = new Checkbox();
    private final Notification successNotification = new Notification("Correct");
    private final Notification failNotification = new Notification("Incorrect");

    private final SimpleWordService simpleWordService;
    private SimpleWord requiredSimpleWord;

    public SimpleWordLearningView(SimpleWordService simpleWordService) {
        setHorizontalComponentAlignment(Alignment.CENTER, header, formLayout);
        this.simpleWordService = simpleWordService;
        initializeUiFields();
        prepareUiButtons();
    }

    private void prepareUiButtons() {
        nextSimpleWord.addClickListener(click -> {
            resetUiFields();
            requiredSimpleWord = simpleWordService.getSimpleWordToLearn();
            englishWord.setValue(requiredSimpleWord.getEnglish());
        });
        nextSimpleWord.addClickShortcut(Key.BACKSLASH);

        checkSimpleWord.addClickListener(click -> {
            SimpleWord suggestedSimpleWord = new SimpleWord();
            suggestedSimpleWord.setDanish(suggestedDanishWord.getValue());
            checkSimpleWord(requiredSimpleWord, suggestedSimpleWord);
        });
        checkSimpleWord.addClickShortcut(Key.ENTER);
    }

    private void resetUiFields() {
        englishWord.clear();
        suggestedDanishWord.clear();
        actualDanishWord.clear();
        isValidDanishWord.clear();
        failNotification.close();
        successNotification.close();
        suggestedDanishWord.focus();
    }

    private void initializeUiFields() {
        successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        failNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        englishWord.setReadOnly(true);
        englishWord.setLabel("Required word");
        suggestedDanishWord.setLabel("Suggested danish word");
        actualDanishWord.setLabel("Actual danish word");

        actualDanishWord.setReadOnly(true);
        isValidDanishWord.setReadOnly(true);

        Label emptySpace1 = new Label("");
        emptySpace1.setWidth(null);
        emptySpace1.setHeight("90px");

        Label emptySpace2 = new Label("");
        emptySpace2.setWidth(null);
        emptySpace2.setHeight("90px");

        formLayout.add(
                englishWord, nextSimpleWord,
                suggestedDanishWord,
                emptySpace1,
                checkSimpleWord,
                emptySpace2,
                actualDanishWord,
                isValidDanishWord
        );
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
        formLayout.setColspan(englishWord, 2);
        formLayout.setColspan(nextSimpleWord, 2);
        formLayout.setColspan(emptySpace1, 4);
        formLayout.setColspan(checkSimpleWord, 4);
        formLayout.setColspan(emptySpace2, 4);
        formLayout.setWidth(1250, Unit.PIXELS);
        add(header, formLayout);

        successNotification.setPosition(Notification.Position.MIDDLE);
        successNotification.setDuration(2000);
        failNotification.setPosition(Notification.Position.MIDDLE);
        failNotification.setDuration(2000);

    }

    private void checkSimpleWord(SimpleWord requiredSimpleWord, SimpleWord suggestedSimpleWord) {
        boolean isDanishWordCorrect = StringUtils.equals(requiredSimpleWord.getDanish(), suggestedSimpleWord.getDanish());

        actualDanishWord.setValue(requiredSimpleWord.getDanish());

        if (isDanishWordCorrect) {
            isValidDanishWord.setValue(true);
        }
        if (isDanishWordCorrect) {
            successNotification.open();
        } else {
            failNotification.open();
        }
    }
}