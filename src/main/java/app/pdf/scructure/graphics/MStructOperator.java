package app.pdf.scructure.graphics;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;

import app.pdf.scructure.AbstractStructOperator;

public class MStructOperator extends AbstractStructOperator {

    public MStructOperator(List<COSBase> cosStack) {
        super(cosStack);
    }
}
