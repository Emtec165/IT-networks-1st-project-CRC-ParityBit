import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kwiat Bombaju on 14.10.2016.
 */
public class CRC {
    private List<Integer> byteArray = new ArrayList<Integer>();
    private int[] bitMask = {1, 2, 4, 8, 16, 32, 64, 128};
    private int dataLengthWithoutCRCResult;
    private int crcSize;
    private boolean[] crcPolynomial;
    private boolean[] result;

    public CRC(List<Integer> byteArray, int dataLengthWithoutCRCResult, int crcSize, boolean[] crcPolynomial){
        this.byteArray = byteArray;
        this.dataLengthWithoutCRCResult = dataLengthWithoutCRCResult;
        this.crcSize = crcSize;
        this.crcPolynomial = crcPolynomial;
        this.result = new boolean[crcSize];
    }

    public List<Integer> calculateCRC(boolean calculate){
        int crcResult;
        if (!calculate){//zczytywanie CRC i usuwanie z danych
            crcResult = byteArray.remove(byteArray.size()-1).byteValue();
            byteArray.add(crcResult << 8 - crcSize);
            System.out.println("CRC read from data :" + crcResult);
        }

        int[] byteIntArray = new int[byteArray.size()];// zamiana Integer na int (zajęło mi to duuużo czasu...)
        for (int i=0; i < byteIntArray.length; i++) {
            byteIntArray[i] = byteArray.get(i).intValue();
        }

        List<Boolean> singleBitInCell = new ArrayList<Boolean>();//tworzenie i inicjalizowanie listy typu Bool
        for (int i = 0; i < byteArray.size() * 8; i++){
            singleBitInCell.add(false);
        }

        for (int i = byteArray.size() - 1; i >= 0; i--) {//wypełnianie bitowej listy
            singleBitInCell.set(0 + i*8, ((byteIntArray[i] & bitMask[7]) != 0));
            singleBitInCell.set(1 + i*8, ((byteIntArray[i] & bitMask[6]) != 0));
            singleBitInCell.set(2 + i*8, ((byteIntArray[i] & bitMask[5]) != 0));
            singleBitInCell.set(3 + i*8, ((byteIntArray[i] & bitMask[4]) != 0));
            singleBitInCell.set(4 + i*8, ((byteIntArray[i] & bitMask[3]) != 0));
            singleBitInCell.set(5 + i*8, ((byteIntArray[i] & bitMask[2]) != 0));
            singleBitInCell.set(6 + i*8, ((byteIntArray[i] & bitMask[1]) != 0));
            singleBitInCell.set(7 + i*8, ((byteIntArray[i] & bitMask[0]) != 0));
        }
        printBoolBit(singleBitInCell);

        if (calculate){//w przypadku pierszego obliczania CRC dododanie na koncu danych miejsca na wynik CRC
            addSpaceForResultsBitsAtTheEndOfData(singleBitInCell);
        }
        printBoolBit(singleBitInCell);

        for (int i = 0; i < singleBitInCell.size() - crcPolynomial.length + 1; i++) {
            if (singleBitInCell.get(i) != false) {
                for (int j = 0; j<crcPolynomial.length; ++j) {
                    singleBitInCell.set(j + i, singleBitInCell.get(j + i) ^ crcPolynomial[j]);
                }
            }
            printBoolBit(singleBitInCell);//odkomentuj żeby zobaczyć poszczególne XOR'owanie
        }

        System.out.println("\nCRC result in binary:");
        printBoolBit(singleBitInCell);

        addCRCResultToData(singleBitInCell, calculate);

        return byteArray;
    }

    private void printBoolBit(List<Boolean> tab){
        for (int i = 0; i <tab.size(); i++){
            if (tab.get(i) == true){
                System.out.print(1);
            }
            else {
                System.out.print(0);
            }
        }
        System.out.println();
    }

    private void addSpaceForResultsBitsAtTheEndOfData(List<Boolean> tab){
        for (int i = 0; i < crcSize; i++){
            tab.add(false);
        }
    }

    private boolean[] addCRCResultToData(List<Boolean> tab, boolean calculating){
        int resultInt = 0;
        for (int i = 0; i < crcSize; i++){
            resultInt = (resultInt << 1) + (tab.get(tab.size() - crcSize + i) ? 1 : 0);
            result[i] = tab.get(tab.size() - crcSize + i);
        }

        if (calculating) {
            byteArray.add(resultInt);
        }
        System.out.println("\nCRC result in decimal:\n" + resultInt);

        if (!calculating){
            if (resultInt == 0){
                System.out.println("Calculating CRC twice on the same data gave 0 which means data is not corrupted");
            }
            else{
                System.out.println("Calculating CRC twice on the same data did not gave 0 which means data corrupted");
            }
        }

        return result;
    }
}
