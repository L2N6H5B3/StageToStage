package stageonjava.model;

import static stageonjava.util.ThreadUtil.sleep;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import stageonjava.Main;

/**
 * @author Luke Bradtke
 * @version 1.5
 * @since 1.0
 */

public class StageDisplayWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	// Initializes WindowFrame
	private JFrame window;
	// Initializes StageDisplayPanel
	private StageDisplayPanel stageDisplayPanel;
	// Initializes the expected Width and Height from the ProPresenter Stage Layouts XML
	private int expectedWidth, expectedHeight;
	// Initializes the Hashmap to hold the Frames added to the StageDisplayPanel
	private HashMap<String, JLabel> addedFrames;
	// Initializes a variable to hold the current layout
	private DisplayLayout displayLayout;
	// Initializes Labels
	private static JLabel currentSlideLabel, nextSlideLabel, clockLabel, messageLabel, videoCountdownLabel;
	// Initializes MessageStore Variable
	private static String lastMessage;
	
	
	public StageDisplayWindow() {
		// Initializes "q" quit button listener
		KeyListener quitListener = setKeyListener();
		
	    // Initializes Last Stage Message Variable
		lastMessage = "";
		
		// Creates a transparent 16 x 16 pixel cursor image.
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Initializes window
		window = new JFrame("ProPresenter StageToStage");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setLayout(new BorderLayout());
	    window.setUndecorated(true);
	    
	    // Sets Window and Panel Background Colour
	    window.setBackground(Color.BLACK);
	    window.setForeground(Color.BLACK);
	    
	    // Configures Main Window Settings
	    window.addKeyListener(quitListener);
	    window.getContentPane().setCursor(blankCursor);
	    window.setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
	    
	    if (!Main.props.getProperty("auto-layout").toLowerCase().contains("yes")) {
	    	System.out.println("Creating Manual Layout Window...");
	    	
	    	addedFrames = new HashMap<String, JLabel>();
	    	    	
	    	// Initializes Font Size Variables
			int currentSlideFontSize = Main.getCurrentSlideFontSize();
			int nextSlideFontSize = Main.getNextSlideFontSize();
			int smallFontSize = Main.getSmallFontSize();
			int titleFontSize = Main.getTitleFontSize();
			
			// Initializes panels
			JPanel mainPanel = new JPanel();
			mainPanel.setFocusable(true);
			mainPanel.addKeyListener(quitListener);
			
			// Sets Font
		    Font currentSlideFont = new Font("Arial", Font.PLAIN, currentSlideFontSize);
		    Font nextSlideFont = new Font("Arial", Font.PLAIN, nextSlideFontSize);
		    Font smallFont = new Font("Arial", Font.PLAIN, smallFontSize);
		    Font titleFont = new Font("Arial", Font.PLAIN, titleFontSize);
		    
		    // Adds different stage elements
		    currentSlideLabel = new JLabel("");
		    currentSlideLabel.setFont(currentSlideFont);
		    currentSlideLabel.setForeground(Color.WHITE);
		    currentSlideLabel.setVerticalAlignment(SwingConstants.TOP);
		    nextSlideLabel = new JLabel("");
		    nextSlideLabel.setFont(nextSlideFont);
		    nextSlideLabel.setForeground(Color.WHITE);
		    nextSlideLabel.setVerticalAlignment(SwingConstants.TOP);
		    messageLabel = new JLabel("", SwingConstants.CENTER);
		    messageLabel.setFont(smallFont);
		    messageLabel.setForeground(Color.WHITE);
		    videoCountdownLabel = new JLabel("", SwingConstants.CENTER);
		    videoCountdownLabel.setFont(smallFont);
		    videoCountdownLabel.setForeground(Color.WHITE);
		    clockLabel = new JLabel("", SwingConstants.CENTER);
		    clockLabel.setFont(smallFont);
		    clockLabel.setForeground(Color.WHITE);
		    
		    // Creates Border Template for Stage Display Elements
		    Border newBorder = BorderFactory.createEmptyBorder();
		    MatteBorder mainGrayBorder = BorderFactory.createMatteBorder(20, 2, 2, 2, Color.GRAY);
		    // Creates a Border for each Element
		    TitledBorder currentSlideBorder = setBorder("Current Slide", newBorder, titleFont, mainGrayBorder);
		    TitledBorder nextSlideBorder = setBorder("Next Slide", newBorder, titleFont, mainGrayBorder);
		    TitledBorder messageBorder = setBorder("Message", newBorder, titleFont, mainGrayBorder);
		    TitledBorder videoCountdownBorder = setBorder("Video Countdown", newBorder, titleFont, mainGrayBorder);
		    TitledBorder clockBorder = setBorder("Clock", newBorder, titleFont, mainGrayBorder);
		    
		    // Adds a Border to each Element
		    currentSlideLabel.setBorder(currentSlideBorder);
		    nextSlideLabel.setBorder(nextSlideBorder);
		    messageLabel.setBorder(messageBorder);
		    videoCountdownLabel.setBorder(videoCountdownBorder);
		    clockLabel.setBorder(clockBorder);
		    
		    // Creates a Layout for All Elements
		    mainPanel.setLayout(new GridBagLayout());
		    
		    // Sets Element Layout for Secondary Elements (Clock, Message, VideoCounter, Etc)
		    GridBagConstraints secondaryConstraints = new GridBagConstraints();
	        secondaryConstraints.fill = GridBagConstraints.HORIZONTAL;
		    secondaryConstraints.weightx = 1;
		    secondaryConstraints.weighty = 0;
		    secondaryConstraints.gridx = 0;
		    secondaryConstraints.gridy = 0;
		    mainPanel.add(messageLabel, secondaryConstraints);
		    secondaryConstraints.fill = GridBagConstraints.HORIZONTAL;
		    secondaryConstraints.weightx = 0;
		    secondaryConstraints.weighty = 0;
		    secondaryConstraints.gridx = 1;
		    secondaryConstraints.gridy = 0;
		    mainPanel.add(videoCountdownLabel, secondaryConstraints);
		    secondaryConstraints.fill = GridBagConstraints.HORIZONTAL;
		    secondaryConstraints.weightx = 0;
		    secondaryConstraints.weighty = 0;
		    secondaryConstraints.gridx = 2;
		    secondaryConstraints.gridy = 0;
		    mainPanel.add(clockLabel, secondaryConstraints);
		    
		    // Sets Element Layout for Primary Elements (CurrentSlide, NextSlide, Etc)
		    GridBagConstraints mainConstraints = new GridBagConstraints();
	        mainConstraints.fill = GridBagConstraints.BOTH;
		    mainConstraints.weightx = 1;
		    mainConstraints.weighty = 1.01;
		    mainConstraints.gridx = 0;
		    mainConstraints.gridy = 1;
		    mainConstraints.gridwidth = 3;
		    mainPanel.add(currentSlideLabel, mainConstraints);
		    mainConstraints.fill = GridBagConstraints.BOTH;
		    mainConstraints.weightx = 1;
		    mainConstraints.weighty = 1;
		    mainConstraints.gridx = 0;
		    mainConstraints.gridy = 2;
		    mainConstraints.gridwidth = 3;
		    mainPanel.add(nextSlideLabel, mainConstraints);
		    
		    mainPanel.setBackground(Color.BLACK);
		    
		    addedFrames.put("CurrentSlide", currentSlideLabel);
		    addedFrames.put("NextSlide", nextSlideLabel);
		    addedFrames.put("Message", messageLabel);
		    addedFrames.put("VideoCounter", videoCountdownLabel);
		    addedFrames.put("Clock", clockLabel);
		    
		    
		    // Adds Main Element Panel to Main Window
		    window.add(mainPanel, BorderLayout.CENTER);
		    
			window.pack();
		    
		    // Sets the Main Window visible
		    window.setVisible(true);
		    
		    // Refreshes Main Window contents
		    window.revalidate();
	    } else {
	    	
	    	stageDisplayPanel = new StageDisplayPanel();
	    	
	    	// Adds Main Element Panel to Main Window
		    window.add(stageDisplayPanel, BorderLayout.CENTER);
		    
	    	window.pack();
		    
		    // Sets the Main Window visible
		    window.setVisible(true);
		    
		    // Refreshes Main Window contents
		    window.revalidate();
	    }
    }


	public void setCurrentLayout(DisplayLayouts displayLayouts) {
		
		if (Main.props.getProperty("auto-layout").toLowerCase().contains("yes")) {
			String currentLayout = displayLayouts.getSelected();
			
			// If the Stage Display Panel has been given a layout
			if(stageDisplayPanel.getCurrentLayout() != null) {
				// If the Stage Display Panel has the same layout already
				if(!stageDisplayPanel.getCurrentLayout().equals(currentLayout)) {
					setLayout(displayLayouts);
				} else {
					System.out.println("Layout: "+currentLayout+" already applied...");
				}
			} else {
				setLayout(displayLayouts);
			}
		}
	}
	
	private void setLayout(DisplayLayouts displayLayouts) {
		
		String currentLayout = displayLayouts.getSelected();
		
		for (DisplayLayout layout : displayLayouts.getLayouts()) {
	        if (layout.getIdentifier().equals(currentLayout)) {
	        	displayLayout = layout;
	        	
	        	System.out.println("Setting Layout: "+layout.getIdentifier());
	        	
            	expectedWidth = layout.getWidth();
	        	expectedHeight = layout.getHeight();
	        	
	        	stageDisplayPanel.setLayout(layout, expectedWidth, expectedHeight);
	        	window.repaint();
	        	window.revalidate();
	        	System.out.println("Changed panel contents...");
	        }
	    }
	}
	
	public void updateData(StageDisplayData displayData) {
		
		StageDisplayFields stageDisplayFields = displayData.getStageDisplayDataFields();
		if (Main.props.getProperty("auto-layout").toLowerCase().contains("yes")) {
			addedFrames = stageDisplayPanel.getAddedFrames();
		}
		
		for (HashMap.Entry<String, JLabel> frame : addedFrames.entrySet()) {
		    for (StageDisplayField field : stageDisplayFields.getFields()) {
			    if(frame.getKey().equals(field.getIdentifier())) {
			    	frame.getValue().setText(field.getValue());
			    	
			    	// If this current field is the Clock, Message, or Video Counter, apply some formatting
			    	if ((field.getIdentifier().equals("Clock") || field.getIdentifier().equals("Message") || field.getIdentifier().equals("VideoCounter")) && Main.props.getProperty("auto-layout").toLowerCase().contains("yes")) {
			    		// Set the text to Center
			    		frame.getValue().setHorizontalAlignment(SwingConstants.CENTER);
			    		frame.getValue().setVerticalAlignment(SwingConstants.CENTER);
			    		
				    	for(DisplayLayoutFrame layoutFrame : displayLayout.getFrames()) {
				    		if(layoutFrame.getIdentifier().equals(field.getIdentifier())) {
				    			adaptFont(frame.getValue(), layoutFrame.getFontSize());
				    		}
				    	}
			    	}
			    	
			    	// If this current field is the Message, flash the message
			    	if(field.getIdentifier().equals("Message")) {
			    		// If this current message is not the same as the last message, flash the display
			    		if (!(lastMessage.equals(field.getValue())) && !(field.getValue().equals(" "))) {
			    			// Flash the Label
			    			if (Main.props.getProperty("auto-layout").toLowerCase().contains("yes")) {
			    				flashLabel(frame.getValue(), stageDisplayPanel.getMessageFlashColour());
			    			} else {
			    				flashLabel(frame.getValue(), Main.getFlashColour());
			    			}
			    			
			    			
			    			lastMessage = field.getValue();
			    		} else if (field.getValue().equals(" ")) {
			    			lastMessage = field.getValue();
			    		}
			    	} 
			    }
			}
		}
	}
	
	public void disconnected() {
		if (addedFrames != null) {
			for (HashMap.Entry<String, JLabel> frame : addedFrames.entrySet()) {
			    
			    switch (frame.getKey()) {
			    case "CurrentSlide":
			    	frame.getValue().setText(editNewLine("Attempting Connection to:"+"\n"+Main.PP.getHost()+":"+Main.PP.getPort()+"\n"+"Press Q to Close"));
			    	break;
			    case "NextSlide":
			    	frame.getValue().setText(" ");
			    	break;
			    case "Message":
			    	frame.getValue().setText("[Error:] ProPresenter Unavailable - Check Network");
			    	break;
			    case "Clock":
			    	frame.getValue().setText("- - : - - --");
			    	break;
			    case "VideoCounter":
			    	frame.getValue().setText("- -:- -:- -");
			    	break;
			    }
			}
		}
	}
	
	private void adaptFont(JLabel label, int maxSize) {
		
		Font labelFont = label.getFont();
		String labelText = label.getText();

		int stringWidth = label.getFontMetrics(labelFont).stringWidth(labelText);
		int stringHeight = label.getFontMetrics(labelFont).getHeight();
		int componentWidth = label.getWidth();
		int componentHeight = label.getHeight();
		
		// Find out how much the font can grow in width.
		double widthRatio = (double)componentWidth / ((double)stringWidth+5);
		
		// Find out how much the font can grow in height.
		double heightRatio = (double)componentHeight / ((double)stringHeight+5);
		
		int newWidthFontSize = (int)(labelFont.getSize() * widthRatio);
		int newHeightFontSize = (int)(labelFont.getSize() * heightRatio);
		
		// Pick a new font size so it will not be larger than the height of label.
		int recSize = Math.min(newWidthFontSize-5, newHeightFontSize);
		
		// Compare the recommended size with the max size
		int fontSizeToUse = Math.min(maxSize, recSize);

		// Set the label's font size to the newly determined size.
		label.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
	}
	
	private void flashLabel(JLabel label, Color flashColour) {
		label.setOpaque(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
	            int counter = 0;
	            while (counter < 3) {
	        		label.setBackground(flashColour);
	        		window.revalidate();
	        		sleep(500);
	        		label.setBackground(Color.BLACK);
	        		window.revalidate();
	        		sleep(500);
	        		counter++;
	            }
        	}
        }).start();
	}
	
	private String editNewLine(String text) {
		text = "<html>" + text.replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br/>") + "</html>";
		return text;
	}
	
	private TitledBorder setBorder(String text, Border newBorder, Font titleFont, MatteBorder mainGrayBorder) {
		TitledBorder border = BorderFactory.createTitledBorder(newBorder, text);
	    border.setTitleColor(Color.GRAY);
	    border.setTitleFont(titleFont);
	    border.setBorder(mainGrayBorder);
	    border.setTitlePosition(TitledBorder.TOP);
	    return border;
	}
	
	// Add KeyListener for exit of StageToStage
	private KeyListener setKeyListener() {
		
		KeyListener listener = new KeyListener() {
			 
			@Override
			public void keyPressed(KeyEvent event) {
			}
			 
			@Override
			public void keyReleased(KeyEvent event) {
			    parseEventInfo(event);
			}
			
			@Override
			public void keyTyped(KeyEvent event) {
			}
			
			private void parseEventInfo(KeyEvent e) {
				if (e.getKeyCode() == 'Q') {
					System.exit(0);
				}
			}
		};
		return listener;
	}
}