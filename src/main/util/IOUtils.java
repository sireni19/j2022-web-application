package main.util;

import java.io.*;

public class IOUtils {
    public static String readFile(String path) {
        File file = new File(path);
        if(!file.exists() || !file.isFile()){
            return null;
        } else if(!file.canRead()){
            System.err.println("No access to read " + path);
            return null;
        }
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            int byteOfData;
            StringBuilder builder = new StringBuilder(512);
            while ((byteOfData = is.read())!= -1){
                char ch = (char)byteOfData;
                builder.append(ch);
            };
            return builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if(is != null){
                    is.close();
                    System.out.println("Stream has been closed");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public static void writeFile(String path, String content){
        write(path, content, false);
    }

    public static void writeOrUpdateFile(String path, String content){
        write(path, content, true);
    }

    private static void write(String path, String content, boolean append){
        File file = new File(path);
        if(file.isDirectory()){
            throw new IllegalArgumentException("Folder is Specified");
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        OutputStream os = null;
        try {
            os = new FileOutputStream(file, append);
            for(char ch : content.toCharArray()){
                os.write((byte)ch);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(os != null){
                    os.close();
                    System.out.println("OutputStream is closed.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static String readFileWithBuff(String path){
        File file = new File(path);
        if(!file.exists() || !file.isFile()){
            return null;
        } else if(!file.canRead()){
            System.err.println("No access to read " + path);
            return null;
        }

        StringBuilder sb = new StringBuilder(512);
        try ( BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine())!= null){
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void writeWithBuffer(String path, String content, boolean append){
        File file = new File(path);
        if(file.isDirectory()){
            throw new IllegalArgumentException("Folder is Specified");
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try(BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file, append))){
            buffWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void writeOrUpdateFileWithBuff(String path, String content){
        writeWithBuffer(path, content, true);
    }

    public static void writeFileWithBuff(String path, String content){
        writeWithBuffer(path, content, false);
    }
}