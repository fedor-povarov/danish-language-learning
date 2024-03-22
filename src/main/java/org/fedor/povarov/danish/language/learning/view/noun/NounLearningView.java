package org.fedor.povarov.danish.language.learning.view.noun;

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
import org.fedor.povarov.danish.language.learning.entity.Noun;
import org.fedor.povarov.danish.language.learning.service.NounService;
import org.fedor.povarov.danish.language.learning.view.MainLayout;

@Route(value = "noun/learning", layout = MainLayout.class)
@PageTitle("Noun Learning")
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class NounLearningView extends VerticalLayout {

    private final FormLayout formLayout = new FormLayout();
    private final H1 header = new H1("Noun Learning");
    private final TextField englishWord = new TextField();
    private final Button checkNoun = new Button("Check");
    private final Button nextNoun = new Button("Next");
    private final TextField suggestedSingularIndefinite = new TextField();
    private final TextField suggestedSingularDefinite = new TextField();
    private final TextField suggestedPluralIndefinite = new TextField();
    private final TextField suggestedPluralDefinite = new TextField();
    private final TextField actualSingularIndefinite = new TextField();
    private final TextField actualSingularDefinite = new TextField();
    private final TextField actualPluralIndefinite = new TextField();
    private final TextField actualPluralDefinite = new TextField();
    private final Checkbox isValidSingularIndefinite = new Checkbox();
    private final Checkbox isValidSingularDefinite = new Checkbox();
    private final Checkbox isValidPluralIndefinite = new Checkbox();
    private final Checkbox isValidPluralDefinite = new Checkbox();
    private final Notification successNotification = new Notification("Correct");
    private final Notification failNotification = new Notification("Incorrect");

    private final NounService nounService;
    private Noun requiredNoun;

    public NounLearningView(NounService nounService) {
        setHorizontalComponentAlignment(Alignment.CENTER, header, formLayout);
        this.nounService = nounService;
        initializeUiFields();
        prepareUiButtons();
    }

    private void prepareUiButtons() {
        nextNoun.addClickListener(click -> {
            resetUiFields();
            requiredNoun = nounService.getNounToLearn();
            englishWord.setValue(requiredNoun.getEnglish());
        });
        nextNoun.addClickShortcut(Key.BACKSLASH);

        checkNoun.addClickListener(click -> {
            Noun suggestedNoun = new Noun();
            suggestedNoun.setSingularIndefinite(suggestedSingularIndefinite.getValue());
            suggestedNoun.setSingularDefinite(suggestedSingularDefinite.getValue());
            suggestedNoun.setPluralIndefinite(suggestedPluralIndefinite.getValue());
            suggestedNoun.setPluralDefinite(suggestedPluralDefinite.getValue());
            checkNoun(requiredNoun, suggestedNoun);
        });
        checkNoun.addClickShortcut(Key.ENTER);
    }

    private void resetUiFields() {
        englishWord.clear();

        suggestedSingularIndefinite.clear();
        suggestedSingularDefinite.clear();
        suggestedPluralIndefinite.clear();
        suggestedPluralDefinite.clear();

        actualSingularIndefinite.clear();
        actualSingularDefinite.clear();
        actualPluralIndefinite.clear();
        actualPluralDefinite.clear();

        isValidSingularIndefinite.clear();
        isValidSingularDefinite.clear();
        isValidPluralIndefinite.clear();
        isValidPluralDefinite.clear();

        failNotification.close();
        successNotification.close();

        suggestedSingularIndefinite.focus();
    }

    private void initializeUiFields() {
        successNotification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        failNotification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        englishWord.setReadOnly(true);
        englishWord.setLabel("Required word");

        suggestedSingularIndefinite.setLabel("Suggested singular indefinite");
        suggestedSingularDefinite.setLabel("Suggested singular definite");
        suggestedPluralIndefinite.setLabel("Suggested plural indefinite");
        suggestedPluralDefinite.setLabel("Suggested plural definite");

        actualSingularIndefinite.setLabel("Actual singular indefinite");
        actualSingularDefinite.setLabel("Actual singular definite");
        actualPluralIndefinite.setLabel("Actual plural indefinite");
        actualPluralDefinite.setLabel("Actual plural definite");

        actualSingularIndefinite.setReadOnly(true);
        actualSingularDefinite.setReadOnly(true);
        actualPluralIndefinite.setReadOnly(true);
        actualPluralDefinite.setReadOnly(true);

        isValidSingularIndefinite.setReadOnly(true);
        isValidSingularDefinite.setReadOnly(true);
        isValidPluralIndefinite.setReadOnly(true);
        isValidPluralDefinite.setReadOnly(true);

        Label emptySpace1 = new Label("");
        emptySpace1.setWidth(null);
        emptySpace1.setHeight("90px");

        Label emptySpace2 = new Label("");
        emptySpace2.setWidth(null);
        emptySpace2.setHeight("90px");

        formLayout.add(
                englishWord, nextNoun,
                suggestedSingularIndefinite, suggestedSingularDefinite, suggestedPluralIndefinite, suggestedPluralDefinite,
                emptySpace1,
                checkNoun,
                emptySpace2,
                actualSingularIndefinite, actualSingularDefinite, actualPluralIndefinite, actualPluralDefinite,
                isValidSingularIndefinite, isValidSingularDefinite, isValidPluralIndefinite, isValidPluralDefinite);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 4));
        formLayout.setColspan(englishWord, 2);
        formLayout.setColspan(nextNoun, 2);
        formLayout.setColspan(emptySpace1, 4);
        formLayout.setColspan(checkNoun, 4);
        formLayout.setColspan(emptySpace2, 4);
        formLayout.setWidth(1250, Unit.PIXELS);
        add(header, formLayout);

        successNotification.setPosition(Notification.Position.MIDDLE);
        successNotification.setDuration(2000);
        failNotification.setPosition(Notification.Position.MIDDLE);
        failNotification.setDuration(2000);
    }

    private void checkNoun(Noun requiredNoun, Noun suggestedNoun) {
        boolean isSingularIndefiniteCorrect = StringUtils.equals(requiredNoun.getSingularIndefinite(), suggestedNoun.getSingularIndefinite());
        boolean isSingularDefiniteCorrect = StringUtils.equals(requiredNoun.getSingularDefinite(), suggestedNoun.getSingularDefinite());
        boolean isPluralIndefiniteCorrect = StringUtils.equals(requiredNoun.getPluralIndefinite(), suggestedNoun.getPluralIndefinite());
        boolean isPluralDefiniteCorrect = StringUtils.equals(requiredNoun.getPluralDefinite(), suggestedNoun.getPluralDefinite());

        actualSingularIndefinite.setValue(requiredNoun.getSingularIndefinite());
        actualSingularDefinite.setValue(requiredNoun.getSingularDefinite());
        actualPluralIndefinite.setValue(requiredNoun.getPluralIndefinite());
        actualPluralDefinite.setValue(requiredNoun.getPluralDefinite());

        if (isSingularIndefiniteCorrect) {
            isValidSingularIndefinite.setValue(true);
        }
        if (isSingularDefiniteCorrect) {
            isValidSingularDefinite.setValue(true);
        }
        if (isPluralIndefiniteCorrect) {
            isValidPluralIndefinite.setValue(true);
        }
        if (isPluralDefiniteCorrect) {
            isValidPluralDefinite.setValue(true);
        }
        if (isSingularIndefiniteCorrect && isSingularDefiniteCorrect && isPluralIndefiniteCorrect && isPluralDefiniteCorrect) {
            successNotification.open();
            nounService.saveNounSuggestion(requiredNoun, true);
        } else {
            failNotification.open();
            nounService.saveNounSuggestion(requiredNoun, false);
        }
    }
}