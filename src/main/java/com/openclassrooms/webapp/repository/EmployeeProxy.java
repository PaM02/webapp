package com.openclassrooms.webapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.webapp.CustomProperties;
import com.openclassrooms.webapp.model.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmployeeProxy {

    @Autowired
    private CustomProperties props;

    /**
     * Get all employees
     * 
     * @return An iterable of all employees
     */

    public Iterable<Employee> getEmployees() {
        // grâce à notre objet CustomProperties, on récupère l’URL de l’API
        String baseApiUrl = props.getApiUrl();
        // on complète l’URL de l’API par le path de l'endpoint à joindre.
        String getEmployeesUrl = baseApiUrl + "/employees";
        // on instancie notre objet RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        /*
         * Ligne 32 : on appelle la méthode exchange en transmettant :
         * 
         * l’URL ;
         * 
         * la méthode HTTP (grâce à l’enum HttpMethod) ;
         * 
         * Null en lieu et place d’un objet HttpEntity, ainsi on laisse un comportement
         * par défaut ;
         * 
         * le type retour, ici je suis obligé d’utiliser un objet
         * ParameterizedTypeReference car /employees renvoie un objet
         * Iterable<Employee>. Mais si l’endpoint renvoie un objet simple, alors il
         * suffira d’indiquer <Object>.class
         */
        ResponseEntity<Iterable<Employee>> response = restTemplate.exchange(
                getEmployeesUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Iterable<Employee>>() {
                });

        log.debug("Get Employees call " + response.getStatusCode().toString());

        // Ligne 41 : on récupère notre objet Iterable<Employee> grâce à la méthode
        // getBody() de l’objet Response.
        return response.getBody();
    }

}