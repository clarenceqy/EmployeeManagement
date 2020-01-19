package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Company {

    private StringProperty name;
    private StringProperty worker;

    public Company(String name,String worker){
        this.name = new SimpleStringProperty(name);
        this.worker = new SimpleStringProperty(worker);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getWorker() {
        return worker.get();
    }

    public StringProperty workerProperty() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker.set(worker);
    }
}
