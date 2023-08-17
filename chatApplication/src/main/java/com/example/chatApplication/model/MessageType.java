package com.example.chatApplication.model;

public enum MessageType {
     CHAT{
         @Override
         public String toString() {
             return "chat";
         }
     },LEAVE{
        @Override
        public String toString() {
            return "leave";
        }
    },JOIN{
        @Override
        public String toString() {
            return "join";
        }
    };


}
