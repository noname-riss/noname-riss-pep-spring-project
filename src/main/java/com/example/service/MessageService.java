package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    private  MessageRepository messageRepository;


    @Autowired
    public MessageService(MessageRepository messageRepository)
    {
        this.messageRepository=messageRepository;
    }

    
    public List<Message> getAllMessages()
    {
        return messageRepository.findAll();
    }

    public Message getMessageByID(Integer messageId)
    {
            Optional<Message> om=messageRepository.findById(messageId);
            if(om.isPresent())
            {
                return om.get();
            }
            else{
                return null;
            }
            

    }

    public Message createNewMessage(Message m)
    {
        if(!(m.getMessageText().equals(""))&&m.getMessageText().length()<255&&messageRepository.getMessagesBypostedBy(m.getPostedBy()).size()!=0)
        {
        return messageRepository.save(m);
        }
        else{
            return null;
        }
        
    }

    public Integer deleteMessage(Integer messageId)
    {

        Optional<Message> m=messageRepository.findById(messageId);
        
            if(!m.isEmpty())
            {
                messageRepository.deleteById(messageId);
                 return 1;  
            }
           else 
           {
             return 0;  
           }
             
           
    }

    public Message updateMessageById(String messageText, Integer messageId)
    {
        
        if(!(messageText.isEmpty())&&messageText.length()<255)
        {
            Optional<Message> om=messageRepository.findById(messageId);
            
            if(om.isPresent())
            {
                Message m=om.get();
                m.setMessageText(messageText);
                 return messageRepository.save(m);
            }
        
        }
       return null;
    }

    public List<Message> getMessageByUser(Integer postedBy)
    {
        return messageRepository.getMessagesBypostedBy(postedBy);
    }


    
}
