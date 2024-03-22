package org.fedor.povarov.danish.language.learning.view.verb;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.fedor.povarov.danish.language.learning.entity.Verb;
import org.fedor.povarov.danish.language.learning.service.VerbService;
import org.fedor.povarov.danish.language.learning.view.MainLayout;

@Route(value = "verb/list", layout = MainLayout.class)
@PageTitle("Verbs")
public class VerbListView extends VerticalLayout {
    private final Grid<Verb> grid = new Grid<>(Verb.class);
    private final TextField filterText = new TextField();
    private VerbEditForm form;

    private final VerbService verbService;

    public VerbListView(VerbService verbService) {
        this.verbService = verbService;
        add(new H1("Verbs"));
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void configureForm() {
        form = new VerbEditForm();
        form.setWidth("25em");
        form.addListener(VerbEditForm.SaveEvent.class, this::saveVerb);
        form.addListener(VerbEditForm.DeleteEvent.class, this::deleteVerb);
        form.addListener(VerbEditForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveVerb(VerbEditForm.SaveEvent event) {
        verbService.save(event.getVerb());
        updateList();
        closeEditor();
    }

    private void deleteVerb(VerbEditForm.DeleteEvent event) {
        verbService.delete(event.getVerb());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("english", "danish");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editVerb(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addVerbButton = new Button("Add verb");
        addVerbButton.addClickListener(click -> addVerb());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addVerbButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    public void editVerb(Verb verb) {
        if (verb == null) {
            closeEditor();
        } else {
            form.setVerb(verb);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setVerb(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addVerb() {
        grid.asSingleSelect().clear();
        editVerb(new Verb());
    }

    private void updateList() {
        grid.setItems(verbService.findByEnglishContains(filterText.getValue()));
    }

}
