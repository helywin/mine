import java.util.*;

/**
 * Created by hacwell on 2017/6/8.
 */
public class GameData {
    ArrayList<Integer> data;
    HashSet<Integer> mines;
    int rows;
    int cols;
    int mineNum;
    public GameData(int r, int c, int m){
        rows = r;
        cols = c;
        mines = new HashSet<Integer>();
        mineNum = m;
        generateCells();
    }
    public void generateCells(){
        data = new ArrayList<Integer>();
        for(int i=0; i<rows*cols; i++){
            data.add(0);
        }
        while(mines.size()<mineNum){
            Random num =new Random();
            mines.add(num.nextInt(rows*cols));
        }
        for(Integer i: mines){
            int x;
            int y;
            if((x = i%cols) <0)
                x=x+cols;
            y = (i-x)/cols;

            if(x == 0){
                if(y == 0){
                    data.set(i+1,data.get(i+1)+1);
                    data.set(i+cols,data.get(i+cols)+1);
                    data.set(i+cols+1,data.get(i+cols+1)+1);
                } else if(y == rows-1){
                    data.set(i-cols+1,data.get(i-cols+1)+1);
                    data.set(i-cols,data.get(i-cols)+1);
                    data.set(i+1,data.get(i+1)+1);
                } else {
                    data.set(i+1,data.get(i+1)+1);
                    data.set(i+cols,data.get(i+cols)+1);
                    data.set(i+cols+1,data.get(i+cols+1)+1);
                    data.set(i-cols+1,data.get(i-cols+1)+1);
                    data.set(i-cols,data.get(i-cols)+1);
                }
            }else if(x == cols-1){
                if(y == 0){
                    data.set(i-1,data.get(i-1)+1);
                    data.set(i+cols,data.get(i+cols)+1);
                    data.set(i+cols-1,data.get(i+cols-1)+1);
                }else if(y == rows-1){
                    data.set(i-cols-1,data.get(i-cols-1)+1);
                    data.set(i-cols,data.get(i-cols)+1);
                    data.set(i-1,data.get(i-1)+1);
                }else{
                    data.set(i-1,data.get(i-1)+1);
                    data.set(i+cols,data.get(i+cols)+1);
                    data.set(i+cols-1,data.get(i+cols-1)+1);
                    data.set(i-cols-1,data.get(i-cols-1)+1);
                    data.set(i-cols,data.get(i-cols)+1);
                }
            }else {
                if(y == 0){
                    data.set(i+cols,data.get(i+cols)+1);
                    data.set(i+1,data.get(i+1)+1);
                    data.set(i+cols+1,data.get(i+cols+1)+1);
                    data.set(i-1,data.get(i-1)+1);
                    data.set(i+cols-1,data.get(i+cols-1)+1);
                }else if( y == cols-1){
                    data.set(i-cols+1,data.get(i-cols+1)+1);
                    data.set(i-cols,data.get(i-cols)+1);
                    data.set(i+1,data.get(i+1)+1);
                    data.set(i-cols-1,data.get(i-cols-1)+1);
                    data.set(i-1,data.get(i-1)+1);
                }else{
                    data.set(i+cols,data.get(i+cols)+1);
                    data.set(i+1,data.get(i+1)+1);
                    data.set(i+cols+1,data.get(i+cols+1)+1);
                    data.set(i-1,data.get(i-1)+1);
                    data.set(i+cols-1,data.get(i+cols-1)+1);
                    data.set(i-cols+1,data.get(i-cols+1)+1);
                    data.set(i-cols,data.get(i-cols)+1);
                    data.set(i-cols-1,data.get(i-cols-1)+1);
                }
            }
        }
        for(Integer i:mines){
            data.set(i,9);
        }
    }
    public void resetData(int r, int c, int m){
        rows = r;
        cols = c;
        mineNum = m;
        generateCells();
    }
    public int getSize(){
        return rows*cols;
    }
    public Integer get(int index){
        return data.get(index);
    }
}

