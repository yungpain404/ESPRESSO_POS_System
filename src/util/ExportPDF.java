package util;

import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import entity.ChiTietHoaDon;
import entity.HoaDon;

public class ExportPDF {
	public static void exportInvoice(HoaDon hd, String filePath) {
		try (PDDocument document = new PDDocument()){
			PDPage page = new PDPage();
			document.addPage(page);
			
			PDType0Font font = PDType0Font.load(document, new File("font/ARIAL.TTF"));
			try (PDPageContentStream content = new PDPageContentStream(document, page)) {
			    content.beginText();
			    content.setFont(font, 18);
			    content.newLineAtOffset(50, 750);
			    content.showText("HOÁ ĐƠN THANH TOÁN");
			    
			    content.setFont(font, 12);
			    content.newLineAtOffset(0, -30);
			    content.showText("Mã HD: " + hd.getMaHD());
			    content.newLineAtOffset(0, -20);
			    content.showText("Ngày lập: " + hd.getNgayGioLap().toString());
			    content.endText();
			    
			    content.beginText();
			    content.setFont(font, 12);
			    content.newLineAtOffset(50, 650);
			    content.showText("Tên sản phẩm");
			    content.newLineAtOffset(250, 0);
			    content.showText("SL");
			    content.newLineAtOffset(150, 0);
			    content.showText("Thành tiền");
			    
			    for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
			        content.newLineAtOffset(-400, -20);
			        content.showText(ct.getMon().getTenMon());
			        content.newLineAtOffset(250, 0);
			        content.showText(String.valueOf(ct.getSoLuongMon()));
			        content.newLineAtOffset(150, 0);
			        content.showText(String.format("$%.2f", ct.getThanhTien()));
			    }
			    
			    content.newLineAtOffset(-400, -30);
			    content.showText("Tổng tiền: " + String.format("$%.2f", hd.getTongTien()));
			    content.endText();
			}
            document.save(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}