package store;

import model.Student;

import java.util.*;

public class CacheManager {

    private Map<String, Student> cache;

    private int capacity;

    public CacheManager() {
    }

    public void setCapacity(int capacity) {
        cache = Collections.synchronizedMap(new LinkedHashMap<String, Student>(capacity));
        this.capacity = capacity;
    }

    public Optional<Student> create(Student student) {
        String id = UUID.randomUUID().toString();
        student.setId(id);

        if(cache.size() == capacity) //FIFO implementation
        {
            Map.Entry<String,Student> entry = cache.entrySet().iterator().next();
            cache.remove(entry.getKey());
        }

        cache.put(id, student);
        return Optional.ofNullable(student);
    }

    public Optional<Student> update(String id, Student student){
        if(cache.containsKey(id)){
            cache.put(id, student);
            student.setId(id);
            return Optional.ofNullable(student);
        }
        return Optional.empty();
    }

    public Optional<Student> get(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    public Set<Student> getAll() {
        return new HashSet<>(cache.values());
    }
    public boolean delete(String cacheKey) {
        return cache.remove(cacheKey)!=null;
    }

    public void clear() {
        cache.clear();
    }

}