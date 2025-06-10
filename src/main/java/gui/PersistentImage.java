package gui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PersistentImage
{
    private byte[] data;
    
    public PersistentImage(BufferedImage image) throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        new JPEGImageEncoderImpl(out).encode(image);
        data = out.toByteArray();        
    }

    public Image makeImage() throws IOException
    {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        return new JPEGImageDecoderImpl(in).decodeAsBufferedImage();
    }
}
