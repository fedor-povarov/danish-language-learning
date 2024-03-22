package org.fedor.povarov.danish.language.learning.view.simple.word;

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
import org.fedor.povarov.danish.language.learning.entity.SimpleWord;
import org.fedor.povarov.danish.language.learning.service.SimpleWordService;
import org.fedor.povarov.danish.language.learning.view.MainLayout;

@Route(value = "simple-word/list", layout = MainLayout.class)
@PageTitle("Simple Words")
public class SimpleWordListView extends VerticalLayout {
    private final Grid<SimpleWord> grid = new Grid<>(SimpleWord.class);
    private final TextField filterText = new TextField();
    private SimpleWordEditForm form;

    private final SimpleWordService simpleWordService;

    public SimpleWordListView(SimpleWordService simpleWordService) {
        this.simpleWordService = simpleWordService;
        add(new H1("Simple Words"));
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
        form = new SimpleWordEditForm();
        form.setWidth("25em");
        form.addListener(SimpleWordEditForm.SaveEvent.class, this::saveSimpleWord);
        form.addListener(SimpleWordEditForm.DeleteEvent.class, this::deleteSimpleWord);
        form.addListener(SimpleWordEditForm.CloseEvent.class, e -> closeEditor());
    }

    private void saveSimpleWord(SimpleWordEditForm.SaveEvent event) {
        simpleWordService.save(event.getSimpleWord());
        updateList();
        closeEditor();
    }

    private void deleteSimpleWord(SimpleWordEditForm.DeleteEvent event) {
        simpleWordService.delete(event.getSimpleWord());
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("english", "danish");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editSimpleWord(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addSimpleWordButton = new Button("Add simple word");
        addSimpleWordButton.addClickListener(click -> addSimpleWord());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addSimpleWordButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    public void editSimpleWord(SimpleWord simpleWord) {
        if (simpleWord == null) {
            closeEditor();
        } else {
            form.setSimpleWord(simpleWord);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setSimpleWord(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void addSimpleWord() {
        grid.asSingleSelect().clear();
        editSimpleWord(new SimpleWord());
    }

    private void updateList() {
        grid.setItems(simpleWordService.findByEnglishContains(filterText.getValue()));
    }

}
