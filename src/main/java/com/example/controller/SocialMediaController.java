package com.example.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

@Autowired    
private MessageService messageService;

@Autowired
private AccountService accountService;






    

    @PostMapping("register")
    public ResponseEntity registerAccount(@RequestBody Account ac)
    {
        if(accountService.registerAccount(ac.getUsername(), ac.getPassword())!=null){
            return ResponseEntity.status(200).body(accountService.registerAccount(ac.getUsername(),ac.getPassword()));
        }
        return ResponseEntity.status(409).body("");
    }


    @PostMapping("login")
    public ResponseEntity loginIntoAccount(@RequestBody Account ac)
    {
        if(accountService.login(ac.getUsername(), ac.getPassword())!=null)
        {
            return ResponseEntity.status(200).body(accountService.login(ac.getUsername(), ac.getPassword()));
        }
        return ResponseEntity.status(401).body("");
    }


    @PostMapping("messages")
    public ResponseEntity createMessage(@RequestBody Message message)
    {
        Message m =messageService.createNewMessage(message);
        if(m!=null)
        {
            return ResponseEntity.status(200).body(m);
        }
        return ResponseEntity.status(400).body("");
    }


    @GetMapping("messages")
    public ResponseEntity getAllMessages()
    {
        List<Message> mList=messageService.getAllMessages();

        return ResponseEntity.status(200).body(mList);
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity getMessagebyID(@PathVariable Integer messageId)
    {
        return ResponseEntity.status(200).body(messageService.getMessageByID(messageId));
    }

    @DeleteMapping("messages/{messageId}")
    public ResponseEntity deleteMessageByID(@PathVariable Integer messageId)
    {
        if(messageService.deleteMessage(messageId)==1)
        {
            return ResponseEntity.status(200).body("1");
        }
        else
        {
             return ResponseEntity.status(200).body("");
        }
       
        
    }

    @PatchMapping("messages/{messageId}")
    public ResponseEntity updateMessage(@PathVariable Integer messageId,@RequestBody String messageText)
    {
        String[] messageArray=messageText.split(":");
        String actualText=messageArray[1];
        messageArray=actualText.split("}");
        actualText=messageArray[0];
       messageArray=actualText.split("\"");
       if(messageArray.length<=1)
       {
         actualText=messageArray[0];
       }
      
        if(actualText.equals(" "))
        {
            actualText="";
        }
        else{
            actualText=messageText;
        } 
      


        if(messageService.updateMessageById(actualText, messageId)!=null)
        {
            return ResponseEntity.status(200).body(1);
        }
        return ResponseEntity.status(400).body("");
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity getMessagesByUser(@PathVariable Integer accountId)
    {
        return ResponseEntity.status(200).body(messageService.getMessageByUser(accountId));
    }
}
