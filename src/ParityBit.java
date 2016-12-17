import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kwiat Bombaju on 14.10.2016.
 */
public class ParityBit {
    private List<Integer> byteArray = new ArrayList<>();
    private int[] bitArray = {1, 2, 4, 8, 16, 32, 64, 128};
    private int counter = 0;;

    public ParityBit(List<Integer> byteArray){
        this.byteArray = byteArray;
    }

    public int calculateParityBit(){
        for (int i = 0; i < byteArray.size(); i++){
            int selectedByte = byteArray.get(i);
            if (0 != (selectedByte & bitArray[0])) ++counter;
            if (0 != (selectedByte & bitArray[1])) ++counter;
            if (0 != (selectedByte & bitArray[2])) ++counter;
            if (0 != (selectedByte & bitArray[3])) ++counter;
            if (0 != (selectedByte & bitArray[4])) ++counter;
            if (0 != (selectedByte & bitArray[5])) ++counter;
            if (0 != (selectedByte & bitArray[6])) ++counter;
        }
        System.out.println("Parity bit result " + counter % 2);
        return counter % 2;
    }
}
