package de.ollie.ucg.gui.swing.edit.model;

import de.ollie.ucg.gui.swing.edit.model.EnumIdentifierEntryListPopupMenu.PopupMenuObserver;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Optional;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import lombok.Generated;

@Generated
class EnumIdentifierTable extends JTable implements MouseListener, PopupMenuObserver {

	private static class EnumIdentifierTableModel extends AbstractTableModel {

		private final List<String> identifiers;

		EnumIdentifierTableModel(List<String> identifiers) {
			this.identifiers = identifiers;
		}

		@Override
		public int getRowCount() {
			return identifiers.size();
		}

		@Override
		public int getColumnCount() {
			return 1;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return identifiers.get(rowIndex);
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			identifiers.remove(row);
			identifiers.add(row, (String) value);
			fireTableCellUpdated(row, col);
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return col == 0;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}
	}

	private final List<String> identifiers;

	private final EnumIdentifierTableModel tableModel;

	EnumIdentifierTable(List<String> identifiers) {
		this.identifiers = identifiers;
		tableModel = new EnumIdentifierTableModel(identifiers);
		setModel(tableModel);
		addMouseListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isRightButton(e)) {
			new EnumIdentifierEntryListPopupMenu(this).show(this, e.getX(), e.getY());
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
				identifiers.remove(selectedRow.intValue());
				tableModel.fireTableDataChanged();
			});
	}

	private Optional<Integer> getSelectedIdentifierIndex() {
		return Optional.ofNullable(getSelectedRow() > -1 ? getSelectedRow() : null);
	}

	@Override
	public void onNew() {
		identifiers.add("NEW_IDENTIFIER");
		tableModel.fireTableDataChanged();
	}

	@Override
	public void onMoveDown() {
		getSelectedIdentifierIndex()
			.ifPresent(selectedRow -> {
				int newRow = selectedRow + 1;
				if (newRow < identifiers.size()) {
					String value = identifiers.remove(selectedRow.intValue());
					identifiers.add(newRow, value);
					tableModel.fireTableDataChanged();
				}
			});
	}

	@Override
	public void onMoveUp() {
		getSelectedIdentifierIndex()
			.ifPresent(selectedRow -> {
				int newRow = selectedRow - 1;
				if (newRow >= 0) {
					String value = identifiers.remove(selectedRow.intValue());
					identifiers.add(newRow, value);
					tableModel.fireTableDataChanged();
				}
			});
	}
}
