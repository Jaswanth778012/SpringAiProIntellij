package com.kodnest.springaiprointellij;

import com.kodnest.springaiprointellij.helper.Helper;
import com.kodnest.springaiprointellij.service.ChatService;
import com.kodnest.springaiprointellij.service.DataLoader;
import com.kodnest.springaiprointellij.tools.WeatherTool;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SpringAiProIntellijApplicationTests {

//	@Autowired
//	private ChatService chatService;

	@Autowired
	private DataLoader dataLoader;

	@Autowired
	private WeatherTool weatherTool;


//	@Test
//	void saveDataToVectorDatabase() {
//		System.out.println("Saving Data to Database");
//		this.chatService.savedData(Helper.getData());
//		System.out.println("Data Saved Successfully");
//	}
//
//
//	@Autowired
//	private EmbeddingModel embeddingModel;
//
//	@Test
//	void testEmbedding() {
//
//		float[] embedding =
//				embeddingModel.embed("Spring Boot is a Java framework");
//
//		System.out.println("Embedding dimensions = " + embedding.length);
//
//		for (int i = 0; i < 10; i++) {
//			System.out.println(embedding[i]);
//		}
//	}@

	@Test
	void testDataLoader() {
		List<Document> documents = dataLoader.loadDataFromJson();

		documents.forEach(item->{
			System.out.println(item);
		});

		System.out.println("----------------------");

		System.out.println(documents.size());
	}

	@Test
	void testDataLoaderPdf() {
		List<Document> documents = dataLoader.loadDataFromPdf();

		System.out.println(documents.size());

		documents.forEach(item -> {
			System.out.println(item);
		});
	}

	@Test
	public void getWeatherTest() {
		var response = weatherTool.getWeather("Delhi India");

		System.out.println(response);
	}


}
