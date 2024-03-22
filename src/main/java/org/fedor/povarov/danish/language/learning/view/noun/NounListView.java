package org.fedor.povarov.danish.language.learning.view.noun;

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
import org.fedor.povarov.danish.language.learning.entity.Noun;
import org.fedor.povarov.danish.language.learning.service.NounService;
import org.fedor.povarov.danish.language.learning.view.MainLayout;

@Route(value = "noun/list", layout = MainLayout.class)
@PageTitle("Nouns")
public class NounListView extends VerticalLayout {
    private final Grid<Noun> grid = new Grid<>(Noun.class);
    private final TextField filterText = new TextField();
    private final NounService nounService;
    private NounEditForm form;

    public NounListView(NounService nounService) {
        this.nounService = nounService;
        add(new H1("Nouns"));
        setSizeFull();
        configureGrid();
        configureForm();
        add(getToolbar(), getContent());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("english",
                "gender",
                "singularIndefinite",
                "singularDefinite",
                "pluralIndefinite",
                "pluralDefinite",
                "isIrregular");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editNoun(event.getValue()));
    }

    private void configureForm() {
        form = new NounEditForm();
        form.setWidth("25em");
        form.addListener(NounEditForm.SaveEvent.class, this::saveNoun);
        form.addListener(NounEditForm.DeleteEvent.class, this::deleteNoun);
        form.addListener(NounEditForm.CloseEvent.class, e -> closeEditor());
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addNounButton = new Button("Add noun");
        addNounButton.addClickListener(click -> addNoun());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addNounButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void saveNoun(NounEditForm.SaveEvent event) {
        nounService.save(event.getNoun());
        updateList();
        closeEditor();
    }

    public void editNoun(Noun noun) {
        if (noun == null) {
            closeEditor();
        } else {
            form.setNoun(noun);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void deleteNoun(NounEditForm.DeleteEvent event) {
        nounService.delete(event.getNoun());
        updateList();
        closeEditor();
    }

    private void closeEditor() {
        form.setNoun(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addNoun() {
        grid.asSingleSelect().clear();
        editNoun(new Noun());
    }

    private void updateList() {
        grid.setItems(nounService.findByEnglishContains(filterText.getValue()));
    }

}
