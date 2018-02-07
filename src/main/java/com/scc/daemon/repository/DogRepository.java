package com.scc.daemon.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.scc.daemon.model.AgriaDog;

@Repository
public interface DogRepository extends CrudRepository<AgriaDog,String>  {
    public AgriaDog findById(int id);
}
