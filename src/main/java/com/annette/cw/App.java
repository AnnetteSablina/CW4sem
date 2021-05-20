package com.annette.cw;

import com.annette.cw.ditch.*;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;

//
//public class App {
//    public static class Cat {
//
//        public String name; // имя
//        public int age; // возраст
//        public int color; // цвет
//
//        // Конструктор
//        public Cat(){
//
//        }
//
//        @Override
//        public String toString() {
//            return "Cat{" +
//                    "name='" + name + '\'' +
//                    ", age=" + age +
//                    ", color=" + color +
//                    '}';
//        }
//    }
//    public static void main(String[] args) throws IOException {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://cw4sem-server.herokuapp.com/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        LoginRequest request = new LoginRequest("jornwer", "qwertyuiop");
//
//        //просто гет запрос
//        example(retrofit);
//
//        //гет запрос с квери параметром
//        greeting(retrofit, "Annette");
//
//        //пост запрос с боди
//        AuthService service = retrofit.create(AuthService.class);
//        String token = service.login(request).execute().body().authenticationToken;
//
//        //гет запрос с хттп-хедером
//        getSelf(token);
//    }
//
//    private static void getSelf(String token) throws IOException {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://cw4sem-server.herokuapp.com/")
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .build();
//
//        AuthService service = retrofit.create(AuthService.class);
//        System.out.println(service.getSelf(token).execute().body());
//    }
//
//    private static void greeting(Retrofit retrofit, String param) throws IOException {
//        GreetingService s = retrofit.create(GreetingService.class);
//        System.out.println(s.getGreeting(param).execute().body().toString());
//    }
//
//    private static void example(Retrofit retrofit) throws IOException {
//        UserService service = retrofit.create(UserService.class);
//        for (User user : service.getUsers().execute().body()) {
//            System.out.println(user.toString());
//        }
//    }
//
//
//}
