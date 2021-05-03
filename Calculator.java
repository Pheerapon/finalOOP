
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.math.*;

public class Calculator extends JFrame implements ActionListener {
    /* all for layout of calculator */
    JPanel[] row = new JPanel[8]; /* 7 rows + 1 textarea */

    /* button[0]=="acos", button[length-1]=="=" */
    String[] buttonString = { "sin", "cos", "tan", "log", "ln", "x^2", "x^y", "e^x", "1/x", "sqrt", "7", "8", "9",
            "DEL", "C", "4", "5", "6", "*", "/", "1", "2", "3", "+", "-", "0", ".", "pi", "e", "=" };

    JButton[] button = new JButton[buttonString.length];

    int[] dimW = { 300, 60 }; /* widths for buttons and textarea */
    int[] dimH = { 30, 40 }; /* heights for buttons and textarea */
    Dimension displayDimension = new Dimension(dimW[0], dimH[0]);
    Dimension regularDimension = new Dimension(dimW[1], dimH[1]);

    String function = ""; /* add,sub,mul,div */
    double[] operand = { 0, 0 };
    /* holds operands for operators ex. 1 + 1 */
    JTextArea display = new JTextArea(1, 20); /* displays input/output */
    Font font = new Font("Times new Roman", Font.BOLD, 14);

    Calculator() {
        super("Calculator");
        setDesign(); /* need to call this to avoid error */
        setSize(320, 350);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(7, 5); /* 8 rows, 5 col */
        setLayout(grid);

        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        FlowLayout f2 = new FlowLayout(FlowLayout.CENTER, 1, 1);

        for (int i = 0; i < row.length; ++i) {
            row[i] = new JPanel();
        }
        row[0].setLayout(f1); /* text area in 0th JPanel */
        for (int i = 1; i < row.length; i++) /* buttons in 1-4 JPanel */
            row[i].setLayout(f2);

        /* create each new button obj in a loop */
        for (int i = 0; i < button.length; ++i) {
            button[i] = new JButton();
            button[i].setText(buttonString[i]);
            button[i].setFont(font);
            button[i].addActionListener(this);
            /* need this to make buttons listen to click events */
        }

        display.setFont(font);
        display.setEditable(false);
        display.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        display.setPreferredSize(displayDimension);

        /* set sizes for various buttons */
        for (int i = 0; i < button.length; ++i) {
            button[i].setPreferredSize(regularDimension);
        }

        /* add buttons to each JPanel (row) obj */
        row[0].add(display);
        add(row[0]);

        for (int i = 0; i < 5; ++i)
            row[1].add(button[i]);
        add(row[1]);

        for (int i = 5; i < 10; ++i)
            row[2].add(button[i]);
        add(row[2]);

        for (int i = 10; i < 15; ++i)
            row[3].add(button[i]);
        add(row[3]);

        for (int i = 15; i < 20; ++i)
            row[4].add(button[i]);
        add(row[4]);

        for (int i = 20; i < 25; ++i)
            row[5].add(button[i]);
        add(row[5]);

        for (int i = 25; i < 30; ++i)
            row[6].add(button[i]);
        add(row[6]);

        setVisible(true);

    }
    /* end of all the GUI layout code */

