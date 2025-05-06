// File: src/view/UnitSelectionView.java
package view;

import controller.WeatherController;
import model.UnitType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel with two radio buttons to let user toggle between Celsius and Fahrenheit.
 * Not an observer—only pushes changes to the controller and reflects
 * the model’s current unit on startup.
 */
public class UnitSelectionView extends JPanel {
    private final WeatherController controller;
    private JRadioButton celsiusButton;
    private JRadioButton fahrenheitButton;

    public UnitSelectionView(WeatherController controller) {
        this.controller = controller;
        initComponents();
        // initialize selection from model
        UnitType current = controller.getModel().getUnit();
        if (current == UnitType.CELSIUS) {
            celsiusButton.setSelected(true);
        } else {
            fahrenheitButton.setSelected(true);
        }
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Temperature Unit"));
        setLayout(new GridLayout(2,1));
        celsiusButton = new JRadioButton("Celsius");
        fahrenheitButton = new JRadioButton("Fahrenheit");

        ButtonGroup group = new ButtonGroup();
        group.add(celsiusButton);
        group.add(fahrenheitButton);

        add(celsiusButton);
        add(fahrenheitButton);

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UnitType selected =
                    celsiusButton.isSelected()
                        ? UnitType.CELSIUS
                        : UnitType.FAHRENHEIT;
                controller.onUnitChanged(selected);
            }
        };
        celsiusButton.addActionListener(al);
        fahrenheitButton.addActionListener(al);
    }
}
