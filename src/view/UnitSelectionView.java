// File: src/view/UnitSelectionView.java
package view;

import enums.TempUnit;
import model.WeatherSubject;

import java.awt.*;
import java.util.Objects;
import java.util.function.Supplier;
import javax.swing.*;

/**
 * Panel with radio buttons for unit selection.
 * Implements WeatherObserver so it can re-sync its buttons
 * whenever the model's unit changes.
 */
public class UnitSelectionView extends JPanel implements WeatherObserver {
    private final JRadioButton celsiusBtn;
    private final JRadioButton fahrenheitBtn;
    private UnitSelectionListener listener;

    /**
     * Supplier that returns the "current" unit from the model.
     * Must be set by the controller at startup.
     */
    private Supplier<TempUnit> unitSupplier;

    public UnitSelectionView() {
        celsiusBtn = new JRadioButton("Celsius");
        fahrenheitBtn = new JRadioButton("Fahrenheit");
        initComponents();
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Temperature Unit"));
        
        // Use more vertical space for a more prominent display
        setLayout(new GridLayout(2, 1, 0, 10));
        setPreferredSize(new Dimension(180, 100));
        
        // Make radio buttons bigger
        celsiusBtn.setFont(new Font(celsiusBtn.getFont().getName(), Font.PLAIN, 14));
        fahrenheitBtn.setFont(new Font(fahrenheitBtn.getFont().getName(), Font.PLAIN, 14));
        
        // Add padding around radio buttons
        celsiusBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));
        fahrenheitBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

        ButtonGroup grp = new ButtonGroup();
        grp.add(celsiusBtn);
        grp.add(fahrenheitBtn);

        add(celsiusBtn);
        add(fahrenheitBtn);
        
        // Select Celsius by default
        celsiusBtn.setSelected(true);

        // User clicks → notify controller
        celsiusBtn.addActionListener(e -> {
            if (listener != null) listener.onUnitChanged(TempUnit.CELSIUS);
        });
        fahrenheitBtn.addActionListener(e -> {
            if (listener != null) listener.onUnitChanged(TempUnit.FAHRENHEIT);
        });
    }

    /**
     * Called by the model (via WeatherSubject) when its state changes.
     * Pulls the current unit from the supplied getter and refreshes the buttons.
     */
    @Override
    public void update(WeatherSubject subject) {
        if (unitSupplier == null) {
            // No supplier set → nothing to do
            return;
        }
        TempUnit current = Objects.requireNonNull(
            unitSupplier.get(),
            "UnitSupplier must not return null"
        );
        setUnit(current);
    }

    /**
     * Programmatically set the selected radio button.
     */
    public void setUnit(TempUnit unit) {
        if (unit == TempUnit.CELSIUS) {
            celsiusBtn.setSelected(true);
        } else {
            fahrenheitBtn.setSelected(true);
        }
    }

    /** Register the listener for user‐driven changes. */
    public void addUnitChangeListener(UnitSelectionListener l) {
        this.listener = l;
    }

    /**
     * Register the supplier that provides the current model unit.
     * The controller should call this once, passing in: () -> model.getUnit()
     */
    public void setUnitSupplier(Supplier<TempUnit> unitSupplier) {
        this.unitSupplier = unitSupplier;
    }
}
