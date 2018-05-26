
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import static java.lang.Character.isDigit;
import java.util.Objects;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author witek
 */
public class Person {

    private String name;
    private String surname;
    private String pesel;
    private String birthDate;
    private String gender;
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);

    @Override
    public String toString() {
        return "Person: {"
                + "Name: " + name + ", "
                + "Surname: " + surname + ", "
                + "Pesel: " + pesel + ", "
                + "Date of birth: " + birthDate
                + "}";
    }

    public void setName(String name) {
        String old = this.name;
        this.name = name;
        changeSupport.firePropertyChange("name", old, this.name);
    }

    public void setSurname(String surname) {
        String old = this.surname;
        this.surname = surname;
        changeSupport.firePropertyChange("surname", old, this.surname);
    }

    private void setGender(String gender) {
        String old = this.gender;
        this.gender = gender;
        changeSupport.firePropertyChange("gender", old, this.gender);
    }

    private void setBirthDate(String birthDate) {
        String old = this.birthDate;
        this.birthDate = birthDate;
        changeSupport.firePropertyChange("birthDate", old, this.birthDate);
    }

    public void setPesel(String pesel) {
        if (isValid(pesel)) {
            readBirthYear();
            readGender();
        }
        this.pesel = pesel;
        String old = this.pesel;
        changeSupport.firePropertyChange("pesel", old, this.pesel);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPesel() {
        return pesel;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    private void readGender() {
        if (Integer.parseInt(pesel.substring(9, 10)) % 2 == 0) {
            setGender("Female");
        } else {
            setGender("Male");
        }
    }

    private void readBirthYear() {
        int baseYear = Integer.parseInt(pesel.substring(0, 2));
        int baseMonth = Integer.parseInt(pesel.substring(2, 4));
        int baseDay = Integer.parseInt(pesel.substring(4, 6));
        if (baseMonth >= 80 && baseMonth <= 92) {
            baseYear = 1800 + baseYear;
        } else if (baseMonth >= 0 && baseMonth <= 12) {
            baseYear = 1900 + baseYear;
        } else if (baseMonth >= 20 && baseMonth <= 32) {
            baseYear = 2000 + baseYear;
        } else if (baseMonth >= 40 && baseMonth <= 52) {
            baseYear = 2100 + baseYear;
        } else if (baseMonth >= 60 && baseMonth <= 72) {
            baseYear = 2200 + baseYear;
        }
        baseMonth = (baseMonth - 1) % 12 + 1;

        setBirthDate(baseDay + ", " + baseMonth + ", " + baseYear);
    }

    private boolean isValid(String pesel) {
        if (pesel.length() != 11) {
            return false;
        }
        for (int i = 0; i < pesel.length(); i++) {
            if (!isDigit(pesel.charAt(i))) {
                return false;
            }
        }
        if (Integer.parseInt(pesel.substring(2, 4)) % 12 > 12) {
            return false;
        }
        if (Integer.parseInt(pesel.substring(4, 6)) > 31) {
            return false;
        }
        /*
         * Nie sprawdzam dalszej poprawności, 
         * żeby testowanie nie było zbyt uciążliwe
         */
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.pesel);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (!Objects.equals(this.pesel, other.pesel)) {
            return false;
        }
        return true;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}
