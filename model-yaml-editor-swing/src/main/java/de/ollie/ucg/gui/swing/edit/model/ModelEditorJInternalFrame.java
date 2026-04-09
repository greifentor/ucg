package de.ollie.ucg.gui.swing.edit.model;

import static de.ollie.baselib.swing.Constants.HGAP;
import static de.ollie.baselib.swing.Constants.VGAP;

import de.ollie.baselib.swing.Constants;
import de.ollie.ucg.core.model.Model;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import lombok.Generated;

@Generated
public class ModelEditorJInternalFrame extends JInternalFrame implements FocusListener, KeyListener {

	private final Model model;

	private JTextField textFieldTitle;

	public ModelEditorJInternalFrame(Model model, JDesktopPane desktopPane) {
		super("?", true, true, true, true);
		this.model = model;
		desktopPane.add(this);
		setTitle(getFrameTitle());
		setContentPane(createMainPanel());
		setBorder(Constants.createEmptyBorder());
		setBounds(desktopPane.getBounds());
		setVisible(true);
	}

	private String getFrameTitle() {
		return model.getTitle() == null ? "No Title" : model.getTitle();
	}

	private JPanel createMainPanel() {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		p.add(createHeaderDataPanel(), BorderLayout.NORTH);
		p.add(createDetailsTabPanel(), BorderLayout.CENTER);
		p.add(new JLabel("THERE WILL BE BUTTONS!"), BorderLayout.SOUTH);
		return p;
	}

	private JPanel createHeaderDataPanel() {
		JPanel p = new JPanel(new BorderLayout(HGAP, VGAP));
		p.setBorder(Constants.createEmptyBorder());
		textFieldTitle = new JTextField(getTitle());
		textFieldTitle.addFocusListener(this);
		textFieldTitle.addKeyListener(this);
		textFieldTitle.setText(model.getTitle());
		p.add(new ColumnPanel<String>("Title: "), BorderLayout.WEST);
		p.add(new ColumnPanel<JComponent>(textFieldTitle), BorderLayout.CENTER);
		return p;
	}

	private JTabbedPane createDetailsTabPanel() {
		JTabbedPane tp = new JTabbedPane();
		ClassesPanel cp = new ClassesPanel(model).init();
		EnumsPanel ep = new EnumsPanel(model).init();
		tp.add("Classes", cp);
		tp.add("Enums", ep);
		tp.addChangeListener(e -> {
			Component c = ((JTabbedPane) e.getSource()).getSelectedComponent();
			if (c instanceof ClassesPanel classPanel) {
				classPanel.transferContentToModel();
			} else if (c instanceof EnumsPanel enumPanel) {
				enumPanel.transferContentToModel();
			}
		});
		return tp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// NOP
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			setTitle(textFieldTitle.getText());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// NOP
	}

	@Override
	public void focusGained(FocusEvent e) {
		// NOP
	}

	@Override
	public void focusLost(FocusEvent e) {
		setTitle(textFieldTitle.getText());
	}
}
