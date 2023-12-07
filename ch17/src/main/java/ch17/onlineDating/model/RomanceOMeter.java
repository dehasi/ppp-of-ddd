package ch17.onlineDating.model;

// Domain Service - part of Ubiquitous Language
class RomanceOMeter {


    // stateless - collaborators are allowed, though

    // behavior-only
    public CompatibilityRating assessCompatibility(LoveSeeker seeker1, LoveSeeker seeker2)
    {
        var rating = new CompatibilityRating();

        // orchestrate Entities:
        // compare dating history, blood type, lifestyle etc
        if (seeker1.bloodType() == seeker2.bloodType())
        {
            rating = rating.boost(new CompatibilityRating(250));
        }

        // ..

        // return another Domain Object (Value Object in this case)
        return rating;
    }

    private CompatibilityRating compatibilityRating(int value) {
        // ...
        return new CompatibilityRating();
    }
}
