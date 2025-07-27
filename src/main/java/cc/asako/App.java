package cc.asako;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * Hello world!
 *
 */
public class App {
    private static final String OUTPUT_GIF = "output.gif";
    private static final int CHUNK_SIZE = 1024; // size of file chunks
    private static final int QR_CODE_WIDTH = 1000; // width of QR code
    private static final int QR_CODE_HEIGHT = 1000; // height of QR code

    public static void main(String[] args) throws Exception {
        String path = "input.txt";
        if (args.length > 0) {
            path = args[0];
        }
        File file = new File(path); // input file

        String fileContentBase64 = encodeFileToBase64(file);

        int count = (int) Math.ceil((fileContentBase64.length() + 0.0) / CHUNK_SIZE);
        int index = 0;
        List<BufferedImage> images = new ArrayList<>();
        Gson gs = new Gson();
        while (index < fileContentBase64.length()) {
            String chunk = fileContentBase64.substring(index, Math.min(index + CHUNK_SIZE, fileContentBase64.length()));
            // add metadata
            Map<String, String> data = new LinkedHashMap<>();
            data.put("name", Base64.getEncoder().encodeToString(file.getName().getBytes(StandardCharsets.UTF_8)));
            data.put("count", count + "");
            data.put("index", index / CHUNK_SIZE + "");
            data.put("data", chunk);
            BufferedImage qrCode = createQRCode(gs.toJson(data), QR_CODE_WIDTH, QR_CODE_HEIGHT);
            images.add(qrCode);
            index += CHUNK_SIZE;
        }

        createAnimatedGif(images, new File(file.getName().replaceAll("\\.([a-zA-Z]+\\w*|\\w*[a-zA-Z]+)$", "") + ".gif"));
    }

    private static String encodeFileToBase64(File file) throws IOException {
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    private static BufferedImage createQRCode(String data, int width, int height) throws Exception {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    private static void createAnimatedGif(List<BufferedImage> images, File output) throws IOException {
        try (ImageOutputStream outputStream = new FileImageOutputStream(output)) {
            GifSequenceWriter writer = new GifSequenceWriter(outputStream, BufferedImage.TYPE_INT_RGB, 3000, true);
            for (BufferedImage image : images) {
                writer.writeToSequence(image);
            }
            writer.close();
        }
    }
}
