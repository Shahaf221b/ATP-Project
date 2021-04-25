package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class SimpleDecompressorInputStream extends MyDecompressorInputStream {

    public SimpleDecompressorInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public int read(byte[] b) throws IOException {
        byte[] bytes =this.in.readAllBytes();
        byte[] newArray = Arrays.copyOfRange(bytes, 0, 18);
        byte[] matrixContent = new byte[20]; //TODO:change
        int m=0;
        //return cells values
        int mod = 0, count;
        for (int index = 18; index < bytes.length; index++) {
            if (index % 2 == mod) {
                count = bytes[index];
                while (count > 0) {
                    matrixContent[m] = 0;
                    count -= 1;
                    m++;
                }
            } else {
                count = bytes[index];
                while (count > 0) {
                    matrixContent[m] = 1;
                    count -= 1;
                    m++;
                }
            }
        }
        byte[] result = new byte[newArray.length + matrixContent.length];
        System.arraycopy(newArray, 0, result, 0, newArray.length);
        System.arraycopy(matrixContent, 0, result, newArray.length, matrixContent.length);
        b=result;
        return 0;
    }
}
