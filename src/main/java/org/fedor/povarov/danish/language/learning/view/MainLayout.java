package org.fedor.povarov.danish.language.learning.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.fedor.povarov.danish.language.learning.view.noun.NounLearningView;
import org.fedor.povarov.danish.language.learning.view.noun.NounListView;
import org.fedor.povarov.danish.language.learning.view.simple.word.SimpleWordLearningView;
import org.fedor.povarov.danish.language.learning.view.simple.word.SimpleWordListView;
import org.fedor.povarov.danish.language.learning.view.verb.VerbLearningView;
import org.fedor.povarov.danish.language.learning.view.verb.VerbListView;

@Route("/")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PWA(name = "Danish Language Learning", shortName = "Danish Language Learning", description = "This is a Danish Language Learning application.", enableInstallPrompt = false)
@CssImport("./styles/shared-styles.css")
@CssImport(value = "./styles/vaadin-text-field-styles.css", themeFor = "vaadin-text-field")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Danish Language Learning");
        logo.addClassNames("text-l", "m-m");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink simpleWords = new RouterLink("Simple Words", SimpleWordListView.class);
        simpleWords.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink simpleWordLearning = new RouterLink("Simple Word Learning", SimpleWordLearningView.class);
        simpleWordLearning.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink nouns = new RouterLink("Nouns", NounListView.class);
        nouns.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink nounLearning = new RouterLink("Noun Learning", NounLearningView.class);
        nounLearning.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink verbs = new RouterLink("Verbs", VerbListView.class);
        verbs.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink verbLearning = new RouterLink("Verb Learning", VerbLearningView.class);
        verbLearning.setHighlightCondition(HighlightConditions.sameLocation());

        VerticalLayout verticalLayout = new VerticalLayout(
                simpleWords, simpleWordLearning, new Hr(),
                nouns, nounLearning, new Hr(),
                verbs, verbLearning
        );
        addToDrawer(verticalLayout);
    }

}
