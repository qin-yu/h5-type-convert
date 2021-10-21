import ij.IJ
import ij.gui.ImageRoi
import ij.gui.Overlay
import ij.process.ShortProcessor

import java.awt.image.BufferedImage
import java.awt.image.IndexColorModel

// Create a random 16-bit LUT
int n = Math.pow(2, 16)-1 as int
def r = new byte[n]
def g = new byte[n]
def b = new byte[n]
def a = new byte[n]
def rand = new Random(47)
rand.nextBytes(r)
rand.nextBytes(g)
rand.nextBytes(b)
Arrays.fill(a, (byte)255)
a[0] = 0
def model = new IndexColorModel(16, n, r, g, b, a)

// Create 16-bit ImageRois, and add to an overlay
def imp = IJ.getImage()
int width = imp.getWidth()
int height = imp.getHeight()
def overlay = new Overlay()
for (int s = 1; s <= imp.getStack().getSize(); s++) {
    def pixels = imp.getStack().getPixels(s) as short[]
    def raster = model.createCompatibleWritableRaster(width, height)
    def buffer = raster.getDataBuffer()
    System.arraycopy(pixels, 0, buffer.getData(), 0, buffer.getData().length);
    def img = new BufferedImage(model, raster, false, null)
    println(img)
    println(raster)

    // Try to show the image in ImageJ with a translucent overlay
    def roi = new ImageRoi(0, 0, img)
    roi.setOpacity(0.75)
    roi.setPosition(s)
    overlay.add(roi)
}
imp.setOverlay(overlay)
