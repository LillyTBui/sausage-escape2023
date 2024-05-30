package no.uib.inf101.sem2.helpMethods;

import java.awt.image.BufferedImage;

public class CompareImages {

    /**
     * Compares two images pixel by pixel.
     *
     * @param imgA the first image.
     * @param imgB the second image.
     * @return whether the images are both the same or not.
     *         <p>
     *         <h4>SOURCE CODE:</h4>
     *         <ul>
     *         <li>Author: Mr. Polywhirl</li>
     *         <li>Date: 27.04.2015</li>
     *         <li>Title: Is there a simple way to compare BufferedImage
     *         instances?</li>
     *         <li>URL:
     *         https://stackoverflow.com/questions/11006394/is-there-a-simple-way-to-compare
     *         -bufferedimage-instances</li>
     *         </ul>
     */
    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The images must be the same size.
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        int width = imgA.getWidth();
        int height = imgA.getHeight();

        // Loop over every pixel.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
}
