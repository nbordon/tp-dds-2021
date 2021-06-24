package imagenormalization;

import java.io.IOException;

public interface ImageNormalizator {

    void normalizeImage(String path, ImageQuality quality, ImageSize size) throws IOException;
}