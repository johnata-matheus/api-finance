package br.com.finance.dtos.response;

public record TotalExpenseResponseDto(int year, int month, int day, double total) {}