package com.example.sweater;

import com.example.sweater.domain.Message;
import com.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepo messageRepo;


    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping ("/main")
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = messageRepo.findAll();
        checkMessage(messages);
        model.put("messages", messages);
        return "main";
    }

    private void checkMessage(Iterable<Message> messages) {
//        for(Message item : messages){
////            if(item.getTag() == null){
////                item.setTag("");
////            }
////            if(item.getText() == null){
////                item.setText("");
////            }
////        }
        messages.forEach((p) -> {
            p.setTag(p.getTag() == null ? "" : p.getTag());
            p.setText(p.getText() == null ? "" : p.getText());
        });
    }


    @PostMapping ("/main")
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);

        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();
        checkMessage(messages);
        model.put("messages", messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messageRepo.findByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        checkMessage(messages);
        model.put("messages", messages);
        return "main";
    }

}

