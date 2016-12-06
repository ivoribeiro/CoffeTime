import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Keyboard extends Thread implements ActionListener {

    private JButton __chocBtn;
    private JButton __descaBtn;
    private JButton __teaBtn;
    private JButton __coffeBtn;
    private JButton __resetBtn;

    private final Main __main;


    public Keyboard(Main main) {
        this.__main = main;
    }

    private void buildUI() {

        String myname = Thread.currentThread().getName();
        LayoutBuilder layoutBuilder = new LayoutBuilder(myname, 500, 75);

        //---Button building
        this.__chocBtn = layoutBuilder.button("Chocolate");
        this.__coffeBtn = layoutBuilder.button("Café");
        this.__descaBtn = layoutBuilder.button("Descafeinado");
        this.__teaBtn = layoutBuilder.button("Chá");
        this.__resetBtn = layoutBuilder.button("Devolução");
        //--End

        //---Button listing
        __chocBtn.addActionListener(this);
        __coffeBtn.addActionListener(this);
        __teaBtn.addActionListener(this);
        __descaBtn.addActionListener(this);
        __resetBtn.addActionListener(this);
        //--End

        //---Add buttons to frame
        layoutBuilder.addButton(__chocBtn);
        layoutBuilder.addButton(__descaBtn);
        layoutBuilder.addButton(__teaBtn);
        layoutBuilder.addButton(__coffeBtn);
        layoutBuilder.addButton(__resetBtn);
        //--End

        layoutBuilder.setPosition(450, 250);
        layoutBuilder.build();
    }

    public void run() {
        try {
            this.__main.getLogSemaphore().acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        __main.writeLogMessage("[Keyboard]-set thread name");
        this.setName("Keyboard Thread");
        __main.writeLogMessage("[Keyboard]-name settled");
        __main.writeLogMessage("[Keyboard]-Build the GUI");
        this.buildUI();
        __main.writeLogMessage("[Keyboard]-GUI builded");
        this.__main.getLogSemaphore().release();

        //f.dispose();

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getSource() == __chocBtn) {
            __main.processDrink("choco");
        } else if (actionEvent.getSource() == __coffeBtn) {
            __main.processDrink("coffe");
        } else if (actionEvent.getSource() == __teaBtn) {
            __main.processDrink("tea");
        } else if (actionEvent.getSource() == __descaBtn) {
            __main.processDrink("desca");
        } else if (actionEvent.getSource() == __resetBtn) {
            __main.resetSlot();
        }


    }
}
