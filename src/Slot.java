import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ivoribeiro on 17-11-2016.
 */
public class Slot extends Thread implements ActionListener {

    Main __main;

    JButton __5Btn;
    JButton __10Btn;
    JButton __20Btn;
    JButton __50Btn;
    JButton __1Btn;
    JButton __2Btn;

    public Slot(Main main) {
        this.__main = main;
    }

    private void buildUI() {
        String myname = Thread.currentThread().getName();
        LayoutBuilder layoutBuilder = new LayoutBuilder(myname, 200, 175);
        layoutBuilder.addLabel(layoutBuilder.label("Insira aqui o dinheiro!"));
        //---Button building
        this.__5Btn = layoutBuilder.button("0.05€");
        this.__10Btn = layoutBuilder.button("0.10€");
        this.__20Btn = layoutBuilder.button("0.20€");
        this.__50Btn = layoutBuilder.button("0.50€");
        this.__1Btn = layoutBuilder.button("1€");
        this.__2Btn = layoutBuilder.button("2€");
        //--End
        //---Button listing
        __5Btn.addActionListener(this);
        __10Btn.addActionListener(this);
        __20Btn.addActionListener(this);
        __50Btn.addActionListener(this);
        __1Btn.addActionListener(this);
        __2Btn.addActionListener(this);
        //--End
        //---Add buttons to frame
        layoutBuilder.addButton(__5Btn);
        layoutBuilder.addButton(__10Btn);
        layoutBuilder.addButton(__20Btn);
        layoutBuilder.addButton(__50Btn);
        layoutBuilder.addButton(__1Btn);
        layoutBuilder.addButton(__2Btn);
        //--End
        layoutBuilder.setPosition(950, 150);
        layoutBuilder.build();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == __5Btn) {
            __main.writeLogMessage("[Slot]-0.05 btn pressed");
            __main.processSlot(0.05);
            __main.writeLogMessage("[Slot]-after btn pressed");
        } else if (actionEvent.getSource() == __10Btn) {
            __main.writeLogMessage("[Slot]-0.10 btn pressed");
            __main.processSlot(0.10);
            __main.writeLogMessage("[Slot]-after btn pressed");
        } else if (actionEvent.getSource() == __20Btn) {
            __main.writeLogMessage("[Slot]-0.20 btn pressed");
            __main.processSlot(0.20);
            __main.writeLogMessage("[Slot]-after btn pressed");
        } else if (actionEvent.getSource() == __50Btn) {
            __main.writeLogMessage("[Slot]-0.50 btn pressed");
            __main.processSlot(0.50);
            __main.writeLogMessage("[Slot]-after btn pressed");
        } else if (actionEvent.getSource() == __1Btn) {
            __main.writeLogMessage("[Slot]-1 btn pressed");
            __main.processSlot(1);
            __main.writeLogMessage("[Slot]-after btn pressed");
        } else if (actionEvent.getSource() == __2Btn) {
            __main.writeLogMessage("[Slot]-1 btn pressed");
            __main.processSlot(2);
            __main.writeLogMessage("[Slot]-after btn pressed");
        }
    }

    public void run() {
        try {
            this.__main.getLogSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        __main.writeLogMessage("[Slot]-set thread name");
        this.setName("Slot Thread");
        __main.writeLogMessage("[Slot]-name settled");
        __main.writeLogMessage("[Slot]-Build the GUI");
        this.buildUI();
        __main.writeLogMessage("[Slot]-GUI builded");
        this.__main.getLogSemaphore().release();
        //f.dispose();
    }
}
