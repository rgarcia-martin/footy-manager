package vaadin;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class FactoryEditJug implements FormFieldFactory {
	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Field createField(Item item, Object propertyId,
                             Component uiContext) {
        // Identify the fields by their Property ID.
        String pid = (String) propertyId;

        if ("name".equals(pid)) {
            return new TextField("Nombre");
        } else if ("number".equals(pid)) {
            return new TextField("Numero del Jugador");
        } else if ("actualTraining".equals(pid)) {        	
            Select select = new Select("Entrenamiento Actual");
            select.addItem("ABL");
            select.addItem("FRM");
            select.addItem("FIT");
            select.addItem("RST");
            select.addItem("CB");
            select.addItem("SB");
            select.addItem("CM");
            select.addItem("SM");
            select.addItem("CF");
            select.setNewItemsAllowed(true);
            return select;
        } 
        
        return null; // Invalid field (property) name.
    }
	
}
