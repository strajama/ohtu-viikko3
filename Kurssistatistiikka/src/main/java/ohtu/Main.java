package ohtu;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonSyntaxException;
import com.google.gson.JsonParser;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws IOException {
        // ÄLÄ laita githubiin omaa opiskelijanumeroasi
        String studentNr = "012345678";

        if (args.length > 0) {
            studentNr = args[0];
        }

        String subUrl = "https://studies.cs.helsinki.fi/courses/students/" + studentNr + "/submissions";
        String subBodytext = Request.Get(subUrl).execute().returnContent().asString();

        String courseUrl = "https://studies.cs.helsinki.fi/courses/courseinfo";
        String courseBodyText = Request.Get(courseUrl).execute().returnContent().asString();

//        System.out.println("json-muotoinen data:");
        //      System.out.println(subBodytext);
        //    System.out.println(courseBodyText);
        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(subBodytext, Submission[].class);
        Course[] courses = mapper.fromJson(courseBodyText, Course[].class);

        System.out.println("\nopiskelijanumero " + studentNr + "\n");
        for (Course course : courses) {
            boolean print = false;
            int te = 0;
            int tu = 0;

            for (Submission submission : subs) {
                if (submission.getCourse().equals(course.getName())) {
                    if (!print) {
                        System.out.println(course.printName());
                        print = true;
                    }
                    System.out.println("viikko " + submission.getWeek() + ":");
                    System.out.println(" tehtyjä tehtäviä " + submission.getExercises().length
                            + "/" + course.getExercises()[submission.getWeek()]
                            + " aikaa kului " + submission.getHours() + " tehdyt tehtävät: " + submission.printExercises());
                    te += submission.getExercises().length;
                    tu += submission.getHours();
                }
            }
            if (te + tu != 0) {
                System.out.println("\nyhteensä: " + te + "/" + course.countExercises() + " tehtävää ja " + tu
                        + " tuntia\n");
                try {
                    String statsResponse = Request.Get("https://studies.cs.helsinki.fi/courses/" + course.getName() + "/stats").execute().returnContent().asString();;
                    JsonParser parser = new JsonParser();
                    JsonObject parsittuData = parser.parse(statsResponse).getAsJsonObject();
                    int hour = 0;
                    int exercise = 0;
                    int student = 0;
                    for (String i : parsittuData.keySet()) {
                        JsonObject jsonData = parsittuData.getAsJsonObject(i);
                        hour += jsonData.get("hour_total").getAsInt();
                        exercise += jsonData.get("exercise_total").getAsInt();
                        student += jsonData.get("students").getAsInt();
                    }

                    System.out.println("kurssilla yhteensä " + student + " palautusta, "
                            + "palautettuja tehtäviä " + exercise + " kpl, aikaa käytetty yhteensä " + hour + " tuntia");

                } catch (JsonSyntaxException e) {
                    System.out.println("napattu\n");
                };
            }

        }

    }
}
/*

kurssilla yhteensä 425 palautusta, palautettuja tehtäviä 5186 kpl, aikaa käytetty yhteensä 3436 tuntia
opiskelijanumero 012345678

Web-palvelinohjelmointi Ruby on Rails syksy 2018
 
viikko 1:
 tehtyjä tehtäviä 10/11 aikaa kului 10 tehdyt tehtävät: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
viikko 6:
 tehtyjä tehtäviä 2/15 aikaa kului 2 tehdyt tehtävät: 1, 4

yhteensä: 12/103 tehtävää 12 tuntia
 
Ohjelmistotuotanto syksy 2018
 
viikko 1:
 tehtyjä tehtäviä 15/17 aikaa kului 20 tehdyt tehtävät: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
viikko 2:
 tehtyjä tehtäviä 11/13 aikaa kului 5 tehdyt tehtävät: 1, 2, 3, 4, 6, 7, 8, 10, 11, 12, 13

yhteensä: 26/30 tehtävää 25 tuntia

 */
