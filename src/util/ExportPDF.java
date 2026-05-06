package util;

import java.io.File;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import entity.ChiTietHoaDon;
import entity.HoaDon;

public class ExportPDF {
	public static void exportInvoice(HoaDon hd, String filePath) {
		@SuppressWarnings("deprecation")
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		try (PDDocument document = new PDDocument()) {
			PDPage page = new PDPage();
			document.addPage(page);

			PDType0Font font = PDType0Font.load(document, new File("font/ARIAL.TTF"));
			PDType0Font fontBold = PDType0Font.load(document, new File("font/ARIAL.TTF"));

			try (PDPageContentStream content = new PDPageContentStream(document, page)) {
				content.beginText();
				content.setFont(fontBold, 20);
				content.newLineAtOffset(50, 750);
				content.showText("ESPRESSO LOGIC - HOA DON");
				content.endText();

				content.beginText();
				content.setFont(font, 12);
				content.newLineAtOffset(50, 720);
				content.showText("Ma HD: " + hd.getMaHD());
				content.newLineAtOffset(0, -15);
				content.showText("Thoi gian: " + LocalDateTime.now().format(dtf));
				content.endText();

				float margin = 50;
				float yStart = 680;
				float rowHeight = 25;

				content.setLineWidth(1f);
				content.moveTo(margin, yStart + 20);
				content.lineTo(550, yStart + 20);
				content.stroke();

				content.beginText();
				content.setFont(fontBold, 12);
				content.newLineAtOffset(margin, yStart);
				content.showText("Sản Phẩm");
				content.newLineAtOffset(200, 0);
				content.showText("Đơn Giá");
				content.newLineAtOffset(100, 0);
				content.showText("SL");
				content.newLineAtOffset(100, 0);
				content.showText("Thành Tiền");
				content.endText();

				content.moveTo(margin, yStart - 5);
				content.lineTo(550, yStart - 5);
				content.stroke();

				float yPosition = yStart - rowHeight;
				content.setFont(font, 12);
				for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
					content.beginText();
					content.newLineAtOffset(margin, yPosition);
					content.showText(ct.getMon().getTenMon());
					content.newLineAtOffset(200, 0);
					content.showText(currencyFormatter.format(ct.getMon().getDonGiaBan()));
					content.newLineAtOffset(100, 0);
					content.showText(String.valueOf(ct.getSoLuongMon()));
					content.newLineAtOffset(100, 0);
					content.showText(currencyFormatter.format(ct.getThanhTien()));
					content.endText();
					yPosition -= rowHeight;
				}

				content.moveTo(margin, yPosition);
				content.lineTo(550, yPosition);
				content.stroke();

				content.beginText();
				content.setFont(fontBold, 14);
				content.newLineAtOffset(400, yPosition - 20);
				content.showText("Tổng cộng: ");
				content.showText(currencyFormatter.format(hd.getTongTien()));
				content.endText();
			}
			document.save(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}