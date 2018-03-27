package TestSwing;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

public class FileChooser1 {

	public static void main(String[] args) {

//		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
//
//		int returnValue = jfc.showOpenDialog(null);
//		// int returnValue = jfc.showSaveDialog(null);
//
//		if (returnValue == JFileChooser.APPROVE_OPTION) {
//			File selectedFile = jfc.getSelectedFile();
//			System.out.println(selectedFile.getAbsolutePath());
//		}
		
		
		String text = "one two three four five six seven eight nine ten ";
	    JTextArea textArea = new JTextArea(20,25);
	    textArea.setColumns(50);
	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);
	    textArea.append(text);
	    textArea.append(text);
	    textArea.append(text);
	    textArea.append(text);
	    textArea.append(text);
	    textArea.setSize(textArea.getPreferredSize().width, 1);
	    JOptionPane.showMessageDialog(null, new JScrollPane( textArea), "Information!",
	        JOptionPane.WARNING_MESSAGE);
	    text = "je suis là ";
	    textArea.append(text);
	    textArea.append(text);
	    textArea.append(text);
	    textArea.append(text);
	    textArea.append(text);
	    
	    JOptionPane.showMessageDialog(null, new JScrollPane( textArea), "Information!",
		        JOptionPane.WARNING_MESSAGE);

	}

}