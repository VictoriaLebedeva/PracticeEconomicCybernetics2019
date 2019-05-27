package bsu.fpmi.educational_practice2019;

public class AnswerEvent extends java.util.EventObject {
    public static final int ACCEPT = 0, WRITE = 1;  
    protected int id;
    protected String message;									
    public AnswerEvent(Object source, String message, int id) {
		super(source);
		this.id = id;
		this.message = message;
    }
    public int getID() { return id; }       
    public String getMessage() { return this.message; } 
}