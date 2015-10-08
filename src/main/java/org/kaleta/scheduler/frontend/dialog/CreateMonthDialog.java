package org.kaleta.scheduler.frontend.dialog;

import org.kaleta.scheduler.backend.entity.Month;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormatSymbols;
import java.util.*;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 24.08.2015.
 */
public class CreateMonthDialog extends JDialog {
    private boolean result;

    private JTextField textFieldName;
    private JSpinner spinnerNumberOfDays;
    private JSpinner spinnerMonthStartsIn;
    private JButton buttonImportMonth;
    private JPanel panelImportMonth;
    private List<JCheckBox> publicHolidayCheckBoxList;
    private JCheckBox checkBoxSelectAfter;

    public CreateMonthDialog(){
        result = false;
        this.setTitle("Create Month");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        this.setModal(true);
        this.pack();
    }

    private void initComponents() {
        JLabel labelName = new JLabel("Month Name: ");
        textFieldName = new JTextField();

        JLabel labelNumberOfDays = new JLabel("Number of Days: ");
        spinnerNumberOfDays = new JSpinner(
                new SpinnerListModel(new String[]{"28", "29", "30", "31"}));
        ((JSpinner.DefaultEditor) spinnerNumberOfDays.getEditor()).getTextField().setEditable(false);
        spinnerNumberOfDays.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                computeMonthAndWeekends();
            }
        });

        JLabel labelMonthStartsIn = new JLabel("Month starts in: ");
        spinnerMonthStartsIn = new JSpinner(
                new SpinnerListModel(new String[]{"Monday", "Tuesday",
                        "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}));
        ((JSpinner.DefaultEditor) spinnerMonthStartsIn.getEditor()).getTextField().setEditable(false);
        spinnerMonthStartsIn.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                computeMonthAndWeekends();
            }
        });

        buttonImportMonth = new JButton("Import Month Definition");
        buttonImportMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buttonImportMonth.setEnabled(false);
                panelImportMonth.setVisible(true);
                CreateMonthDialog.this.pack();
            }
        });
        panelImportMonth = new JPanel();
        panelImportMonth.setVisible(false);
        panelImportMonth.setLayout(new BoxLayout(panelImportMonth, BoxLayout.Y_AXIS));

        String[] defs = getPreviousMonthDefinition();
        JLabel previousMonthDef = new JLabel("previous: " + defs[0] + " " + defs[1], JLabel.CENTER);
        JPanel bgForPrevious = new JPanel();
        bgForPrevious.add(previousMonthDef);
        bgForPrevious.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] defs = getPreviousMonthDefinition();
                textFieldName.setText(defs[0] + " " + defs[1]);

                spinnerNumberOfDays.setValue(defs[2]);
                spinnerMonthStartsIn.setValue(defs[3]);

                buttonImportMonth.setEnabled(true);
                panelImportMonth.setVisible(false);
                CreateMonthDialog.this.pack();
            }
        });
        bgForPrevious.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelImportMonth.add(bgForPrevious);

        defs = getActualMonthDefinition();
        JLabel actualMonthDef = new JLabel("actual: " + defs[0] + " " + defs[1], JLabel.CENTER);
        JPanel bgForActual = new JPanel();
        bgForActual.add(actualMonthDef);
        bgForActual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] defs = getActualMonthDefinition();
                textFieldName.setText(defs[0] + " " + defs[1]);

                spinnerNumberOfDays.setValue(defs[2]);
                spinnerMonthStartsIn.setValue(defs[3]);

                buttonImportMonth.setEnabled(true);
                panelImportMonth.setVisible(false);
                CreateMonthDialog.this.pack();
            }
        });
        bgForActual.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelImportMonth.add(bgForActual);

        defs = getNextMonthDefinition();
        JLabel nextMonthDef = new JLabel("next: " + defs[0] + " " + defs[1], JLabel.CENTER);
        JPanel bgForNext = new JPanel();
        bgForNext.add(nextMonthDef);
        bgForNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String[] defs = getNextMonthDefinition();
                textFieldName.setText(defs[0] + " " + defs[1]);

                spinnerNumberOfDays.setValue(defs[2]);
                spinnerMonthStartsIn.setValue(defs[3]);

                buttonImportMonth.setEnabled(true);
                panelImportMonth.setVisible(false);
                CreateMonthDialog.this.pack();
            }
        });
        bgForNext.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        panelImportMonth.add(bgForNext);

        JSeparator separator1 = new JSeparator(JSeparator.HORIZONTAL);

        JLabel labelPublicHolidays = new JLabel("Public Free Days");
        labelPublicHolidays.setFont(new Font(labelPublicHolidays.getFont().getName(), Font.BOLD, 15));
        JPanel panelPublicHolidays = new JPanel();
        panelPublicHolidays.setLayout(new GridBagLayout());

        publicHolidayCheckBoxList = new ArrayList<>();

        for (int y = 0; y <= 6; y++) {
            for (int x = 0; x <= 7; x++) {
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = x;
                c.gridy = y;
                c.gridwidth = 1;
                c.gridheight = 1;
                c.weightx = 0;
                c.weighty = 0;

                if (x == 0 && y == 0) {
                    panelPublicHolidays.add(new JLabel("week\\day"), c);
                }

                if (x == 0 && y != 0) {
                    panelPublicHolidays.add(new JLabel(String.valueOf(y)), c);
                }

                if (x != 0 && y == 0) {
                    panelPublicHolidays.add(new JLabel(String.valueOf(x)), c);
                }

                if (x != 0 && y != 0) {
                    JCheckBox checkBoxDay = new JCheckBox();
                    int dayIndex = x + 7 * (y - 1) - 1;
                    publicHolidayCheckBoxList.add(dayIndex, checkBoxDay);

                    panelPublicHolidays.add(checkBoxDay, c);
                }
            }
        }

        computeMonthAndWeekends();

        JSeparator separator2 = new JSeparator(JSeparator.HORIZONTAL);

        JLabel labelSelectAfter = new JLabel("Select Month After Creation");
        checkBoxSelectAfter = new JCheckBox();

        JButton buttonCreate = new JButton("Create");
        buttonCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = true;
                CreateMonthDialog.this.dispose();
            }
        });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = false;
                CreateMonthDialog.this.dispose();
            }
        });

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        int width = 110;
        int height = 25;

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelName, height, height, height)
                        .addComponent(textFieldName, height, height, height))
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelNumberOfDays, height, height, height)
                        .addComponent(spinnerNumberOfDays, height, height, height))
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelMonthStartsIn, height, height, height)
                        .addComponent(spinnerMonthStartsIn, height, height, height))
                .addGap(5)
                .addComponent(buttonImportMonth, height, height, height)
                .addComponent(panelImportMonth)
                .addGap(5)
                .addComponent(separator1)
                .addGap(5)
                .addComponent(labelPublicHolidays, height, height, height)
                .addGap(5)
                .addComponent(panelPublicHolidays)
                .addGap(5)
                .addComponent(separator2)
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(checkBoxSelectAfter)
                        .addComponent(labelSelectAfter, height, height, height))
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonCreate, height, height, height)
                        .addComponent(buttonCancel, height, height, height))
                .addGap(10));

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addComponent(labelName, width, width, width)
                                .addGap(5)
                                .addComponent(textFieldName, width, width, width))
                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addComponent(labelNumberOfDays, width, width, width)
                                .addGap(5)
                                .addComponent(spinnerNumberOfDays, width, width, width))
                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addComponent(labelMonthStartsIn, width, width, width)
                                .addGap(5)
                                .addComponent(spinnerMonthStartsIn, width, width, width))
                        .addComponent(buttonImportMonth, GroupLayout.Alignment.TRAILING)
                        .addComponent(panelImportMonth)
                        .addComponent(separator1)
                        .addComponent(labelPublicHolidays, GroupLayout.Alignment.CENTER)
                        .addComponent(panelPublicHolidays)
                        .addComponent(separator2)
                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addComponent(checkBoxSelectAfter)
                                .addComponent(labelSelectAfter))
                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addComponent(buttonCreate, 80, 80, 80)
                                .addGap(5)
                                .addComponent(buttonCancel, 80, 80, 80)))
                .addGap(10));
    }

    private void computeMonthAndWeekends(){
        int numberOfDays = Integer.parseInt((String) spinnerNumberOfDays.getValue());

        int startingDay = 1;
        for (String s : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}){
            if (spinnerMonthStartsIn.getValue().equals(s)){
                break;
            }
            startingDay++;
        }

        for (int i=1;i<=42;i++){
            if (i < startingDay || i > (startingDay - 1 + numberOfDays)){
                publicHolidayCheckBoxList.get(i - 1).setEnabled(false);
                publicHolidayCheckBoxList.get(i - 1).setSelected(false);
                publicHolidayCheckBoxList.get(i - 1).setName("-1");
            } else {
                publicHolidayCheckBoxList.get(i - 1).setEnabled(true);
                int dayInMonth = i - (startingDay - 1);
                publicHolidayCheckBoxList.get(i - 1).setName(String.valueOf(dayInMonth));
                if ((i - 6) % 7 == 0 || (i - 7) % 7 == 0){
                    publicHolidayCheckBoxList.get(i - 1).setSelected(true);
                } else {
                    publicHolidayCheckBoxList.get(i - 1).setSelected(false);
                }
            }
        }
    }

    private String[] getPreviousMonthDefinition(){
        Calendar now = Calendar.getInstance();

        int previousMonth = now.get(Calendar.MONTH) - 1;
        int year = now.get(Calendar.YEAR);
        if (previousMonth < 0){
            previousMonth = 11;
            year--;
        }

        Calendar previous = new GregorianCalendar(year, previousMonth, 1);

        String[] monthNames = new DateFormatSymbols().getMonths();
        String[] dayNames = new DateFormatSymbols().getWeekdays();

        String[] output = new String[]{monthNames[previousMonth],
                String.valueOf(year),
                String.valueOf(previous.getActualMaximum(Calendar.DAY_OF_MONTH)),
                dayNames[previous.get(Calendar.DAY_OF_WEEK)]};
        return output;
    }

    private String[] getActualMonthDefinition(){
        Calendar now = Calendar.getInstance();

        Calendar actual = new GregorianCalendar(now.get(Calendar.YEAR), now.get(Calendar.MONTH), 1);

        String[] monthNames = new DateFormatSymbols().getMonths();
        String[] dayNames = new DateFormatSymbols().getWeekdays();

        String[] output = new String[]{monthNames[actual.get(Calendar.MONTH)],
                String.valueOf(actual.get(Calendar.YEAR)),
                String.valueOf(actual.getActualMaximum(Calendar.DAY_OF_MONTH)),
                dayNames[actual.get(Calendar.DAY_OF_WEEK)]};
        return output;
    }

    private String[] getNextMonthDefinition(){
        Calendar now = Calendar.getInstance();

        int nextMonth = now.get(Calendar.MONTH) + 1;
        int year = now.get(Calendar.YEAR);
        if (nextMonth > 11){
            nextMonth = 0;
            year++;
        }

        Calendar next = new GregorianCalendar(year, nextMonth, 1);

        String[] monthNames = new DateFormatSymbols().getMonths();
        String[] dayNames = new DateFormatSymbols().getWeekdays();

        String[] output = new String[]{monthNames[nextMonth],
                String.valueOf(year),
                String.valueOf(next.getActualMaximum(Calendar.DAY_OF_MONTH)),
                dayNames[next.get(Calendar.DAY_OF_WEEK)]};
        return output;
    }

    public boolean getResult(){
        return result;
    }

    public boolean wantToSelectCreatedMonth(){
        return checkBoxSelectAfter.isSelected();
    }

    public Month getCreatedMonth(){
        Month month = new Month();

        month.setName(textFieldName.getText());

        int startingDay = 1;
        for (String s : new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"}){
            if (spinnerMonthStartsIn.getValue().equals(s)){
                break;
            }
            startingDay++;
        }
        month.setDayStartsWith(startingDay);

        month.setDaysNumber(Integer.parseInt((String) spinnerNumberOfDays.getValue()));

        for (JCheckBox day : publicHolidayCheckBoxList){
            int dayNumber = Integer.parseInt(day.getName());
            if (dayNumber != -1 && day.isSelected()){
                month.getPublicFreeDays().add(dayNumber);
            }
        }

        return month;
    }
}
