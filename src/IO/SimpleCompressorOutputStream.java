package IO;

import algorithms.mazeGenerators.Position;
import algorithms.search.MazeState;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleCompressorOutputStream extends MyCompressorOutputStream {

    public SimpleCompressorOutputStream(OutputStream outputStream) {
        super(outputStream);
    }


    @Override
    public void write(byte[] b) throws IOException {
        writeSimple(b);
    }
}
