import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

public class Utils {

    public static long getCrc32(File file) throws IOException {
        CRC32 crc32 = new CRC32();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(file));
        int cnt;
        while ((cnt = stream.read()) != -1) {
            crc32.update(cnt);
        }
        return crc32.getValue();
    }

}
