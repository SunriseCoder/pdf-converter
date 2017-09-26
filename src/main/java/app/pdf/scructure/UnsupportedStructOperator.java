package app.pdf.scructure;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;

public class UnsupportedStructOperator extends AbstractStructOperator {

    public UnsupportedStructOperator(List<COSBase> cosStack) {
        super(cosStack);
    }
}
