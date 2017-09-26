package app.pdf.scructure;

import app.pdf.PDFParser;

public abstract class AbstractOperatorGroup {
    public abstract boolean parseOperator(PDFParser parser, String operator);
}
