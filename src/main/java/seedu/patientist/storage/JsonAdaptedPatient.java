package seedu.patientist.storage;

import static seedu.patientist.model.person.patient.Patient.PATIENT_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.patientist.commons.exceptions.IllegalValueException;
import seedu.patientist.model.person.Address;
import seedu.patientist.model.person.Email;
import seedu.patientist.model.person.IdNumber;
import seedu.patientist.model.person.Name;
import seedu.patientist.model.person.Person;
import seedu.patientist.model.person.Phone;
import seedu.patientist.model.person.patient.Patient;
import seedu.patientist.model.person.patient.PatientStatusDetails;
import seedu.patientist.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPatient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String id;
    private final String phone;
    private final String email;
    private final String address;
    private final String status;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPatient(@JsonProperty("name") String name, @JsonProperty("id") String id,
                              @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                              @JsonProperty("address") String address, @JsonProperty("status") String status,
                              @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.status = status;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPatient(Person source) {
        Patient patient = (Patient) source;
        name = patient.getName().fullName;
        phone = patient.getPhone().value;
        email = patient.getEmail().value;
        id = patient.getIdNumber().toString();
        address = patient.getAddress().value;
        status = patient.getPatientStatusDetails().getDetails();
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!IdNumber.isValidPid(id)) {
            throw new IllegalValueException(IdNumber.MESSAGE_CONSTRAINTS);
        }
        final IdNumber modelId = new IdNumber(id);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (modelTags.contains(PATIENT_TAG) && status != null) {
            final PatientStatusDetails modelDetails = new PatientStatusDetails(status);
            return new Patient(modelId, modelName, modelPhone, modelEmail, modelAddress, modelDetails, modelTags);
        }
        return new Patient(modelEmail, modelName, modelPhone, modelId, modelAddress, modelTags);
    }

}
