package Main;

import Graphics2d.Vec2D;
import SolarSystem.AstronomicBodies.AstronomicBody;
import SolarSystem.AstronomicBodies.ColorAstronomicBody;
import SolarSystem.AstronomicBodies.MapAstronomicBody;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BodyCreator extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField radiusVal;
    private JComboBox<AstronomicBody> parents;
    private JTextField massVal;
    private JTextField massPow;
    private JTextField perigeliumVal;
    private JTextField perigiliumPow;
    private JTextField speedVal;
    private JTextField speedPow;
    private JTextField inclination;
    private JRadioButton preferImgRadioButton;
    private JTextField pathToImg;
    private JTextField colorInput;

    private JTextField name;
    private JLabel filename;
    private JButton chooseMapButton;

    public AstronomicBody body = null;

    private String filepath = null;

    public BodyCreator(List<AstronomicBody> parentsList) {
        updateListPlanet(parentsList);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        chooseMapButton.addActionListener(e -> {
            FileDialog chooseMapDialog = new FileDialog(this, "choose map pic", FileDialog.LOAD);
            chooseMapDialog.setDirectory(System.getProperty("user.dir"));
            chooseMapDialog.pack();
            chooseMapDialog.setVisible(true);
            Path path = Paths.get(chooseMapDialog.getDirectory(), chooseMapDialog.getFile());
            filepath = path.toString();
            filename.setText(truncateString(filepath, 10));
        });
    }
    static String truncateString(String text, int length) {
        if (text.length() <= length) {
            return text;
        } else {
            return ".." + text.substring(text.length() - length - 2);
        }
    }

    public void updateListPlanet(List<AstronomicBody> parentsList){
        parents.setModel(new DefaultComboBoxModel<>(parentsList.toArray(new AstronomicBody[0])));
    }

    private void onOK() {
        try {
            body = createBody();
            dispose();
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this, "illegal input");
        }
    }

    public AstronomicBody createBody() throws NumberFormatException{
        double r = Double.parseDouble(radiusVal.getText());

        double mass = Double.parseDouble(massVal.getText())  * Math.pow(10, Double.parseDouble(massVal.getText()));
        double perigee = Double.parseDouble(perigeliumVal.getText())  * Math.pow(10, Double.parseDouble(perigiliumPow.getText())); // meters
        double v = Double.parseDouble(speedVal.getText())  * Math.pow(10, Double.parseDouble(speedPow.getText()));;
        double inc = Double.parseDouble(inclination.getText()) * Math.PI / 180;

        Vec2D startPos = new Vec2D(perigee, 0);

        AstronomicBody body;

        if(filepath != null) {
            body = new MapAstronomicBody(name.getText(), r, mass, startPos, filepath);
        } else {

            body = new ColorAstronomicBody(name.getText(), r, mass, startPos, new Color(Integer.parseInt(colorInput.getText(), 16)));
        }

        body.initAstro(new Vec2D(0 , v), (AstronomicBody) parents.getSelectedItem(), inc, Color.RED);

        return body;
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
