package IO;

import java.io.OutputStream;

public class SimpleCompressorOutputStream extends MyCompressorOutputStream{

    public SimpleCompressorOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void write(byte[] b) {

    }
}
