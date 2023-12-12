package prog5.vizsga.beadando.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Repository;
import prog5.vizsga.beadando.entity.JobOpportunity;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Repository
public class JobOpportunityRepository implements MongoRepository<JobOpportunity, String> {

    @Override
    public <S extends JobOpportunity> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends JobOpportunity> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends JobOpportunity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends JobOpportunity> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends JobOpportunity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends JobOpportunity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends JobOpportunity> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends JobOpportunity> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends JobOpportunity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends JobOpportunity> S save(S entity) {
        return null;
    }

    @Override
    public <S extends JobOpportunity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<JobOpportunity> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<JobOpportunity> findAll() {
        return null;
    }

    @Override
    public List<JobOpportunity> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(JobOpportunity entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends JobOpportunity> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<JobOpportunity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<JobOpportunity> findAll(Pageable pageable) {
        return null;
    }
}

