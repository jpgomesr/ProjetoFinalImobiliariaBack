package com.hav.imobiliaria.controller.dto.auth;

import software.amazon.awssdk.services.s3.endpoints.internal.Value;

public record LoginRequestDTO(String email, String senha) {
}
