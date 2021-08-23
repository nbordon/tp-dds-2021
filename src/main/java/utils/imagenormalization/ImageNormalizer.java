package utils.imagenormalization;

import net.coobird.thumbnailator.Thumbnails;

import java.io.IOException;

public class ImageNormalizer implements ImageNormalizator {

    private static final String IMAGE_DEFAULT_PATH = "src/main/java/resources/foto-de-perrito.jpg";

    private void createImage(String imagePath, ImageQuality quality, ImageSize size) throws IOException {
        Thumbnails.of(imagePath)
                .forceSize(size.getWidth(), size.getHeight())
                .outputQuality(quality.value())
                .toFile(fileNameConvention(quality, size));
    }

    private String fileNameConvention(ImageQuality quality, ImageSize size) {
        return "thumbnail_" + quality.name() + "_" + size.getWidth() + "x" + size.getHeight() + ".jpg";
    }

    @Override
    public void normalizeImage(String path, ImageQuality quality, ImageSize size) throws IOException {
        if (path == null || !path.isEmpty()) {
            createImage(IMAGE_DEFAULT_PATH, quality, size);
        } else {
            createImage(path, quality, size);
        }
    }
}
