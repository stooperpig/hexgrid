package wps.hexgrid;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HexGridGenerator {

    public static void main(String[] args) {
        double shortDiagonal = 76.0;
        // c^2 = a^2 + b^2 - 2*a*b*cos(120)
        double side = Math.sqrt(Math.pow(shortDiagonal,2.0)/3);
        double doubleDx = Math.sqrt(Math.pow(side,2) - Math.pow(shortDiagonal/2.0,2));
        double longDiagonal = Math.sqrt(Math.pow(side,2) + Math.pow(shortDiagonal,2));


        int dx = (int)Math.round(doubleDx);
        int dy = (int)Math.round(shortDiagonal/2);
        int hexWidth = (int)Math.round(longDiagonal -doubleDx);
        int hexHeight = (int)Math.round(shortDiagonal);

        int hexSide = (int)side;

        int rows = 42;
        int columns = 42;
        int test = (int)Math.round(1.4);


        System.out.println("dx: " + dx);
        System.out.println("dy: " + dy);
        System.out.println("hexWidth: " + hexWidth);
        System.out.println("hexHeight: " + hexHeight);
        System.out.println("side: " + hexSide);
        System.out.println("test: " + test);

        try {
            int width = (columns * hexWidth) + dx;
            int height = (rows * hexHeight) + dy + 1;

            // TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed
            // into integer pixels
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            Graphics2D ig2 = bi.createGraphics();

            Font font = new Font("TimesRoman", Font.PLAIN, 12);
            ig2.setFont(font);

            ig2.setPaint(Color.BLACK);
            ig2.fillRect(0,0, width, height);
            ig2.setPaint(Color.DARK_GRAY);

            int y = dy;
            for(int i = 0; i < rows; ++i) {
                int x0 = 0;
                int y0 = y;
                for(int j = 0; j < columns; ++j) {
                    drawHex(ig2, x0, y0, i, j, dx, dy, hexSide, hexWidth);
                    if (j % 2 == 0) {
                        x0 += hexWidth;
                        y0 += dy;
                    } else {
                        x0 += hexWidth;
                        y0 -= dy;
                    }
                }

                y += hexHeight;
            }

            ImageIO.write(bi, "gif", new File("map.gif"));
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


    public static void drawHex(Graphics2D ig2, int x, int y, int row, int col, int dx, int dy, int hexSide, int hexWidth) {
        int x0 = x;
        int y0 = y;
        int x1 = x0 + dx;
        int y1 = y0 - dy;
        ig2.drawLine(x0, y0, x1, y1);
        x0 = x1;
        y0 = y1;
        x1 = x0 + hexSide;
        y1 = y0;
        ig2.drawLine(x0, y0, x1, y1);
        x0 = x1;
        y0 = y1;
        x1 = x0 + dx;
        y1 = y0 + dy;
        ig2.drawLine(x0, y0, x1, y1);
        x0 = x1;
        y0 = y1;
        x1 = x0 - dx;
        y1 = y0 + dy;
        ig2.drawLine(x0, y0, x1, y1);
        x0 = x1;
        y0 = y1;
        x1 = x0 - hexSide;
        y1 = y0;
        ig2.drawLine(x0, y0, x1, y1);
        x0 = x1;
        y0 = y1;
        x1 = x0 - dx;
        y1 = y0 - dy;
        ig2.drawLine(x0, y0, x1, y1);

        String label = getHexLabel(col,row);
        FontMetrics fontMetrics = ig2.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(label);
        int stringHeight = fontMetrics.getAscent();

        int xPos = x + ((hexWidth + dx) - stringWidth)/ 2;
        int yPos = y + stringHeight / 2;

        ig2.setPaint(Color.GRAY);
        ig2.drawString(label, xPos, yPos);
        ig2.setPaint(Color.DARK_GRAY);
    }

    private static String getHexLabel(int col, int row) {
        String label;

        if (col < 26) {
            label = "" + (char)('A'  + col);
        } else {
            char character = (char)('A' + (col-26));
            label = character + "" + character;
        }

        return label + row;
    }
}
