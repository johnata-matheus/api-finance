package br.com.finance.dtos.response;

import java.math.BigDecimal;

public record ValueResponseDto(int year, int month, int day, BigDecimal total) {}