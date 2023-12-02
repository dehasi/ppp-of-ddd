package ch15.CustomerWithAddressBook;

import ch15.ValueObject;

import java.util.List;

class PhoneBook extends ValueObject<PhoneBook> {

    public final PhoneNumber homeNumber;
    public final PhoneNumber mobileNumber;
    public final PhoneNumber workNumber;

    PhoneBook(PhoneNumber homeNumber, PhoneNumber mobileNumber, PhoneNumber workNumber) {
        this.homeNumber = homeNumber;
        this.mobileNumber = mobileNumber;
        this.workNumber = workNumber;
    }

    @Override protected Iterable<Object> getAttributesToIncludeInEqualityCheck() {
        return List.of(homeNumber, mobileNumber, workNumber);
    }
}
