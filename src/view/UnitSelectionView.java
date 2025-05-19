package view;

import enums.TempUnit;
import listener.UnitSelectionListener;
import model.WeatherSubject;

import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class UnitSelectionView extends JPanel implements WeatherObserver {
    // map one button per unit
    private final Map<TempUnit, JRadioButton> buttons =
        new EnumMap<>(TempUnit.class);
    private UnitSelectionListener listener;
    private Supplier<TempUnit> unitSupplier;

    public UnitSelectionView() {
        initComponents();
    }

    private void initComponents() {
        setBorder(BorderFactory.createTitledBorder("Temperature Unit"));
        setLayout(new GridLayout(TempUnit.values().length, 1, 0, 10));
        setPreferredSize(new Dimension(180, 40 * TempUnit.values().length));

        Font btnFont = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
        ButtonGroup group = new ButtonGroup();

        // for each enum constant, create a button
        for (TempUnit unit : TempUnit.values()) {
            // display either the symbol or name
            String label = unit.toString();
            JRadioButton btn = new JRadioButton(label);
            btn.setFont(btnFont);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 5));

            // when clicked, notify controller with the correct unit
            btn.addActionListener(e -> {
                if (listener != null) {
                    listener.onUnitChanged(unit);
                }
            });

            group.add(btn);
            buttons.put(unit, btn);
            add(btn);
        }

        // pick a sensible default
        buttons.get(TempUnit.CELSIUS).setSelected(true);
    }

    @Override
    public void update(WeatherSubject subject) {
        if (unitSupplier == null) return;
        TempUnit current = unitSupplier.get();
        setUnit(current);
    }

    public void setUnit(TempUnit unit) {
        JRadioButton btn = buttons.get(unit);
        if (btn != null) {
            btn.setSelected(true);
        }
    }

    public void addUnitChangeListener(UnitSelectionListener l) {
        this.listener = l;
    }

    public void setUnitSupplier(Supplier<TempUnit> unitSupplier) {
        this.unitSupplier = unitSupplier;
    }
}
