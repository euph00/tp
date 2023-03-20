package seedu.patientist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static seedu.patientist.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
//import static seedu.patientist.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.patientist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
//import static seedu.patientist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
//import static seedu.patientist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.patientist.testutil.Assert.assertThrows;
import static seedu.patientist.testutil.TypicalPersons.ALICE;
import static seedu.patientist.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

//import seedu.patientist.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = null; //new PersonBuilder().build(); TODO
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = null; //new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
        //.withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build(); TODO
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = null; //new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build(); TODO
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = null; //new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build(); TODO
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = null; //new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build(); TODO
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = null; //new PersonBuilder(ALICE).build(); TODO
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = null; //new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build(); TODO
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = null; //new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build(); TODO
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = null; //new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build(); TODO
        assertFalse(ALICE.equals(editedAlice));

        // different patientist -> returns false
        editedAlice = null; //new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build(); TODO
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = null; //new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build(); TODO
        assertFalse(ALICE.equals(editedAlice));
    }
}
