# QR Code Generator from File

This Java application reads a file, encodes it to a Base64 string, splits the string into chunks, and converts each chunk into a QR code. It then combines all the QR codes into an animated GIF.


## Dependencies

This project uses the following libraries:

- ZXing (Zebra Crossing) for generating QR codes.
- ImageIO for creating an animated GIF.

## Usage

The application reads an input file named `input.txt` in the project root directory. It splits the file into chunks, converts each chunk into a QR code, and combines the QR codes into an animated GIF named `output.gif`.

You can change the input file, chunk size, QR code size, and output file by modifying the constants in the `App` class.

## Todo
- [ ] gif file is too large // https://www.baeldung.com/java-image-compression-lossy-lossless

## License

This project is licensed under the MIT License.