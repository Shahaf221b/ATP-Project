package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    @Override
    public void write(int b) throws IOException {

    }
    // (b[] byte(write v)
    @Override
    public void write(byte[] b){

    }
}
