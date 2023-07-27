package com.example.tteapi.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ImageUtils {
    private static ImageUtils instance;

    private final Color[] fontColors = new Color[] {
            Color.cyan,
            Color.green,
            Color.BLACK,
            Color.red,
            Color.blue
    };

    private final int FontSize = 60;

    private final Font[] fonts = new Font[] {
            new Font("Roboto", Font.PLAIN, FontSize),
            new Font("Arial", Font.PLAIN, FontSize),
            new Font("Palatino", Font.PLAIN, FontSize),
            new Font("Sans-serif", Font.PLAIN, FontSize),
            new Font("Futura", Font.PLAIN, FontSize),
            new Font("Times New Roman", Font.PLAIN, FontSize),
            new Font("Garamond", Font.PLAIN, FontSize),
            new Font("Helvetica", Font.PLAIN, FontSize)
    };

    private ImageUtils() {
    }

    public static ImageUtils getInstance() {
        if (instance == null) {
            if (instance == null) {
                instance = new ImageUtils();
            }
        }
        return instance;
    }

    private int charSpace(int width, int padding) {
        width -= padding;
        final int available = width / padding;
        return available;
    }

    public String[] wordLayout(String Words, int charsAvailable) {
        List<String> lstStr = new ArrayList<String>(Arrays.asList(Words.split(" ")));
        List<String> WordMatrix = new ArrayList<String>();

        int currentLength = 0;
        String currentRow = "";
        for (String word : lstStr) {
            currentLength += word.length() + 1;
            if (currentLength <= charsAvailable) {
                currentRow += word + " ";
            } else {
                WordMatrix.add(currentRow.substring(0, currentRow.length() - 1));
                currentRow = word + " ";
                currentLength = currentRow.length();
            }
        }
        WordMatrix.add(currentRow.substring(0, currentRow.length() - 1));

        String[] strArr = new String[WordMatrix.size()];
        strArr = WordMatrix.toArray(strArr);

        return strArr;
    }

    public void writeTextToImage(Graphics2D img, String text, String quoteOwner, int width, int height) {
        // Image Size = 565 x 589
        final Font randomFont = fonts[(int) Math.floor(Math.random() * (fonts.length))];
        final Color fontCol = fontColors[(int) Math.floor(Math.random() * (fontColors.length))];

        final int fontConvertedSize = 30;

        final int spaceAvailable = charSpace(width, fontConvertedSize);
        final String[] wordLayout = wordLayout(text, spaceAvailable);

        img.setFont(randomFont);
        img.setColor(fontCol);

        img.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int yOffset = (height - FontSize * wordLayout.length) / 2;

        for (int x = 0; x < wordLayout.length; x++) {
            String line = wordLayout[x];

            int xOffset = (width - img.getFontMetrics().stringWidth(line)) / 2;

            img.drawString(((x == 0) ? "\"" : "") + line + ((x == wordLayout.length - 1) ? '"' : ""), xOffset, yOffset);
            yOffset += FontSize + 10;
        }

        img.drawString("~" + quoteOwner, (width - quoteOwner.length() * fontConvertedSize) / 2, height - FontSize - 20);
    }

}
