import javax.swing.*;
import java.awt.*;

class LayoutBuilder extends JFrame {
    private final JFrame __frame;

    public LayoutBuilder(String name, int width, int height) {
        this.__frame = new JFrame(name);
        this.__frame.setLayout(new FlowLayout());
        this.__frame.setSize(width, height);
    }

    public JFrame getFrame() {
        return this.__frame;
    }

    public JButton button(String name) {
        return new JButton(name);
    }

    public JLabel label(String s) {
        return new JLabel(s);
    }

    public JButton addButton(JButton button) {
        this.__frame.add(button);
        return button;
    }

    public JLabel addLabel(JLabel label) {
        this.__frame.add(label);
        return label;
    }

    public void setPosition(int x, int y) {
        this.__frame.setLocation(x, y);
    }

    public void build() {
        this.__frame.setVisible(true);
    }


}
