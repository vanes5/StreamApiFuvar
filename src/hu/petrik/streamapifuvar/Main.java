package hu.petrik.streamapifuvar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class Main {
    private static List<Fuvar> list = new ArrayList<Fuvar>();

    public static void main(String[] args) {
        try{
            readFile("fuvar.csv");
            System.out.printf("%d utazás került feljegyzésre%n", list.size());
            System.out.printf("A 6185-ös taxi bevétele: %s$ volt %d fuvar alatt%n", getSumSalary(6185), countDrive(6185));
            System.out.printf("Összesen %s mérföldet tettek meg a taxisok%n", getSumTav());
            System.out.printf("A időben a leghosszabb fuvar adatai: %s%n", getLongestDrive());
            System.out.printf("Legbőkezűbb borravalójú fuvar: %s%n", getLargestTip());
            System.out.printf("A 4261-es taxi összesen %skm-t tett meg%n", getKm(4261));
            System.out.printf("Hibás sorok:%nDB: %d%nÖsszes időtartam: %d%nTeljes bevétel: %s%n", getHibas().count(), getHibas().mapToInt(Fuvar::getIdotartam).sum(), getHibas().mapToDouble(Fuvar::getDij).sum()+getHibas().mapToDouble(Fuvar::getBorravalo).sum());
            if (isDriverInLog(1452)){
                System.out.println("Szerepel az 1452-es azonosítójú taxis az adatok között");
            }
            else{
                System.out.println("Nem szerepel az 1452-es az adatok között");
            }
            System.out.printf("A 3 legrövidebb utazás: %s%n", getShortestDrives());
            System.out.printf("%d fuvar volt december 24.-én%n", getDrivesByDate(LocalDate.parse("2016-12-24")).count());
            System.out.printf("December 31.én %s arányban adtak borravalót", getRate());
        }
        catch (IOException e){
            System.err.println( e );
        }

    }

    private static String getRate(){
        int yes = Integer.parseInt(String.valueOf(getDrivesByDate(LocalDate.parse("2016-12-31"))
                    .filter(f -> f.getBorravalo() > 0)
                    .count()));
        int all = Integer.parseInt(String.valueOf(getDrivesByDate(LocalDate.parse("2016-12-31")).count()));
        return yes + ":" + all;
    }

    private static Stream<Fuvar> getDrivesByDate(LocalDate date){
        return list.stream()
                .filter(f -> f.getIndulas().toLocalDate().equals(date));

    }

    private static List<Fuvar> getShortestDrives(){
        return list.stream()
                .filter(f -> f.getIdotartam() != 0)
                .sorted(Comparator.comparingInt(Fuvar::getIdotartam))
                .limit(3)
                .toList();
    }

    private static Stream<Fuvar> getHibas(){
        return list.stream()
                .filter(f -> f.getTav() == 0.0 && f.getDij() > 0 && f.getIdotartam() > 0);
    }

    private static Fuvar getLargestTip() {
        return list.stream()
                .max((f1,f2) ->Float.compare( f1.getBorravalo()/f1.getDij(), f2.getBorravalo()/f2.getDij()))
                .get();
    }

    private static double getKm(int ID) {
        return getDriverLog(ID).mapToDouble(Fuvar::getTav).sum()/1.6;
    }

    private static Fuvar getLongestDrive() {
        return list.stream()
                .max(Comparator.comparingInt(Fuvar::getIdotartam))
                .get();
    }


    private static double getSumTav() {
        return list.stream()
                .mapToDouble(Fuvar::getTav)
                .sum();
    }

    private static double getSumSalary(int ID) {
        return getDriverLog(ID).mapToDouble(Fuvar::getDij).sum() + getDriverLog(ID).mapToDouble(Fuvar::getBorravalo).sum();
    }

    private static int countDrive(int ID) {
        return Integer.parseInt(String.valueOf(getDriverLog(ID).count()));
    }

    private static boolean isDriverInLog(int ID) {
        return list.stream().anyMatch(f -> f.getTaxi_azon() == ID);
    }

    private static Stream<Fuvar> getDriverLog(int ID) {
        return list.stream().filter(d -> d.getTaxi_azon() == ID);
    }

    private static void readFile(String file) throws IOException {
        BufferedReader br = new BufferedReader( new FileReader( file ) );
        br.readLine();
        String line = br.readLine();
        while (line != null && !line.isEmpty()) {
            list.add(new Fuvar(line));
            line = br.readLine();
        }
        br.close();
    }


}