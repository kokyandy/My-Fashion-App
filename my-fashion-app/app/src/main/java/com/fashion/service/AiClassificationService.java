package com.fashion.service;

import com.fashion.model.GarmentAnalysisDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
public class AiClassificationService {

    private final ChatClient chatClient;

    public AiClassificationService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public GarmentAnalysisDTO classifyGarment(Resource imageResource) {
        String prompt = "You are an expert fashion analyst. Analyze this inspiration image. " +
                        "Extract the garment type, style, material, color palette, pattern, season, " +
                        "and deduce the likely location context. Return strictly according to the requested format.";

        // Spring AI magic: send the image and automatically parse the large model's returned JSON into a DTO
        return chatClient.prompt()
                .user(u -> u.text(prompt).media(MimeTypeUtils.IMAGE_JPEG, imageResource))
                .call()
                .entity(GarmentAnalysisDTO.class); 
    }
}