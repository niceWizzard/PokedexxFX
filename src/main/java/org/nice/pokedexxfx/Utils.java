package org.nice.pokedexxfx;

import javafx.scene.image.Image;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Utils {

    public static Image getImage(String resourcePath) {
        return new Image(getImagePath(resourcePath));
    }

//    public static Image getImage(String resourcePath, int width, int height) {
//        return resizeImage(getImage(resourcePath), width, height);
//    }

    public static String escapeRegex(String s) {
        String regexChars = "([\\\\.^$|?*+()\\[{])";

        // Escape these characters in the input string
        return s.replaceAll(regexChars, "\\\\$1");
    }

//    public static ImageIcon resizeImage(ImageIcon orig, int width, int height) {
//        var scaled = orig.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        return new ImageIcon(scaled);
//    }

    public static String getImagePath(String resourcePath) {
        try {
            var path = Objects.requireNonNull(
                    Utils.class.getResource(resourcePath)
            );
            return URLDecoder.decode(path.toString(), StandardCharsets.UTF_8);
        }catch(NullPointerException e) {
            throw  new RuntimeException("The file with path: " + resourcePath + " does not exists.");
        }
    }

    public static String getResource(String resourcePath) {
        try {
            var path = Objects.requireNonNull(
                    Utils.class.getResource(resourcePath)
            );
            return URLDecoder.decode(path.getPath(), StandardCharsets.UTF_8);
        }catch(NullPointerException e) {
            throw  new RuntimeException("The file with path: " + resourcePath + " does not exists.");
        }
    }
}
