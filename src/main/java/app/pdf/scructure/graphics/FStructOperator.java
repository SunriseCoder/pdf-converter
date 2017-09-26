package app.pdf.scructure.graphics;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;

import app.pdf.scructure.AbstractStructOperator;

public class FStructOperator extends AbstractStructOperator {

    public FStructOperator(List<COSBase> cosStack) {
        super(cosStack);
    }
}
