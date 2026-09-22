package com.kodnest.springaiprointellij.service;


import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.JsonReader;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataLoaderImpl implements DataLoader{

    @Value("classpath:sample.json")
    private Resource sampleData;

    @Value("classpath:sam.pdf")
    private Resource pdfdata;

    @Override
    public List<Document> loadDataFromJson() {

        System.out.println("started loading Json");

        JsonReader jsonReader = new JsonReader(sampleData);
        List<Document> read = jsonReader.read();
        return read;
    }

    @Override
    public List<Document> loadDataFromPdf() {

        System.out.println("Started Loading from Pdf");

        PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(pdfdata, PdfDocumentReaderConfig.builder()
                .withPageTopMargin(0)
                .withPageExtractedTextFormatter(ExtractedTextFormatter.builder().withNumberOfTopTextLinesToDelete(0).build())
                .build());

        return pagePdfDocumentReader.read();
    }
}
