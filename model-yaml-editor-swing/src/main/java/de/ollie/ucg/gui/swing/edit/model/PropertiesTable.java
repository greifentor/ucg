package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.core.model.Property;
import de.ollie.ucg.gui.swing.edit.model.PropertiesTablePopupMenu.PopupMenuObserver;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import lombok.Generated;

@Generated
class PropertiesTable extends JTable implements MouseListener, PopupMenuObserver {

	static final String NEW_PROPERTY_NAME = "NEW_NAME";

	private static class PropertiesIdentifierTableModel extends AbstractTableModel {

		private final List<Property> properties;

		PropertiesIdentifierTableModel(List<Property> properties) {
			this.properties = properties;
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
			return 1;
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
	}

	private final List<Property> properties;

	private final PropertiesIdentifierTableModel tableModel;

	PropertiesTable(List<Property> properties) {
		this.properties = properties;
		tableModel = new PropertiesIdentifierTableModel(properties);
		setModel(tableModel);
		addMouseListener(this);
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

	@Override
	public void onDelete() {
		getSelectedIdentifierIndex()
			.ifPresent(selectedRow -> {
				properties.remove(selectedRow.intValue());
				tableModel.fireTableDataChanged();
			});
	}

	private Optional<Integer> getSelectedIdentifierIndex() {
		return Optional.ofNullable(getSelectedRow() > -1 ? getSelectedRow() : null);
	}

	@Override
	public void onNew() {
		properties.add(new Property().setName(NEW_PROPERTY_NAME).setValue("NEW_VALUE"));
		tableModel.fireTableDataChanged();
	}
}
