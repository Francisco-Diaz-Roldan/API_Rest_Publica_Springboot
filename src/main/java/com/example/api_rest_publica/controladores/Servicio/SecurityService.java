package com.example.api_rest_publica.controladores.Servicio;


import org.springframework.stereotype.Service;

@Service
public class SecurityService { public Boolean tokenDeValidacion(String token) { return (token.equals("t0k3n")); } }
