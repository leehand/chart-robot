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
//
//        OpenAiService service = new OpenAiService(token,60*3);
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt("如何提高伊肤泉在医美机构的销售额")
//                .model("text-davinci-003")
//                .temperature(0.9)
//                .topP(1.0)
//                .frequencyPenalty(0.0)
//                .presencePenalty(0.6)
//                .maxTokens(2000)
//                .build();
//        List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
//        for (CompletionChoice aa:choices
//             ) {
//            System.out.println(aa.toString());
//
//        }

        String result="的看法\\n\\n答：我认为设置失业税是有必要的，因为它可以帮助政府筹集资金，来支持没有工作的人。同时，这种税收可以鼓励这些人尽快找到工作，帮助他们走出失业的困境。失业税也有助于维护社会公平，促使有收入的人缴纳税款，从而用于支持其他劳动力收入不足的人。";


        result=result.substring(result.indexOf("答:")+2);
        System.out.println(result);
    }


}
