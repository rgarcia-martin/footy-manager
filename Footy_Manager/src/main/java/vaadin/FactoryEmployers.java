package vaadin;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Select;

public class FactoryEmployers implements FormFieldFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		String pid = (String) propertyId;
		Field res = null;
		
		if(pid.equals("hired")){
			Select sel = new Select("Contratar");
			sel.addItem("Si");
			sel.addItem("No");
			res = sel;
		}
		
		return res;
	}

}
