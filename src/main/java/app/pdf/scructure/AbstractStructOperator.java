package app.pdf.scructure;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.font.PDFont;

public abstract class AbstractStructOperator {
    private AbstractStructOperator parent;
    protected List<AbstractStructOperator> children;

    protected PDFont font;
    protected List<COSBase> cosStack;

    public AbstractStructOperator(List<COSBase> cosStack) {
        this.children = new ArrayList<>();
        this.cosStack = new ArrayList<>(cosStack);
        cosStack.clear();
    }

    public AbstractStructOperator getParent() {
        return parent;
    }

    public void setParent(AbstractStructOperator parent) {
        this.parent = parent;
    }

    public void setFont(PDFont font) {
        this.font = font;
    }

    public void addChild(AbstractStructOperator childOperator) {
        children.add(childOperator);
    }

    public String generateOutput() throws IOException {
        return "";
    }

    protected void decodeString(COSString string, StringBuilder sb) throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(string.getBytes());
        while (is.available() > 0) {
            int charCode = font.readCode(is);
            if (charCode == 12 && font.getName().equals("ELLWRV+VrindaranyaItalic")) {
                System.out.println();
            }
            String unicodeChar = font.toUnicode(charCode);
            sb.append(unicodeChar);
        }
    }

    protected String generateTextOutput() throws IOException {
        String fontName = font.getName();
        StringBuilder sb = new StringBuilder();
        sb.append("<font face=\"" + fontName + "\">");
        for (COSBase param : cosStack) {
            if (param instanceof COSString) {
                decodeString((COSString) param, sb);
            } else if (param instanceof COSArray) {
                COSArray array = (COSArray) param;
                for (COSBase element : array) {
                    if (element instanceof COSString) {
                        decodeString((COSString) element, sb);
                    }
                }
            }
        }
        sb.append("</font>");
        return sb.toString();
    }

}
