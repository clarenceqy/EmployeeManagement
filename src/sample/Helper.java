package sample;

import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;

public class Helper {


    public ArrayList<Company> readCompany(){

        ArrayList <Company>companydataArrayList = new ArrayList<>();

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        //File file = new File(s + "/DataFile/Companydata.csv");
        File file = new File(s + "\\DataFile\\Companydata.csv");


        BufferedReader reader = null;
        try {
            DataInputStream in=new DataInputStream(new FileInputStream(file));
            reader = new BufferedReader(new InputStreamReader(in));
            String tempString = reader.readLine();
            while ((tempString = reader.readLine()) != null) {
                String[] templist = tempString.split(",");
                companydataArrayList.add(new Company(templist[0],templist[1]));

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return companydataArrayList;
    }

    public ArrayList<Employee> readEmployee(){
        ArrayList <Employee> employeedataArrayList = new ArrayList<>();
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        //File file = new File(s + "/DataFile/Employee.csv");
        File file = new File(s + "\\DataFile\\Employee.csv");


        BufferedReader reader = null;
        try {
            DataInputStream in=new DataInputStream(new FileInputStream(file));
            reader = new BufferedReader(new InputStreamReader(in));

//            Skip the header
            String tempString = reader.readLine();

            while((tempString = reader.readLine()) != null){
                //First time block
                String [] tempstr = tempString.split(",");
                Employee employee = new Employee(tempstr[0]);
                String str = tempstr[3];
                employee.getSingleTimeBlock(0).addLocationElement(addline(str));

                for(int i = 1;i < 12;i++){
                    tempString = reader.readLine();
                    tempstr = tempString.split(",");
                    String str2 = tempstr[3];
                    employee.getSingleTimeBlock(i).addLocationElement(addline(str2));
                }
//Read remaining data

                for(int i = 1;i < 31;i++){
                    for(int j = 0;j < 12;j++){
                        tempString = reader.readLine();
                        tempstr = tempString.split(",");
                        String str2 = tempstr[3];
                        employee.getSingleTimeBlock(j).addLocationElement(addline(str2));
                    }
                }
                employeedataArrayList.add(employee);
//add new employee
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return employeedataArrayList;
    }

    public void addemployee(String name){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        //File file = new File(s + "/DataFile/Employee.csv");
        File file = new File(s + "\\DataFile\\Employee.csv");

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GB18030"));

            for (int i = 1;i < 32 ;i++){
                writer.write('\n'+name+','+i+','+"00:00-08:00"+','+'/');
                for(int j = 8;j < 18;j++){
                    String hour = "";
                    String hour2 = "";
                    if(j<10) {hour = "0"+j;}
                    if(j>=10) hour = Integer.toString(j);
                    if(j+1<10) {int temp = j+1;hour2 = "0"+temp;}
                    if(j+1>=10) hour2 = Integer.toString(j+1);

                    writer.write('\n'+name+','+i+','+hour+":00-"+hour2+":00"+','+"/");
                }
                writer.write('\n'+name+','+i+','+"18:00-23:59"+','+'/');
                writer.flush();
            }
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void writesaveddata(ArrayList<Employee> employeelist){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();

        //File file = new File(s + "/DataFile/Employee.csv");
        File file = new File(s + "\\DataFile\\Employee.csv");

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false),"GB18030"));
            writer.write("员工"+','+"时期"+','+"时间"+','+"地点");
            writer.flush();
            int size = employeelist.size();

            for(int i = 0;i < size; i++){
                Employee e = employeelist.get(i);
                String name = e.getName();
                for(int j = 1 ;j < 32;j++){
                    String day = Integer.toString(j);
                    for(int k = 0;k < 12;k++){
                        String time = e.getSingleTimeBlock(k).getTimename();
                        String location = e.getSingleTimeBlock(k).getLocationElement(j-1).getValue();
                        time = time.replaceAll("\n","");
                        location = location.replaceAll("\n","");
                        //System.out.println(name+','+day+','+time+','+location);
                        writer.write('\n'+name+','+day+','+time+','+location);
                    }
                }

            }
            writer.flush();
            writer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeaddcompany(Company company){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        //File file = new File(s + "/DataFile/Companydata.csv");
        File file = new File(s + "\\DataFile\\Companydata.csv");

        try{
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true),"GB18030"));
            writer.write("\n"+company.getName()+","+company.getWorker());
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writedeletecompany(ObservableList<Company> companylist){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        //File file = new File(s + "/DataFile/Companydata.csv");
        File file = new File(s + "\\DataFile\\Companydata.csv");

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,false),"GB18030"));
            writer.write("公司"+","+"名额");
            writer.flush();
            for(Company e : companylist){
                writer.write("\n"+e.getName()+","+"1");
            }
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public String addline(String str){
        String newstr = "";
        if(str.length()>5){
            for(int i = 0;i < 5;i++ ){
                newstr = newstr + str.charAt(i);
            }
            newstr = newstr +"\n";
            for(int i = 5;i < str.length();i++){
                newstr = newstr + str.charAt(i);
            }
        }
        else{
            newstr = str;
        }
        return newstr;
    }
}
