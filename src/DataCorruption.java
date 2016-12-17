import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Kwiat Bombaju on 14.10.2016.
 */
public class DataCorruption {
    private List<Integer> byteArray = new ArrayList<>();
    private Random generator = new Random();
    private int randomByteIndex;
    private int randomBitIndex;
    private Integer rolledByte;
    private int[] bitCorrupctionArray = {1, 2, 4, 8, 16, 32, 64, 128};


    public DataCorruption(List<Integer> byteArray){
        this.byteArray = byteArray;
    }

    private void rollRandomOneByteIndex(){
        randomByteIndex = generator.nextInt(byteArray.size());
    }

    private void rollRandomBitIndex(){
        randomBitIndex = generator.nextInt(8);
    }

    private void performSingleBitDataCorrupction(){
        rollRandomOneByteIndex();
        rollRandomBitIndex();

        rolledByte = byteArray.get(randomByteIndex);

//        System.out.println( Integer.toString(rolledByte,2));
        int corruptedByte = rolledByte ^ bitCorrupctionArray[randomBitIndex]; //DATA CORRUPCTION HAPPENS HERE
//        System.out.println( Integer.toString(corruptedByte,2) + "\n");
        byteArray.set(randomByteIndex, corruptedByte);

    }

    public List<Integer> howManyTimesCourruptData(int j){
        while(j != 0){
            performSingleBitDataCorrupction();
            j--;
        }
        return byteArray;
    }
}
