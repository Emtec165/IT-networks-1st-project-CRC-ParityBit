import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException{
        Scanner userInput = new Scanner(System.in);
        int readerSize = 10;
        int writerSize = 10;
        BufferedReader in = new BufferedReader(new FileReader("pliktekstowydopierwszegoprojektu.txt"), readerSize);
        BufferedWriter out = new BufferedWriter(new FileWriter("outagain.txt"), writerSize);
        List<Integer> myList = new ArrayList<>();
        int howManyTimesCorruptBits;
        int selectMethod;
        int parityBitResult1 = 0, parityBitResult2 = 0;
        boolean[] crcPolynomial = {true, false, true, true};

        // TODO Auto-generated method stub
        System.out.println("Select method!\n\n1. Parity bit\n2. CRC\n");
        selectMethod = userInput.nextInt();

        try {
            int dataInReader;
            while ((dataInReader = in.read()) != -1) {
                myList.add(dataInReader);
//                System.out.println(Integer.toString(dataInReader,2));
            }
        } finally {
            System.out.println(myList);
            System.out.println("Data without corrupction:");
            for (int i = 0; i < myList.size(); i++){//wypisywanie danych w systemie binarnym
                System.out.print(Integer.toString(myList.get(i),2));
            }
            System.out.println();


            if (selectMethod == 1){
                System.out.println("Selected: Parity bit!");

                ParityBit parityBit = new ParityBit(myList);
                parityBitResult1 = parityBit.calculateParityBit();

                System.out.println("Data with appended result");//wypisywanie danych w systemie binarnym
                for (int i = 0; i < myList.size(); i++){
                    System.out.print(Integer.toString(myList.get(i),2));
                }
                System.out.println(parityBitResult1);
            }

            else {
//                myList = new ArrayList<>();//dane testowe
//                myList.add(52);
//                myList.add(238);

                System.out.println("Selected: CRC!");

                CRC crc = new CRC(myList, myList.size(), crcPolynomial.length - 1, crcPolynomial);
                myList = crc.calculateCRC(true);
            }

            System.out.println("How many times corrupt bits?");
            howManyTimesCorruptBits = userInput.nextInt();

            DataCorruption dataCorrupter = new DataCorruption(myList);
            dataCorrupter.howManyTimesCourruptData(howManyTimesCorruptBits);
            System.out.println(myList);

            if (selectMethod == 1){
                ParityBit parityBit = new ParityBit(myList);
                parityBitResult2 = parityBit.calculateParityBit();
                if (parityBitResult1 == parityBitResult2){
                    System.out.println("Parity bit results match");
                }
                else {
                    System.out.println("Parity bit results are different");
                }
            }

            else {
                CRC crc = new CRC(myList, myList.size(), crcPolynomial.length - 1, crcPolynomial);
                myList = crc.calculateCRC(false);
            }

            for (int i = 0; i < myList.size(); i++ ){
                out.write(myList.get(i));
            }
            out.close();
        }
    }
}