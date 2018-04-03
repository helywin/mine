import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hacwell on 2017/6/8.
 */
public class GameGui {
    GameData data;
    JFrame frame;
    JPanel content;
    ImageIcon gameIcon;
    JMenuBar menuBar;
    ArrayList<JMenu> menus;
    JPanel statu;
    JLabel mineNum;
    JLabel face;
    JLabel time;
    JPanel gameZone;
    FlowLayout layout;
    ArrayList<JLabel> cells;
//    final EmptyBorder border = new EmptyBorder(10,10,10,10);
    static final String path = System.getProperty("user.dir");
    int rows;
    int cols;
    Integer mines;
    Integer minesNow;
    int gameStatu;
    //0:running 1:lose 2:win
    Date init;
    MineTimer timer;
    Thread thread;
    GameDialog dialog;
    public static void main(String[] args){
        GameGui gui = new GameGui();
        gui.init();
    }
    public GameGui(){
        gameStatu = 0;
        rows = 9;
        cols = 9;
        mines = 9;
        data = new GameData(rows,cols,mines);
        gameZone = new JPanel();
        frame = new JFrame();
        content = new JPanel();
        gameIcon = new ImageIcon(path+"\\resource\\mine.png");
        menuBar = new JMenuBar();
        menus = new ArrayList<JMenu>();
        statu = new JPanel();
        mineNum = new JLabel("雷数:9");
        face = new JLabel("^_^");
        time = new JLabel("时间:0");
        cells = new ArrayList<JLabel>();
    }

