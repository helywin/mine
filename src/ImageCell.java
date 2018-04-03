import javax.swing.*;
/**
 * Created by hacwell on 2017/6/9.
 */
public class ImageCell extends JLabel{
 /*     file path:
 *     pic_blank = "/resource/0.png"
 *     pic_num_1 = "/resource/1.png"
 *     pic_num_2 = "/resource/2.png"
 *     pic_num_3 = "/resource/3.png"
 *     pic_num_4 = "/resource/4.png"
 *     pic_num_5 = "/resource/5.png"
 *     pic_num_6 = "/resource/6.png"
 *     pic_num_7 = "/resource/7.png"
 *     pic_num_8 = "/resource/8.png"
 *     pic_mine = "/resource/9.png"
 *     pic_click_what = "/resource/10.png"
 *     pic_not_mine = "/resource/11.png"
 *     pic_click_mine = "/resource/12.png"
 *     pic_what = "/resource/13.png"
 *     pic_flag = "/resource/14.png"
 *     pic_click_blank = "/resource/15.png";
 *     realVal realVals include 0-10
 */
    int realVal;
    boolean isClicked;
    boolean isDiged;
    static boolean isLost;
    static final String path = System.getProperty("user.dir");
    public ImageCell(Integer index){
        super();
        isClicked = false;
        realVal = index;
        setIcon(new ImageIcon(path + "\\resource\\0.png"));
        setText(null);
    }
    public void realVal(){
        if(!isClicked) {
            if (realVal == 0)
                setIcon(new ImageIcon(path + "\\resource\\15.png"));
            else{
                if(realVal == 9){
                    setIcon(new ImageIcon(path + "\\resource\\12.png"));
                    isLost = true;
                }
                else
                    setIcon(new ImageIcon(path + "\\resource\\" + realVal + ".png"));
            }

            isClicked = true;
        }
    }

    public void pressed(){
        if(!isClicked)
            setIcon(new ImageIcon(path + "\\resource\\15.png"));
    }
    public void digMine(){
        if(!isClicked) {
            if (!isDiged) {
                if (realVal != 9) {
                    setIcon(new ImageIcon(path + "\\resource\\11.png"));
                    isLost = true;
                }
                else
                    setIcon(new ImageIcon(path + "\\resource\\9.png"));
            }
        }
    }
    public static void resetStatu(){
        isLost = false;
    }
    public static boolean isLost(){
        return isLost;
    }
}
