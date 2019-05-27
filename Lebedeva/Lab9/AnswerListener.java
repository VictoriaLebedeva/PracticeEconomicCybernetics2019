package bsu.fpmi.educational_practice2019;

public interface AnswerListener extends java.util.EventListener {
    public void write(AnswerEvent e);
    public void accept(AnswerEvent e);
}