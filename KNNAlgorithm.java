import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class KNNAlgorithm {


    public static void main(String[] args) {
        
        ArrayList<Banknot> trainData_AL = new ArrayList<Banknot>(); //data banknote içerisindeki verileri tutmak için arraylist oluşturuluyor.
        String filePath = "C:\\Users\\berfi\\IdeaProjects\\Project1\\src\\data_banknote_authentication.txt" ;
        trainData_AL = readFile(filePath, trainData_AL);



       Banknot userObject = getInput();    //kullanıcı nesnesi oluşturuluyor.
       Scanner klavye = new Scanner(System.in);
       System.out.print("K Değerini Giriniz: ");
       int k = Integer.parseInt(klavye.next());



       int[] kkomsu = new int[k];
       double[] DM = distance(userObject, trainData_AL);    //uzaklık matrisi döndürülüyor.
       int tur = turBelirle(DM, k, trainData_AL, kkomsu);   //tür belirleniyor.

       userObject.setTur(tur);

       System.out.println();
       System.out.printf("%60s %60s %16s", "ÖZELLİKLER", "TÜR", "DISTANCE");
       System.out.println();

       for(int item:kkomsu){
           System.out.print(trainData_AL.get(item).toString());  //k adet komşunun özellikleri yazdırılıyor.
           System.out.format("%15f", DM[item]);
           System.out.println();


        }
       System.out.println();

       userObject.setTur(tur);
       System.out.println("Türü belirlenmek istenen banknot:");
       System.out.println("Tahmin edilen tür: " + userObject.getTur());
       System.out.println(userObject.toString());
       System.out.println();

       ArrayList<Banknot> testData_AL = new ArrayList<Banknot>();  //test data dosyası içindeki verileri tutmak için arraylist oluşturuluyor.
       String filePath2 = "C:\\Users\\berfi\\IdeaProjects\\Project1\\src\\data_test.txt";
       testData_AL = readFile(filePath2, testData_AL);

       System.out.print("K Değerini Giriniz: ");
       int k2 = Integer.parseInt(klavye.next());
       int correctValue = 0;
       int falseValue = 0;


       for (Banknot item:testData_AL){
           int[] kkomsu2 = new int[k2];
           double[] DM2 = distance(item, trainData_AL);  //uzaklık matrisi döndürülüyor.
           int gercekTur = item.getTur();
           int tur2 = turBelirle(DM2, k2, trainData_AL, kkomsu2);  //tür belirleniyor.
           if (item.getTur() == tur2){
               correctValue++;
           }else {
               falseValue++;
           }

           item.setTur(tur2);

           System.out.println();
           System.out.printf("%60s %60s %16s", "ÖZELLİKLER", "TÜR", "DISTANCE");
           System.out.println();

           for(int eleman:kkomsu2){
               System.out.print(trainData_AL.get(eleman).toString());  //k adet komşunun özellikleri yazdırılıyor.
               System.out.format("%15f", DM2[eleman]);
               System.out.println();

           }
           System.out.println();
           System.out.printf("Gerçek tür: %2s, Tahminlenen tür: %2s",gercekTur, tur2);
           System.out.println();
           System.out.println(item.toString());
       }


       int rate = testData_AL.size()/100;
       System.out.print("Başarı Oranı: %");
       System.out.println(correctValue/rate);
       System.out.println();

       System.out.println("Data Banknote Veriseti: ");
       System.out.println();
       for (int i=0;i<trainData_AL.size(); i++){ //data banknote veriseti yazdırılıyor.
           System.out.println(trainData_AL.get(i).toString());

        }


    }
    
    public static double [] distance(Banknot kullaniciNesne, ArrayList<Banknot> arrayList ){
        double[] DM = new double[arrayList.size()];
        //verilen arraylistteki her bir nesne ile test setindeki her bir nesne arasındaki uzaklık hesaplanıyor.
        double ilkVaryans = kullaniciNesne.getVaryans();
        double ilkCarpiklik = kullaniciNesne.getCarpiklik();
        double ilkBasiklik = kullaniciNesne.getBasiklik();
        double ilkEntropi = kullaniciNesne.getEntropi();
        
        for (int i = 0; i<arrayList.size(); i++){
            double sonVaryans = arrayList.get(i).getVaryans();
            double sonCarpiklik = arrayList.get(i).getCarpiklik();
            double sonBasiklik = arrayList.get(i).getBasiklik();
            double sonEntropi = arrayList.get(i).getEntropi();
            
            double farkV = Math.pow(sonVaryans-ilkVaryans, 2);
            double farkC = Math.pow(sonCarpiklik-ilkCarpiklik, 2);
            double farkB = Math.pow(sonBasiklik-ilkBasiklik, 2);
            double farkE = Math.pow(sonEntropi-ilkEntropi, 2);
            
            double distance = Math.sqrt(farkV + farkC + farkB + farkE);
            
            DM[i] = distance;
            
        }
      
        return DM;
        
    }

    public static int turBelirle(double[] DM, int k, ArrayList<Banknot> arrayList, int [] kkomsu ){
        double[] copyDM = DM.clone();
        Arrays.sort(copyDM);  // uzaklıklara göre sıralanıyor.
        int[] minIndexList = new int[k];


        for (int i = 0; i < k; i++) {   //en küçük k komşunun indexleri alınıyor.
            double min = copyDM[i];
            for (int j = 0; j < DM.length; j++) {
                if(min == DM[j]){
                    minIndexList[i] = j;
                }
            }
        }

        int tur0 = 0;       //en yakın k komşunun türlerine göre tür atama işlemi yapılıyor.
        int tur1 = 0;
        for(int item:minIndexList){
            int tur = arrayList.get(item).getTur();
            if(tur == 0){
                tur0 +=1;
            }
            else{
                tur1 += 1;
            }
        }
        for(int a =0; a<minIndexList.length; a++){
            kkomsu[a] = minIndexList[a];
        }

        if(tur1 > tur0)
            return 1;
        else if(tur0> tur1)
            return 0;
        else
            return arrayList.get(minIndexList[0]).getTur();


    }

    public static Banknot getInput(){
        //kullanıcıdan değerler alarak nesne oluşturuyor.

        Scanner klavye = new Scanner(System.in);
        System.out.print("Varyans Değerini Giriniz: ");
        double userVaryans = Double.parseDouble(klavye.next());
        System.out.print("Çarpıklık Değerini Giriniz: ");
        double userCarpiklik = Double.parseDouble(klavye.next());
        System.out.print("Basıklık Değerini Giriniz: ");
        double userBasiklik = Double.parseDouble(klavye.next());
        System.out.print("Entropi Değerini Giriniz: ");
        double userEntropi = Double.parseDouble(klavye.next());

        Banknot userObject = new Banknot(userVaryans, userCarpiklik, userBasiklik, userEntropi, 0);

        return userObject;
    }


    public static ArrayList<Banknot> readFile(String filePath, ArrayList<Banknot> arrayList) {
        //try catch blokları arasında dosyadaki veriler okunarak baknot nesnesi oluşturuluyor ve gönderilen arrayliste ekleniyor.
        try {
            File file1 = new File(filePath);
            Scanner file = new Scanner(file1);

            try {
                while (file.hasNext()){
                    String satir = file.next();
                    satir = satir.replace(",", " ");

                    String[] aliste = satir.split(" ", 5);
                    double varyans = Double.parseDouble(aliste[0]);
                    double carpiklik = Double.parseDouble(aliste[1]);
                    double basiklik = Double.parseDouble(aliste[2]);
                    double entropi = Double.parseDouble(aliste[3]);
                    int tur = Integer.parseInt(aliste[4]);

                    Banknot newBanknot = new Banknot(varyans, carpiklik, basiklik, entropi, tur);

                    arrayList.add(newBanknot);

                }
                file.close();
            } catch (NoSuchElementException e) {
                System.out.println("Dosyada Başka Eleman Kalmadı!");
            }

        } catch (FileNotFoundException e) {
            System.out.println("Dosya Bulunamadı!" + e.toString());
            System.exit(0);
        }
        return arrayList;
      }

}