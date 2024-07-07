import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class Calculator implements ActionListener{

    JFrame frame;
    JTextField display;
    JPanel buttonsPanel;
    JLabel label;

    JButton[] numberButtons = new JButton[10];
    JButton[] funcButtons = new JButton[9];

    double result=0;
    
    boolean lastEquPressed, lastAddPressed, lastSubPressed, lastMulPressed, lastDivPressed, firstTime = true;

    Calculator() {
        frame = new JFrame("Calculator");
        frame.setSize(380, 450);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonsPanel = new JPanel();
        buttonsPanel.setBounds(0, 100, 380, 350);
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 15));
        frame.add(buttonsPanel);

        display = new JTextField("0");
        display.setBounds(35, 30, 300, 45);
        display.setFont(new Font("Monospaced", Font.PLAIN, 30));
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFocusable(false);
        frame.add(display);

        for(int i=0; i<10; i++){
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].setPreferredSize(new Dimension(75, 40));
            numberButtons[i].setBackground(new Color(240, 240, 240));
            numberButtons[i].setCursor(new Cursor(12));
            numberButtons[i].setFocusable(false);
            numberButtons[i].setFont(new Font("Monospaced", Font.BOLD, 20));
            numberButtons[i].addActionListener(this);
        }

        funcButtons[0] = new JButton("+");
        funcButtons[1] = new JButton("-");
        funcButtons[2] = new JButton("*");
        funcButtons[3] = new JButton("/");
        funcButtons[4] = new JButton(".");
        funcButtons[5] = new JButton("=");
        funcButtons[6] = new JButton("DEL");
        funcButtons[7] = new JButton("C");
        funcButtons[8] = new JButton("(-)");

        for(JButton fb : funcButtons) {
            fb.setPreferredSize(new Dimension(75, 40));
            fb.setBackground(new Color(170, 200, 150));
            fb.setCursor(new Cursor(12));
            fb.setFocusable(false);
            fb.setFont(new Font("Monospaced", Font.BOLD, 20));
            fb.addActionListener(this);
            if(fb.getText().equals("(-)") || fb.getText().equals("DEL") || fb.getText().equals("C"))
                fb.setPreferredSize(new Dimension(100, 40));
        }
        

        buttonsPanel.add(numberButtons[1]);
        buttonsPanel.add(numberButtons[2]);
        buttonsPanel.add(numberButtons[3]);
        buttonsPanel.add(funcButtons[0]);
        buttonsPanel.add(numberButtons[4]);
        buttonsPanel.add(numberButtons[5]);
        buttonsPanel.add(numberButtons[6]);
        buttonsPanel.add(funcButtons[1]);
        buttonsPanel.add(numberButtons[7]);
        buttonsPanel.add(numberButtons[8]);
        buttonsPanel.add(numberButtons[9]);
        buttonsPanel.add(funcButtons[2]);
        buttonsPanel.add(funcButtons[4]);
        buttonsPanel.add(numberButtons[0]);
        buttonsPanel.add(funcButtons[5]);
        buttonsPanel.add(funcButtons[3]);
        buttonsPanel.add(funcButtons[8]);
        buttonsPanel.add(funcButtons[6]);
        buttonsPanel.add(funcButtons[7]);

        
        frame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(display.getText().equals("0"))
            display.setText("");

        String source = e.getActionCommand(),
               text = display.getText();

        switch(source) {
            case "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" :
                display.setText(text.concat(source));
                break;

            case "+" :
                try { 
                    if(!lastEquPressed)
                        result += Double.parseDouble(text);
                } catch(NumberFormatException E) {display.setText("Invalid Expression");}
            
                firstTime = false;
                display.setText("");
                clear();
                lastAddPressed = true;
                break;

            case "-" :
                try { 
                    if(!lastEquPressed)
                        result -= Double.parseDouble(text);
                } catch(NumberFormatException E) {display.setText("Invalid Expression");}
                
                if(firstTime) {
                    result = Double.parseDouble(text);
                    display.setText("");
                    firstTime = false;
                }
                display.setText("");
                clear();
                lastSubPressed = true;
                break;
            
            case "*" :
                if(firstTime) {
                    result = 1;
                    firstTime = false;
                }
                try {if(!lastEquPressed)
                        result *= Double.parseDouble(text);
                } catch(NumberFormatException E) {display.setText("Invalid Expression");;}
                
                display.setText("");
                clear();
                lastMulPressed = true;
                break;

            case "/" :
                if(!lastEquPressed)
                    result /= Double.parseDouble(text);
                display.setText("");
                firstTime = false;
                clear();
                lastDivPressed = true;
                break;
                
            case "." :
                if(!text.contains("."))
                    display.setText(text.concat("."));
                break;
            
            case "=" :
                if(lastAddPressed && !text.equals(""))
                    result += Double.parseDouble(text);
                if(lastSubPressed && !text.equals(""))
                    result -= Double.parseDouble(text);
                if(lastMulPressed && !text.equals(""))
                    result *= Double.parseDouble(text);
                if(lastDivPressed && !text.equals(""))
                    result /= Double.parseDouble(text);
                display.setText(String.valueOf(result));
                clear();
                lastEquPressed = true;
                break;
                
            case "DEL" :
                StringBuffer str = new StringBuffer(text);
                try{
                    str.deleteCharAt(str.length()-1);
                    display.setText(str.toString());
                } catch(StringIndexOutOfBoundsException E) { }
                break;
            
            case "C" :
                result = 0;
                display.setText("");
                clear();
                firstTime = true;
                break;

            case "(-)" :
                if(text.equals(""))
                    display.setText("-");
                else
                    display.setText(String.valueOf(Double.parseDouble(text) * -1));
                break;
        }
    }

    private void clear() {
        lastEquPressed = false;
        lastAddPressed = false;
        lastSubPressed = false;
        lastMulPressed = false;
        lastDivPressed = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator());   
    }
       
}