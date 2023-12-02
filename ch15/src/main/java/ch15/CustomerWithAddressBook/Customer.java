package ch15.CustomerWithAddressBook;

import java.util.UUID;

record Customer(UUID id, PhoneBook phoneNumbers) {}
