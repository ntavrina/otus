package otus.hw8;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String... args) {
        try {
            Musician co = new Musician();
            co.setName("Фредди Меркьюри");
            co.setAge(40);
            co.setSalary(100000);
            co.setMusicInstruments(new String[]{"гитара","балалайка","треугольник"});
            co.setPhones(Arrays.asList(new String[]{"+74992341567","+36876534123"}));

            System.out.println(MyJsonWriter.toJson(co).toString());

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    static class Musician {
        private String name;
        private int age;
        private double salary;
        private String[] musicInstruments;
        private List<String> phones;

        public void setAge(int age) {
            this.age = age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSalary(double salary) {
            this.salary = salary;
        }

        public void setMusicInstruments(String[] musicInstruments) {
            this.musicInstruments = musicInstruments;
        }

        public void setPhones(List<String> phones) {
            this.phones = phones;
        }
    }
}
