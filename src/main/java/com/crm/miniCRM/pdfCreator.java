package com.crm.miniCRM;

import com.crm.miniCRM.model.Person;
import com.crm.miniCRM.model.persistence.PersonRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.Table;
import org.springframework.data.repository.CrudRepository;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class pdfCreator {

    public static void main(String args[]) throws Exception {

        Path path = Paths.get("C:/pdfMiniCRM/output.pdf");

        Files.createDirectories(path.getParent());

        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            System.err.println("already exists: " + e.getMessage());
        } finally {

            pdfData pdfData = new pdfData();

            ArrayList<String> data =  pdfData.output();

            String dest = "C:/pdfMiniCRM/output.pdf";
            PdfWriter writer = new PdfWriter(dest);

            PdfDocument pdf = new PdfDocument(writer);

            Document doc = new Document(pdf);

            float [] pointColumnWidths = {150F, 25F, 150F, 25F, 150F, 25F};
            Table output = new Table(pointColumnWidths);
            Table table;
            Table empty = new Table(1);
            empty.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            empty.setWidth(100F);

            int tempTeller = 0;
            for (int x = 0; x < (data.size()/6); x++){
                table = new Table(1);
                table.addCell(new Cell().add(new Paragraph(data.get(tempTeller) + " " + data.get(tempTeller+1))).setBorder(Border.NO_BORDER));
                tempTeller = tempTeller + 2;
                table.addCell(new Cell().add(new Paragraph(data.get(tempTeller) + " " + data.get(tempTeller+1))).setBorder(Border.NO_BORDER));
                tempTeller = tempTeller + 2;
                table.addCell(new Cell().add(new Paragraph(data.get(tempTeller) + " " + data.get(tempTeller+1))).setBorder(Border.NO_BORDER));
                tempTeller = tempTeller + 2;
                table.addCell(new Cell().add(new Paragraph(" ")).setBorder(Border.NO_BORDER).setHeight(20F));
                output.addCell(new Cell().add(table).setBorder(Border.NO_BORDER));
                output.addCell(new Cell().add(empty).setBorder(Border.NO_BORDER));
            }

            doc.add(output);

            doc.close();
            System.out.println("Table created successfully..");
        }
    }
}
