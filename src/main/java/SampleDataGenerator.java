import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Utility class to create sample patient data for testing
 */
public class SampleDataGenerator {
    
    /**
     * Create sample patient data for testing purposes
     */
    public static void createSamplePatients() {
        PatientDataStorage storage = PatientDataStorage.getInstance();
        
        // Sample Patient 1
        PatientDataObject patient1 = new PatientDataObject();
        patient1.setFirstName("John");
        patient1.setLastName("Smith");
        patient1.setDateOfBirth(LocalDate.of(1985, 3, 15));
        patient1.setGender("Male");
        patient1.setAge(39);
        patient1.setPhoneNumber("555-0123");
        patient1.setEmail("john.smith@email.com");
        patient1.setInsuranceProvider("Blue Cross");
        patient1.setCheckInComplete(false);
        storage.savePatientData(patient1);
        System.out.println("Created patient: " + patient1.getFirstName() + " " + patient1.getLastName() + " (DOB: " + patient1.getDateOfBirth() + ")");
        
        // Sample Patient 2
        PatientDataObject patient2 = new PatientDataObject();
        patient2.setFirstName("Mary");
        patient2.setLastName("Johnson");
        patient2.setDateOfBirth(LocalDate.of(1972, 8, 22));
        patient2.setGender("Female");
        patient2.setAge(52);
        patient2.setPhoneNumber("555-0456");
        patient2.setEmail("mary.johnson@email.com");
        patient2.setInsuranceProvider("Aetna");
        patient2.setCheckInComplete(true);
        storage.savePatientData(patient2);
        System.out.println("Created patient: " + patient2.getFirstName() + " " + patient2.getLastName() + " (DOB: " + patient2.getDateOfBirth() + ")");
        
        // Sample Patient 3
        PatientDataObject patient3 = new PatientDataObject();
        patient3.setFirstName("Robert");
        patient3.setLastName("Davis");
        patient3.setDateOfBirth(LocalDate.of(1990, 12, 5));
        patient3.setGender("Male");
        patient3.setAge(33);
        patient3.setPhoneNumber("555-0789");
        patient3.setEmail("robert.davis@email.com");
        patient3.setInsuranceProvider("United Healthcare");
        patient3.setCheckInComplete(false);
        storage.savePatientData(patient3);
        System.out.println("Created patient: " + patient3.getFirstName() + " " + patient3.getLastName() + " (DOB: " + patient3.getDateOfBirth() + ")");
        
        // Sample Patient 4
        PatientDataObject patient4 = new PatientDataObject();
        patient4.setFirstName("Sarah");
        patient4.setLastName("Wilson");
        patient4.setDateOfBirth(LocalDate.of(1988, 6, 10));
        patient4.setGender("Female");
        patient4.setAge(36);
        patient4.setPhoneNumber("555-0321");
        patient4.setEmail("sarah.wilson@email.com");
        patient4.setInsuranceProvider("Cigna");
        patient4.setCurrentSymptoms("Regular checkup");
        patient4.setAppointmentDateTime(LocalDateTime.now().minusDays(1));
        patient4.setCheckInComplete(true);
        storage.savePatientData(patient4);
        System.out.println("Created patient: " + patient4.getFirstName() + " " + patient4.getLastName() + " (DOB: " + patient4.getDateOfBirth() + ")");
        
        System.out.println("✅ Created 4 sample patients for testing");
    }
}