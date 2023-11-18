package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.service.crud.CategoryCrudService;
import cz.muni.fi.pv168.project.business.service.crud.UnitService;
import cz.muni.fi.pv168.project.business.service.crud.IngredientCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeCrudService;
import cz.muni.fi.pv168.project.business.service.export.GenericImportService;
import cz.muni.fi.pv168.project.business.service.export.importer.BatchXmlImporter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;

public class ImportAction extends AbstractAction {

    protected final CategoryCrudService categoryCrudService;
    protected final UnitService unitService;
    protected final IngredientCrudService ingredientCrudService;
    protected final RecipeCrudService recipeCrudService;
    private final Runnable callback;

    public ImportAction(
            String name,
            CategoryCrudService categoryCrudService,
            UnitService unitService,
            IngredientCrudService ingredientCrudService,
            RecipeCrudService recipeCrudService,
            Runnable callback
    ) {
        super(name);
        this.categoryCrudService = categoryCrudService;
        this.unitService = unitService;
        this.ingredientCrudService = ingredientCrudService;
        this.recipeCrudService = recipeCrudService;
        this.callback = callback;
        putValue(SHORT_DESCRIPTION, "Imports from an XML file");
        putValue(MNEMONIC_KEY, KeyEvent.VK_I);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl I"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("xml file", "xml"));
        fileChooser.getChoosableFileFilters();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            BatchXmlImporter xmlImporter = new BatchXmlImporter();
            GenericImportService genericImportService = new GenericImportService(
                    recipeCrudService,
                    ingredientCrudService,
                    unitService,
                    categoryCrudService,
                    List.of(xmlImporter)
            );
            genericImportService.importData(selectedFile.getAbsolutePath());
            callback.run();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());

            JOptionPane.showMessageDialog(null, "Import has successfully finished.");
        }
    }
}
