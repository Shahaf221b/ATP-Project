package IO;

import algorithms.mazeGenerators.Position;
import algorithms.search.MazeState;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleCompressorOutputStream extends MyCompressorOutputStream{

    public SimpleCompressorOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    /**
     * @param b byte array that holds all the information about specific maze
     * structure: {r1,r2,r3,c1,c2,c3,s1,s2,s3,s4,s5,s6,e1,e2,e3,e4,e5,e6,matrix content}
     *      * where:
     *      * r1*r2+r3 = maze's rows
     *      * c1*c2+c3 = maze's columns
     *      * s1*s2+s3 = maze's start position row
     *      * s4*s5+s6 = maze's start position column
     *      * e1*e2+e3 = maze's goal position row
     *      * e4*e5+e6 = maze's goal position column
     *      * matrix content will be a sequence of numbers that represented the contents of the maze matrix.
     *this simple compressor will compress the matrix:
     *      * when going through the maze in the order of the lines from left to right.
     *      * The first digit represents how many times the digit 0 appears in sequence starting from position (0,0).
     *      * The second digit represents how many times the digit 1 appears afterwards, etc ..
     *
     */
    @Override
    public void write(byte[] b)throws IOException {
        //no need to change first 18 elements given in b
        //get maze dimensions
        List<Byte> matrixContent = new ArrayList<>();
        int numOfRows = ((int)b[0]&255)*((int)b[1]&255)+((int)b[2]&255);
        int numOfColumns = ((int)b[3]&255)*((int)b[4]&255)+((int)b[5]&255);
//        byte[] matrixContent= {}; //TODO:change
        int[] matrixContentHelper={0,0};
        int count,cur_index=18;
        if(b[cur_index]!=0){
            matrixContent.add((byte) 0);
        }
        while(cur_index<b.length){
            matrixContentHelper = countSequence(cur_index,b);
            count = matrixContentHelper[0];
            if(count>255){
                covertSequenceIntToByte(matrixContent,count);
            }
            else{
                matrixContent.add((byte) count);
            }
            cur_index = matrixContentHelper[1];
        }

        byte[] newArr = Arrays.copyOfRange(b, 0, 18);
        byte[] result = new byte[newArr.length+matrixContent.size()];
        System.arraycopy(newArr,0,result,0,newArr.length);
        byte[] matrixContentToArray = new byte[matrixContent.size()];
        for(int x=0; x<matrixContent.size(); x++){
            matrixContentToArray[x] = matrixContent.get(x);
        }
        System.arraycopy(matrixContentToArray,0,result,newArr.length,matrixContentToArray.length);

        System.out.println("after Simple compressor: "+Arrays.toString(result));
        this.out.write(result);

    }

    private int[] countSequence(int cur_index,byte[] bytes){
        int val = bytes[cur_index],count=0;
        while( cur_index<bytes.length && bytes[cur_index]==val){
            count++;
            cur_index++;
        }
        return new int[]{count, cur_index};
    }

    private void covertSequenceIntToByte(List<Byte> matrixContent, int count){

        matrixContent.add((byte) 255);
        matrixContent.add((byte)0);
        int temp = count-255;
        while(temp>255){
            matrixContent.add((byte) 255);
            matrixContent.add((byte)0);
            temp -= 255;
        }
        matrixContent.add((byte) temp);

    }
}
