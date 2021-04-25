package IO;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleDecompressorInputStream extends MyDecompressorInputStream {

    public SimpleDecompressorInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    public int read(byte[] b) throws IOException {
        byte[] bytes = this.in.readAllBytes();
        byte[] newArray = Arrays.copyOfRange(bytes, 0, 18);
        List<Byte> matrixContent = new ArrayList<>();
        //return cells values
        int mod = 0, count;
        for (int index = 18; index < bytes.length; index++) {
            if (index % 2 == mod) {
                count = bytes[index];
                while (count > 0) {
                    matrixContent.add((byte) 0);
                    count -= 1;
                }
            } else {
                count = bytes[index];
                while (count > 0) {
                    matrixContent.add((byte) 1);
                    count -= 1;
                }
            }
        }

        byte[] result = new byte[newArray.length + matrixContent.size()];
        System.arraycopy(newArray, 0, result, 0, newArray.length);
        byte[] matrixContentToArray = new byte[matrixContent.size()];
        for (int x = 0; x < matrixContent.size(); x++) {
            matrixContentToArray[x] = matrixContent.get(x);
        }
        System.arraycopy(matrixContentToArray, 0, result, newArray.length, matrixContentToArray.length);
        System.arraycopy(result, 0, b, 0, result.length);
        return result.length;
    }
}
