package com.klub.entrypoint.api.service;

import com.klub.entrypoint.api.helper.utils.CamelCaseUtils;
import com.klub.entrypoint.api.helper.utils.MethodInvocationUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public class BaseEntityCrudService<E, ID, RI extends JpaRepository<E, ID>> {

    protected final RI daoRepository;

    protected Class<E> clazz;

    public BaseEntityCrudService(RI daoRepository) {
        this.daoRepository = daoRepository;
    }

    public E save(E model){
        return daoRepository.save(model);
    }

    public E update(E model, Map<String , Object> data, List<String> exclude){
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            //TODO use a container for attribute to escape
            MethodInvocationUtils.set(
                    model.getClass(),
                    model,
                    CamelCaseUtils.fromSnakeCase(entry.getKey()),
                    entry.getValue());
        }
        return daoRepository.save(model);
    }

    public void delete(E model){
        daoRepository.delete(model);
    }

    public  void deleteBy(ID id){
        daoRepository.deleteById(id);
    }
}
