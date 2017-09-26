package app.pdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;

import app.pdf.scructure.AbstractOperatorGroup;
import app.pdf.scructure.AbstractStructOperator;
import app.pdf.scructure.GraphicOperators;
import app.pdf.scructure.StructPage;
import app.pdf.scructure.TextOperators;

public class PDFParser {
    private static PrintWriter tmpFile;
    private static PrintWriter logFile;
    private static PrintWriter outputFile;


    private List<AbstractOperatorGroup> operatorGroups;

    private Map<String, PDFont> fonts;
    private List<StructPage> pageList;
    private List<AbstractStructOperator> structList;
    private List<COSBase> cosStack;

    // Stack variables
    private AbstractStructOperator currentStructOperator;
    private PDFont currentFont;

    public PDFParser() {
        operatorGroups = new ArrayList<>();
        operatorGroups.add(new TextOperators());
        operatorGroups.add(new GraphicOperators());

        fonts = new HashMap<>();
        pageList = new ArrayList<>();
        structList = new ArrayList<>();
        cosStack = new ArrayList<>();
    }

    public static void main(String[] args) throws InvalidPasswordException, IOException {
        PDFParser parser = new PDFParser();
        String fileName = "data/bg.pdf";
        String tmpFileName = fileName + "_tmp.html";
        tmpFile = new PrintWriter(tmpFileName);
        logFile = new PrintWriter(fileName + ".log");
        outputFile = new PrintWriter(fileName + ".html");
        try {
            parser.parseFile(fileName);
        } catch (Exception e) {
            e.printStackTrace(logFile);
            throw e;
        } finally {
            logFile.flush();
            logFile.close();
        }

        try {
            parser.generateOutput();
        } finally {
            tmpFile.flush();
            tmpFile.close();
        }

        try {
            parser.combineTags(tmpFileName);
        } finally {
            outputFile.flush();
            outputFile.close();
        }
    }

    private void parseFile(String fileName) throws InvalidPasswordException, IOException {
        try (PDDocument doc = PDDocument.load(new File(fileName))) {
            int numberOfPages = doc.getNumberOfPages();
            for (int i = 0; i < numberOfPages; i++) {
                PDPage page = doc.getPage(i);
                parseFonts(page);
                logFile.println("Parsing page: " + i);
                System.out.println("Parsing page: " + i);
                parsePage(page);
                pageList.add(new StructPage(structList));
                structList = new ArrayList<>();
            }
            System.out.println("Parsing done!");
        }
    }

    private void parseFonts(PDPage page) throws IOException {
        PDResources res = page.getResources();
        for (COSName fontName : res.getFontNames()) {
            String fontKey = fontName.getName();
            PDFont font = res.getFont(fontName);
            fonts.put(fontKey, font);
        }
    }

    private void parsePage(PDPage page) throws IOException {
        PDFStreamParser parser = new PDFStreamParser(page);
        Object token;
        while ((token = parser.parseNextToken()) != null) {
            AbstractStructOperator op = currentStructOperator;
            while (op != null) {
                logFile.print("  ");
                op = op.getParent();
            }
            logFile.println(token);
            //System.out.println(token);
            parseToken(token);
        }
    }

    private void parseToken(Object token) {
        if (token instanceof Operator) {
            parseOperator((Operator) token);
        } else if (token instanceof COSDictionary) {
            parseCOSDictionary((COSDictionary) token);
        } else if (token instanceof COSName) {
            parseCOSName((COSName) token);
        } else if (token instanceof COSInteger) {
            parseCOSInt((COSInteger) token);
        } else if (token instanceof COSFloat) {
            parseCOSFloat((COSFloat) token);
        } else if (token instanceof COSString) {
            parseCOSString((COSString) token);
        } else if (token instanceof COSArray) {
            parseCOSArray((COSArray) token);
        } else {
            throw new UnsupportedOperationException(token.toString());
        }
    }

    private void parseOperator(Operator operator) {
        String operatorName = operator.getName();

        for (AbstractOperatorGroup operatorGroup : operatorGroups) {
            boolean parsed = operatorGroup.parseOperator(this, operatorName);
            if (parsed) {
                return;
            }
        }

        throw new UnsupportedOperationException(operator.toString());
    }

    private void parseCOSDictionary(COSDictionary token) {
        cosStack.add(token);
    }

    private void parseCOSName(COSName token) {
        cosStack.add(token);
    }

    private void parseCOSInt(COSInteger token) {
        cosStack.add(token);
    }

    private void parseCOSFloat(COSFloat token) {
        cosStack.add(token);
    }

    private void parseCOSString(COSString token) {
        cosStack.add(token);
    }

    private void parseCOSArray(COSArray token) {
        cosStack.add(token);
    }

    public List<COSBase> getCosStack() {
        return cosStack;
    }

    public AbstractStructOperator getCurrentStructOperator() {
        return currentStructOperator;
    }

    public void setCurrentStructOperator(AbstractStructOperator operator) {
        this.currentStructOperator = operator;
    }

    public PDFont getCurrentFont() {
        return currentFont;
    }

    public void setCurrentFont(PDFont font) {
        this.currentFont = font;
    }

    public PDFont getFont(String fontKey) {
        PDFont font = fonts.get(fontKey);
        return font;
    }

    public List<AbstractStructOperator> getStructList() {
        return structList;
    }

    private void generateOutput() throws IOException {
        tmpFile.println("<html>");
        for (StructPage page : pageList) {
            structList = page.getStructList();
            for (AbstractStructOperator operator : structList) {
                String output = operator.generateOutput();
                if (!output.isEmpty()) {
                    tmpFile.println(output);
                }
            }
        }
        tmpFile.println("</html>");

        System.out.println("Output generation done!");
    }

    private void combineTags(String tmpFileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(tmpFileName));
        StringBuilder sb = new StringBuilder();
        String currentFontFamily = null;
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            String font = parseFont(line);
            String fontFamily = parseFontFamily(font);
            if (fontFamily == null) {
                continue;
            }

            String text = parseText(line);
            if (text == null || text.isEmpty()) {
                continue;
            }

            if (!fontFamily.equals(currentFontFamily)) {
                if (sb.length() > 0) {
                    outputFile.println("<p style=\"font-family: " + currentFontFamily + ";\">" + sb.toString() + "</p>");
                    sb = new StringBuilder();
                }
                currentFontFamily = fontFamily;
            }
            sb.append(text);
        }
    }

    private static final Pattern FONT_PARSE_PATTERN = Pattern.compile("^<font face=\"([A-z \\\\+\\-]*)\\\">(.*)</font>$");

    private String parseFont(String line) {
        Matcher matcher = FONT_PARSE_PATTERN.matcher(line);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    private String parseFontFamily(String font) {
        if (font == null) {
            return null;
        }
        String fontFamily = font.replace("Normal", "");
        fontFamily = fontFamily.replace("Bold", "");
        fontFamily = fontFamily.replace("Italic", "");
        return fontFamily;
    }

    private String parseText(String line) {
        Matcher matcher = FONT_PARSE_PATTERN.matcher(line);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        return null;
    }
}
