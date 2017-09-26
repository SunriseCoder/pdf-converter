package app.pdf.scructure.graphics;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;

import app.pdf.scructure.AbstractStructOperator;

public class CS_StructOperator extends AbstractStructOperator {

    public CS_StructOperator(List<COSBase> cosStack) {
        super(cosStack);
    }
}
