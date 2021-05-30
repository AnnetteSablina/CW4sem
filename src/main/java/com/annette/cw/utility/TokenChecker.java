package com.annette.cw.utility;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class TokenChecker {
    private static final File tokenFile= new File ("tokenFile.txt");
    public static boolean isFileEmpty() {
       return tokenFile.length() == 0;
    }
    public static String readToken(){
        Scanner sc = null;
        try {
            sc = new Scanner(tokenFile);
        } catch (FileNotFoundException ignored) {}
        String token = null;
        while(Objects.requireNonNull(sc).hasNext()){
            token = sc.nextLine();
        }
        return token;
    }
    public static void writeToken(String token){
        try {
            FileWriter writer = new FileWriter(tokenFile);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(token);
            bufferWriter.close();
        }
        catch (IOException ignored) {}
    }
    public static void clearFile(){
        try {
            new PrintWriter(tokenFile).close();
        } catch (FileNotFoundException ignored) {}

    }
}
