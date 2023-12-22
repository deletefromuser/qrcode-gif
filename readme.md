# QR Code Generator from File

This Java application reads a file, encodes it to a Base64 string, splits the string into chunks, and converts each chunk into a QR code. It then combines all the QR codes into an animated GIF.


## Dependencies

This project uses the following libraries:

- ZXing (Zebra Crossing) for generating QR codes.
- ImageIO for creating an animated GIF.

## Package
```bash
mvn clean compile assembly:single
```

## Usage
```bash
java -jar qrcode-gif-1.0-SNAPSHOT-jar-with-dependencies.jar /path/file
```

The application reads an input file. It splits the file into chunks, converts each chunk into a QR code, and combines the QR codes into an animated GIF named `output.gif`.

You can change the input file, chunk size, QR code size, and output file by modifying the constants in the `App` class.

To improve the quality of a QR code image, it is best to compress the original file to the ultra using 7-Zip. Then, compress the compressed file again and split it into 4000 byte chunks.

## Todo
- [ ] gif file is too large // https://www.baeldung.com/java-image-compression-lossy-lossless

## License

This project is licensed under the MIT License.