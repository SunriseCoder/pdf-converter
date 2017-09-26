package app.pdf.scructure.graphics;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;

import app.pdf.scructure.AbstractStructOperator;

public class ReStructOperator extends AbstractStructOperator {

    public ReStructOperator(List<COSBase> cosStack) {
        super(cosStack);
    }
}
