import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSInteger;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class HelloWorld {

    /*
     * PDF Operators:
     *
     * BT - Begin Text (Begin text operators block, should be end with ET)
     * ET - End Text (End of the text block (which was opened by operator BT)
     *
     * */

    private Map<String, PDFont> fonts;
    private PDFont currentFont;

    private List<Text> textList;

    public HelloWorld() {
        fonts = new HashMap<>();
        textList = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException {
        HelloWorld hw = new HelloWorld();
        hw.createHelloDoc();
        hw.parseHelloDoc();
    }

    private void parseHelloDoc() throws InvalidPasswordException, IOException {
        try(PDDocument doc = PDDocument.load(new File("data/hello.pdf"))) {
            int numberOfPages = doc.getNumberOfPages();
            for (int i = 0; i < numberOfPages; i++) {
                PDPage page = doc.getPage(i);
                PDResources res = page.getResources();
                for (COSName fontName : res.getFontNames()) {
                    String name = fontName.getName();
                    PDFont font = res.getFont(fontName);
                    fonts.put(name, font);
                }
                if (i > 35 - 2) {
                    System.out.println();
                }
                PDFStreamParser parser = new PDFStreamParser(page);
                Object token;
                while ((token = parser.parseNextToken()) != null) {
                    parseToken(token);
                }
            }
        }
    }

    private void parseToken(Object token) throws IOException {
        System.out.println(token);
        if (token instanceof Operator) {

        } else if (token instanceof COSName) {
            COSName cosName = (COSName) token;
            String name = cosName.getName();
            if (fonts.containsKey(name)) {
                currentFont = fonts.get(name);
            }
        } else if (token instanceof COSDictionary) {

        } else if (token instanceof COSInteger) {

        } else if (token instanceof COSFloat) {

        } else if (token instanceof COSArray) {
            COSArray array = (COSArray) token;
            Text text = new Text();
            text.font = currentFont.getName();
            StringBuilder sb = new StringBuilder();
            for (COSBase element : array) {
                if (element instanceof COSString) {
                    COSString string = (COSString) element;
                    processString(string, sb);
                } else if (element instanceof COSFloat) {

                } else if (element instanceof COSInteger) {

                } else {
                    throw new UnsupportedOperationException(element.toString());
//                    System.out.println();
                }
            }
            text.text = sb.toString();
            addText(text);
//            System.out.println(token);
        } if (token instanceof COSString) {
            Text text = new Text();
            text.font = currentFont.getName();
            StringBuilder sb = new StringBuilder();

            COSString string = (COSString) token;
            processString(string, sb);

            text.text = sb.toString();
            addText(text);

        } else {
//            System.out.println(token);
        }
    }

    private void processString(COSString string, StringBuilder sb) throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(string.getBytes());
        while (is.available() > 0) {
            int charCode = currentFont.readCode(is);
            String unicodeChar = currentFont.toUnicode(charCode);
            sb.append(unicodeChar);
        }
    }

    private void addText(Text text) {
        textList.add(text);
        //System.out.println(text);
        text = text;
    }

    private static void createHelloDoc() throws IOException {
        try(PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage();
            doc.addPage(page);

            PDFont font = PDType1Font.HELVETICA_BOLD;

            try (PDPageContentStream contents = new PDPageContentStream(doc, page)) {
                contents.beginText();
                contents.setFont(font, 12);
                contents.showText("eh");
                contents.newLineAtOffset(100, 700);
                contents.showText("Hello, World!");
                contents.endText();
            }
            /*
             * PDFOperator{BT}
             * COSName{F1}
             * COSInt{12}
             * PDFOperator{Tf}
             * COSInt{100}
             * COSInt{700}
             * PDFOperator{Td}
             * COSString{Hello, World!}
             * PDFOperator{Tj}
             * PDFOperator{ET}
             */

            doc.save("data/hello.pdf");
        }
    }

    private static class Text {
        String font;
        String text;

        @Override
        public String toString() {
            return font + ": " + text;
        }
    }
}
