package com.annette.cw.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.view.ExceptionWindow;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileMaker {

    public static void writeInfoIntoFile(List<String> info, Long id) {

        String filename = id.toString();
        Path file = Paths.get(filename + ".txt");
        try {
            Files.write(file, createMoreInfo(info), StandardCharsets.UTF_8);
        } catch (IOException e) {
            ExceptionWindow.makeLabel("Не удалось записать результат в файл");
        }
    }

    public static ArrayList<String> createMoreInfo(List<String> info) {
        ArrayList<String> someInfo = new ArrayList<>();
        someInfo.add(Controller.getInstance().getSelfUser().getFirstName());
        someInfo.add(Controller.getInstance().getSelfUser().getLastName());
        someInfo.add(Controller.getInstance().getSelfUser().getUsername());
        someInfo.add(Controller.getInstance().getSelfUser().getOrganization().toString());
        someInfo.add(Controller.getInstance().getChangeableDecision().getName());
        someInfo.addAll(info);
        return someInfo;
    }
}
