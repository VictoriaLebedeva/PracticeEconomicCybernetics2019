package bsu.fpmi.educational_practice2019;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Form extends Panel {   
    protected String text;
    protected String messageText;  
    protected int alignment;
    protected String buttonLabel;     
    protected Label message;
    protected Button button;
    protected TextField textField;    
  
    public Form() { this("Your\nMessage\nHere", "Input smth"); }

    public Form(String messageText, String textField) { 
    	this(messageText, Label.LEFT, "Button", textField);
    }
      
    public Form(String messageText, int alignment, String buttonLabel, String field) {
    	setLayout(new BorderLayout(15, 15));
    	message = new Label(messageText, alignment); 
    	add(message, BorderLayout.CENTER);	
    	Panel buttonbox = new Panel();
    	buttonbox.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 15));
    	add(buttonbox, BorderLayout.SOUTH);
    	button = new Button();                  
    	textField = new TextField(field);    	
    	System.out.println(textField.getText());    	
    	buttonbox.add(textField);    	
    	buttonbox.add(button);
       	button.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
			    fireEvent(new AnswerEvent(Form.this,  Form.this.textField.getText(),
						      AnswerEvent.ACCEPT));
			    setText(Form.this.textField.getText());
			}
		});

    	
    	textField.addKeyListener(new KeyListener() {
    		public void keyPressed(KeyEvent e) {
    			 fireEvent(new AnswerEvent(Form.this, Form.this.textField.getText(), AnswerEvent.WRITE));     
            }

			@Override
			public void keyReleased(KeyEvent e) {
								
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}
    	});
			
		setMessageText(messageText);
		setAlignment(alignment);
		setButtonLabel(buttonLabel);
    }
  
    public String getMessageText() { return messageText; }
    public int getAlignment() { return alignment; }
    public String getButtonLabel() { return buttonLabel; }
    public void setMessageText(String messageText) {
		this.messageText = messageText;
		message.setText(messageText);
		validate();
    }
    
    private void setText(String text) {
		this.text = text;
    }
    
    private String getText() {
		return this.text;
    }

    public void setAlignment(int alignment) {
		this.alignment = alignment;
		message.setAlignment(alignment);
    }

    public void setButtonLabel(String l) {
		buttonLabel = l;
		button.setLabel(l);
		button.setVisible((l != null) && (l.length() > 0));
		validate();
    }

    public void setFont(Font f) {
		super.setFont(f);    
		message.setFont(f);  
		button.setFont(f);
		textField.setFont(f);
		validate();
    }
    
    protected Vector<AnswerListener> listeners = new Vector<AnswerListener>();
    
    public void addAnswerListener(AnswerListener l) {
    	listeners.addElement(l);
    }
    
    public void removeAnswerListener(AnswerListener l) {
    	listeners.removeElement(l);
    }

    public void fireEvent(AnswerEvent e) {
	Vector list = (Vector) listeners.clone();
	for(int i = 0; i < list.size(); i++) {
	    AnswerListener listener = (AnswerListener)list.elementAt(i);
	    switch(e.getID()) {
		    case AnswerEvent.ACCEPT: listener.accept(e); break;
		    case AnswerEvent.WRITE:  listener.write(e); break;
	    }
	}
    }
  
  
    public static void main(String[] args) {
		Form p = new Form("Do you really want to quit?", "hhh");
		p.addAnswerListener(new AnswerListener() {
			public void write(AnswerEvent e) { 
				System.out.println("write");
			}
			public void accept(AnswerEvent e) {
				System.out.println("accept"); 
				System.exit(0);
			}
		});	
		Frame f = new Frame();
		f.add(p);
		f.pack();
		f.setVisible(true);
    }
}