    public void init(){
        frame.setIconImage(gameIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.getContentPane().add(content);
//        frame.getContentPane().setBorder(border);
        frame.getContentPane().setBackground(new Color(200,200,200));
        frame.setBackground(new Color(200,200,200));
        setMenuBar();
        frame.setJMenuBar(menuBar);
        frame.setResizable(false);
        frame.getContentPane().add(BorderLayout.NORTH,statu);
        frame.addFocusListener(new FrameFoucsListener());
//        GridBagConstraints s = new GridBagConstraints();
        layout = new FlowLayout(FlowLayout.CENTER);
        layout.setHgap(frame.getWidth()/5); //change if frame size has changed
        statu.setLayout(layout);
        statu.setBackground(new Color(255,255,255));
        statu.add(mineNum);
        statu.add(face);
        statu.add(time);
        face.addMouseListener(new FaceListener());
        restartGame();
        frame.setLocation(500,250);
        frame.setVisible(true);
    }
    public void setMenuBar(){
        menuBar.setBackground(new Color(255,255,255));
        String[][] menuTitles = {{ "游戏", "G" }, { "帮助", "H" }};
        for (String[] menuTitle : menuTitles) {
            menus.add(new JMenu(menuTitle[0], false));
            menus.get(menus.size() - 1).setMnemonic(menuTitle[1].toCharArray()[0]);
        }
        menus.get(0).add(new JMenuItem("开局"));
        menus.get(0).addSeparator();
        menus.get(0).add(new JMenuItem("初级"));
        menus.get(0).add(new JMenuItem("中级"));
        menus.get(0).add(new JMenuItem("高级"));
        menus.get(0).add(new JMenuItem("自定义"));
        menus.get(0).addSeparator();
        menus.get(0).add(new JMenuItem("退出"));

        menus.get(1).add(new JMenuItem("目录"));
        menus.get(1).add(new JMenuItem("关于扫雷"));
        menuBar.add(menus.get(0));
        menuBar.add(menus.get(1));

        MenuListener menuListener = new MenuListener();
        menus.get(0).getItem(0).addActionListener(menuListener);
        menus.get(0).getItem(2).addActionListener(menuListener);
        menus.get(0).getItem(3).addActionListener(menuListener);
        menus.get(0).getItem(4).addActionListener(menuListener);
        menus.get(0).getItem(5).addActionListener(menuListener);
        menus.get(0).getItem(7).addActionListener(menuListener);

        menus.get(1).getItem(0).addActionListener(menuListener);
        menus.get(1).getItem(1).addActionListener(menuListener);

    }
    public void resetGui(){}
    public class CellMouseListener extends MouseAdapter implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(gameStatu==0&&e.getButton() == MouseEvent.BUTTON1){
                ImageCell src = (ImageCell)e.getSource();
                src.pressed();
                face.setText(">_<");
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (gameStatu==0&&e.getButton() == MouseEvent.BUTTON3) {
                ImageCell src = (ImageCell) e.getSource();
                src.pressed();
                src.digMine();
                face.setText("^_^");
                if(ImageCell.isLost()) {
                    gameOver();
                }else if(src.realVal==9){
                    minesNow = minesNow-1;
                    mineNum.setText("雷数："+minesNow.toString());
                    frame.repaint();
                    if(minesNow == 0)
                        gameWin();
                }
            }
            if(gameStatu==0&&e.getButton() == MouseEvent.BUTTON1){
                ImageCell src = (ImageCell)e.getSource();
                src.realVal();
                face.setText("^_^");
                if(ImageCell.isLost()) {
                    gameOver();
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
    public void gameOver(){
        gameStatu = 1;
        timer.stop();
        face.setText("o_o");
    }

    public void gameWin(){
        gameStatu = 2;
        timer.stop();
        face.setText("?ω?");
    }

    public void restartGame(){
        if(mines>rows*cols) {
            mines = rows*2;
        }
        if(timer!=null)
        timer.stop();
        time.setText("时间:0");
        gameStatu = 0;
        minesNow = mines;
        data = new GameData(rows,cols,mines);
        ImageCell.resetStatu();
        frame.getContentPane().remove(gameZone);
        gameZone = new JPanel();
        gameZone.setBackground(new Color(200,200,200));
        GridLayout grid = new GridLayout(rows,cols);
        grid.setVgap(0);
        grid.setHgap(0);
        CellMouseListener mouseListener = new CellMouseListener();
        gameZone.setLayout(grid);
        for (int i = 0;i<rows*cols;i++){
            ImageCell imagecell = new ImageCell(data.get(i));
            imagecell.addMouseListener(mouseListener);
            gameZone.add(imagecell);
        }
        mineNum.setText("雷数:"+mines.toString());
        frame.setSize(20+cols*16,90+rows*16);
        layout.setHgap(frame.getWidth()/16);
        frame.getContentPane().add(BorderLayout.CENTER,gameZone);
        frame.repaint();
        frame.revalidate();
        timer = new MineTimer();
        thread = new Thread(timer);
        thread.start();
    }
    public class MenuListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("开局")){restartGame();}
            if(e.getActionCommand().equals("初级")){
                rows = 9;
                cols = 9;
                mines = 10;
                restartGame();
            }
            if(e.getActionCommand().equals("中级")){
                rows = 16;
                cols = 16;
                mines = 40;
                restartGame();
            }
            if(e.getActionCommand().equals("高级")){
                rows = 16;
                cols = 30;
                mines = 99;
                restartGame();
            }
            if(e.getActionCommand().equals("自定义")){
                dialog = new GameDialog(frame);
                dialog.setInitVal(rows,cols,mines);
                dialog.setVisible(true);
            }
            if(e.getActionCommand().equals("退出")){
                System.exit(0);
            }
        }
    }
    public class MineTimer implements Runnable , ActionListener{
        Timer timer;
        @Override
        public void run() {
            timer = new Timer(1000,this);
            timer.start();
            init = new Date();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            long interval = new Date().getTime() - init.getTime();
            Integer s = (int) interval/1000;
            time.setText("时间:"+s.toString());
            time.repaint();
        }
        public void stop(){
            timer.stop();
        }
    }
    public class FrameFoucsListener implements FocusListener{

        @Override
        public void focusGained(FocusEvent e) {
            if(dialog!=null&&dialog.isOk()){
                rows = dialog.rows;
                cols = dialog.cols;
                mines = dialog.mineNum;
                restartGame();
            }
        }

        @Override
        public void focusLost(FocusEvent e) {

        }
    }
    public class FaceListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            face.setText(">_<");
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            restartGame();
            face.setText("^_^");
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
