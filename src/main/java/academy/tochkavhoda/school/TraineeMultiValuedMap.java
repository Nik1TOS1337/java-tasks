package academy.tochkavhoda.school;

import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import java.util.Collection;
import java.util.Collections;

public class TraineeMultiValuedMap {
    private final HashSetValuedHashMap<Integer, Trainee> multiValuedMap;

    public TraineeMultiValuedMap() {
        this.multiValuedMap = new HashSetValuedHashMap<>();
    }

    public boolean addTrainee(Trainee trainee) {
        return multiValuedMap.put(trainee.getRating(), trainee);
    }

    public boolean removeTrainee(Trainee trainee) {
        return multiValuedMap.removeMapping(trainee.getRating(), trainee);
    }

    public Collection<Trainee> getTraineesByRating(int rating) {
        if (!multiValuedMap.containsKey(rating)) {
            return Collections.emptyList();
        }
        return multiValuedMap.get(rating);
    }

    public boolean hasAnyBodyRating(int rating) {
        return multiValuedMap.containsKey(rating) && !multiValuedMap.get(rating).isEmpty();
    }

    public int getTraineesCount() {
        return multiValuedMap.size();
    }
}
