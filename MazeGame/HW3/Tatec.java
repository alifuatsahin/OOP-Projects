import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Collections;
import java.nio.charset.Charset;
import java.lang.Math;
import java.util.Random;
import java.util.*;

import static java.nio.file.Files.lines;


public class Tatec
{
    private static final int CORRECT_TOTAL_TOKEN_PER_STUDENT = 100;
    private static final String OUT_TATEC_UNHAPPY = "unhappyOutTATEC.txt";
    private static final String OUT_TATEC_ADMISSION = "admissionOutTATEC.txt";
    private static final String OUT_RAND_UNHAPPY = "unhappyOutRANDOM.txt";
    private static final String OUT_RAND_ADMISSION = "admissionOutRANDOM.txt";


    public static void main(String args[])
    {

        System.out.println(args.length);


        if(args.length < 4)
        {
            System.err.println("Not enough arguments!");
            return;
        }
        // File Paths
        Path courseFilePath = Paths.get(args[0]);
        Path studentIdFilePath = Paths.get(args[1]);
        Path tokenFilePath = Paths.get(args[2]);
        double h;



        try { h = Double.parseDouble(args[3]);}
        catch (NumberFormatException ex)
        {
            System.err.println("4th argument is not a double!");
            return;
        }

        // TODO: Rest is up to you
        Random random = new Random();

        ArrayList<String[]> courses = new ArrayList<>();
        ArrayList<String> students = new ArrayList<>();
        ArrayList<ArrayList<Integer>> tokens = new ArrayList<>();
        try {
            courses = Files.lines(courseFilePath)
                    .map(s -> s.replaceAll("\\s", ""))
                    .map(s -> s.split(","))
                    .collect(Collectors.toCollection(() -> new ArrayList<String[]>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            students = Files.lines(studentIdFilePath)
                    .map(s -> s.replaceAll("\\s", ""))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            tokens = Files.lines(tokenFilePath)
                    .map(s -> s.replaceAll("\\s", "")).map(s -> s.split(","))
                    .map(s -> Stream.of(s).map(number -> Integer.parseInt(number)).collect(Collectors.toCollection(ArrayList::new)))
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        tokens.stream()
                .forEach(s -> {
                    if (s.stream()
                            .reduce(0, Integer::sum) != CORRECT_TOTAL_TOKEN_PER_STUDENT) {
                        System.out.println("Tokens higher than 100");
                        System.exit(0);
                    }
                });



        final ArrayList<String[]> Courses = courses;
        final ArrayList<String> Students = students.stream().map(element -> ", " + element).collect(Collectors.toCollection(ArrayList::new));
        final ArrayList<ArrayList<Integer>> Token = tokens;

        Path tatecAddPath = Paths.get(OUT_TATEC_ADMISSION);
        Path tatecUnhappyPath = Paths.get(OUT_TATEC_UNHAPPY);
        Path randomAddPath = Paths.get(OUT_RAND_ADMISSION);
        Path randomUnhappyPath = Paths.get(OUT_RAND_UNHAPPY);

        ArrayList<String> TatecOut = new ArrayList<>();
        ArrayList<String> randOut = new ArrayList<>();
        Set<String> randIn = new HashSet<String>();
        ArrayList<ArrayList<Double>> unhappyOut = new ArrayList<>();
        ArrayList<ArrayList<Double>> randUnhappyOut = new ArrayList<>();
        ArrayList<String> unhappyOutList = new ArrayList<>();
        ArrayList<String> randUnhappyOutList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> xf = Token.stream()
                .map(element -> element.stream().filter(number -> number !=0).collect(Collectors.toCollection(ArrayList::new)))
                .map(element -> Stream.of(element.size()).collect(Collectors.toCollection(ArrayList::new)))
                .collect(Collectors.toCollection(ArrayList::new));
        IntStream.rangeClosed(0,xf.size()-1).forEach(number -> xf.get(number).add(number));




        try {
        Path tatecOutFilePath = Files.createFile(tatecAddPath);
        Path randOutFilePath = Files.createFile(randomAddPath);
        Path tatecUnhappyFilePath = Files.createFile(tatecUnhappyPath);
        Path randUnhappyFilePath = Files.createFile(randomUnhappyPath);
        IntStream.rangeClosed(0,Courses.size()-1).forEach(course -> {
            ArrayList<Integer> addTokens = Token.stream()
                    .map(s -> s.get(course))
                    .sorted(Collections.reverseOrder())
                    .limit(Integer.parseInt(Courses.get(course)[1]))
                    .filter(number -> number != 0)
                    .distinct()
                    .collect(Collectors.toCollection(ArrayList::new));
            ArrayList<Double> unhappiness = Token.stream()
                    .map(s -> Double.valueOf(s.get(course)))
                    .map(number -> (-100/h*Math.log(1-number/100) > 100) ? 100: -100/h*Math.log(1-number/100))
                    .collect(Collectors.toCollection(ArrayList::new));
            TatecOut.add(Courses.get(course)[0]);
            randOut.add(Courses.get(course)[0]);
            IntStream.rangeClosed(0,Token.size()-1)
                    .forEach(s -> {
                        addTokens.stream()
                                .forEach(token -> {
                                    if(Token.get(s).get(course) == token) {
                                        TatecOut.add(Students.get(s));
                                        unhappiness.set(s,Double.valueOf(0));
                                    }
                                });
                    });
            ArrayList<Double> randUnhappiness = Token.stream()
                    .map(s -> Double.valueOf(s.get(course)))
                    .map(number -> (-100/h*Math.log(1-number/100) > 100) ? 100: -100/h*Math.log(1-number/100))
                    .collect(Collectors.toCollection(ArrayList::new));
            xf.stream().filter(number -> number.get(0) != 0);
            IntStream.rangeClosed(0,Integer.parseInt(Courses.get(course)[1])-1)
                    .forEach(element -> {
                        randIn.add(Students.get(xf.get(random.nextInt(xf.size()-1)).get(1)));
                        randIn.stream().forEach(s -> xf.get(Students.indexOf(s)).set(0,xf.get(Students.indexOf(s)).get(0)-1));
                        randIn.stream().forEach(s -> randUnhappiness.set(Students.indexOf(s),Double.valueOf(0)));
                        xf.stream().filter(number -> number.get(0) != 0);
                    });
            randOut.addAll(randIn);
            randIn.clear();
            unhappyOut.add(unhappiness);
            randUnhappyOut.add(randUnhappiness);
            TatecOut.add("\n");
            randOut.add("\n");
        });
        IntStream.rangeClosed(0,Students.size()-1)
                .forEach(number -> unhappyOutList.add(Double.toString(unhappyOut.stream()
                        .map(s->s.get(number))
                        .collect(Collectors.summingDouble(Double::doubleValue)))));
        IntStream.rangeClosed(0,Students.size()-1)
                .forEach(number -> randUnhappyOutList.add(Double.toString(randUnhappyOut.stream()
                        .map(s->s.get(number))
                        .collect(Collectors.summingDouble(Double::doubleValue)))));
        Students.stream().forEach(element -> {
            if(!randOut.contains(element) && Token.get(Students.indexOf(element)).stream().filter(s -> s != 0) != null){
                unhappyOutList.set(Students.indexOf(element),Double.toString(Math.pow(Double.parseDouble(unhappyOutList.get(Students.indexOf(element))),2)));
            }
        });
        Students.stream().forEach(element -> {
            if(!randOut.contains(element) && Token.get(Students.indexOf(element)).stream().filter(s -> s != 0) != null){
                randUnhappyOutList.set(Students.indexOf(element),Double.toString(Math.pow(Double.parseDouble(randUnhappyOutList.get(Students.indexOf(element))),2)));
            }
        });
        unhappyOutList.add(0,Double.toString(unhappyOutList.stream()
                .map(number -> Double.parseDouble(number))
                .collect(Collectors.summingDouble(Double::doubleValue))/Students.size()));
        randUnhappyOutList.add(0,Double.toString(randUnhappyOutList.stream()
                .map(number -> Double.parseDouble(number))
                .collect(Collectors.summingDouble(Double::doubleValue))/Students.size()));
        Files.writeString(tatecOutFilePath, TatecOut.stream().collect(Collectors.joining("")), Charset.defaultCharset());
        Files.writeString(randOutFilePath, randOut.stream().collect(Collectors.joining("")), Charset.defaultCharset());
        Files.write(tatecUnhappyFilePath, unhappyOutList, Charset.defaultCharset());
        Files.write(randUnhappyFilePath, randUnhappyOutList, Charset.defaultCharset());
    } catch (IOException e) {
        e.printStackTrace();
    }






    }


}
