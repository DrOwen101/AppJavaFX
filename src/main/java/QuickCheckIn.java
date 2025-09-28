import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Non-GUI helper that encapsulates the quick check-in logic.
 * - Finds a patient by ID (or accepts an existing PatientDataObject)
 * - Updates minimal fields (timestamp, check-in flag, completion pct) using reflection so it
 *   works even if setters are not directly referenced in compile-time code.
 * - Persists changes via PatientDataStorage.savePatientData(...)
 *
 * Returns boolean indicating success (true = persisted).
 */
public final class QuickCheckIn {

    private static final Logger LOGGER = Logger.getLogger(QuickCheckIn.class.getName());

    private QuickCheckIn() { /* utility class */ }

    /**
     * Perform a quick check-in by patientId.
     * Synchronized on the storage instance to avoid concurrent update races.
     */
    public static boolean quickCheckInById(String patientId) {
        if (patientId == null || patientId.trim().isEmpty()) {
            LOGGER.warning("quickCheckInById called with null/empty id");
            return false;
        }

        PatientDataStorage storage = PatientDataStorage.getInstance();
        synchronized (storage) {
            Optional<PatientDataObject> opt = storage.findPatientById(patientId.trim());
            if (!opt.isPresent()) {
                LOGGER.info("Patient not found for quick check-in: " + patientId);
                return false;
            }
            return quickCheckInObject(opt.get(), storage);
        }
    }

    /**
     * Perform a quick check-in for an already-obtained PatientDataObject.
     * Uses reflection to update minimal fields and then persists via the provided storage.
     */
    public static boolean quickCheckInObject(PatientDataObject patient, PatientDataStorage storage) {
        if (patient == null || storage == null) {
            LOGGER.warning("quickCheckInObject called with null argument(s)");
            return false;
        }

        try {
            boolean mutated = applyQuickCheckInMutations(patient);
            if (!mutated) {
                LOGGER.warning("Patient model does not expose setters for quick check-in fields");
                return false;
            }

            // Persist change
            boolean saved = storage.savePatientData(patient);
            if (saved) {
                LOGGER.info("Quick check-in persisted for patient: " + patient.getPatientId());
            } else {
                LOGGER.warning("Failed to persist quick check-in for patient: " + patient.getPatientId());
            }
            return saved;
        } catch (Exception e) {
            LOGGER.severe("Exception during quick check-in: " + e.getMessage());
            return false;
        }
    }

    /**
     * Try to set minimal quick-checkin fields via reflection.
     * Returns true if at least one field was successfully set.
     *
     * Expected setters (any subset may exist):
     *   - setSavedTimestamp(java.time.ZonedDateTime)
     *   - setCheckInComplete(boolean)
     *   - setCompletionPercentage(int)
     */
    private static boolean applyQuickCheckInMutations(PatientDataObject patient) {
        boolean changed = false;
        Class<?> cls = patient.getClass();
        ZonedDateTime now = ZonedDateTime.now();

        try {
            Method m = cls.getMethod("setSavedTimestamp", ZonedDateTime.class);
            m.invoke(patient, now);
            changed = true;
        } catch (NoSuchMethodException ignored) {
            // setter not present — ok, continue
        } catch (Exception e) {
            LOGGER.fine("Failed to set saved timestamp via reflection: " + e.getMessage());
        }

        try {
            Method m = cls.getMethod("setCheckInComplete", boolean.class);
            m.invoke(patient, true);
            changed = true;
        } catch (NoSuchMethodException ignored) {
        } catch (Exception e) {
            LOGGER.fine("Failed to set check-in flag via reflection: " + e.getMessage());
        }

        try {
            Method m = cls.getMethod("setCompletionPercentage", int.class);
            m.invoke(patient, 100);
            changed = true;
        } catch (NoSuchMethodException ignored) {
        } catch (Exception e) {
            LOGGER.fine("Failed to set completion percentage via reflection: " + e.getMessage());
        }

        return changed;
    }
}