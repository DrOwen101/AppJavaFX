- The first thing I noticed when perusing the code with the PatientForm and NewPatient classes. I think this is great as it keeps one class from having too many fields. It's a clean separation. I agree with that decision. However, in ```Main.java:193```, the JavaFX alert class is not imported, therefore the identifier is a little more verbose than it needs to be. Comorbidly, its used three times in the same line with most of it taken up by the full identifier. We can resolve this by importing the needed classes, then we can access ```AlertType``` as a sub under Alert. 
    - Add to top of file:
        - ```import javafx.scene.controls.Alert;```
    - Before:
        - ```javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);```
    - After:
        - ```Alert alert = new Alert(Alert.AlertType.INFORMATION);```

- To have a more cohesive and scalable program, we can add a theme manager that will handle styling for the elements. We can, for example, set an accent color and use this to color our buttons, have a background color for the Scene and cut down on a lot of the inline styling. I suggest this approach versus a CSS stylesheet approach as I feel like keeping track of two identifiers and separating them out this way would add complexity not lessen it and styling elements just based off what class they are (```Button```, ```Scene```, etc.) is inflexible.

- Another strength of the current codebase is the handling of data throughout the app. Java streams are used which make a lot of the sorting and other operations a lot more readable and maintainable as they are generic and flexible. 

- In ```NewPatient.java:141```  it returns a copy of the list which is good and important. This is consistent throughout the codebase. Same for ```PatientForm.java:135```. In ```PatientForm.java:33```, the functionality of the ```allPatients``` list, while decreasing coupling, can be moved into ```PatientDataStorage```. The various accessor methods can also be moved with little complexity in refactoring. Even though it’s static and belongs to the class so we’re not making multiple instances, it feels like its out of place in this class in this case.

- The biggest stinker however is the JSON implementation. In order to be scalable, we should implement a JSON library especially as they’re not too much overhead for a useful abstraction. Writing out that much is hard to read, and prone to typos and errors. It would be much better to use a JSON abstraction as we can add keys and have them syntactically taken care of for us to minimize the vulnerability to human error. The library might also have sanitization functionality as well, which can ensure silly bugs are kept to a minimum. This rationale is supported by the DRY principle. But this simple improvement does not fully implement DRY. See further for a better solution. For example:
    - Before: 
    ```java
    json.append("  \"patientId\": \"").append(patientId).append("\",\n");
    ```
    - After: `AnyJsonLibrary.addKey("patientId", patientId);`
    - An even better implementation can use a map to associate the key with the data and we can use the Jackson serializer to really follow this principle. For the fields in `PatientDataObject.java`, initialize them as maps (for example): 
        ```
        // build a root map to represent the root of our json
        Map<String, Object> root = new HashMap<>();
        root.put("patientId", this.patientId);
        root.put("savedTimestamp", this.savedTimestamp);
        
        Map<String, Object> personalInfo = new HashMap<>();
        personalInfo.put("firstName", this.firstName != null ? this.firstName : "");
        personalInfo.put("lastName", this.lastName != null ? this.lastName : "");
        personalInfo.put("dateOfBirth", this.dateOfBirth);
        personalInfo.put("gender", this.gender != null ? this.gender : "");
        personalInfo.put("age", this.age);
        root.put("personalInfo", personalInfo);

        // ... build other nested maps (contactInfo, insuranceInfo, appointmentInfo, etc.)

        // serialize
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

        return json;
        ```