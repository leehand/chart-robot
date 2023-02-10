package com.mongcent.risk.manager.service.gpt;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GPTService {



    private static Logger LOGGER = LoggerFactory.getLogger(GPTService.class);
    private static Gson gson = new GsonBuilder()
            .create();



    public String getgpt(String token,String keyword){

        OpenAiService service = new OpenAiService(token,60*3);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(keyword)
                .model("text-davinci-003")
                .temperature(0.9)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.6)
                .maxTokens(2000)
                .build();
        List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
        String result="";

        for (CompletionChoice aa:choices
        ) {
            result = aa.getText();


        }
        LOGGER.info(result);

        result=result.substring(result.indexOf("答：")+2);


        return result;
    }



}
