package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.gui.swing.edit.model.PropertiesTablePopupMenu.PopupMenuObserver;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import lombok.Generated;

@Generated
class PropertiesTable extends JTable implements MouseListener, PopupMenuObserver {

	static final String NEW_PROPERTY_NAME = "NEW_PROPERTY";

	private static class PropertiesIdentifierTableModel extends AbstractTableModel {

		private final List<Property> properties;

		PropertiesIdentifierTableModel(List<Property> properties) {
			this.properties = new ArrayList<>(properties);
		}

		@Override
		public String getColumnName(int col) {
			if (col == 0) {
				return "Name";
			} else if (col == 1) {
				return "Value";
			}
			return "-";
		}

		@Override
		public int getRowCount() {
			return properties.size();
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public Object getValueAt(int row, int col) {
			Property property = properties.get(row);
			if (col == 0) {
				return property.getName();
			} else if (col == 1) {
				return property.getValue();
			}
			return "-";
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			Property property = properties.get(row);
			if (col == 0) {
				property.setName((String) value);
			} else if (col == 1) {
				property.setValue((String) value);
			}
			fireTableCellUpdated(row, col);
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return true;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}

		public void addElement(Property property) {
			properties.add(property);
			fireTableDataChanged();
		}

		public void removeByIndex(int index) {
			properties.remove(index);
			fireTableDataChanged();
		}

		public List<Property> getElements() {
			return properties;
		}
	}

	private final PropertiesIdentifierTableModel tableModel;

	PropertiesTable(List<Property> properties) {
		tableModel = new PropertiesIdentifierTableModel(properties);
		setModel(tableModel);
		addMouseListener(this);
		getTableHeader().addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isRightButton(e)) {
			new PropertiesTablePopupMenu(this).show(this, e.getX(), e.getY());
		}
	}

	private boolean isRightButton(MouseEvent e) {
		return e.getButton() == MouseEvent.BUTTON3;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// NOP
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// NOP
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// NOP
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// NOP
	}

	public List<Property> getElements() {
		return tableModel.getElements();
	}

	@Override
	public void onDelete() {
		getSelectedIdentifierIndex().ifPresent(selectedRow -> tableModel.removeByIndex(selectedRow.intValue()));
	}

	private Optional<Integer> getSelectedIdentifierIndex() {
		return Optional.ofNullable(getSelectedRow() > -1 ? getSelectedRow() : null);
	}

	@Override
	public void onNew() {
		tableModel.addElement(new Property().setName(NEW_PROPERTY_NAME).setValue("NEW_VALUE"));
	}
}
