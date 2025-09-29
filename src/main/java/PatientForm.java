import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a patient form with basic demographic information,
 * medical history, and patient management functionality.
 */
public abstract class PatientForm {
    
    // Patient demographic information
    protected String firstName;
    protected String lastName;
    protected LocalDate dateOfBirth;
    protected String gender;
    // added address components (keim)
    protected String street;
    protected String city;
    protected String state;
    protected String zipCode;
    // end of added address components (keim)
    protected String address;
    protected String phoneNumber;
    protected String email;
    protected String emergencyContact;
    protected String emergencyPhone;
    protected String insuranceProvider;
    protected String insurancePolicyNumber;
    
    // Patient picture path
    protected String patientPicturePath;
    
    // Medical history arrays
    protected List<String> medications;
    protected List<String> diagnoses;
    protected List<String> allergies;
    
    // Static storage for all patients (simulating database)
    protected static List<PatientForm> allPatients = new ArrayList<>();
    
    /**
     * Constructor for PatientForm
     */
    public PatientForm() {
        this.medications = new ArrayList<>();
        this.diagnoses = new ArrayList<>();
        this.allergies = new ArrayList<>();
    }
    
    /**
     * Constructor with basic patient information
     */
    public PatientForm(String firstName, String lastName, LocalDate dateOfBirth, String gender) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }
    
    // Getters and Setters for demographic information
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    // start of added address components (keim)
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getAddress() { return address; }
    public void setAddressSimple(String address) { this.address = address; }
    public void setAddress(String street, String city, String state, String zipCode) { 
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.address = street + ", " + city + ", " + state + " " + zipCode;
    }
    // end of added address components (keim)
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }
    
    public String getEmergencyPhone() { return emergencyPhone; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }
    
    public String getInsuranceProvider() { return insuranceProvider; }
    public void setInsuranceProvider(String insuranceProvider) { this.insuranceProvider = insuranceProvider; }
    
    public String getInsurancePolicyNumber() { return insurancePolicyNumber; }
    public void setInsurancePolicyNumber(String insurancePolicyNumber) { this.insurancePolicyNumber = insurancePolicyNumber; }
    
    public String getPatientPicturePath() { return patientPicturePath; }
    public void setPatientPicturePath(String patientPicturePath) { this.patientPicturePath = patientPicturePath; }
    
    // Methods for managing medications
    public List<String> getMedications() { return new ArrayList<>(medications); }
    public void addMedication(String medication) { 
        if (medication != null && !medication.trim().isEmpty()) {
            medications.add(medication.trim()); 
        }
    }
    public void removeMedication(String medication) { medications.remove(medication); }
    public void clearMedications() { medications.clear(); }
    
    // Methods for managing diagnoses
    public List<String> getDiagnoses() { return new ArrayList<>(diagnoses); }
    public void addDiagnosis(String diagnosis) { 
        if (diagnosis != null && !diagnosis.trim().isEmpty()) {
            diagnoses.add(diagnosis.trim()); 
        }
    }
    public void removeDiagnosis(String diagnosis) { diagnoses.remove(diagnosis); }
    public void clearDiagnoses() { diagnoses.clear(); }
    
    // Methods for managing allergies
    public List<String> getAllergies() { return new ArrayList<>(allergies); }
    public void addAllergy(String allergy) { 
        if (allergy != null && !allergy.trim().isEmpty()) {
            allergies.add(allergy.trim()); 
        }
    }
    public void removeAllergy(String allergy) { allergies.remove(allergy); }
    public void clearAllergies() { allergies.clear(); }
    
    /**
     * Save this patient to the static patient list
     */
    public void savePatient() {
        if (!allPatients.contains(this)) {
            allPatients.add(this);
        }
    }
    
    /**
     * Get all saved patients
     */
    public static List<PatientForm> getAllPatients() {
        return new ArrayList<>(allPatients);
    }
    
    /**
     * Abstract method to be implemented by concrete classes
     * for displaying patient information
     */
    public abstract String getPatientSummary();
    
    /**
     * Abstract method to be implemented by concrete classes
     * for validating patient data
     */
    public abstract boolean validatePatientData();
    
    /**
     * Get full patient name
     */
    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }
    
    /**
     * Calculate patient age based on date of birth
     */
    public int getAge() {
        if (dateOfBirth == null) return 0;
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
    
    @Override
    public String toString() {
        return "Patient: " + getFullName() + " (Age: " + getAge() + ")";
    }
}