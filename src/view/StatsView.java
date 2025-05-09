package view;

import enums.TempUnit;
import enums.WeatherStatKey;
import exception.WeatherStatsNotValidException;
import listener.StatsListener;
import model.WeatherStats;
import model.WeatherSubject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class StatsView extends JPanel implements WeatherObserver {
    private final EnumMap<WeatherStatKey, JLabel> statLabels = 
        new EnumMap<>(WeatherStatKey.class);
    private StatsListener listener;

    public StatsView() {
        initComponents();
    }

    private void initComponents() {
        // Titled border
        TitledBorder border = BorderFactory.createTitledBorder("Statistics");
        border.setTitleFont(border.getTitleFont().deriveFont(Font.BOLD, 12f));
        setBorder(BorderFactory.createCompoundBorder(border, new EmptyBorder(5,5,5,5)));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // One loop for all enum keys
        Font labelFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        for (WeatherStatKey key : WeatherStatKey.values()) {
            String title = key.toString(); // or key.name()
            JLabel lbl = createCompactLabel(labelFont, title + ": N/A");
            lbl.setAlignmentX(LEFT_ALIGNMENT);

            statLabels.put(key, lbl);
            add(lbl);
        }
    }

    private JLabel createCompactLabel(Font font, String text) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        return label;
    }

    public void addStatsListener(StatsListener l) {
        this.listener = l;
    }

    public void displayStats(WeatherStats stats, TempUnit unit) {
        try {
            Map<WeatherStatKey, String> all = stats.getAllStats();
            for (Map.Entry<WeatherStatKey, String> e : all.entrySet()) {
                JLabel lbl = statLabels.get(e.getKey());
                if (lbl != null) {
                    lbl.setText(e.getKey().toString() + ": " + e.getValue());
                }
            }
        } catch (WeatherStatsNotValidException ex) {
            // on invalid, just reset all to N/A
            statLabels.values().forEach(l -> l.setText(l.getText().split(":")[0] + ": N/A"));
        }
    }

    @Override
    public void update(WeatherSubject subject) {
        if (listener != null) listener.onStatsRequested();
    }
}
