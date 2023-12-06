package org.example.Data;
import org.example.Logic.ProductwWeight;
import org.example.Logic.ShopList;
import org.example.Logic.TypeFood;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.io.IOException;
import java.util.ArrayList;

public class PDFGenerator {
    private static final int PAGE_MARGIN = 50;
    private static final int LINE_HEIGHT = 15;
    public static void exportToPDF(ArrayList<ShopList> shopListArrayList) {
            try {
                PDDocument document = new PDDocument();
                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);

                int yOffset = (int) (PDRectangle.A4.getHeight() - PAGE_MARGIN);

                contentStream.beginText();
                contentStream.newLineAtOffset(PAGE_MARGIN, yOffset);
                contentStream.showText("Shopping List");
                contentStream.newLineAtOffset(0, -LINE_HEIGHT);
                contentStream.endText();

                yOffset -= LINE_HEIGHT * 2;

                for (ShopList shopList : shopListArrayList) {
                    TypeFood category = shopList.getTypeFood();
                    ArrayList<ProductwWeight> items = shopList.getWeight();

                    if (yOffset - LINE_HEIGHT * (items.size() + 2) < PAGE_MARGIN) {

                        contentStream.close();
                        page = new PDPage(PDRectangle.A4);
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                        yOffset = (int) PDRectangle.A4.getHeight() - PAGE_MARGIN;
                    }

                    yOffset -= LINE_HEIGHT;

                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                    contentStream.beginText();
                    contentStream.newLineAtOffset(PAGE_MARGIN, yOffset);
                    contentStream.showText("  " + category.toString());
                    contentStream.endText();

                    yOffset -= LINE_HEIGHT;

                    contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 10);
                    for (ProductwWeight item : items) {
                        yOffset -= LINE_HEIGHT;
                        String itemText = String.format("    %s - %.2f g", item.getProducts().getName(), item.getWeight());
                        contentStream.beginText();
                        contentStream.newLineAtOffset(PAGE_MARGIN * 2, yOffset);
                        contentStream.showText(itemText);
                        contentStream.endText();
                    }

                    yOffset -= LINE_HEIGHT;
                }

                contentStream.close();

                document.save("/Users/maks_rz/Desktop/Shop_list.pdf");
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}

