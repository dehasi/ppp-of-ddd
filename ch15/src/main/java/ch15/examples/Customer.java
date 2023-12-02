package ch15.examples;

import java.util.List;
import java.util.UUID;

record Customer(UUID id, List<PhoneNumber> phoneNumbers) {}
