package br.com.finance.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ValidateReponseDto(
   @JsonProperty("is_valid") boolean isValid
) {}
