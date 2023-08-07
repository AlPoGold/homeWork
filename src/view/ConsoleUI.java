package view;

import model.human.Gender;
import model.human.Human;
import presenter.Presenter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI implements View{

    private static final String INPUT_ERROR = "WRONG DATA";
    private Presenter presenter;
    private Scanner scanner;
    private boolean work;
    private Menu menu;

    public ConsoleUI(){
        presenter = new Presenter(this);
        scanner = new Scanner(System.in);
        work = true;
        menu = new Menu(this);
    }

    @Override
    public void start() {
        System.out.println("Welcome to GenealogicalTree app");

        while(work){
            System.out.println(menu.menu());
            System.out.print("You should choose the action: ");
            String choice = scanner.nextLine();

            try{
                int choiceInt = Integer.parseInt(choice);
                if(choiceInt < menu.getSize()) menu.execute(choiceInt);
                else errorInput();
            } catch (Exception e) {
                System.out.println(INPUT_ERROR);
            }



        }

    }

    public void finish() {
        System.out.println("Goodbye!");
        work = false;
    }

    public void errorInput() {
        System.out.println("You have entered the wrong number! Try again!");
    }

    public void sortByAge() {
        presenter.sortByAge();
        presenter.getInfo();

    }

    public void sortByName() {
        presenter.sortByName();
        presenter.getInfo();
    }

    public void getFamilyInfo() {
        presenter.getInfo();
    }

    public void addPerson() {
        //NAME
        System.out.print("Insert name: ");
        String name = scanner.nextLine();

        //LAST NAME
        System.out.print("Insert last name: ");
        String lastName = scanner.nextLine();



        //GENDER
        Gender gender = null;
        System.out.print("Insert gender of person(male or female): ");
        String genderString = scanner.nextLine();
        Gender[] genders = Gender.values();
        for (int i = 0; i < genders.length; i++) {
            String genderCheck = genders[i].toString();
            if(genderCheck.equalsIgnoreCase(genderString)){
                gender = genders[i];
            }

        }


        //TODO проверка на валидность


        //BIRTH DATE
        LocalDate birthDate = null;
        System.out.println("Insert date of birth in format dd.mm.yyyy: ");
        String birthdateString = scanner.nextLine();
        SimpleDateFormat formatBirth = new SimpleDateFormat();
        formatBirth.applyPattern("dd.MM.yyyy");
        try {
            Date birthdate= formatBirth.parse(birthdateString);
            birthDate = convertToLocalDate(birthdate);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //DEATH DATE
        LocalDate deathDate = null;

        System.out.println("Insert date of date in format dd.mm.yyyy\nPrint null if person is alive: ");
        String deathdateString = scanner.nextLine();
        SimpleDateFormat formatDeath = new SimpleDateFormat();
        formatDeath.applyPattern("dd.MM.yyyy");
        try {
            if(!deathdateString.equals("null")) {
                Date deathdate = formatDeath.parse(birthdateString);
                deathDate = convertToLocalDate(deathdate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // MOTHER
        Human mother = null;


        //FATHER

        Human father = null;

        //SPOUSE

        Human spouse = null;

        //CHILDREN
        List<Human> children = null;


        presenter.addHuman(name, lastName, gender, birthDate, deathDate, mother, father, spouse, children);
    }

    @Override
    public void printAnswer(String text) {
        System.out.println(text);

    }

    public LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
