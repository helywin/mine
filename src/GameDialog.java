import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hacwell on 2017/6/10.
 */
public class GameDialog extends JDialog {
    boolean isOk;
    JLabel height;
    JLabel width;
    JLabel mines;
    JTextField inRows;
    JTextField inCols;
    JTextField inMines;
    JButton ok;
    JButton cancel;
    int rows;
    int cols;
    int mineNum;
    boolean isOK;
    public GameDialog(Frame owner) {
        super(owner);
        setTitle("自定义");
        height = new JLabel("高度");
        width = new JLabel("宽度");
        mines = new JLabel("雷数");
        inRows = new JTextField();
        inCols = new JTextField();
        inMines = new JTextField();
        ok = new JButton("确认");
        cancel = new JButton("取消");
        rows = 0;
        cols = 0;
        mineNum = 0;
        isOk = false;
        buildGui();
        setLocation(500,250);
    }
    public void buildGui(){
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints s = new GridBagConstraints();
        setLayout(layout);
        this.add(height);
        this.add(inRows);
        this.add(width);
        this.add(inCols);
        this.add(mines);
        this.add(inMines);
        this.add(ok);
        this.add(cancel);
        ButtonListener listener = new ButtonListener();
        ok.addActionListener(listener);
        cancel.addActionListener(listener);
        s.fill = GridBagConstraints.BOTH;
        s.insets = new Insets(5,10,5,10);
        s.gridwidth = 1;
        s.gridheight = 1;
        s.weightx = 0;
        s.weighty = 0;
        layout.setConstraints(height,s);
        s.gridwidth = 0;
        layout.setConstraints(inRows,s);
        s.gridwidth=1;
        layout.setConstraints(width,s);
        s.gridwidth=0;
        layout.setConstraints(inCols,s);
        s.gridwidth=1;
        layout.setConstraints(mines,s);
        s.gridwidth=0;
        layout.setConstraints(inMines,s);
        s.gridwidth=1;
        s.gridheight=0;
        layout.setConstraints(ok,s);
        s.gridwidth=0;
        layout.setConstraints(cancel,s);
        setSize(200,180);
        setResizable(false);
        repaint();
    }
    public void setInitVal(Integer r, Integer c, Integer m){
        rows = r;
        cols = c;
        mineNum = m;
        inCols.setText(c.toString());
        inRows.setText(r.toString());
        inMines.setText(m.toString());
        repaint();
    }
    public class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("确认")){
                rows = Integer.parseInt(inRows.getText());
                cols = Integer.parseInt(inCols.getText());
                mineNum = Integer.parseInt(inMines.getText());
                isOk = true;
                setVisible(false);
            }
            if(e.getActionCommand().equals("取消")){
                setVisible(false);
            }
        }
    }
    public boolean isOk(){return isOk;}
}
