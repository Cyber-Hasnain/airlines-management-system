package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIStyle {
    public static final Color BG_DARK = new Color(15, 23, 42);       // #0F172A
    public static final Color PANEL_DARK = new Color(30, 41, 59);    // #1E293B
    public static final Color ACCENT = new Color(16, 185, 129);      // #10B981 (Emerald)
    public static final Color ACCENT_HOVER = new Color(5, 150, 105);
    public static final Color BUTTON_PRIMARY = new Color(59, 130, 246); // #3B82F6 (Blue)
    public static final Color BUTTON_PRIMARY_HOVER = new Color(29, 78, 216);
    public static final Color TEXT_LIGHT = new Color(248, 250, 252); // #F8FAFC
    public static final Color TEXT_MUTED = new Color(148, 163, 184); // #94A3B8
    public static final Color DANGER = new Color(239, 68, 68);       // #EF4444 (Red)
    public static final Color DANGER_HOVER = new Color(185, 28, 28);

    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_HEADER = new Font("Segoe UI", Font.BOLD, 16);
    public static final Font FONT_BODY = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONT_SMALL = new Font("Segoe UI", Font.PLAIN, 12);

    public static JButton createStyledButton(String text, Color baseColor, Color hoverColor) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_HEADER);
        btn.setForeground(TEXT_LIGHT);
        btn.setBackground(baseColor);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(baseColor);
            }
        });
        return btn;
    }

    public static void styleTextField(JTextField field) {
        field.setBackground(new Color(51, 65, 85)); // #334155
        field.setForeground(TEXT_LIGHT);
        field.setCaretColor(TEXT_LIGHT);
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(71, 85, 105), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
    }

    public static void styleTable(JTable table) {
        table.setBackground(PANEL_DARK);
        table.setForeground(TEXT_LIGHT);
        table.setGridColor(new Color(51, 65, 85));
        table.setFont(FONT_BODY);
        table.setRowHeight(25);
        table.setSelectionBackground(new Color(51, 65, 85));
        table.setSelectionForeground(TEXT_LIGHT);

        // Style the Table Header using custom renderer to bypass native OS overrides
        javax.swing.table.JTableHeader header = table.getTableHeader();
        header.setFont(FONT_HEADER);
        header.setDefaultRenderer(new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                label.setBackground(BG_DARK);
                label.setForeground(TEXT_LIGHT);
                label.setFont(FONT_HEADER);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(51, 65, 85)),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)
                ));
                label.setOpaque(true);
                return label;
            }
        });
    }

    public static void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }
}
