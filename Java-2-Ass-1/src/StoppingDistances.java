import java.awt.Frame;
import java.awt.Panel;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Button;
import java.awt.TextArea;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StoppingDistances {
	public static void main(String[] args) {
		StoppingPanels distanceForm = new StoppingPanels();
		distanceForm.setTitle("Stopping Distances");
		distanceForm.setVisible(true);
		distanceForm.setResizable(false);
	}
}



class StoppingPanels extends Frame implements ActionListener {

	public StoppingPanels() {
		Color yellow = Color.yellow;
		Font tableFont = new Font("Courier", Font.PLAIN, 12);
		Font labelFont = new Font("Times New Roman", Font.PLAIN, 12);
		final int FRAME_WIDTH = 300;
		final int FRAME_HEIGHT = 400;
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		panelOne = new Panel();
		panelOne.setLayout (new GridLayout(5, 2));
		panelOne.setBackground(Color.lightGray);
		
		// Add Start label and text box
		
		startLabel = new Label( "Start", Label.CENTER );
		startLabel.setBackground(Color.cyan);
		startLabel.setFont(labelFont);
		panelOne.add(startLabel);
		
		startValue = new TextField();
		startValue.setBackground(yellow); 
		panelOne.add(startValue);
		
		// Add End label and text box
		endLabel = new Label( "End", Label.CENTER );
		endLabel.setBackground(Color.cyan);
		endLabel.setFont(labelFont);
		panelOne.add(endLabel);
		
		endValue = new TextField();
		endValue.setBackground(yellow); 
		panelOne.add(endValue);
		
		// Add Increment label and text box
		incrementLabel = new Label( "Increment", Label.CENTER );
		incrementLabel.setBackground(Color.cyan);
		panelOne.add(incrementLabel);
		
		incrementValue = new TextField();
		incrementValue.setBackground(yellow); 
		panelOne.add(incrementValue);
		
		// Add Buttons
		// Clear
		clearButton = new Button("Clear");
		clearButton.addActionListener(this);
		clearButton.setBackground(Color.blue); 
		clearButton.setForeground(Color.white);
		panelOne.add(clearButton);
		
		// Table
		tableButton = new Button("Table");
		tableButton.addActionListener(this);
		tableButton.setBackground(Color.blue); 
		tableButton.setForeground(Color.white);
		panelOne.add(tableButton);
		
		// Exit
		exitButton = new Button("Exit");
		exitButton.addActionListener(this);
		exitButton.setBackground(Color.blue); 
		exitButton.setForeground(Color.white);
		panelOne.add(exitButton);
		
		
		// Add output box
		panelTwo = new Panel();
		panelTwo.setLayout(new GridLayout(1,1));
		textBox = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		textBox.setFont(tableFont);
		textBox.setEditable(false);
		textBox.setBackground(Color.pink);
		panelTwo.add(textBox);
		
		
		// Add Panels to form
		setLayout(new GridLayout(2,1));
		add(panelOne);
		add(panelTwo);

	}


	public void actionPerformed(ActionEvent thisEvent) {
		if (thisEvent.getSource() == tableButton) {
	//		textBox.append( startValue.getText() + "\n" );
			textBox.setText(constructTable());
		} else if (thisEvent.getSource() == clearButton) {
			textBox.setText(" ");
			startValue.setText(" ");
			endValue.setText(" ");
			incrementValue.setText(" ");
		} else {
			System.exit(0);
		}     
	}
    
	private String constructTable() {
		String tableString = "";
		String errorString = checkErrors();
		String divider = "    ******************************* \n";
		
		if (errorString != "" ) {
			tableString = errorString;
		} else {
			
			tableString = divider;
			tableString = tableString.concat("    * Speed(mph) * Distance(feet) * \n");
			tableString = tableString.concat(divider);
			
			int startNumber = Integer.parseInt(startValue.getText().trim());
			int endNumber = Integer.parseInt(endValue.getText().trim());
			int incrementNumber = Integer.parseInt(incrementValue.getText().trim());		
			
			for(int i = startNumber; i <= endNumber; i += incrementNumber) {
				// S = ( V * V ) / 20 + V
				
				int thisDistance = (i * i) / 20 + i;
				String printLine = String.format("%5s%7d%6s%12d%5s \n", "*", i, "*", thisDistance, "*");
				
				
				tableString = tableString.concat(printLine);
			}

			tableString = tableString.concat(divider);
		}
		
		return tableString;
	}
	
	private String checkErrors() {
		String errorString = "";
		
		// First check valid integers entered
		if ( !checkInt(startValue.getText().trim())) {
			errorString = "The start value must be a valid integer \n";
		}
		if ( !checkInt(endValue.getText().trim())) {
			errorString = errorString + "The end value must be a valid integer \n";
		}
		if ( !checkInt(incrementValue.getText().trim())) {
			errorString = errorString + "The increment value must be a valid integer \n";
		}
		
		// then check if end value is greater than start value
		if (errorString == "") {
			int startNumber = Integer.parseInt(startValue.getText().trim());
			int endNumber = Integer.parseInt(endValue.getText().trim());
			int incrementNumber = Integer.parseInt(incrementValue.getText().trim());
			
			// Check positive integers entered
			if (startNumber < 0) {
				errorString = "The start value must be a positive integer \n";
			}
			if (endNumber < 1) {
				errorString = errorString + "The end value must be a positive integer above zero \n";
			}
			if (incrementNumber == 0) {
				errorString = errorString + "The increment value cannot be zero \n";
			}
			if (incrementNumber < 0) {
				errorString = errorString + "The increment value must be a positive integer \n";
			}
			
			// check if end value is greater than start value
			if (startNumber > endNumber) {
				errorString =  errorString + "The end value must be at least equal to the start value \n";
			}
			// Ensure upper range isn't exceeded
			if (startNumber > 1000) {
				errorString =  errorString + "The maximum start value is 1000 \n";
			}
			// Ensure upper range isn't exceeded
			if (endNumber > 1000) {
				errorString =  errorString + "The maximum end value is 1000 \n";
			}
		}
		return errorString;
	}
	
	private boolean checkInt(String enteredNumber) {
		try {
			int enteredValue = Integer.parseInt(enteredNumber);
			return true;
		} catch (NumberFormatException e) {
		    //error
			return false;
		}
	}
	
	
    private Label startLabel, endLabel, incrementLabel;
    private TextField startValue, endValue, incrementValue;
    private Button clearButton, tableButton, exitButton;
    private TextArea textBox;
    private Panel panelOne, panelTwo;

}
