package cc.asako;

import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;

public class GifSequenceWriter {
        protected ImageWriter gifWriter;
        protected ImageWriteParam imageWriteParam;
        protected IIOMetadata imageMetaData;

        public GifSequenceWriter(
                        ImageOutputStream outputStream,
                        int imageType,
                        int timeBetweenFramesMS,
                        boolean loopContinuously) throws IIOException, IOException {
                gifWriter = ImageIO.getImageWritersBySuffix("gif").next();
                imageWriteParam = gifWriter.getDefaultWriteParam();
                // System.out.println(new Gson().toJson(imageWriteParam.getCompressionTypes())
                // );
                // imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                // imageWriteParam.setCompressionType("lzw");
                // imageWriteParam.setCompressionQuality(0.5f);
                ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);

                imageMetaData = gifWriter.getDefaultImageMetadata(imageTypeSpecifier,
                                imageWriteParam);

                String metaFormatName = imageMetaData.getNativeMetadataFormatName();

                IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree(metaFormatName);

                IIOMetadataNode graphicsControlExtensionNode = getNode(
                                root,
                                "GraphicControlExtension");

                graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
                graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
                graphicsControlExtensionNode.setAttribute(
                                "transparentColorFlag",
                                "FALSE");
                graphicsControlExtensionNode.setAttribute(
                                "delayTime",
                                Integer.toString(timeBetweenFramesMS / 10));
                graphicsControlExtensionNode.setAttribute(
                                "transparentColorIndex",
                                "0");

                IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
                commentsNode.setAttribute("CommentExtension", "Created by MAH");

                IIOMetadataNode appEntensionsNode = getNode(
                                root,
                                "ApplicationExtensions");

                IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");

                child.setAttribute("applicationID", "NETSCAPE");
                child.setAttribute("authenticationCode", "2.0");

                int loop = loopContinuously ? 0 : 1;

                child.setUserObject(new byte[] { 0x1, (byte) (loop & 0xFF), (byte) ((loop >> 8) & 0xFF) });
                appEntensionsNode.appendChild(child);

                imageMetaData.setFromTree(metaFormatName, root);

                gifWriter.setOutput(outputStream);

                gifWriter.prepareWriteSequence(null);
        }

        public void writeToSequence(RenderedImage img) throws IOException {
                gifWriter.writeToSequence(
                                new IIOImage(
                                                img,
                                                null,
                                                imageMetaData),
                                imageWriteParam);
        }

        public void close() throws IOException {
                gifWriter.endWriteSequence();
        }

        protected static IIOMetadataNode getNode(
                        IIOMetadataNode rootNode,
                        String nodeName) {
                int nNodes = rootNode.getLength();
                for (int i = 0; i < nNodes; i++) {
                        if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName) == 0) {
                                return ((IIOMetadataNode) rootNode.item(i));
                        }
                }
                IIOMetadataNode node = new IIOMetadataNode(nodeName);
                rootNode.appendChild(node);
                return (node);
        }
}
