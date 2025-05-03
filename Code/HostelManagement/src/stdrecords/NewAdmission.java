package com.mycompany.stdrecords;
import java.util.*;

class Student {
    String id, name, email;
    boolean admitted, docs;
    int room;

    public Student(String name, String email, boolean admitted, boolean docs, int room) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.admitted = admitted;
        this.docs = docs;
        this.room = room;
    }
}

public class NewAdmission {

    static List<Student> students = new ArrayList<>();
    static boolean[] rooms = new boolean[5]; // 5 rooms, false = available

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Student Email: ");
        String email = sc.nextLine();

        System.out.print("Is the student institutionally admitted? (true/false): ");
        boolean admitted = sc.nextBoolean();

        System.out.print("Are documents submitted? (true/false): ");
        boolean docs = sc.nextBoolean();

        if (!admitted || !docs) {
            System.out.println("❌ Admission or documents missing.");
            return;
        }

        int assignedRoom = assignRoom();
        if (assignedRoom == -1) {
            System.out.println("⚠️ No rooms available. Student added to waitlist.");
            return;
        }

        Student s = new Student(name, email, admitted, docs, assignedRoom);
        students.add(s);

        System.out.println("✅ " + s.name + " admitted and assigned Room " + s.room);
    }

    static int assignRoom() {
        for (int i = 0; i < rooms.length; i++) {
            if (!rooms[i]) {
                rooms[i] = true;
                return i + 1; // Room numbers start from 1
            }
        }
        return -1; // No rooms available
    }
}

