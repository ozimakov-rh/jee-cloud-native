package com.redhat.demo.kudos.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.redhat.demo.kudos.entity.Kudos;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

@ApplicationScoped
public class FileSystemKudosRepoImpl implements KudosRepository {

    private final File dataFile;
    private final Gson gson = new Gson();

    public FileSystemKudosRepoImpl() {
        try {
            dataFile = File.createTempFile("kudos", ".json");
            System.out.println(" > " + dataFile.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized <T> T doPersisted(Function<List<Kudos>, T> fun) {
        try {
            // load list from file
            List<Kudos> dataList = gson.fromJson(new FileReader(dataFile), new TypeToken<ArrayList<Kudos>>() {
            }.getType());

            if (dataList == null) {
                dataList = new ArrayList<>();
            }

            // do stuff
            T result = fun.apply(dataList);

            // save the list back
            String jsonData = gson.toJson(dataList);
            Files.write(dataFile.toPath(), jsonData.getBytes(StandardCharsets.UTF_8));

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Kudos kudos) {
        doPersisted(kudosList -> {
            kudosList.add(kudos);
            return null;
        });
    }

    @Override
    public Stream<Kudos> stream() {
        return doPersisted(kudos -> kudos.stream());
    }

    @Override
    public List<Kudos> list() {
        return doPersisted(kudos -> kudos);
    }

    @Override
    public void deleteById(Long id) {
        doPersisted(kudosList -> kudosList.removeIf(kudos -> kudos.getId().equals(id)));
    }
}
