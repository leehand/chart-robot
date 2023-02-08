package openai;

import cn.hutool.http.*;
import cn.hutool.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatGPTTest1 {

    String token="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";



    @Test
    public  void  test1(){
        /**
         *       json.put("temperature",0.9);
         *             json.put("max_tokens",2048);
         *             json.put("top_p",1);
         *             json.put("frequency_penalty",0.0);
         *             json.put("presence_penalty",0.6);
         */

        OpenAiService service = new OpenAiService(token,60*3);
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("伊肤泉补水文案")
                .model("text-davinci-003")
                .temperature(0.9)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.6)
                .maxTokens(2000)
                .build();
        List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
        for (CompletionChoice aa:choices
             ) {
            System.out.println(aa.toString());

        }

//        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
    }


}
