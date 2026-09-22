package com.kodnest.springaiprointellij.service;

import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DataLoader {
    List<Document> loadDataFromJson();
    List<Document> loadDataFromPdf();
}
