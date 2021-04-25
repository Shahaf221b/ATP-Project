package IO;

import algorithms.mazeGenerators.Position;
import algorithms.search.MazeState;

import java.io.IOException;
import java.io.OutputStream;

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
        int numOfRows = b[0]*b[1]+b[2];
        int numOfColumns = b[3]*b[4]+b[5];
        byte[] matrixContent= new byte[numOfRows*numOfColumns]; //TODO:change
        int[] matrixContentHelper={0,0};
        int count,cur_index=18;
        if(b[cur_index]!=0){
            matrixContent[0] =0;
        }
        while(cur_index<b.length){
            matrixContentHelper = countSequence(cur_index,b);
            count = matrixContentHelper[0];
            if(count>255){
                covertSequenceIntToByte(matrixContent,count);
            }
            else{
                matrixContent[matrixContent.length-1] = (byte) count;
            }
            cur_index = matrixContentHelper[1];
        }
        byte[] result = new byte[b.length+matrixContent.length];
        System.arraycopy(b,0,result,0,b.length);
        System.arraycopy(matrixContent,0,result,b.length,matrixContent.length);
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

    private void covertSequenceIntToByte(byte[] matrixContent,int count){

        matrixContent[matrixContent.length] = (byte) 255;
        matrixContent[matrixContent.length] =0;
        int temp = count-255;
        while(temp>255){
            matrixContent[matrixContent.length] = (byte) 255;
            matrixContent[matrixContent.length]=0;
            temp -= 255;
        }
        matrixContent[matrixContent.length] = (byte) temp;

    }
}
