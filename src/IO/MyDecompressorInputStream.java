package IO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.InflaterOutputStream;

public class MyDecompressorInputStream extends InputStream {

    protected InputStream in;

    public MyDecompressorInputStream(InputStream inputStream) {
        this.in =inputStream;
    }

//    @Override
//    public int read() throws IOException {
//        return 0;
//    }

    // TODO: ??
    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        byte[] bytes = this.in.readAllBytes();
//        byte[] firstByte = Arrays.copyOfRange(bytes, 0, 1);
//        System.out.println("de: " + Arrays.toString(bytes));
        byte[] decompressed = decompress(bytes);
        System.arraycopy(decompressed, 0, b, 0, decompressed.length);
        return decompressed.length;
    }

    public static byte[] decompress(byte[] in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InflaterOutputStream infl = new InflaterOutputStream(out);
            infl.write(in);
            infl.flush();
            infl.close();

            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(150);
            return null;
        }
    }


}
