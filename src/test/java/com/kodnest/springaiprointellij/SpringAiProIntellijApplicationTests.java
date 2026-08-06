package com.kodnest.springaiprointellij;

import com.kodnest.springaiprointellij.helper.Helper;
import com.kodnest.springaiprointellij.service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAiProIntellijApplicationTests {

	@Autowired
	private ChatService chatService;

	@Test
	void saveDataToVectorDatabase() {
		System.out.println("Saving Data to Database");
		this.chatService.savedData(Helper.getData());
		System.out.println("Data Saved Successfully");
	}


	@Autowired
	private EmbeddingModel embeddingModel;

	@Test
	void testEmbedding() {

		float[] embedding =
				embeddingModel.embed("Spring Boot is a Java framework");

		System.out.println("Embedding dimensions = " + embedding.length);

		for (int i = 0; i < 10; i++) {
			System.out.println(embedding[i]);
		}
	}
}
