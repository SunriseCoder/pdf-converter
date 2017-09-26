package app.pdf.scructure.graphics;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;

import app.pdf.scructure.AbstractStructOperator;

public class CStructOperator extends AbstractStructOperator {

    public CStructOperator(List<COSBase> cosStack) {
        super(cosStack);
    }
}
