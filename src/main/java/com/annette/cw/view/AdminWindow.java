package com.annette.cw.view;

import com.annette.cw.view.utility.AdminWindowFunction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminWindow {
    private static final JPanel grid = new JPanel();

    public static void createAdminWindow(Window window) {
        grid.setBackground(new Color(120, 110, 255));
        createUI();
        window.add(grid);
        window.repaint();
        window.setVisible(true);
    }

    private static void addButton(String caption) {
        JButton button = new JButton(caption);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        AdminWindow.grid.add(button);
        AdminWindow.grid.add (Box.createRigidArea(new Dimension(0,25)));
    }

    private static void createUI() {
        grid.setBorder(BorderFactory.createTitledBorder("Меню администратора"));
        AdminWindow.grid.setLayout(new BoxLayout(AdminWindow.grid, BoxLayout.Y_AXIS));
        AdminWindow.grid.add (Box.createRigidArea(new Dimension(0,50)));
        addButton("Зарегистрировать пользователя");
        addButton("Изменить данные пользователя ");
        addButton("    Сортировать пользователей    ");
        addButton("              Принять решение              ");
        addButton("Мороженные продукты");
    }
}
