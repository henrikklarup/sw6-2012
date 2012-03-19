import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

//The Server Runs by default on port 50000 !



public class Client2 extends JFrame implements ActionListener {
	//	Client details
	private String userName;
	private Socket client;
	//	I/O devices
	private PrintWriter printer;
	private BufferedReader reader;
	//	Graphical elements
	private JTextArea textArea;
	private JTextField textField;
	private JButton btnSend;
	private JButton btnExit;
	
	private final int PORT = 50000;
	private final int ROWS = 10;
	private final int COLUMNS = 50;
	private final int SIZEH = 500;
	private final int SIZEW = 300;
	private final boolean EDITABLE = false;
	private final boolean VISIBLE = false;
	
	public Client2(String userName, String serverName) throws Exception {
		super(userName);	//Set the title for the frame...
		this.userName = userName;
		this.client = new Socket(serverName, this.PORT);
		//Open the I/O devices
		this.reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
		this.printer = new PrintWriter(this.client.getOutputStream());
		this.printer.println(this.userName);	//Send the name to the Server
		buildInterface();
		new MessageThread().start();	//Thread for listening for messages
	}
	
	private void buildInterface() {
		this.btnSend = new JButton("Send");
		this.btnExit = new JButton("Exit");
		
		this.textArea = new JTextArea();
		this.textArea.setRows(this.ROWS);
		this.textArea.setColumns(this.COLUMNS);
		this.textArea.setEditable(this.EDITABLE);
		
		this.textField = new JTextField(this.COLUMNS);
		
		JScrollPane scrollPane = new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, "Center");
		
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(this.textField);
		panel.add(this.btnSend);
		panel.add(this.btnExit);
		add(panel, "South");
		
		this.btnSend.addActionListener(this);
		this.btnExit.addActionListener(this);
		
		setSize(this.SIZEH, this.SIZEW);
		setVisible(this.VISIBLE);
		pack();		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == this.btnExit) {
			this.printer.println("Disconnected");
			System.exit(0);
		}
		else {
			this.printer.println(this.textField.getText());
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		String name = JOptionPane.showInputDialog(null, "Enter username: ", "Username", JOptionPane.PLAIN_MESSAGE);
		String serverName = "localhost";
		
		try {
			new Client2(name, serverName);
		}	catch(Exception ex) {
			System.out.println("Error in Main: " + ex.getMessage());
		}	
	}
	
	//Inner class for MessageThreads
	class MessageThread extends Thread {
		public void run() {
			String line;
			try {
				while(true) {
					line = reader.readLine();
					textArea.append(line + "\n");
				}
			}	catch(Exception ex) {
				System.out.println("Error in MessageThread: " + ex.getMessage());
			}
		}
		
	}
	
	
	

}
