package mainframe;

import java.util.Hashtable;

import javax.swing.JFrame;

public abstract class FrameAdapter extends JFrame {
	
	public abstract void setText(String text);
	public abstract Hashtable getUserTable();
	public abstract void updateUserList();

}
