
package com.lp.reddittopimages.redditimages_api;

public class ColorUtil {

    public static double distance(int color1, int color2) {
        double deltaR = ((color1 >> 16) & 0x0ff) - ((color2 >> 16) & 0x0ff);
        double deltaG = ((color1 >> 8) & 0x0ff) - ((color2 >> 8) & 0x0ff);
        double deltaB = (color1 & 0x0ff) - (color2 & 0x0ff);
        double rMean = (((color1 & 0xff000000) >>> 24) + ((color2 & 0xff000000) >>> 24)) / 2;

        double colorDistanceSquare =
            (2 * deltaR * deltaR) + (4 * deltaG * deltaG) + (3 * deltaB * deltaB)

                + (rMean * (deltaR * deltaR - deltaB * deltaB) / 256

                )

        ;

        return Math.sqrt(colorDistanceSquare);

    }

}
