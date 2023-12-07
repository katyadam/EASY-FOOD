package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.*;
import cz.muni.fi.pv168.project.business.service.export.batch.Batch;
import cz.muni.fi.pv168.project.business.service.export.batch.BatchXmlExporter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

public class ExportAction extends AbstractAction {
    protected final CrudService<Category> categoryCrudService;
    protected final CrudService<Unit> unitService;
    protected final CrudService<Ingredient> ingredientCrudService;
    protected final CrudService<Recipe> recipeCrudService;

    public ExportAction(
            String name,
            CrudService<Category> categoryCrudService,
            CrudService<Unit> unitService,
            CrudService<Ingredient> ingredientCrudService,
            CrudService<Recipe> recipeCrudService
    ) {
        super(name);
        this.categoryCrudService = categoryCrudService;
        this.unitService = unitService;
        this.ingredientCrudService = ingredientCrudService;
        this.recipeCrudService = recipeCrudService;
        putValue(SHORT_DESCRIPTION, "Exports your data to an XML file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_X);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl X"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("xml file", "xml"));
        fileChooser.getChoosableFileFilters();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        Batch batch = new Batch(this.recipeCrudService.findAll(),
                this.ingredientCrudService.findAll(),
                this.unitService.findAll(),
                this.categoryCrudService.findAll());

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            BatchXmlExporter exportBatch = new BatchXmlExporter();
            exportBatch.exportBatch(batch, selectedFile.getAbsolutePath() + ".xml");
            System.out.println("Selected file: " + selectedFile.getAbsolutePath() + ".xml");

            JOptionPane.showMessageDialog(null, "Export has successfully finished.");
        }

    }
}
