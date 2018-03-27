package TestSwing;

import java.awt.*;
import javax.swing.*;
 
public class Resize extends JFrame {
   JPanel pane = new JPanel(false);
   JScrollPane scrollPane;
   JTextArea text = new JTextArea(50, 80);
   JTabbedPane tabbedPane = new JTabbedPane();
 
   public Resize() {
      pane.setLayout(new BorderLayout()); // set the JPanel's layout to BorderLayout
       
      scrollPane = new JScrollPane(text);
      pane.add(scrollPane, BorderLayout.CENTER);
      tabbedPane.addTab("Resize", null, pane, "");
      add(tabbedPane);
   }
 
   public static void main(String args[]) {
      final Resize window = new Resize();
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setSize(100, 100);
      window.setVisible(true);
   }
}