    public final void setDesign() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }
    }

    /* functions buttons (add, sub, mul, etc) */
    public void clear() {
        try {
            display.setText(""); /* clears display */
            function = null;
            for (int i = 0; i < operand.length; ++i) {
                operand[i] = 0; /* sets operand vars to 0 */
            }
        } catch (NullPointerException e) {
            clear();
            display.setText("");
        }
    }

    public void del() {
        try {
            String val = display.getText(); // just need length
            int len = val.length();
            display.replaceRange("", len - 1, len);
            operand[0] = Double.parseDouble(display.getText());
        } catch (Exception e) {
            clear();
            display.setText("");
        }
    }

    public BigInteger getFactorial(int n) {
        BigInteger ans = new BigInteger("1");
        if (n <= 1)
            return ans; // only defined for pos nums
        for (; n > 1; --n) {
            ans = ans.multiply(new BigInteger(n + ""));
            // BigInt constructor takes Strings, not ints
        }
        return ans;
    }

    public void getResult() {
        double result = 0;
        operand[1] = Double.parseDouble(display.getText());

        String temp0 = Double.toString(operand[0]);
        String temp1 = Double.toString(operand[1]);
        try {
            if (temp0.contains("-")) {
                String[] temp00 = temp0.split("-", 2);
                operand[0] = (Double.parseDouble(temp00[1]) * -1);
            }
            if (temp1.contains("-")) {
                String[] temp11 = temp1.split("-", 2);
                operand[1] = (Double.parseDouble(temp11[1]) * -1);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            clear();
            display.setText("Error");
        }

        /* order is Parenthsis, Exponent, Mul, Div, Add, Sub */
        try {
            if (function.equals("x^2"))
                result = Math.pow(operand[0], 2.0);
            else if (function.equals("y^x"))
                result = Math.pow(operand[0], operand[1]);
            else if (function.equals("sqrt"))
                result = Math.sqrt(operand[0]);
            else if (function.equals("10^x"))
                result = Math.pow(10.0, operand[0]);
            else if (function.equals("log"))
                result = Math.log10(operand[0]);
            else if (function.equals("*"))
                result = operand[0] * operand[1];
            else if (function.equals("/"))
                result = operand[0] / operand[1];
            else if (function.equals("%"))
                result = operand[0] % operand[1];
            else if (function.equals("+"))
                result = operand[0] + operand[1];
            else if (function.equals("-"))
                result = operand[0] - operand[1];

            display.setText(Double.toString(result));
        } catch (Exception e) {
            clear();
            display.setText("");
        }
    }

    @Override
    public void actionPerformed(ActionEvent bt) {

        if (bt.getSource() == button[0]) { // sin
            operand[0] = Double.parseDouble(display.getText());
            double val = Math.sin(operand[0]);
            clear();
            display.setText(String.valueOf(val));
            button[0].setMnemonic(KeyEvent.VK_S);

        }
        if (bt.getSource() == button[1]) { // cos
            operand[0] = Double.parseDouble(display.getText());
            double val = Math.cos(operand[0]);
            clear();
            display.setText(String.valueOf(val));
        }
        if (bt.getSource() == button[2]) { // tan
            operand[0] = Double.parseDouble(display.getText());
            double val = Math.tan(operand[0]);
            clear();
            display.setText(String.valueOf(val));
        }
        if (bt.getSource() == button[3]) { // log
            operand[0] = Double.parseDouble(display.getText());
            double val = Math.log10(operand[0]);
            display.setText(String.valueOf(val));
        }
        if (bt.getSource() == button[4]) { // ln
            operand[0] = Double.parseDouble(display.getText());
            double val = Math.log(operand[0]);
            clear();
            display.setText(String.valueOf(val));
        }
        if (bt.getSource() == button[5]) { // x^2
            operand[0] = Double.parseDouble(display.getText());
            double val = Math.pow(operand[0], 2);
            clear();
            display.setText(String.valueOf(val));
        }
        if (bt.getSource() == button[6]) { // x^y
            operand[0] = Double.parseDouble(display.getText());
            function = "y^x";
            display.setText("");
        }
        if (bt.getSource() == button[7]) { // e^x
            operand[0] = Double.parseDouble(display.getText());
            double val = Math.pow(Math.E, operand[0]);
            clear();
            display.setText(String.valueOf(val));
        }
        if (bt.getSource() == button[8]) { // 1/x
            operand[0] = Double.parseDouble(display.getText());
            double val = Math.pow(operand[0], -1);
            clear();
            display.setText(String.valueOf(val));
        }
        if (bt.getSource() == button[9]) { // sqrt
            operand[0] = Double.parseDouble(display.getText());
            double val = Math.sqrt(operand[0]);
            clear();
            display.setText(String.valueOf(val));
        }
        if (bt.getSource() == button[10]) { // 7
            display.append("7");
        }
        if (bt.getSource() == button[11]) { // 8
            display.append("8");
        }
        if (bt.getSource() == button[12]) { // 9
            display.append("9");
        }
        if (bt.getSource() == button[13]) { // DEL
            del();
        }
        if (bt.getSource() == button[14]) { // C
            clear();
        }
        if (bt.getSource() == button[15]) { // 4
            display.append("4");
        }
        if (bt.getSource() == button[16]) { // 5
            display.append("5");
        }
        if (bt.getSource() == button[17]) { // 6
            display.append("6");
        }
        if (bt.getSource() == button[18]) { // *
            operand[0] = Double.parseDouble(display.getText());
            function = "*";
            display.setText("");

        }
        if (bt.getSource() == button[19]) { // /
            operand[0] = Double.parseDouble(display.getText());
            function = "/";
            display.setText("");
        }
        if (bt.getSource() == button[20]) { // 1
            display.append("1");
        }
        if (bt.getSource() == button[21]) { // 2
            display.append("2");
        }
        if (bt.getSource() == button[22]) { // 3
            display.append("3");
        }
        if (bt.getSource() == button[23]) { // +
            operand[0] = Double.parseDouble(display.getText());
            function = "+";
            display.setText("");
        }
        if (bt.getSource() == button[24]) { // -
            operand[0] = Double.parseDouble(display.getText());
            function = "-";
            display.setText("");
        }
        if (bt.getSource() == button[25]) { // 0
            display.append("0");
        }
        if (bt.getSource() == button[26]) { // .
            display.append(".");
        }
        if (bt.getSource() == button[27]) { // pi
            display.append(String.valueOf(Math.PI));
        }
        if (bt.getSource() == button[28]) { // e
            display.append(String.valueOf(Math.E));
        }
        if (bt.getSource() == button[29]) { // =
            getResult();
        }
    }
    // start calculator

    public void shortcut() {
        button[0].setMnemonic(KeyEvent.VK_A);
        button[1].setMnemonic(KeyEvent.VK_B);
        button[2].setMnemonic(KeyEvent.VK_C);
        button[3].setMnemonic(KeyEvent.VK_D);
        button[4].setMnemonic(KeyEvent.VK_E);
        button[5].setMnemonic(KeyEvent.VK_F);
        button[6].setMnemonic(KeyEvent.VK_G);
        button[7].setMnemonic(KeyEvent.VK_H);
        button[8].setMnemonic(KeyEvent.VK_I);
        button[9].setMnemonic(KeyEvent.VK_J);
        button[10].setMnemonic(KeyEvent.VK_K);
        button[11].setMnemonic(KeyEvent.VK_L);
        button[12].setMnemonic(KeyEvent.VK_M);
        button[13].setMnemonic(KeyEvent.VK_N);
        button[14].setMnemonic(KeyEvent.VK_N);
        button[15].setMnemonic(KeyEvent.VK_O);
        button[16].setMnemonic(KeyEvent.VK_P);
        button[17].setMnemonic(KeyEvent.VK_Q);
        button[18].setMnemonic(KeyEvent.VK_R);
        button[19].setMnemonic(KeyEvent.VK_S);
        button[20].setMnemonic(KeyEvent.VK_T);
        button[21].setMnemonic(KeyEvent.VK_U);
        button[22].setMnemonic(KeyEvent.VK_V);
        button[23].setMnemonic(KeyEvent.VK_W);
        button[24].setMnemonic(KeyEvent.VK_X);
        button[25].setMnemonic(KeyEvent.VK_Y);
        button[26].setMnemonic(KeyEvent.VK_Z);
        button[27].setMnemonic(KeyEvent.VK_1);
        button[28].setMnemonic(KeyEvent.VK_2);
        button[29].setMnemonic(KeyEvent.VK_3);
    }

    public static void main(String args[]) {
        Calculator c = new Calculator();
    }
}
